package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingZoneService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.function.Consumer;


@Route(value = "/parking-places", layout = MainLayout.class)
@PageTitle("ParkingPlaces | Vaadin CRM")
public class ParkingPlaceView extends VerticalLayout {
    private Grid<ParkingPlaceDto> placeGrid = new Grid<>(ParkingPlaceDto.class);
    private TextField filter = new TextField("Filter by");
    private Button add = new Button("add place");
    private ParkingPlaceService parkingPlaceService;
    private ParkingZoneService parkingZoneService;
    private CarService carService;
    private ParkingPlaceForm form;
    private ParkingPlaceFilter gridFilter;


    public ParkingPlaceView(ParkingPlaceService parkingPlaceService, ParkingZoneService parkingZoneService, CarService carService)
    {
        this.parkingPlaceService = parkingPlaceService;
        this.parkingZoneService = parkingZoneService;
        this.carService = carService;
        configure();


        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> {
            onFilterChange();
        });

       form = new ParkingPlaceForm(parkingZoneService.getParkingZones(), carService.getAllCars(), this::savePlace, this::deletePlace);
       add.addClickListener(event -> form.setParkingPlace(new ParkingPlaceDto()));
       placeGrid.asSingleSelect().addValueChangeListener(event -> form.setParkingPlace(event.getValue()));
       add(filter, add, getContent());
       updateGrid(new ParkingPlaceFilterDto());

    }

    private void savePlace(ParkingPlaceDto parkingPlaceDto){
        if(parkingPlaceDto.getId()!=null){
            parkingPlaceService.updateParkingPlace(parkingPlaceDto);
        }else{
            parkingPlaceService.createParkingPlace(parkingPlaceDto);
        }
        updateGrid(new ParkingPlaceFilterDto());
    }

    private  void deletePlace(ParkingPlaceDto parkingPlaceDto){
        parkingPlaceService.deleteParkingPlace(parkingPlaceDto.getId());
        updateGrid(new ParkingPlaceFilterDto());
    }

    private void configure(){
        setSizeFull();
        placeGrid.setSizeFull();
        placeGrid.removeAllColumns();

        Grid.Column<ParkingPlaceDto> numberColumn = placeGrid.addColumn("number");

        Grid.Column<ParkingPlaceDto> car = placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getCar()!=null ?
                parkingPlaceDto.getCar().getPlateNumber() : "").setHeader("car");

        placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getParkingZone().getName()).setHeader("zone");
        ListDataProvider<ParkingPlaceDto> listDataProvider = new ListDataProvider<>(new ArrayList<>());
        gridFilter = new ParkingPlaceFilter();
        gridFilter.setDataProvider(listDataProvider);
        placeGrid.setDataProvider(listDataProvider);

        HeaderRow header = placeGrid.appendHeaderRow();
        header.getCell(numberColumn).setComponent(createFilterHeaderCell("", number -> this.gridFilter.setNumber(number)));
        header.getCell(car).setComponent(createFilterHeaderCell("", plateNumber -> this.gridFilter.setPlateNumber(plateNumber)));


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

    private void updateGrid(ParkingPlaceFilterDto parkingPlaceFilterDto){
//        String numberToString = Integer.toString(parkingPlaceFilterDto.getNumber());
        ListDataProvider<ParkingPlaceDto> listDataProvider;
        if(StringUtils.isBlank(parkingPlaceFilterDto.getNumber())){
            listDataProvider = DataProvider.ofCollection(parkingPlaceService.getParkingPlaces());
        }else{
            listDataProvider = DataProvider.ofCollection(parkingPlaceService.findByFilter(parkingPlaceFilterDto));
        }
        placeGrid.setDataProvider(listDataProvider);
        gridFilter.setDataProvider(listDataProvider);
    }

    private void onFilterChange(){
        ParkingPlaceFilterDto parkingPlaceFilterDto = new ParkingPlaceFilterDto();
//        String number = Integer.toString(parkingPlaceFilterDto.getNumber());
        if(StringUtils.isBlank(parkingPlaceFilterDto.getNumber())){
            updateGrid(new ParkingPlaceFilterDto());
            return;
        }
        parkingPlaceFilterDto.setNumber(parkingPlaceFilterDto.getNumber());
        updateGrid(parkingPlaceFilterDto);
    }

    private static class ParkingPlaceFilter {

        private ListDataProvider<ParkingPlaceDto> dataProvider;
        private String number;
        private String plateNumber;

        public void setNumber(String number) {
            this.number = number;
            this.dataProvider.refreshAll();
        }
        public void setPlateNumber(String plateNumber){
            this.plateNumber = plateNumber;
            this.dataProvider.refreshAll();
        }

        public void setDataProvider(ListDataProvider<ParkingPlaceDto> dataProvider) {
            this.dataProvider = dataProvider;
            this.dataProvider.addFilter(this::test);
        }


        private boolean test(ParkingPlaceDto parkingPlace){
            boolean matchesPlateNumber = true;
            boolean matchesNumber = matches(parkingPlace.getNumber(), number);
            if(!StringUtils.isBlank(plateNumber)){

               matchesPlateNumber = parkingPlace.getCar() == null ? false : matches(parkingPlace.getCar().getPlateNumber(), plateNumber);
            }


            return matchesNumber && matchesPlateNumber;
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
