package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.presenter.ParkingPlacePresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.function.Consumer;


@Route(value = "/parking-places", layout = MainLayout.class)
@PageTitle("ParkingPlaces | Vaadin CRM")
public class ParkingPlaceView extends VerticalLayout {
    private Grid<ParkingPlaceDto> placeGrid = new Grid<>(ParkingPlaceDto.class);


    private Button add = new Button("add place");
    private ParkingPlacePresenter parkingPlacePresenter;
    private ParkingPlaceForm form;
    private ParkingPlaceFilter gridFilter;


    public ParkingPlaceView(ParkingPlacePresenter parkingPlacePresenter)
    {
        this.parkingPlacePresenter = parkingPlacePresenter;
        this.parkingPlacePresenter.setParkingPlaceView(this);


        configure();
        parkingPlacePresenter.onViewInit();



       form = new ParkingPlaceForm(this::savePlace, this::deletePlace);
       parkingPlacePresenter.carDtos(form);
       parkingPlacePresenter.zoneDtos(form);
       add.addClickListener(event -> form.setParkingPlace(new ParkingPlaceDto()));
       placeGrid.asSingleSelect().addValueChangeListener(event -> form.setParkingPlace(event.getValue()));
       add(add, getContent());
       parkingPlacePresenter.onViewInit();

    }

    private void savePlace(ParkingPlaceDto parkingPlaceDto){
        parkingPlacePresenter.onSavePlace(parkingPlaceDto);
    }

    private  void deletePlace(ParkingPlaceDto parkingPlaceDto){
        parkingPlacePresenter.onDeletePlace(parkingPlaceDto);
    }

    private void configure(){
        setSizeFull();
        placeGrid.setSizeFull();
        placeGrid.removeAllColumns();

        Grid.Column<ParkingPlaceDto> numberColumn = placeGrid.addColumn("number");

        Grid.Column<ParkingPlaceDto> car = placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getCar()!=null ?
                parkingPlaceDto.getCar().getPlateNumber() : "").setHeader("car");

        Grid.Column<ParkingPlaceDto> zone = placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getParkingZone().getName()).setHeader("zone");

        ListDataProvider<ParkingPlaceDto> listDataProvider = new ListDataProvider<>(new ArrayList<>());
        gridFilter = new ParkingPlaceFilter();
        gridFilter.setDataProvider(listDataProvider);
        placeGrid.setDataProvider(listDataProvider);

        HeaderRow header = placeGrid.appendHeaderRow();
        header.getCell(numberColumn).setComponent(createFilterHeaderCell("", number -> this.gridFilter.setNumber(number)));
        header.getCell(car).setComponent(createFilterHeaderCell("", plateNumber -> this.gridFilter.setPlateNumber(plateNumber)));
        header.getCell(zone).setComponent(createFilterHeaderCell("", zoneName -> this.gridFilter.setZoneName(zoneName)));


    }


    private static Component createFilterHeaderCell(String label, Consumer<String> valueChangeListener){
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.addValueChangeListener( event -> valueChangeListener.accept(event.getValue()));
        VerticalLayout layout = new VerticalLayout(textField);
        return layout;
    }


    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout();
        form.setWidth("30em");
        content.add(placeGrid, form);
        content.setSizeFull();
        return content;
    }

    public void updateGrid(ListDataProvider<ParkingPlaceDto> listDataProvider){

        placeGrid.setDataProvider(listDataProvider);
        gridFilter.setDataProvider(listDataProvider);
    }

    public void showErrorMessage(String message) {
        Notification.show(message , 5000, Notification.Position.MIDDLE);
    }

    private static class ParkingPlaceFilter {

        private ListDataProvider<ParkingPlaceDto> dataProvider;
        private String number;
        private String plateNumber;
        private String zoneName;

        public void setNumber(String number) {
            this.number = number;
            this.dataProvider.refreshAll();
        }
        public void setPlateNumber(String plateNumber){
            this.plateNumber = plateNumber;
            this.dataProvider.refreshAll();
        }
        public void setZoneName(String zoneName){
            this.zoneName = zoneName;
            this.dataProvider.refreshAll();
        }

        public void setDataProvider(ListDataProvider<ParkingPlaceDto> dataProvider) {
            this.dataProvider = dataProvider;
            this.dataProvider.addFilter(this::test);
        }


        private boolean test(ParkingPlaceDto parkingPlace){
            boolean matchesPlateNumber = true;
            boolean matchesNumber = matches(parkingPlace.getNumber(), number);
            boolean matchesZoneName = matches(parkingPlace.getParkingZone().getName(), zoneName);
            if(!StringUtils.isBlank(plateNumber)){
               matchesPlateNumber = parkingPlace.getCar() == null ? false : matches(parkingPlace.getCar().getPlateNumber(), plateNumber);
            }


            return matchesNumber && matchesPlateNumber && matchesZoneName;
        }

        private boolean matches(String value, String searchTerm){
            if(StringUtils.isBlank(searchTerm)){
                return true;
            }
           if(value!=null){
               return value.toLowerCase().contains(searchTerm.toLowerCase());
           }
            return true;
        }



    }

}


