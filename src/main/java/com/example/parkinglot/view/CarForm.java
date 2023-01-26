package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
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

public class CarForm extends FormLayout {
    private TextField plateNumber = new TextField("plateNumber");
//    private TextField parkingPlaceNumber = new TextField("placeNumber");
//    private TextField parkingZoneName = new TextField("zoneName");

    private ComboBox<ParkingPlaceDto> places = new ComboBox<>("places");
    private Button save  = new Button("save");
    private Button delete  = new Button("delete");
    private Button clear  = new Button("clear");
    private Consumer<CarDto> onSaveHandler;
    private Consumer<CarDto> deleteHandler;
    private CarDto car;
    private Binder<CarDto> binder = new Binder<>(CarDto.class);

    public CarForm(List<ParkingPlaceDto> placesList, Consumer<CarDto> onSaveHandler, Consumer<CarDto> deleteHandler){
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(places).bind(CarDto::getParkingPlaceDto, CarDto::setParkingPlaceDto);
        binder.forField(plateNumber).bind(CarDto::getPlateNumber, CarDto::setPlateNumber);
        places.setItems(placesList);
        places.setItemLabelGenerator(ParkingPlaceDto::getNumber);
        add(plateNumber, places, getButtonsLayout());
        setCar(new CarDto());



    }

    public void setCar(CarDto car){
        binder.readBean(car);
        this.car = car;
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
