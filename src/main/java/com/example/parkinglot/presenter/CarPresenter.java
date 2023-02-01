package com.example.parkinglot.presenter;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.CarFilterDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.ParkingPlaceService;
import com.example.parkinglot.view.CarForm;
import com.example.parkinglot.view.CarView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope("prototype")
@Component
public class CarPresenter {
    @Autowired
    CarService carService;

    @Autowired
    ParkingPlaceService parkingPlaceService;

    private CarView carView;


    public void onSaveCar(CarDto carDto){
        if(StringUtils.isBlank(carDto.getPlateNumber())){
            this.carView.showErrorMessage("Please fill in the required fields!(plateNumber)");
        }
//        List<String> cars = carService.getAllCars()
//                .stream()
//                .map(CarDto::getPlateNumber)
//                .map(String::toLowerCase)
//                .collect(Collectors.toList());
//        if(cars.contains(carDto.getPlateNumber().toLowerCase())){
//            this.carView.showErrorMessage("Cars with identical plate numbers!");
//        }
        if(carDto.getId()!=null){
            carService.updateCar(carDto);
        }else{
            carService.createCar(carDto);
        }
        onFilterChange(new CarFilterDto());
    }

    public void onDeleteCar(CarDto carDto){
        carService.deleteCar(carDto.getId());
        onFilterChange(new CarFilterDto());
    }
    public void onViewInit(){
        onFilterChange(new CarFilterDto());
    }
    public void setCarView(CarView carView){
        this.carView = carView;
    }

    public void onFilterChange(CarFilterDto carFilterDto){
        List<CarDto> carDtos = carService.findByFilter(carFilterDto);
        carView.updateGrid(carDtos);
    }
    public void placeDtos(CarForm form){
        form.setPlaces(parkingPlaceService.getParkingPlaces());
    }
}
