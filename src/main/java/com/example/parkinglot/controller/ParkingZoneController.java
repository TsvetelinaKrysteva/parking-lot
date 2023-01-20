package com.example.parkinglot.controller;


import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import jakarta.validation.Valid;
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
    public List<ParkingZoneDto> showZonesInParking(@PathVariable Long id) {
        return parkingZoneService.getZonesByParkingId(id);
    }
    @GetMapping("/car-parking-zone/{id}")
    public ParkingZone showParkingZoneOfTheCar(@PathVariable Long id){
        return parkingZoneService.getZoneByCarId(id);
    }
    @GetMapping("/zones")
    public List<ParkingZoneDto> findAllParkingZones(){
        return parkingZoneService.getParkingZones();
    }

    @GetMapping("/zone/{id}")
    public ParkingZoneDto showParkingZoneById(@PathVariable Long id) {
        return parkingZoneService.getParkingZoneById(id);
    }


    @PostMapping("/create-zone/{parkingId}")
    public void createParkingZone(@Valid @RequestBody ParkingZone parkingZone, @PathVariable Long parkingId){

        parkingZoneService.createParkingZone(parkingZone, parkingId);
    }

    @PostMapping("/zone/filter")
    public  List<ParkingZoneDto> filter (@RequestBody ParkingZoneFilterDto parkingZoneFilterDto){
        return parkingZoneService.filter(parkingZoneFilterDto);
    }

    @PutMapping("/update-zone/{id}")
    public void updateParkingZone(@RequestBody ParkingZone parkingZone, @PathVariable Long id) {

        parkingZone.setId(id);
        parkingZone.setParking(parkingZoneService.getZone(id).getParking());
        parkingZoneService.updateParkingZone(parkingZone);


    }

    @DeleteMapping("/delete-zone/{id}")
    public void deleteParkingZone(@PathVariable Long id) {

        parkingZoneService.getZone(id).getParking().removeZone(parkingZoneService.getZone(id));
        parkingZoneService.deleteParkingZone(id);
    }
}
