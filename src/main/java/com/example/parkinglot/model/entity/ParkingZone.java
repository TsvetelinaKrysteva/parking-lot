package com.example.parkinglot.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "parking zones")
public class ParkingZone extends BaseEntity{
    @Column(name = "name")
    private String name;


    @ManyToOne
    @JsonBackReference
    private Parking parking;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ParkingPlace> parkingPlaces;

    public ParkingZone() {
    }

    public ParkingZone(String name, Parking parking, List<ParkingPlace> parkingPlaces) {
        this.name = name;
        this.parking = parking;
        this.parkingPlaces = parkingPlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public List<ParkingPlace> getParkingPlaces() {
        return parkingPlaces;
    }

    public void setParkingPlaces(List<ParkingPlace> parkingPlaces) {
        this.parkingPlaces = parkingPlaces;
    }
    public void addNewPlace(ParkingPlace parkingPlace){
        List<ParkingPlace> pPlaces= this.getParkingPlaces();
        pPlaces.add(parkingPlace);
        this.setParkingPlaces(pPlaces);

    }
    public void removePlace(ParkingPlace parkingPlace){
        this.getParkingPlaces().remove(parkingPlace);
    }


}

