package com.example.parkinglot.presenter;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.model.dto.UserFilterDto;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.UserService;
import com.example.parkinglot.view.UserForm;
import com.example.parkinglot.view.UserView;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Scope("prototype")
@Component
public class UserPresenter {
    @Autowired
    UserService userService;
    @Autowired
    CarService carService;
    private UserView userView;

    public void onViewInit(){
        onFilterChange(new UserFilterDto());
    }
    public void onFilterChange(UserFilterDto userFilterDto){
        ListDataProvider<UserDto> listDataProvider;
        if(StringUtils.isBlank(userFilterDto.getName())){
            listDataProvider = DataProvider.ofCollection(userService.getAllUsers());
        }else{
            listDataProvider = DataProvider.ofCollection(userService.findByFilter(userFilterDto));
        }
        userView.updateGrid(listDataProvider);
    }
    public void onSaveUser(UserDto userDto){
        if(StringUtils.isNotBlank(userDto.getName())){
//            for(CarDto carDto: userDto.getCars()){
//                if(carDto.getUsers()!=null && !carDto.getUsers().contains(userDto)){
//                    this.userView.showErrorMessage(String.format("Car %s belongs to user %s! PLease choose another one!", carDto.getPlateNumber(),
//                            String.join(", ", carDto.getUsers().stream().map(UserDto::getName).collect(Collectors.toList()))));
//                    return;
//                }
//            }
            if(userDto.getId()!=null){
                userService.updateUser(userDto);
            }else{
                userService.createUser(userDto);
            }

        }else{
            this.userView.showErrorMessage("Please fill in the required fields!(name)");
        }

        onFilterChange(new UserFilterDto());
    }
    public void onDeleteUser(UserDto userDto){
        userService.deleteUser(userDto.getId());
        onFilterChange(new UserFilterDto());
    }
    public void setUserView(UserView userView){
        this.userView = userView;

    }
    public void carDtos(UserForm form){
        form.setCars(carService.getAllCars());
    }

}
