package com.example.parkinglot.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;





@Entity
@Table(name = "parking_zones")
public class ParkingZone extends BaseEntity{
    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parkingZone")
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

