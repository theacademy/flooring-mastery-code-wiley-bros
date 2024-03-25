package com.wileybros.flooringmastery.controller;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.service.Service;
import com.wileybros.flooringmastery.ui.View;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class Controller {
    private final View view;
    private final Service service;

    public Controller(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean running = true;
        int menuSelection = 0;
        while (running) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayDateSpecificOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editAnOrder();
                    break;
                case 4:
                    removeAnOrder();
                    break;
                case 5:
                    exportAllData();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    view.displayError("Input invalid command.");
            }

        }
        view.displayExit();
    }

    private int getMenuSelection() {
        view.welcomeBanner();
        return view.displayMenu();
    }

    private void displayDateSpecificOrders() {
        LocalDate date = view.askDate();
        Set<Order> orders = service.getOrdersOnData(date);
        view.displayOrders(orders);
    }

    private void addOrder() {
        Object[] args = view.askOrderArgs();
        if (service.addOrder(args)) {
            view.displaySuccess("Order added");
        } else {
            view.displayFailiure("Order added");
        }
    }

    private void editAnOrder() {
        Integer id = view.askOrderID();
        Object[] args = view.askOrderArgs();
        if (service.updateOrder(id, args)) {
            view.displaySuccess("Order edited");
        } else {
            view.displayFailiure("Order not edited");
        }
    }

    private void removeAnOrder() {
        Integer id = view.askOrderID();
        if (service.removeOrder(id)) {
            view.displaySuccess("Order removed");
        } else {
            view.displayFailiure("Order not removed");
        }
    }

    private void exportAllData() {
        view.displayFailure("Not Implemented yet");
    }
}
