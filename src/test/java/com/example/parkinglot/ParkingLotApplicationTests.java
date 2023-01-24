package com.example.parkinglot;

import com.example.parkinglot.controller.CarController;
import com.example.parkinglot.controller.ParkingController;
import com.example.parkinglot.controller.ParkingPlaceController;
import com.example.parkinglot.controller.ParkingZoneController;
import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingZone;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ParkingLotApplicationTests {


	@Autowired
	ParkingController parkingController;
	@Autowired
	ParkingZoneController parkingZoneController;
	@Autowired
	ParkingPlaceController parkingPlaceController;
	@Autowired
	CarController carController;


	@Test
	void contextLoads() {
	}

	@Test
	public void testParking() {
		List<ParkingDto> parkings = parkingController.showParkings();
		Assert.notEmpty(parkings, "No parkings");
		ParkingDto parkingDto = parkings.get(0);
		Assert.isTrue(parkingDto.getName().equals("Parking 1"), "Invalid operation");

	}

	@Test
	public void testCar(){
		List<CarDto> cars = carController.findAllCars();
		Assert.notEmpty(cars, "Failed");
		Assert.isTrue(cars.get(0).getParkingPlaceNumber() == 8, "Failed");
		Assert.isTrue(cars.get(0).getPlateNumber().equals("PB5870KB"), "Failed");
		Assert.isTrue(cars.get(0).getParkingZoneName().equals("Parking zone 2"), "Failed");
	}

	@Test
	@Transactional
	public void testPlace(){
		List<ParkingPlaceDto> places = parkingPlaceController.findAllPlaces();
		Assert.notEmpty(places, "Failed");
		Assert.isTrue(places.get(0).getNumber() == 8, " ");
		Assert.isTrue(parkingPlaceController.showParkingPlaceOfTheCar(1L).getId() == (places.get(0).getId()), " ");
	}

	@Test
	@Transactional
	public void testZone(){
		List<ParkingZoneDto> zones = parkingZoneController.findAllParkingZones();
		Assert.notEmpty(zones, " ");
		Assert.isTrue(zones.size() == 2, " ");
		Assert.isTrue(parkingZoneController.showParkingZoneById(1L).getName().equals("Parking zone 2"), " ");
//		zones.get(0).setName("changed");
		parkingZoneController.updateParkingZone(zones.get(0), 2L);
		Assert.isTrue(parkingZoneController.showParkingZoneById(2L).getName().equals("Parking zone 2"), " ");

	}

//
//	@Test
//	public void testParkingZone() {
//
//		List<ParkingZoneDto> zones = parkingZoneController.showZonesInParking(1L);
//		Assert.isTrue(zones.size() == 1, "Failed test");
//		Assert.isTrue(zones.get(0).getName().equals("Parking zone 2"), "Failed test");
//	}
}
