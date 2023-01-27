package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;

import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
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


@Route(value = "/cars", layout = MainLayout.class)

@PageTitle("Car | Vaadin CRM")
public class CarView extends VerticalLayout {
    private Grid<CarDto> carGrid = new Grid<>(CarDto.class);
    private CarService carService;
    private ParkingPlaceService parkingPlaceService;
    private Button add = new Button("add car");
    private TextField filter = new TextField("Filter by plate number");
    private CarForm form;


    public CarView(ParkingPlaceService parkingPlaceService, CarService carService){
        this.parkingPlaceService = parkingPlaceService;
        this.carService = carService;
        configure();
        carGrid.setItems(carService.getAllCars());
        form = new CarForm(parkingPlaceService.getParkingPlaces(), this::saveCar, this::deleteCar);
        add.addClickListener(event -> form.setCar(new CarDto()));
        carGrid.asSingleSelect().addValueChangeListener(event -> form.setCar(event.getValue()));
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        filter.addValueChangeListener(event -> {
            onFilterChange();
        });
        add(filter, add, getContent());
        updateGrid(new CarFilterDto());

    }


    private void saveCar(CarDto carDto){
        if(carDto.getId()!=null){
            carService.updateCar(carDto);
            updateGrid(new CarFilterDto());
        }else{
            carService.createCar(carDto);
            updateGrid(new CarFilterDto());
        }

    }
    private void deleteCar(CarDto carDto){
        carService.deleteCar(carDto.getId());
        updateGrid(new CarFilterDto());
    }
    private void configure(){
        setSizeFull();
        carGrid.setSizeFull();
        carGrid.removeAllColumns();
        carGrid.addColumn("plateNumber");
        carGrid.addColumn(car -> car.getParkingPlaceDto().getNumber()).setHeader("Place");
        carGrid.addColumn(car -> car.getParkingZoneDot().getName()).setHeader("Zone");

    }
    public void updateGrid(CarFilterDto carFilterDto){
        if (StringUtils.isBlank(carFilterDto.getPlateNumber())) {
            carGrid.setItems(carService.getAllCars());
        } else {
            carGrid.setItems(carService.findByFilter(carFilterDto));
        }
    }
    private HorizontalLayout getContent() {

        HorizontalLayout content = new HorizontalLayout();
        form.setWidth("30em");
        content.add(carGrid, form);

        content.setSizeFull();
        return content;
    }
    private void onFilterChange(){
        CarFilterDto carFilterDto = new CarFilterDto();
        String plate = filter.getValue();
        if(StringUtils.isBlank(plate)){
            updateGrid(new CarFilterDto());
            return;
        }
        carFilterDto.setPlateNumber(plate);
        updateGrid(carFilterDto);

    }
}
