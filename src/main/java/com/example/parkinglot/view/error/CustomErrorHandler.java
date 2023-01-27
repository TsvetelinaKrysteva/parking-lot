package com.example.parkinglot.view.error;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomErrorHandler implements ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);
    @Override
    public void error(ErrorEvent event) {
        logger.error("", event.getThrowable());
        if(UI.getCurrent() != null){
            UI.getCurrent().access(() -> {
                Notification.show(event.getThrowable().getMessage(), 10000, Notification.Position.MIDDLE);
            });
        }
    }
}

