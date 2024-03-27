package com.wileybros.flooringmastery;

import com.wileybros.flooringmastery.controller.Controller;
import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class App {

    /**
     * The main method of the Flooring Mastery application.
     * This method initializes the application context, retrieves the controller bean, and runs the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.wileybros.flooringmastery");
        appContext.refresh();

        Controller controller = appContext.getBean("controller", Controller.class);
        controller.run();
    }
}
