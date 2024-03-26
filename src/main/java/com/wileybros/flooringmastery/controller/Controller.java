package com.wileybros.flooringmastery.controller;

import org.springframework.stereotype.Component;

@Component
public class Controller {

    public void run(){
        boolean running = true;
        while (running) {

            System.out.println("WIP");

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
            view.displayFailure("Order not added");
        }
    }

    private void editAnOrder() {
        Integer id = view.askOrderID();
        Object[] args = view.askOrderArgs();
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
        view.displayFailure("Not Implemented yet");
    }
}
