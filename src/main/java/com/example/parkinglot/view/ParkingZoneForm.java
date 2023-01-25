package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.service.repository.ParkingRepository;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
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
    private TextField name = new TextField("name");
    private TextField id = new TextField("id");
    private ComboBox<ParkingDto> parkingNames = new ComboBox<>("Parking");
    private ParkingZoneDto parkingZone;
    private Consumer<ParkingZoneDto> onSaveHandler;



    public ParkingZoneForm(List<ParkingDto> parking, Consumer<ParkingZoneDto> onSaveHandler) {
        this.onSaveHandler = onSaveHandler;
        binder.bindInstanceFields(this);
//        binder.forField(parkingNames).bind();
        parkingNames.setItems(parking);
        parkingNames.setItemLabelGenerator(ParkingDto::getName);
        save.addClickListener(event -> validateAndSave());
        clear.addClickListener(event -> {binder.getFields().forEach(f -> f.clear());});

        add(id, name, save, clear, parkingNames);
        setParkingZone(new ParkingZoneDto());

    }
    public void setParkingZone(ParkingZoneDto parkingZone) {
        binder.readBean(parkingZone);
        this.parkingZone = parkingZone;
    }

    private void validateAndSave(){
        try {
            binder.writeBean(parkingZone);
            onSaveHandler.accept(binder.getBean());
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

}
