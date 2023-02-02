package com.example.parkinglot.view;

import com.example.parkinglot.view.error.CustomErrorHandler;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;

@Theme
@PWA(name = "Tsvetelina's parking app", shortName = "app")
public class MainLayout extends AppLayout {
    public MainLayout() {
        VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle());
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        addToNavbar(header);
        RouterLink linkParking = new RouterLink("Parking", ParkingView.class);
        RouterLink linkPlace = new RouterLink("Place", ParkingPlaceView.class);
        RouterLink linkPZone = new RouterLink("Zone", ParkingZoneView.class);
        RouterLink linkCar = new RouterLink("Car", CarView.class);
        RouterLink linkUser = new RouterLink("User", UserView.class);
        VerticalLayout vList = new VerticalLayout(linkParking, linkPlace, linkPZone, linkCar, linkUser);
        addToDrawer(vList);

    }

}
