package com.example.parkinglot.presenter;

import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingFilterDto;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.view.ParkingView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Scope("prototype")
@Component
public class ParkingPresenter {

    @Autowired
    private ParkingService parkingService;

    private ParkingView parkingView;

    public void onViewInit(){
        onFilterChanged(new ParkingFilterDto());
    }
    public void onFilterChanged(ParkingFilterDto parkingFilterDto) {
        List<ParkingDto> dtos = parkingService.findByFilter(parkingFilterDto);
        parkingView.updateGrid(dtos);
    }



    public void onParkingSaved(ParkingDto parkingDto) {
        if(StringUtils.isBlank(parkingDto.getName())){
            this.parkingView.showErrorMessage("Napravi si validaciite");
            return;
        }
		if(parkingDto.getId()!=null){
			parkingService.updateParking(parkingDto, parkingDto.getId());
		}
		else{
			parkingService.createParking(parkingDto);
		}
        onFilterChanged(new ParkingFilterDto());
    }

    public void onDeleteParking(ParkingDto parkingDto) {
        parkingService.deleteParking(parkingDto.getId());
        onFilterChanged(new ParkingFilterDto());
    }


    public void setView(ParkingView view){
        this.parkingView = view;
    }
}
