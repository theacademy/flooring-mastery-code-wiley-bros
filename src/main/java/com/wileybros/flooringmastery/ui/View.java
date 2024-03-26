package com.wileybros.flooringmastery.ui;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class View {

    private UserIO io = new UserIOImpl();

    public String displayMenu() {
        io.printLn("* 1. Display Orders");
        io.printLn("* 2. Add an Order");
        io.printLn("* 3. Edit an Order");
        io.printLn("* 4. Remove an Order");
        io.printLn("* 5. Export All Data");
        io.printLn("* 6. Quit");
        io.printLn("*");
        io.printLn("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readString("Please select from the above choices: ");
    }

    // STATIC DISPLAY METHODS
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

    // ASK METHODS
    public LocalDate askDate(){
        while (true) {
            try {
                return io.readLocalDate("Enter a date (dd/mm/yyyy): ", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                continue;
            }
        }
    }

    public LocalDate askFutureDate(){
        LocalDate date;
        do {
             date = askDate();
        } while (LocalDate.now().isAfter(date));
        return date;
    }

    public int askOrderID(){
        return io.readInt("Enter Order Number: ");
    }

    public String askCustomerName(){
        String name;
        do {
            name = io.readString("Enter the customer name: ");
        } while (!(name.isEmpty() || name.matches("[A-z0-9,. ]+")));
        return name.replaceAll(",",";");
    }

    public String askOrderState(Set<String> stateAbrs ){
        String abr;
        io.printLn(" - - - - - - - - - - - - - ");
        io.printLn("States " + stateAbrs);
        do {
            abr = io.readString("Enter a state abbreviation: ").toUpperCase();
        } while (!(abr.isEmpty() || stateAbrs.contains(abr)));
        return abr;
    }

    public String askOrderProduct( Set<Product> products ){
        String type;
        io.printLn(" - - - - - - - - - - - - - ");
        io.printLn("Products " + products.stream()
                .map(p -> p.getType() + ": Cost $" + p.getCostPSqF() + " Labour $" + p.getLabourPSqF())
                .collect(Collectors.joining(", ", "[", "]")));
        do {
            type = io.readString("Enter the product type: ");
            if (!type.isBlank()) type = type.substring(0,1).toUpperCase() + type.substring(1);
        } while (!(type.isEmpty() || products.contains(type)));
        return type;
    }

    public BigDecimal askOrderArea( ){
        Integer area;
        do {
            area = io.readInt("Enter the area: ");
            if (area == null) return null;
        } while (area < 100);
        return new BigDecimal(area);
    }


    public void displayOrderSummaryBanner(){
        io.printLn("---ORDER SUMMARY---");
    }

    public String confirmOrder(Object[] args){
        displayOrderSummaryBanner();
        displayOrderArgs(args);
        return io.readString("Do you want to place the order? (Y/N)\n").toUpperCase();
    }

    public void displayOrderArgs(Object[] args){
        io.print("%s - %s : %s - %.0fsqf\n", args[0], args[1], args[2], args[3]);
    public String updateOrder(Object[] args){
        displayOrderSummaryBanner();
        displayOrderArgs(args);
        return io.readString("Do you want to update the order? (Y/N)\n").toUpperCase();
    }

    public void displayOrder(Order order){
        io.print("%d) ", order.getId());
        displayOrderArgs(new Object[]{order.getCustomerName(), order.getState().getName(), order.getProduct().getType(), order.getArea()});
        io.printLn("$%.2f + $%.2f (+ $%.2f) = $%.2f\n",order.getMaterialCost(), order.getLabourCost(), order.getTax(), order.getTotal());
    }

    public void displayOrders(Set<Order> orders){
        for (Order order : orders){
            displayOrder(order);
        }
    }
    
}
