package com.example.parkinglot.view;


import java.util.stream.Collectors;

import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import org.apache.commons.lang3.StringUtils;


@Route(value = "/parking-zones")
@Theme()
@PageTitle("ParkingZones | Vaadin CRM")
public class ParkingZoneView extends VerticalLayout {
	private Grid<ParkingZoneDto> parkingZonesGrid = new Grid<>(ParkingZoneDto.class);
	private TextField nameFilter = new TextField("Filter by name");
//	private TextField placeFilter = new TextField("Filter by place");
	private ParkingZoneService parkingZoneService;
	private ParkingService parkingService;
	private ParkingZoneForm parkingZoneForm;
	private Button addZone  = new Button("Add zone");
	
	public ParkingZoneView(ParkingZoneService parkingZoneService, ParkingService parkingService) {
		this.parkingZoneService = parkingZoneService;
		this.parkingService = parkingService;

		configuration();
		parkingZonesGrid.setItems(parkingZoneService.getParkingZones());

		nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
		nameFilter.addValueChangeListener(event -> {
			onFilterChange();
		});


		parkingZoneForm = new ParkingZoneForm(parkingService.getParkings(), this::saveZone, this::deleteZone);
		addZone.addClickListener(event -> parkingZoneForm.setParkingZone(new ParkingZoneDto()));
		parkingZonesGrid.asSingleSelect().addValueChangeListener(event -> parkingZoneForm.setParkingZone(event.getValue()));
		add(nameFilter, addZone, getContent());
		updateGrid(new ParkingZoneFilterDto());

	}


	private void saveZone(ParkingZoneDto parkingZoneDto){
		if(parkingZoneDto.getId()!=null){
			parkingZoneService.updateParkingZone(parkingZoneDto);
			updateGrid(new ParkingZoneFilterDto());
		}else{
			parkingZoneService.createParkingZone(parkingZoneDto);
			updateGrid(new ParkingZoneFilterDto());
		}
	}

	private void deleteZone(ParkingZoneDto parkingZoneDto){
		parkingZoneService.deleteParkingZone(parkingZoneDto.getId());
		updateGrid(new ParkingZoneFilterDto());
	}

	private void configuration(){
		setSizeFull();
		parkingZonesGrid.setSizeFull();
		parkingZonesGrid.removeAllColumns();
		parkingZonesGrid.addColumns(  "name");
		parkingZonesGrid.addColumn(parkingZoneDto -> parkingZoneDto.getPlaces()
				.stream()
				.map(ParkingPlaceDto::getNumber)
				.collect(Collectors.joining(", "))).setHeader("places");



		parkingZonesGrid.addColumn(parkingZone -> parkingZone.getParkingDto().getName()).setHeader("parking");
//		parkingZonesGrid.getHeaderRows().clear();
//		HeaderRow header =  parkingZonesGrid.appendHeaderRow();
//		header.getCell(null).set


	}
	private HorizontalLayout getContent() {

		HorizontalLayout content = new HorizontalLayout();
		parkingZoneForm.setWidth("30em");
		content.add(parkingZonesGrid, parkingZoneForm);

		content.setSizeFull();
		return content;
	}
	private void updateGrid(ParkingZoneFilterDto parkingZoneFilterDto) {
		if (StringUtils.isBlank(parkingZoneFilterDto.getName())) {
			parkingZonesGrid.setItems(parkingZoneService.getParkingZones());
		} else {
			parkingZonesGrid.setItems(parkingZoneService.filter((parkingZoneFilterDto)));
		}
	}

	private void onFilterChange(){
		ParkingZoneFilterDto parkingZoneFilterDto = new ParkingZoneFilterDto();
		String name = nameFilter.getValue();
		if(StringUtils.isBlank(name)){
			updateGrid(new ParkingZoneFilterDto());
			return;
		}
		parkingZoneFilterDto.setName(name);
		updateGrid(parkingZoneFilterDto);

	}

}
