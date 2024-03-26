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
        String menuSelection;
        while (running) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case "1":
                    displayDateSpecificOrders();
                    break;
                case "2":
                    addOrder();
                    break;
                case "3":
                    editAnOrder();
                    break;
                case "4":
                    removeAnOrder();
                    break;
                case "5":
                    exportAllData();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    view.displayError("Input invalid command.");
                    break;
            }

        }
        view.displayExit();
    }

    private String getMenuSelection() {
        view.welcomeBanner();
        return view.displayMenu();
    }

    private void displayDateSpecificOrders() {
        LocalDate date = view.askDate();
        Set<Order> orders = service.getOrdersOnData(date);
        view.displayOrders(orders);
    }

    private void addOrder() {
        LocalDate futureDate = view.askFutureDate();
        Object[] args = {
                view.askCustomerName(),
                view.askOrderState(service.getStateAbrs()),
                view.askOrderProduct(service.getProducts()),
                view.askOrderArea()
        };
        if (view.confirmOrder(args).equals("Y")){
            if (service.addOrder(args, futureDate)) {
                view.displaySuccess("Order added");
            } else {
                view.displayFailure("Order not added");
            }
        }
    }

    private void editAnOrder() {
        Integer id = view.askOrderID();
        Object[] args = {
                view.askCustomerName(),
                view.askOrderState(service.getStateAbrs()),
                view.askOrderProduct(service.getProducts()),
                view.askOrderArea()
        };
        if (service.updateOrder(id, args)) {
            view.displaySuccess("Order edited");
        } else {
            view.displayFailure("Order not edited");
        }
    }

    private void removeAnOrder() {
        Integer id = view.askOrderID();
        if (service.removeOrder(id)) {
            view.displaySuccess("Order removed");
        } else {
            view.displayFailure("Order not removed");
        }
    }

    private void exportAllData() {
        if (service.exportAllData()) {
            view.displaySuccess("Data exported");
        } else {
            view.displayFailure("Data not exported");
        }
    }
}
