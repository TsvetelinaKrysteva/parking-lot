package com.example.parkinglot.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.List;


@Entity
@Table(name = "parkings")
public class Parking extends BaseEntity{
    @NotNull
    @Column(name = "name")
//            , nullable = false)
    private String name;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parking")
    private List<ParkingZone> zones;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;


    @Column(name = "zip_code", length = 5)
    private String zipCode;

    public Parking(){

    }

    public Parking(String name, List<ParkingZone> zones, String city, String street, String zipCode) {
        this.name = name;
        this.zones = zones;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingZone> getZones() {
        return zones;
    }

    public void setZones(List<ParkingZone> zones) {
        this.zones = zones;
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
    public void addNewZone(ParkingZone parkingZone){
        List<ParkingZone> pZones = this.getZones();
        pZones.add(parkingZone);
        this.setZones(pZones);
    }
    public void removeZone(ParkingZone parkingZone){
        this.getZones().remove(parkingZone);
    }


}
