package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.Parking;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.hibernate.service.spi.InjectService;


import java.util.List;
import java.util.stream.Collectors;


public class ParkingZoneDto {


    private Long id;
    private String name;



    private List<ParkingPlaceDto> places;
//    private List<Integer> placeNames;
    private ParkingDto parkingDto;

    public ParkingZoneDto() {
    }

    public ParkingZoneDto(String name, List<ParkingPlaceDto> places, Long id, ParkingDto parkingDto) {
        this.id = id;
        this.name = name;
        this.places = places;
//        this.placeNames = parkingPlaces.stream().map(ParkingPlace::getNumber).collect(Collectors.toList());

        this.parkingDto = parkingDto;
    }

    public List<ParkingPlaceDto> getPlaces() {
        return places;
    }

    public void setPlaces(List<ParkingPlaceDto> places) {
        this.places = places;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public List<Integer> getPlaceNames() {
//        return placeNames;
//    }
//
//    public void setPlaceNames(List<Integer> placeNames) {
//        this.placeNames = placeNames;
//    }

    public ParkingZoneDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ParkingDto getParkingDto() {
        return parkingDto;
    }

    public void setParkingDto(ParkingDto parkingDto) {
        this.parkingDto = parkingDto;
    }
}
