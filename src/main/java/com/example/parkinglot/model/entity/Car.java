package com.example.parkinglot.model.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "cars")
public class Car extends BaseEntity {


    @NotNull
    @Column(unique = true)
    private String plateNumber;

    @OneToOne(mappedBy = "car")
    private ParkingPlace parkingPlace;


    public Car() {

    }

    public Car(String plateNumber, ParkingPlace parkingPlace) {
        this.plateNumber = plateNumber;
        this.parkingPlace = parkingPlace;
    }



    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
    }


}
