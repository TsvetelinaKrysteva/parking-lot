package com.example.parkinglot.view;

import java.util.ArrayList;
import java.util.List;

import com.example.parkinglot.presenter.ParkingPresenter;
import com.vaadin.flow.component.notification.Notification;
import org.apache.commons.lang3.StringUtils;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.view.ParkingForm.SaveEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Parkings | Vaadin CRM")
public class ParkingView extends VerticalLayout {

	private Grid<ParkingDto> parkingGrid = new Grid<>(ParkingDto.class);
	private TextField filter = new TextField();
	private Button button = new Button("Add Parking");
	private ParkingForm parkingForm = new ParkingForm();


	private ParkingPresenter parkingPresenter;

	public ParkingView(ParkingPresenter presenter) {
		this.parkingPresenter = presenter;
		this.parkingPresenter.setView(this);
		configuration();
		filter.setValueChangeMode(ValueChangeMode.LAZY);

		filter.addValueChangeListener(event -> {
			onFilterChange();
		});

		add(getHorizontalLayout(), getContent());

		button.addClickListener(event -> parkingForm.setParking(new ParkingDto()));
		
		parkingForm.addListener(SaveEvent.class, saveEvent -> saveParking(saveEvent));
		parkingForm.addListener(ParkingForm.DeleteEvent.class, deleteEvent -> deleteParking(deleteEvent));
		parkingGrid.asSingleSelect().addValueChangeListener(event -> parkingForm.setParking(event.getValue()));
		parkingPresenter.onViewInit();
	}

	private void deleteParking(ParkingForm.DeleteEvent deleteEvent){
		ParkingDto parkingDto = deleteEvent.getParking();
		parkingPresenter.onDeleteParking(parkingDto);
	}
	private void saveParking(SaveEvent saveEvent) {
		ParkingDto parkingDto = saveEvent.getParking();
		parkingPresenter.onParkingSaved(parkingDto);
	}


	private HorizontalLayout getContent() {
		
		HorizontalLayout content = new HorizontalLayout();
		parkingForm.setWidth("30em");
		content.add(parkingGrid, parkingForm);
		content.setSizeFull();
		return content;
	}

	private void configuration() {
		setSizeFull();
		parkingGrid.setSizeFull();
		parkingGrid.removeAllColumns();
		parkingGrid.addColumns("name", "city", "street", "zipCode");

	}

	private HorizontalLayout getHorizontalLayout() {
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.add(filter, button);
		return toolbar;
	}

	private void onFilterChange() {
		ParkingFilterDto parkingFilterDto = new ParkingFilterDto();
		List<String> names = new ArrayList<>();
		String name = filter.getValue();
		if (StringUtils.isBlank(name)) {
			parkingPresenter.onFilterChanged(new ParkingFilterDto());
			return;
		}
		names.add(name);
		parkingFilterDto.setName(names);
		parkingPresenter.onFilterChanged(parkingFilterDto);

	}

	public void updateGrid(List<ParkingDto> parkings) {
		parkingGrid.setItems(parkings);
	}

	public void showErrorMessage(String message) {
		Notification.show(message , 5000, Notification.Position.MIDDLE);
	}
}
