package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.ParkingDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ParkingForm extends FormLayout {
	
	
	private Binder<ParkingDto> binder = new BeanValidationBinder<>(ParkingDto.class);
	
	private TextField name = new TextField("name");
	private TextField street = new TextField("street");
	private TextField city = new TextField("city");
	private TextField zipCode = new TextField("zipCode");
	
	private Button save = new Button("save");
	private Button delete = new Button("delete");
	private Button cancel = new Button("cancel");
	
	
	private ParkingDto parking;
	
	public ParkingForm() {
		binder.bindInstanceFields(this);
		add(name);
		add(street);
		add(city);
		add(zipCode);
		add(getButtonsLayout());
	}
	
	private HorizontalLayout getButtonsLayout() {
		HorizontalLayout buttons = new HorizontalLayout();
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		delete.addClickListener(event -> fireEvent(new DeleteEvent(this, parking)));
		save.addClickListener( event -> validateAndSave());
		cancel.addClickListener(event -> {binder.getFields().forEach(f -> f.clear());});
		buttons.add(save, delete, cancel);
		return buttons;
	}
	
	public void setParking(ParkingDto parking) {
		binder.readBean(parking);
		this.parking = parking;
	}
	
	private void validateAndSave() {
		try {
			binder.writeBean(parking);
			fireEvent(new SaveEvent(this, parking));
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	
	// Events
	public static abstract class ParkingFormEvent extends ComponentEvent<ParkingForm> {
		private ParkingDto parking;

		protected ParkingFormEvent(ParkingForm source, ParkingDto parking) {
			super(source, false);
			this.parking = parking;
		}

		public ParkingDto getParking() {
			return parking;
		}

	}

	public static class SaveEvent extends ParkingFormEvent {
		SaveEvent(ParkingForm source, ParkingDto parking) {
			super(source, parking);
		}
	}

	public static class DeleteEvent extends ParkingFormEvent {
		DeleteEvent(ParkingForm source, ParkingDto parking) {
			super(source, parking);
		}

	}

	public static class CloseEvent extends ParkingFormEvent {
		CloseEvent(ParkingForm source) {
			super(source, null);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
	
	
}
