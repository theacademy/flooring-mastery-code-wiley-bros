package com.wileybros.flooringmastery.ui;

import com.wileybros.flooringmastery.dto.Order;
import org.springframework.stereotype.Component;

@Component
public class View {

    private UserIO io = new UserIOImpl();

    public int displayMenu() {
        io.printLn("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.printLn("* <<Flooring Program>>");
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

}
