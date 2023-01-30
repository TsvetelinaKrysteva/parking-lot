package com.example.parkinglot.presenter;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.ParkingPlaceDto;
import com.example.parkinglot.model.dto.ParkingPlaceFilterDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;

import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.service.ParkingZoneService;
import com.example.parkinglot.view.ParkingPlaceForm;
import com.example.parkinglot.view.ParkingPlaceView;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope("prototype")
@Component
public class ParkingPlacePresenter {

    @Autowired
    ParkingPlaceService parkingPlaceService;
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    CarService carService;

    private ParkingPlaceView parkingPlaceView;

    public void onViewInit(){
        onFilterChange(new ParkingPlaceFilterDto());
    }

    public void setParkingPlaceView(ParkingPlaceView parkingPlaceView){
        this.parkingPlaceView = parkingPlaceView;
    }

    public void onFilterChange(ParkingPlaceFilterDto parkingPlaceFilterDto){
        ListDataProvider<ParkingPlaceDto> listDataProvider;
        if(StringUtils.isBlank(parkingPlaceFilterDto.getNumber())){
            listDataProvider = DataProvider.ofCollection(parkingPlaceService.getParkingPlaces());
        }else{
            listDataProvider = DataProvider.ofCollection(parkingPlaceService.findByFilter(parkingPlaceFilterDto));
        }
        parkingPlaceView.updateGrid(listDataProvider);
    }

    public void onSavePlace(ParkingPlaceDto parkingPlaceDto){
        if(StringUtils.isNotBlank(parkingPlaceDto.getNumber()) && parkingPlaceDto.getParkingZone()!=null){
            if(parkingPlaceDto.getId()!=null){
                parkingPlaceService.updateParkingPlace(parkingPlaceDto);
            }else{
                parkingPlaceService.createParkingPlace(parkingPlaceDto);
            }
        }else{
            this.parkingPlaceView.showErrorMessage("Please fill in the required fields!(number, zones)");
        }

        onFilterChange(new ParkingPlaceFilterDto());
    }
    public void  onDeletePlace(ParkingPlaceDto parkingPlaceDto){
        parkingPlaceService.deleteParkingPlace(parkingPlaceDto.getId());
        onFilterChange(new ParkingPlaceFilterDto());
    }
    public void carDtos (ParkingPlaceForm form){
        form.setCar(carService.getAllCars());

    }
    public void zoneDtos (ParkingPlaceForm form){
        form.setZones(parkingZoneService.getParkingZones());

    }


}
