package com.example.parkinglot.presenter;


import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.service.ParkingZoneService;
import com.example.parkinglot.view.ParkingZoneView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope("prototype")
@Component
public class ParkingZonePresenter {
    @Autowired
    ParkingZoneService parkingZoneService;

    private ParkingZoneView parkingZoneView;
    public  void onViewInit(){
        onFilterChange(new ParkingZoneFilterDto());
    }

    public void onFilterChange(ParkingZoneFilterDto parkingZoneFilterDto){
        List<ParkingZoneDto> zoneDtos = parkingZoneService.filter(parkingZoneFilterDto);
        parkingZoneView.updateGrid(zoneDtos);
    }
    public  void setParkingZoneView(ParkingZoneView parkingZoneView){
        this.parkingZoneView = parkingZoneView;
    }

    public void onSaveZone(ParkingZoneDto parkingZoneDto){
        if (parkingZoneDto.getId()!=null){
            parkingZoneService.updateParkingZone(parkingZoneDto);
        }else{
            parkingZoneService.createParkingZone(parkingZoneDto);
        }
        onFilterChange(new ParkingZoneFilterDto());
    }

    public void onDeleteZone(ParkingZoneDto parkingZoneDto){
        parkingZoneService.deleteParkingZone(parkingZoneDto.getId());
        onFilterChange(new ParkingZoneFilterDto());
    }
}
