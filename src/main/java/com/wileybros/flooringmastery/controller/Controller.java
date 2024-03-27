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

    /**
     * Constructs a new Controller object.
     *
     * @param view    the View object for user interface
     * @param service the Service object for business logic
     */
    public Controller(View view, Service service) {
        this.view = view;
        this.service = service;
    }

    /**
     * Runs the main program loop.
     */
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

    /**
     * Displays the menu and returns the user's selection.
     *
     * @return the user's menu selection
     */
    private String getMenuSelection() {
        view.welcomeBanner();
        return view.displayMenu();
    }

    /**
     * Displays orders for a specific date.
     */
    private void displayDateSpecificOrders() {
        LocalDate date = view.askDate();
        Set<Order> orders = service.getOrdersOnData(date);
        view.displayOrders(orders);
    }

    /**
     * Adds a new order.
     */
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

    /**
     * Edits an existing order.
     */
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

    /**
     * Removes an existing order.
     */
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

    /**
     * Exports all data.
     */
    private void exportAllData() {
        if (service.exportAllData()) {
            view.displaySuccess("Data exported");
        } else {
            view.displayFailure("Data not exported");
        }
    }
}
