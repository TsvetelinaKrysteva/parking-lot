package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.model.entity.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.selection.MultiSelect;

import java.util.List;
import java.util.function.Consumer;

public class UserForm extends FormLayout {
    private Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
    private Button save = new Button("save");
    private Button clear = new Button("clear");
    private Button delete = new Button("delete");
    private TextField name = new TextField("name");
    MultiSelectListBox<CarDto> cars = new MultiSelectListBox<>();
//    private ComboBox<CarDto> cars = new ComboBox<>("cars");

    private UserDto userDto;
    private Consumer<UserDto> onSaveHandler;
    private Consumer<UserDto> deleteHandler;

    public UserForm(Consumer<UserDto> onSaveHandler, Consumer<UserDto> deleteHandler){
        binder.bindInstanceFields(this);
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(name).bind(UserDto::getName, UserDto::setName);
        binder.forField(cars).bind(UserDto::getCar, UserDto::setCar);
        add(name, cars, getButtonsLayout());
        setUser(new UserDto());
    }


    public void setUser(UserDto userDto){
        binder.readBean(userDto);
        this.userDto = userDto;
    }
    public void setCars(List<CarDto> cars){
        this.cars.setItems(cars);

//        this.cars.setItemLabelGenerator(CarDto::getPlateNumber);

    }
    private void validateAndSave(){
        try {
            binder.writeBean(userDto);
            onSaveHandler.accept(userDto);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
    private HorizontalLayout getButtonsLayout() {
        HorizontalLayout buttons = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clear.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickListener(event -> validateAndSave());
        clear.addClickListener(event -> {binder.getFields().forEach(f -> f.clear());});
        delete.addClickListener(event -> deleteHandler.accept(userDto));

        buttons.add(save, delete, clear);
        return buttons;
    }
}
