package com.example.parkinglot.presenter;


import com.example.parkinglot.model.dto.ParkingDto;
import com.example.parkinglot.model.dto.ParkingZoneDto;
import com.example.parkinglot.model.dto.ParkingZoneFilterDto;
import com.example.parkinglot.model.entity.ParkingZone;
import com.example.parkinglot.service.ParkingService;
import com.example.parkinglot.service.ParkingZoneService;
import com.example.parkinglot.view.ParkingZoneForm;
import com.example.parkinglot.view.ParkingZoneView;
import com.sun.xml.bind.v2.TODO;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Scope("prototype")
@Component
public class ParkingZonePresenter {
    @Autowired
    ParkingZoneService parkingZoneService;
    @Autowired
    ParkingService parkingService;

    private ParkingZoneView parkingZoneView;

    public  void onViewInit(){
        onFilterChange(new ParkingZoneFilterDto());
    }

    public void onFilterChange(ParkingZoneFilterDto parkingZoneFilterDto){
        ListDataProvider<ParkingZoneDto> listDataProvider;
        if(StringUtils.isBlank(parkingZoneFilterDto.getName())){
            listDataProvider = DataProvider.ofCollection(parkingZoneService.getParkingZones());
        }else{
            listDataProvider = DataProvider.ofCollection(parkingZoneService.filter(parkingZoneFilterDto));
        }
        parkingZoneView.updateGrid(listDataProvider);
    }
    public  void setParkingZoneView(ParkingZoneView parkingZoneView){
        this.parkingZoneView = parkingZoneView;
    }


    public void onSaveZone(ParkingZoneDto parkingZoneDto){
        if(parkingZoneDto.getParkingDto()!= null && StringUtils.isNotBlank(parkingZoneDto.getName())){
            // TODO: 30.1.2023 г. fix
            String zones = parkingZoneService.getZonesByParkingId(parkingZoneDto.getParkingDto().getId())
                    .stream()
                    .map(ParkingZoneDto::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.joining(", "));
            if(zones.contains(parkingZoneDto.getName().toLowerCase())){
                this.parkingZoneView.showErrorMessage("No zones with identical names within the same parking are allowed!");
            }
            if (parkingZoneDto.getId()!=null){
                parkingZoneService.updateParkingZone(parkingZoneDto);
            }else{
                parkingZoneService.createParkingZone(parkingZoneDto);
            }

        }else{
            this.parkingZoneView.showErrorMessage("Please fill in the required fields!(name, parking)");
        }

        onFilterChange(new ParkingZoneFilterDto());
    }

    public void onDeleteZone(ParkingZoneDto parkingZoneDto){
        parkingZoneService.deleteParkingZone(parkingZoneDto.getId());
        onFilterChange(new ParkingZoneFilterDto());
    }

   public void parkingDtos(ParkingZoneForm form){
        form.setParkingNames(parkingService.getParkings());
   }
}

