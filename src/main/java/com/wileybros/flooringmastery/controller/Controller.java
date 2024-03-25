package com.wileybros.flooringmastery.controller;

import com.wileybros.flooringmastery.ui.UserIO;
import com.wileybros.flooringmastery.ui.UserIOImpl;
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

    private UserIO io = new UserIOImpl();
    private View view = new View();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    io.printLn("DISPLAY ORDERS");
                    break;
                case 2:
                    io.printLn("ADD AN ORDER");
                    break;
                case 3:
                    io.printLn("EDIT AN ORDER");
                    break;
                case 4:
                    io.printLn("REMOVE AN ORDER");
                    break;
                case 5:
                    io.printLn("EXPORT ALL DATA");
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    io.printLn("UNKNOWN COMMAND");
            }

        }
        io.printLn("GOOD BYE");
    }

    private int getMenuSelection() {
        return view.displayMenu();
    }
}
