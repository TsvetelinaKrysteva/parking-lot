package com.example.parkinglot.view;

import java.util.ArrayList;
import java.util.List;

import com.example.parkinglot.model.entity.Parking;
import com.vaadin.flow.component.crud.Crud;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.view.ParkingForm.ParkingFormEvent;
import com.example.parkinglot.view.ParkingForm.SaveEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

@Route(value = "")
@Theme()
@PageTitle("Parkings | Vaadin CRM")
public class ParkingView extends VerticalLayout {

	private Grid<ParkingDto> parkingGrid = new Grid<>(ParkingDto.class);
	private TextField filter = new TextField();
	private Button button = new Button("Add Parking");
	private ParkingForm parkingForm = new ParkingForm();
	private ParkingService parkingService;




	public ParkingView(ParkingService parkingService) {
		this.parkingService = parkingService;
		configuration();
		parkingGrid.setItems(parkingService.getParkings());

		filter.setValueChangeMode(ValueChangeMode.LAZY);

		filter.addValueChangeListener(event -> {
			onFilterChange();
		});
		
		
		add(getHorizontalLayout(), getContent());

		updateGrid(new ParkingFilterDto());
		button.addClickListener(event -> parkingForm.setParking(new ParkingDto()));
		
		parkingForm.addListener(SaveEvent.class, saveEvent -> saveParking(saveEvent));
		parkingForm.addListener(ParkingForm.DeleteEvent.class, deleteEvent -> deleteParking(deleteEvent));
		parkingGrid.asSingleSelect().addValueChangeListener(event -> parkingForm.setParking(event.getValue()));
	}

	private void deleteParking(ParkingForm.DeleteEvent deleteEvent){
		ParkingDto parkingDto = deleteEvent.getParking();
		parkingService.deleteParking(parkingDto.getId());
		updateGrid(new ParkingFilterDto());
	}
	private void saveParking(SaveEvent saveEvent) {
		ParkingDto parkingDto = saveEvent.getParking();
		if(parkingDto.getId()!=null){
			parkingService.updateParking(parkingDto, parkingDto.getId());
		}
		else{
			parkingService.createParking(parkingDto);
		}

		updateGrid(new ParkingFilterDto());
	}


	private HorizontalLayout getContent() {
		
		HorizontalLayout content = new HorizontalLayout();
		parkingForm.setWidth("30em");
		content.add(parkingGrid, parkingForm);
		content.setFlexGrow(1, parkingGrid);
		content.setFlexGrow(3, parkingForm);
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
			updateGrid(new ParkingFilterDto());
			return;
		}
		names.add(name);
		parkingFilterDto.setName(names);
		updateGrid(parkingFilterDto);

	}

	private void updateGrid(ParkingFilterDto parkingFilterDto) {
		if (CollectionUtils.isEmpty(parkingFilterDto.getName())) {
			parkingGrid.setItems(parkingService.getParkings());
		} else {
			parkingGrid.setItems(parkingService.findByFilter(parkingFilterDto));
		}
	}

}
