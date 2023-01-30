package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;

import com.example.parkinglot.presenter.CarPresenter;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


@Route(value = "/cars", layout = MainLayout.class)

@PageTitle("Car | Vaadin CRM")
public class CarView extends VerticalLayout {
    private Grid<CarDto> carGrid = new Grid<>(CarDto.class);
    private Button add = new Button("add car");
    private TextField filter = new TextField("Filter by plate number");
    private CarForm form;

    private CarPresenter carPresenter;


    public CarView(CarPresenter carPresenter){
        this.carPresenter = carPresenter;
        this.carPresenter.setCarView(this);

        configure();

        form = new CarForm(this::saveCar, this::deleteCar);
        carPresenter.placeDtos(form);
        add.addClickListener(event -> form.setCar(new CarDto()));
        carGrid.asSingleSelect().addValueChangeListener(event -> form.setCar(event.getValue()));
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        filter.addValueChangeListener(event -> {
            onFilterChange();
        });
        add(filter, add, getContent());
        carPresenter.onViewInit();

    }


    private void saveCar(CarDto carDto){
        carPresenter.onSaveCar(carDto);


    }
    private void deleteCar(CarDto carDto){
        carPresenter.onDeleteCar(carDto);
    }
    private void configure(){
        setSizeFull();
        carGrid.setSizeFull();
        carGrid.removeAllColumns();
        carGrid.addColumn("plateNumber");
        carGrid.addColumn(car -> car.getParkingPlaceDto().getNumber()).setHeader("Place");
        carGrid.addColumn(car -> car.getParkingZoneDot().getName()).setHeader("Zone");

    }
    public void updateGrid(List<CarDto> carDtos){
        carGrid.setItems(carDtos);
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
            carPresenter.onFilterChange(carFilterDto);
            return;
        }
        carFilterDto.setPlateNumber(plate);
        carPresenter.onFilterChange(carFilterDto);

    }
    public void showErrorMessage(String message) {
        Notification.show(message , 5000, Notification.Position.MIDDLE);
    }
}
