package com.example.parkinglot.model.dto;


import com.example.parkinglot.model.entity.ParkingZone;
import org.hibernate.service.spi.InjectService;

import java.util.List;
import java.util.Objects;

public class ParkingDto {
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipCode;


    private List<ParkingZoneDto> zones;


    private List<ParkingZoneDto> parkingZoneDtoList;
    public ParkingDto(){

    }

    public ParkingDto(String name, String city, String street, String zipCode, Long id, List<ParkingZoneDto> zones) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.zones = zones;

    }

    public List<ParkingZoneDto> getZones() {
        return zones;
    }

    public void setZones(List<ParkingZoneDto> zones) {
        this.zones = zones;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addNewZone(ParkingZoneDto parkingZone){
        List<ParkingZoneDto> pZones = this.getZones();
        pZones.add(parkingZone);
        this.setZones(pZones);

    }
    public void removeZone(ParkingZone parkingZone){
        this.getZones().remove(parkingZone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingDto that = (ParkingDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
