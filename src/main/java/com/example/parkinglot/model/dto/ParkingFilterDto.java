package com.example.parkinglot.model.dto;

import com.example.parkinglot.model.entity.ParkingZone;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ParkingFilterDto {

    private List<String> name;

    private String parkingZoneName;

    private String city;

    private String street;

    private String zipCode;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getParkingZoneName() {
        return parkingZoneName;
    }

    public void setParkingZoneName(String parkingZoneName) {
        this.parkingZoneName = parkingZoneName;
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
}
