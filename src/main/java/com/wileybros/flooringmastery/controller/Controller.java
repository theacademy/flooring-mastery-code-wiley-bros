package com.wileybros.flooringmastery.controller;

import com.wileybros.flooringmastery.service.Service;
import com.wileybros.flooringmastery.ui.View;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    View view;
    Service service;

    public Controller(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    public void run(){
        boolean running = true;
        view.welcomeBanner();
        while (running) {
            switch (view.displayMenu()) {
                case 1:

            }


        }
    }
}
