package com.wileybros.flooringmastery.ui;

import com.wileybros.flooringmastery.dto.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        return io.readLocalDate("Enter a date (dd/mm/yyyy): ", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

    // TODO Check if it's not blank and allows only [A-z0-9,.]
    // TODO Translate , to ;
    public String askCustomerName(){
        String name;
        do {
            name = io.readString("Enter the customer name: ");
        } while (name.isBlank() || !name.matches("[A-z0-9,.]+"));
        return name.replaceAll(",",";");
    }

    // TODO Check is valid state
    public String askOrderState(String original, Set<String> stateAbrs ){
        io.printLn(" - - - - - - - - - - - - - ");
        String abr;
        do {
            io.printLn("States " + stateAbrs);
//            if (original.isBlank()) io.print("(%s)");
            abr = io.readString("Enter a state abbreviation: ");
        } while (abr.isBlank() || !stateAbrs.contains(abr));
        return abr;
    }

    // TODO CHeck if valid product
    // TODO add product pricing info
    public String askOrderProduct( Set<String> productTypes ){
        io.printLn(" - - - - - - - - - - - - - ");
        String type;
        do {
            io.printLn("Products " + productTypes);
            type = io.readString("Enter the product type: ");
        } while (type.isBlank() || !productTypes.contains(type));
        return type;
    }

    // TODO Check if area is greater than 100
    public BigDecimal askOrderArea( ){
        Integer area;
        do {
            area = io.readInt("Enter the area: ");
        } while (area < 100);
        return new BigDecimal(area);
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
