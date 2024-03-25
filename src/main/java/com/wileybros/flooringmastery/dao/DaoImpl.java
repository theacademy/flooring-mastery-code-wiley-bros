package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DaoImpl implements Dao {
    Map<State, State> states;
    Map<Product, Product> products;
    Map<Order, Order> orders;
    final private String dataSource = "data";

    @Override
    public boolean readData() {
        boolean firstStep = readProductData() && readStateData();
        if (firstStep && readOrderData()) return true;
        return false;
    }

    private boolean readOrderData() {
        try {                   // Tries to get Orders
            String directoryPath = dataSource + "/Orders";
            File directory = new File(directoryPath);

            Scanner scanner;
            orders = new HashMap<>();
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {

                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(file)));

                    LocalDate date = LocalDate.parse(file.getName().replace("[A-z._]*", ""),
                            DateTimeFormatter.ofPattern("MMddyyyy"));
                    while (scanner.hasNextLine()) {
                        String[] args = scanner.nextLine().split(",");
                        //                          orderNumber    -    customerName   -   State
                        Order temp = new Order(Integer.parseInt(args[0]), args[1], states.get(args[2]),
                        //             productType     -     area       -       date
                                products.get(args[4]),new BigDecimal(args[5]), date);
                        orders.put(temp, temp);
                    }

                }
            }

        } catch (FileNotFoundException | SecurityException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean readStateData() {
        try {                   // Tries to get States
            Scanner scanner;
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(dataSource + "/States.txt")));

            states = new HashMap<>();
            while (scanner.hasNextLine()) {
                State temp = State.parseState(scanner.nextLine());
                states.put(temp, temp);
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private boolean readProductData() {
        try {               // Tries to get Products
            Scanner scanner;
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(dataSource + "/Products.txt")));

            products = new HashMap<>();
            while (scanner.hasNextLine()) {
                Product temp = Product.parseProduct(scanner.nextLine());
                products.put(temp, temp);
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean writeData() {
        return false;
    }

    @Override
    public boolean exportData() {
        return false;
    }

    @Override
    public State getState(String abr) {
        return null;
    }

    @Override
    public Product getProduct(String type) {
        return null;
    }

    @Override
    public Set<Order> getOrdersOnDate(LocalDate date) {
        return null;
    }

    @Override
    public boolean distinctID(String id) {
        return false;
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public boolean removeOrder(String id) {
        return false;
    }
}
