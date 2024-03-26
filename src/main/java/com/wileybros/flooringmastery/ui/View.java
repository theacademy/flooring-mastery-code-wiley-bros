package com.wileybros.flooringmastery.ui;

import com.wileybros.flooringmastery.dto.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
public class View {

    private UserIO io = new UserIOImpl();

    public int displayMenu() {
        io.printLn("* 1. Display Orders");
        io.printLn("* 2. Add an Order");
        io.printLn("* 3. Edit an Order");
        io.printLn("* 4. Remove an Order");
        io.printLn("* 5. Export All Data");
        io.printLn("* 6. Quit");
        io.printLn("*");
        io.printLn("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices: ");
    }

    public void welcomeBanner(){
        io.printLn("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.printLn("* <<Flooring Program>>");
    }

    public void displaySuccess(String string){
        io.printLn("Action Success "+string);
    }

    public void displayFailure(String string){
        io.printLn("Action Failure "+string);
    }

    public void displayError(String string){
        io.printLn("Error: "+string);
    }

    public void displayExit(){
        io.printLn("Goodbye!");
    }

    public LocalDate askDate(){
        return io.readLocalDate("Enter a date (dd/MM/yyyy): ", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate askFutureDate(){
        // TODO check that askFutureDate works correctly.
        LocalDate date;
        do {
             date = askDate();
        } while (LocalDate.now().isAfter(date));
        return date;
    }

    public int askOrderID(){
        return io.readInt("Enter Order Number: ");
    }

    public Object[] askOrderArgs(){
        Object[] objs = new Object[4];
        objs[0] = io.readString("Enter the customer name: ");
        objs[1] = io.readString("Enter the state abbreviation: ");
        objs[2] = io.readString("Enter the product type: ");
        objs[3] = io.readBigDecimal("Enter the area: ");
        return objs;
    }

    public void displayOrder(Order order){
        io.printLn("%d) %s - %s : %s - %.0fsqf\n $%.2f + $%.2f (+ $%.2f) = $%.2f\n", order.getId(),
                order.getCustomerName(), order.getState().getName(), order.getProduct().getType(), order.getArea(),
                order.getMaterialCost(), order.getLabourCost(), order.getTax(), order.getTotal());
    }

    public void displayOrders(Set<Order> orders){
        for (Order order : orders){
            displayOrder(order);
        }
    }
    
}
