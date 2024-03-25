package com.wileybros.flooringmastery;

import com.wileybros.flooringmastery.controller.Controller;
import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
//        appContext.scan("com.wileybros.flooringmastery");
//        appContext.refresh();
//
//        Controller controller = appContext.getBean("controller", Controller.class);
//        controller.run();
        Dao dao = new DaoImpl();
        dao.readData();
//        System.out.println(dao.getOrdersOnDate(LocalDate.parse("2013-06-03")));
        int a = 3;
        System.out.println(++a);
//        System.out.println(++a);
    }
}
