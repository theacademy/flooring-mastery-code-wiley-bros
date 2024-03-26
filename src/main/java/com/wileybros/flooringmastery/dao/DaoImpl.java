package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.stereotype.Component;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DaoImpl implements Dao {
    private Map<Integer, State> states;
    private Map<Integer, Product> products;
    private Map<Integer, Order> orders;
    final private String dataSource = "data";
    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMddyyyy");
    private Integer lastMaxID = 0;

    @Override
    public boolean readData() {
        boolean firstStep = readProductData() && readStateData();
        if (firstStep && readOrderData()) return true;
        return false;
    }

    private boolean readOrderData() {
        try {                   // Tries to get Orders
            File directory = new File(dataSource + "/Orders");

            Scanner scanner;
            orders = new HashMap<>();
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {

                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(file)));

                    String dateString = file.getName().substring(7, 15);
                    LocalDate date = LocalDate.parse(dateString,
                            dateFormat);
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String[] args = scanner.nextLine().split(",");
                        Order temp = new Order(
                                Integer.parseInt(args[0]),              // OrderNumber
                                args[1],                                // CustomerName
                                states.get(args[2].hashCode()),         // State
                                products.get(args[4].hashCode()),     // ProductType
                                new BigDecimal(args[5]),                // Area
                                date);                                  // Date in Filename
                        if (temp.getId() > lastMaxID) lastMaxID = temp.getId();
                        orders.put(temp.hashCode(), temp);
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
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                State temp = State.parseState(scanner.nextLine());
                states.put(temp.hashCode(), temp);
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
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Product temp = Product.parseProduct(scanner.nextLine());
                products.put(temp.hashCode(), temp);
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean writeData() {
        // This mess deletes all the files in the folder <datasource>/Orders
        Arrays.stream(Objects.requireNonNull(new File(dataSource + "/Orders").listFiles())).map(File::delete);

        // Repopulates the folder with data from memory
        try {
            Map<LocalDate, PrintWriter> outs = new HashMap<>();
            for (Order order : orders.values()) {
                if (!outs.containsKey(order.getDate())) {
                    outs.put(order.getDate(),
                            new PrintWriter(new FileWriter(dataSource+"/Orders/Orders_" +
                                    order.getDate().format(dateFormat) + ".txt")));
                    outs.get(order.getDate()).println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
                }

                outs.get(order.getDate()).println(order);
                outs.get(order.getDate()).flush();
            }
            for (PrintWriter out : outs.values()) {
                out.close();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean exportData() {
        return false;
    }

    @Override
    public State accessState(String abr) {
        return states.get(abr.hashCode());
    }

    @Override
    public Product accessProduct(String type) {
        return products.get(type.hashCode());
    }

    @Override
    public Set<Order> getOrdersOnDate(LocalDate date) {
        return orders.values().stream().filter(o -> o.getDate().equals(date)).collect(Collectors.toSet());
    }

    @Override
    public Integer getNextID() {
        return ++lastMaxID;
    }

    @Override
    public boolean addOrder(Order order) {
        try {
            orders.put(order.hashCode(), order);
            writeData();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }


    @Override
    public boolean updateOrder(Order updates) {
        Order order = orders.get(updates.hashCode());
        try {
            if (!updates.getCustomerName().isBlank()) order.setCustomerName(updates.getCustomerName());
            if (updates.getState() != null) order.setState(updates.getState());
            if (updates.getProduct() != null) order.setProduct(updates.getProduct());
            if (updates.getArea() != null) order.setArea(updates.getArea());
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeOrder(Integer id) {
        try {
            orders.remove(id.hashCode());
            writeData();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
