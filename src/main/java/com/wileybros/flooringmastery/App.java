package com.wileybros.flooringmastery;

import com.wileybros.flooringmastery.controller.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.wileybros.flooringmastery");
        appContext.refresh();

        Controller controller = appContext.getBean("controller", Controller.class);
        controller.run();
    } 
}
