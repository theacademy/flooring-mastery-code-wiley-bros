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
                    addAnOrder();
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

    private void addAnOrder() {
        Order order = service.createOrder(
                view.askCustomerName(),
                view.askOrderState(service.getStateAbrs()),
                view.askOrderProduct(service.getProducts()),
                view.askOrderArea(),
                view.askFutureDate()
        );
        if (view.placeOrderConfirmation(order)){
            if (service.addOrder(order)) {
                view.displaySuccess("Order added");
            } else {
                view.displayFailure("Order not added");
            }
        }
    }

    private void editAnOrder() {
        Order order = service.combineOrder(
                view.askOrderID(),
                view.askCustomerName(),
                view.askOrderState(service.getStateAbrs()),
                view.askOrderProduct(service.getProducts()),
                view.askOrderArea()
        );
        if (view.updateOrderConfirmation(order)){
            if (service.updateOrder(order)) {
                view.displaySuccess("Order edited");
            } else {
                view.displayFailure("Order not edited");
            }
        }
    }

    private void removeAnOrder() {
        Order order = service.getOrder(view.askOrderID());
        if (view.removeOrderConfirmation(order)) {
            if (service.removeOrder(order)) {
                view.displaySuccess("Order removed");
            } else {
                view.displayFailure("Order not removed");
            }
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
