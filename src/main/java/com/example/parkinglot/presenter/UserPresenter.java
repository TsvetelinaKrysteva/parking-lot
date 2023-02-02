package com.example.parkinglot.presenter;

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
        if(StringUtils.isBlank(userDto.getName())){
            this.userView.showErrorMessage("Please fill in the required fields!(name)");
        }
        if(userDto.getId()!=null){
            userService.updateUser(userDto);
        }else{
            userService.createUser(userDto);
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
