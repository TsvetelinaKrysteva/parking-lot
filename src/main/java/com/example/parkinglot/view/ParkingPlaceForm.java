package com.example.parkinglot.view;


import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;
import java.util.function.Consumer;

public class ParkingPlaceForm extends FormLayout {


    private TextField number = new TextField("number");
    private ComboBox<ParkingZoneDto> zoneName = new ComboBox<>("zones");
    private ComboBox<CarDto> car = new ComboBox<>("cars");
    private Button save  = new Button("save");
    private Button delete  = new Button("delete");
    private Button clear  = new Button("clear");
    private Consumer<ParkingPlaceDto> onSaveHandler;
    private Consumer<ParkingPlaceDto> deleteHandler;
    private ParkingPlaceDto parkingPlace;
    private Binder<ParkingPlaceDto> binder = new BeanValidationBinder<>(ParkingPlaceDto.class);

    public ParkingPlaceForm(Consumer<ParkingPlaceDto> onSaveHandler, Consumer<ParkingPlaceDto> deleteHandler){
        binder.bindInstanceFields(this);
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(zoneName).bind(ParkingPlaceDto::getParkingZone, ParkingPlaceDto::setParkingZone);
        binder.forField(number).bind(ParkingPlaceDto::getNumber, ParkingPlaceDto::setNumber);
        binder.forField(car).bind(ParkingPlaceDto::getCar, ParkingPlaceDto::setCar);

        add(number,car,zoneName,getButtonsLayout());
        setParkingPlace(new ParkingPlaceDto());

    }

    public void setParkingPlace(ParkingPlaceDto parkingPlace){
        binder.readBean(parkingPlace);
        this.parkingPlace = parkingPlace;
    }

    public void setCar(List<CarDto> cars){
        this.car.setItems(cars);
        this.car.setItemLabelGenerator(CarDto::getPlateNumber);
    }

    public void setZones(List<ParkingZoneDto> zones){
        this.zoneName.setItems(zones);
        this.zoneName.setItemLabelGenerator(ParkingZoneDto::getName);
    }

    private HorizontalLayout getButtonsLayout(){
        HorizontalLayout buttons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> deleteHandler.accept(parkingPlace));
        clear.addClickListener(event -> binder.getFields().forEach(f -> f.clear()));
        buttons.add(save, delete, clear);
        return buttons;
    }

    private void validateAndSave(){
        try {
            binder.writeBean(parkingPlace);
            onSaveHandler.accept(parkingPlace);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
