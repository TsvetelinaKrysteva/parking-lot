package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.service.repository.ParkingRepository;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import javax.sql.DataSource;
import java.util.List;
import java.util.function.Consumer;

public class ParkingZoneForm extends FormLayout {

    private Binder<ParkingZoneDto> binder = new BeanValidationBinder<>(ParkingZoneDto.class);
    private Button save = new Button("save");
    private Button clear = new Button("clear");
    private Button delete = new Button("delete");
    private TextField name = new TextField("name");
//    private TextField id = new TextField("id");
    private ComboBox<ParkingDto> parkingNames = new ComboBox<>("Parking");
    private ParkingZoneDto parkingZone;
    private Consumer<ParkingZoneDto> onSaveHandler;
    private Consumer<ParkingZoneDto> deleteHandler;



    public ParkingZoneForm(List<ParkingDto> parking, Consumer<ParkingZoneDto> onSaveHandler, Consumer<ParkingZoneDto> deleteHandler) {
        binder.bindInstanceFields(this);
        this.onSaveHandler = onSaveHandler;
        this.deleteHandler = deleteHandler;
        binder.forField(parkingNames).bind(ParkingZoneDto::getParkingDto, ParkingZoneDto::setParkingDto);
        binder.forField(name).bind(ParkingZoneDto::getName, ParkingZoneDto::setName);
//        binder.bindInstanceFields(parkingNames);
//        binder.forField(parkingNames).bind();
        parkingNames.setItems(parking);
        parkingNames.setItemLabelGenerator(ParkingDto::getName);



        add(name, parkingNames, getButtonsLayout());
        setParkingZone(new ParkingZoneDto());

    }
    public void setParkingZone(ParkingZoneDto parkingZone) {
        binder.readBean(parkingZone);
        this.parkingZone = parkingZone;
    }

    private void validateAndSave(){
        try {
            binder.writeBean(parkingZone);
            onSaveHandler.accept(parkingZone);
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
        delete.addClickListener(event -> deleteHandler.accept(parkingZone));

        buttons.add(save, delete, clear);
        return buttons;
    }


}
