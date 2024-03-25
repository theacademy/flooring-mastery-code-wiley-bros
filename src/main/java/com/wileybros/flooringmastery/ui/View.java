package com.wileybros.flooringmastery.ui;

import org.springframework.stereotype.Component;

@Component
public class View {
<<<<<<< Updated upstream
=======

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
        io.printLn("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
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
        return io.readLocalDate("Enter a date: ");
    }

    public LocalDate askFutureDate(){
        // TODO check that askFutureDate works correctly.
        LocalDate date;
        do {
             date = askDate();
        } while (LocalDate.now().isBefore(date));
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
        io.printLn(order.toString());
    }

    public void displayOrders(Set<Order> orders){
        for (Order order : orders){
            displayOrder(order);
        }
    }
>>>>>>> Stashed changes
}
