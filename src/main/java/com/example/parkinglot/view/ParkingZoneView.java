package com.example.parkinglot.view;


import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.presenter.ParkingZonePresenter;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@Route(value = "/parking-zones", layout = MainLayout.class)

@PageTitle("ParkingZones | Vaadin CRM")
public class ParkingZoneView extends VerticalLayout {
	private Grid<ParkingZoneDto> parkingZonesGrid = new Grid<>(ParkingZoneDto.class);
//	private TextField nameFilter = new TextField("Filter by name");
//	private TextField placeFilter = new TextField("Filter by place");
	private ParkingZoneService parkingZoneService;
	private ParkingService parkingService;
	private ParkingZoneForm parkingZoneForm;
	private Button addZone  = new Button("Add zone");
	private ParkingZoneFilter parkingZoneGridFilter;

	private ParkingZonePresenter parkingZonePresenter;


	public ParkingZoneView(ParkingZonePresenter parkingZonePresenter) {
		this.parkingZonePresenter = parkingZonePresenter;
		this.parkingZonePresenter.setParkingZoneView(this);

		configuration();

//		nameFilter.setValueChangeMode(ValueChangeMode.LAZY);
//		nameFilter.addValueChangeListener(event -> {
//			onFilterChange();
//		});

		parkingZonePresenter.onViewInit();

		parkingZoneForm = new ParkingZoneForm(this::saveZone, this::deleteZone);
		parkingZonePresenter.parkingDtos(parkingZoneForm);

		addZone.addClickListener(event -> parkingZoneForm.setParkingZone(new ParkingZoneDto()));
		parkingZonesGrid.asSingleSelect().addValueChangeListener(event -> parkingZoneForm.setParkingZone(event.getValue()));
		add(addZone, getContent());

		parkingZonePresenter.onViewInit();

	}



	private void saveZone(ParkingZoneDto parkingZoneDto){
		parkingZonePresenter.onSaveZone(parkingZoneDto);
	}

	private void deleteZone(ParkingZoneDto parkingZoneDto){
		parkingZonePresenter.onDeleteZone(parkingZoneDto);
	}

	private void configuration(){
		setSizeFull();
		parkingZonesGrid.setSizeFull();
		parkingZonesGrid.removeAllColumns();
		Grid.Column<ParkingZoneDto> names = parkingZonesGrid.addColumn(  "name");
		Grid.Column<ParkingZoneDto> places = parkingZonesGrid.addColumn(parkingZoneDto -> parkingZoneDto.getPlaces()
				.stream()
				.map(ParkingPlaceDto::getNumber)
				.collect(Collectors.joining(", "))).setHeader("places");



		Grid.Column<ParkingZoneDto> parkings = parkingZonesGrid.addColumn(parkingZone -> parkingZone.getParkingDto().getName()).setHeader("parking");

		ListDataProvider<ParkingZoneDto> listDataProvider = new ListDataProvider<>(new ArrayList<>());
		parkingZoneGridFilter = new ParkingZoneFilter();
		parkingZoneGridFilter.setDataProvider(listDataProvider);
		parkingZonesGrid.setDataProvider(listDataProvider);

		HeaderRow header = parkingZonesGrid.appendHeaderRow();
		header.getCell(names).setComponent(createFilterHeaderCell("", name -> this.parkingZoneGridFilter.setName(name)));
		header.getCell(places).setComponent(createFilterHeaderCell("", place -> this.parkingZoneGridFilter.setPlaceNumber(place)));
		header.getCell(parkings).setComponent(createFilterHeaderCell("", parkingName -> this.parkingZoneGridFilter.setParkingName(parkingName)));

	}

	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout();
		parkingZoneForm.setWidth("30em");
		content.add(parkingZonesGrid, parkingZoneForm);
		content.setSizeFull();
		return content;
	}
	public void updateGrid(ListDataProvider<ParkingZoneDto> listDataProvider) {
		parkingZonesGrid.setDataProvider(listDataProvider);
		parkingZoneGridFilter.setDataProvider(listDataProvider);
	}



	public void showErrorMessage(String message) {
		Notification.show(message , 5000, Notification.Position.MIDDLE);
	}



	private static Component createFilterHeaderCell(String label, Consumer<String> valueChangeListener){
		TextField textField = new TextField();
		textField.setValueChangeMode(ValueChangeMode.EAGER);
		textField.setClearButtonVisible(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.setWidthFull();
		textField.addValueChangeListener( event -> valueChangeListener.accept(event.getValue()));
		VerticalLayout layout = new VerticalLayout(textField);
		return layout;
	}

	private static class ParkingZoneFilter {

		private ListDataProvider<ParkingZoneDto> dataProvider;
		private String name;
		private String placeNumber;
		private String parkingName;

		public void setName(String name) {
			this.name = name;
			this.dataProvider.refreshAll();
		}
		public void setPlaceNumber(String placeNumber){
			this.placeNumber = placeNumber;
			this.dataProvider.refreshAll();
		}
		public void setParkingName(String parkingName){
			this.parkingName = parkingName;
			this.dataProvider.refreshAll();
		}

		public void setDataProvider(ListDataProvider<ParkingZoneDto> dataProvider) {
			this.dataProvider = dataProvider;
			this.dataProvider.addFilter(this::test);
		}


		private boolean test(ParkingZoneDto parkingZoneDto){
			boolean matchesPlaceNumber = matches(parkingZoneDto.getPlaces()
					.stream()
					.map(ParkingPlaceDto::getNumber)
					.collect(Collectors.joining(",")), placeNumber);

			boolean matchesName = matches(parkingZoneDto.getName(), name);
			boolean matchesParkingName = matches(parkingZoneDto.getParkingDto().getName(), parkingName);

			return matchesName && matchesPlaceNumber && matchesParkingName;
		}

		private boolean matches(String value, String searchTerm){
			if(StringUtils.isBlank(searchTerm)){
				return true;
			}
			if(value!=null){
				return value.toLowerCase().contains(searchTerm.toLowerCase());
			}
			return true;
		}


	}

}
