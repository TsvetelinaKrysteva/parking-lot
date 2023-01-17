package com.example.parkinglot.controller;


import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.entity.ParkingPlace;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

@RestController
public class ParkingZoneController {
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    CarService carService;
    @Autowired
    ParkingService parkingService;

    @GetMapping("/zones-in-parking/{id}")
    public List<ParkingZone> showZonesInParking(@PathVariable Long id) {
        return parkingService.getZonesByParkingId(id);
    }
    @GetMapping("/car-parking-zone/{id}")
    public ParkingZone showParkingZoneOfTheCar(@PathVariable Long id){
        return carService.getParkingZoneOfTheCar(id);
    }
    @GetMapping("/zones")
    public List<ParkingZoneDto> findAllParkingZones(){
        return parkingZoneService.getParkingZones();
    }

    @GetMapping("/zone/{id}")
    public ParkingZone showParkingZoneById(@PathVariable Long id) {
        return parkingZoneService.getParkingZoneById(id);
    }


    @PostMapping("/create-zone/{parkingId}")
    public void createParkingZone(@RequestBody ParkingZone parkingZone, @PathVariable Long parkingId){

        parkingZoneService.createParkingZone(parkingZone, parkingId);
    }

    @PutMapping("/update-zone/{id}")
    public void updateParkingZone(@RequestBody ParkingZone parkingZone, @PathVariable Long id) {

        parkingZone.setId(id);
        parkingZone.setParking(parkingZoneService.getParkingZoneById(id).getParking());
        parkingZoneService.updateParkingZone(parkingZone);


    }

    @DeleteMapping("/delete-zone/{id}")
    public void deleteParkingZone(@PathVariable Long id) {

        parkingZoneService.getParkingZoneById(id).getParking().removeZone(parkingZoneService.getParkingZoneById(id));
        parkingZoneService.deleteParkingZone(id);
    }
}
