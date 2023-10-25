package com.example.parkinglot.view;

import com.example.parkinglot.model.dto.CarDto;
import com.example.parkinglot.model.dto.UserDto;
import com.example.parkinglot.presenter.UserPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Route(value = "/users", layout = MainLayout.class)
@PageTitle("Users | Vaadin CRM")
public class UserView extends VerticalLayout{
    private Grid<UserDto> usersGrid = new Grid<>(UserDto.class);
    private UserPresenter userPresenter;
    private Button addUser  = new Button("Add user");
    private UserFilter userGridFilter;
    private UserForm userForm;

    public UserView(UserPresenter userPresenter){
        this.userPresenter = userPresenter;
        this.userPresenter.setUserView(this);
        configuration();

        userPresenter.onViewInit();
        userForm = new UserForm(this::saveUser, this::deleteUser);
        userPresenter.carDtos(userForm);

        addUser.addClickListener(event -> userForm.setUser(new UserDto()));
        usersGrid.asSingleSelect().addValueChangeListener(event -> userForm.setUser(event.getValue()));
        add(addUser, getContent());
        userPresenter.onViewInit();

    }
    private void saveUser(UserDto userDto){
        userPresenter.onSaveUser(userDto);
    }
    private void deleteUser(UserDto userDto){
        userPresenter.onDeleteUser(userDto);
    }
    private void configuration(){
        setSizeFull();
        usersGrid.setSizeFull();
        usersGrid.removeAllColumns();
        Grid.Column<UserDto> names = usersGrid.addColumn("name");
        Grid.Column<UserDto> cars = usersGrid.addColumn(userDto -> !userDto.getCars().isEmpty() ? userDto.getCars()
                .stream()
                .map(CarDto::getPlateNumber)
                .collect(Collectors.joining(", ")) : "").setHeader("cars");

        ListDataProvider<UserDto> listDataProvider = new ListDataProvider<>(new ArrayList<>());
        userGridFilter = new UserFilter();
        userGridFilter.setDataProvider(listDataProvider);
        usersGrid.setDataProvider(listDataProvider);

        HeaderRow header = usersGrid.appendHeaderRow();
        header.getCell(names).setComponent(createFilterHeaderCell("", name -> this.userGridFilter.setName(name)));
        header.getCell(cars).setComponent(createFilterHeaderCell("", car -> this.userGridFilter.setCar(car)));
    }


    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout();
        userForm.setWidth("30em");
        content.add(usersGrid, userForm);
        content.setSizeFull();
        return content;
    }

    public void updateGrid(ListDataProvider<UserDto> listDataProvider) {
        usersGrid.setDataProvider(listDataProvider);
        userGridFilter.setDataProvider(listDataProvider);
    }


    public void showErrorMessage(String message) {
        Notification.show(message , 5000, Notification.Position.MIDDLE);
    }




    private static Component createFilterHeaderCell(String label, Consumer<String> valueChangeListener){
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.addValueChangeListener( event -> valueChangeListener.accept(event.getValue()));
        VerticalLayout layout = new VerticalLayout(textField);
        return layout;
    }

    private static class UserFilter {

        private ListDataProvider<UserDto> dataProvider;
        private String name;
        private String car;

        public void setName(String name) {
            this.name = name;
            this.dataProvider.refreshAll();
        }
        public void setCar(String car){
            this.car = car;
            this.dataProvider.refreshAll();
        }


        public void setDataProvider(ListDataProvider<UserDto> dataProvider) {
            this.dataProvider = dataProvider;
            this.dataProvider.addFilter(this::test);
        }


        private boolean test(UserDto userDto){
            boolean matchesPlaceNumber = matches(userDto.getCars()
                    .stream()
                    .map(CarDto::getPlateNumber)
                    .collect(Collectors.joining(",")), car);

            boolean matchesName = matches(userDto.getName(), name);


            return matchesName && matchesPlaceNumber;
        }

        private boolean matches(String value, String searchTerm){
            if(StringUtils.isBlank(searchTerm)){
                return true;
            }
            if(value!=null){
                return value.toLowerCase().contains(searchTerm.toLowerCase());
            }
            return true;
        }


    }
}
