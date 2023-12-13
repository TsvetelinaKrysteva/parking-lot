package com.example.parkinglot.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "cars")
public class Car extends BaseEntity {


    @NotNull
    @Column(unique = true)
    private String plateNumber;

//    @OneToOne
//    @JoinColumn(name = "parking_place_id")
    @ManyToOne
    @JoinColumn(name="parking_place_id",unique=true)
    private ParkingPlace parkingPlace;



//    @ManyToOne
//    @JoinColumn(name = "user_id")

//    @JoinTable(name = "car_user"
//            joinColumns = @JoinColumn(name = "cars_id"),
//            inverseJoinColumns = @JoinColumn(name = "users_id")
//    )

    @ManyToMany(mappedBy = "cars")
    private List<User> users;

    public Car() {

    }

    public Car(String plateNumber, ParkingPlace parkingPlace, List<User> users) {
        this.plateNumber = plateNumber;
        this.parkingPlace = parkingPlace;
        this.users = users;
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
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
