package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingZoneService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import org.apache.commons.lang3.StringUtils;


@Route(value = "/parking-places")
@Theme()
@PageTitle("ParkingPlaces | Vaadin CRM")
public class ParkingPlaceView extends VerticalLayout {
    private Grid<ParkingPlaceDto> placeGrid = new Grid<>(ParkingPlaceDto.class);
    private TextField filter = new TextField("Filter by");
    private Button add = new Button("add place");
    private ParkingPlaceService parkingPlaceService;
    private ParkingZoneService parkingZoneService;
    private CarService carService;
    private ParkingPlaceForm form;


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
        placeGrid.addColumn("number");
        placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getCar()!=null ?
                parkingPlaceDto.getCar().getPlateNumber() : "").setHeader("car");
        placeGrid.addColumn(parkingPlaceDto -> parkingPlaceDto.getParkingZone().getName()).setHeader("zone");

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
        if(StringUtils.isBlank(parkingPlaceFilterDto.getNumber())){
            placeGrid.setItems(parkingPlaceService.getParkingPlaces());
        }else{
            placeGrid.setItems(parkingPlaceService.findByFilter(parkingPlaceFilterDto));
        }
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
}
