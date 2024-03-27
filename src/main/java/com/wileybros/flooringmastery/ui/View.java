package com.wileybros.flooringmastery.ui;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
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
    
    /**
     * Constructs a View object with the specified UserIO.
     *
     * @param io the user io object used for interacting with the user
     */
    public View(UserIO io){
        this.io = io;
    }
    
    /**
     * Displays the menu options and prompts the user to select a choice.
     * 
     * @return the user's choice as a string
     */
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

    // STATIC DISPLAY METHODS -----------------------------------------------------------------------------------
    
    /**
     * Displays the welcome banner for the flooring program.
     */
    public void welcomeBanner(){
        io.printLn("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.printLn("* <<Flooring Program>>");
    }

    /**
     * Displays a success message.
     * 
     * @param string the success message to display
     */
    public void displaySuccess(String string){
        io.printLn("Operation Success : "+string);
    }

    /**
     * Displays a failure message.
     * 
     * @param string the failure message to display
     */
    public void displayFailure(String string){
        io.printLn("Operation Failure : "+string);
    }

    /**
     * Displays an error message.
     * 
     * @param string the error message to display
     */
    public void displayError(String string){
        io.printLn("Error : "+string);
    }

    /**
     * Displays a goodbye message.
     */
    public void displayExit(){
        io.printLn("Goodbye!");
    }

    // ASK METHODS -----------------------------------------------------------------------------------
    
    /**
     * Asks the user to enter a date and returns it as a LocalDate object.
     * 
     * @return the user-entered date as a LocalDate object
     */
    public LocalDate askDate(){
        while (true) {
            try {
                return io.readLocalDate("Enter a date (dd/mm/yyyy): ", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                continue;
            }
        }
    }

    /**
     * Asks the user to enter a future date and returns it as a LocalDate object.
     * 
     * @return the user-entered future date as a LocalDate object
     */
    public LocalDate askFutureDate(){
        LocalDate date;
        do {
             date = askDate();
        } while (LocalDate.now().isAfter(date));
        return date;
    }

    /**
     * Asks the user to enter an order ID and returns it as an Integer.
     * 
     * @return the user-entered order ID as an Integer
     */
    public Integer askOrderID(){
        return io.readInt("Enter Order Number: ");
    }

    /**
     * Asks the user to enter a customer name and returns it as a String.
     * 
     * @return the user-entered customer name as a String
     */
    public String askCustomerName(){
        String name;
        do {
            name = io.readString("Enter the customer name: ");
            if (name.isBlank()) return null;
        } while (!name.matches("[A-z0-9,. ]+"));
        return name.replaceAll(",",";");
    }

    /**
     * Asks the user to enter an order state abbreviation and returns it as a String.
     * 
     * @param stateAbrs a set of valid state abbreviations
     * @return the user-entered state abbreviation as a String
     */
    public String askOrderState(Set<String> stateAbrs ){
        String abr;
        io.printLn(" - - - - - - - - - - - - - ");
        io.printLn("States " + stateAbrs);
        do {
            abr = io.readString("Enter a state abbreviation: ").toUpperCase();
            if (abr.isBlank()) return null;
        } while (!stateAbrs.contains(abr));
        return abr;
    }

    /**
     * Asks the user to enter an order product type and returns it as a String.
     * 
     * @param products a set of valid products
     * @return the user-entered product type as a String
     */
    public String askOrderProduct( Set<Product> products ){
        String type;
        io.printLn(" - - - - - - - - - - - - - ");
        io.printLn("Products " + products.stream()
                .map(p -> p.getType() + ": Cost $" + p.getCostPSqF() + " Labour $" + p.getLabourPSqF())
                .collect(Collectors.joining(", ", "[", "]")));
        while (true) {
            type = io.readString("Enter the product type: ");
            if (type.isBlank()) return null;
            type = type.substring(0,1).toUpperCase() + type.substring(1);

            for (Product p : products) {
                if (Objects.equals(p, type)) {
                    return type;
                }
            }
        }
    }

    /**
     * Asks the user to enter an order area and returns it as a BigDecimal.
     * 
     * @return the user-entered order area as a BigDecimal
     */
    public BigDecimal askOrderArea( ){
        Integer area;
        do {
            area = io.readInt("Enter the area: ");
            if (area == null) return null;
        } while (area < 100);
        return new BigDecimal(area);
    }

    // CONFIRMATION METHODS -------------------------------------------------------------------------------------

    /**
     * Asks the user for confirmation to place an order.
     * 
     * @param order the order to be placed
     * @return true if the user confirms, false otherwise
     */
    public boolean placeOrderConfirmation(Order order){
        if (order == null) return true; // This will skip showing if some parameters where null, it will then
        // fail.
        io.printLn("---Order Summary---");
        displayOrder(order);
        return io.readString("Do you want to place this order? (Y/N)\n").equalsIgnoreCase("Y");
    }

    /**
     * Asks the user for confirmation to update an order.
     * 
     * @param order the order to be updated
     * @return true if the user confirms, false otherwise
     */
    public boolean updateOrderConfirmation(Order order) {
        if (order == null) return true; // This will skip showing if there is no matching order, it will then fail.
        io.printLn("---Update Summary---");
        displayOrder(order);
        return io.readString("Do you want to update this order? (Y/N)\n").equalsIgnoreCase("Y");
    }

    /**
     * Asks the user for confirmation to remove an order.
     * 
     * @param order the order to be removed
     * @return true if the user confirms, false otherwise
     */
    public boolean removeOrderConfirmation(Order order) {
        if (order == null) return true; // This will skip showing if there is no matching order, it will then fail.
        io.printLn("---Removal Summary---");
        displayOrder(order);
        return io.readString("Do you want to remove this order? (Y/N)\n").equalsIgnoreCase("Y");
    }

    // DYNAMIC DISPLAYS METHODS -----------------------------------------------------------------------------------

    /**
     * Displays the details of an order.
     * 
     * @param order the order to be displayed
     */
    public void displayOrder(Order order){
        io.printLn("%s) %s - %s : %s - %.0fsqf\n $%.2f + $%.2f (+ $%.2f) = $%.2f\n",
                order.getId() == null ? "??" : order.getId().toString(), order.getCustomerName().replaceAll(";", ","),
                order.getState().getName(), order.getProduct().getType(), order.getArea(), order.getMaterialCost(),
                order.getLabourCost(), order.getTax(), order.getTotal());
    }

    /**
     * Displays the details of multiple orders.
     * 
     * @param orders a set of orders to be displayed
     */
    public void displayOrders(Set<Order> orders){
        for (Order order : orders){
            displayOrder(order);
        }
    }
    
}
