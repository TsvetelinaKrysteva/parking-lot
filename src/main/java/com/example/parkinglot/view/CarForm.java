package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
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

public class CarForm extends FormLayout {
    private TextField plateNumber = new TextField("plateNumber");


    private ComboBox<ParkingPlaceDto> places = new ComboBox<>("places");
    private Button save  = new Button("save");
    private Button delete  = new Button("delete");
    private Button clear  = new Button("clear");
    private Consumer<CarDto> onSaveHandler;
    private Consumer<CarDto> deleteHandler;
    private CarDto car;
    private Binder<CarDto> binder = new BeanValidationBinder<>(CarDto.class);

    public CarForm(Consumer<CarDto> onSaveHandler, Consumer<CarDto> deleteHandler){
        binder.bindInstanceFields(this);
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(places).bind(CarDto::getParkingPlaceDto, CarDto::setParkingPlaceDto);
        binder.forField(plateNumber).bind(CarDto::getPlateNumber, CarDto::setPlateNumber);
        add(plateNumber, places, getButtonsLayout());
        setCar(new CarDto());



    }

    public void setCar(CarDto car){
        binder.readBean(car);
        this.car = car;
    }
    public void setPlaces(List<ParkingPlaceDto> places){
        this.places.setItems(places);
        this.places.setItemLabelGenerator(ParkingPlaceDto::getNumber);
    }

    private HorizontalLayout getButtonsLayout(){
        HorizontalLayout buttons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> deleteHandler.accept(car));
        clear.addClickListener(event -> binder.getFields().forEach(f->f.clear()));

        buttons.add(save, delete, clear);
        return buttons;

    }



    private void validateAndSave(){
        try {
            binder.writeBean(car);
            onSaveHandler.accept(car);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

}
