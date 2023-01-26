package com.example.parkinglot.view;


import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;
import java.util.function.Consumer;

public class ParkingPlaceForm extends FormLayout {


    private TextField number = new TextField("number");
    private ComboBox<ParkingZoneDto> zoneName = new ComboBox<>("zones");
    private Button save  = new Button("save");
    private Button delete  = new Button("delete");
    private Button clear  = new Button("clear");
    private Consumer<ParkingPlaceDto> onSaveHandler;
    private Consumer<ParkingPlaceDto> deleteHandler;
    private ParkingPlaceDto parkingPlace;
    private Binder<ParkingPlaceDto> binder = new Binder<>(ParkingPlaceDto.class);

    public ParkingPlaceForm(List<ParkingZoneDto> zones, Consumer<ParkingPlaceDto> onSaveHandler, Consumer<ParkingPlaceDto> deleteHandler){
//        binder.bindInstanceFields(this);
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(zoneName).bind(ParkingPlaceDto::getParkingZone, ParkingPlaceDto::setParkingZone);
        binder.forField(number).bind(ParkingPlaceDto::getNumber, ParkingPlaceDto::setNumber);
        zoneName.setItems(zones);
        zoneName.setItemLabelGenerator(ParkingZoneDto::getName);
        setParkingPlace(new ParkingPlaceDto());
        add(number,zoneName,getButtonsLayout());


    }

    public void setParkingPlace(ParkingPlaceDto parkingPlace){
        binder.readBean(parkingPlace);
        this.parkingPlace = parkingPlace;
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
