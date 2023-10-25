package com.example.parkinglot.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "parking_zones")
public class ParkingZone extends BaseEntity{
    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parking_id")

    private Parking parking;

    @OneToMany(orphanRemoval = true, mappedBy = "parkingZone", fetch = FetchType.EAGER)
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


}

