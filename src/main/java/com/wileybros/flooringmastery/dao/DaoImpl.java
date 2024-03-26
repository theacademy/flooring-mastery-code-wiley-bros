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

    public DaoImpl() {
        readData();
    }

    // File Handling --------------------------------------------------
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
                    scanner.close();
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
            scanner.close();
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
            scanner.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean writeData() {
        try {
            // This mess deletes all the files in the folder <datasource>/Orders
            File dir = new File(dataSource + "/Orders");
            for (File file : dir.listFiles()) {
                file.delete();
            }


            // Repopulates the folder with data from memory
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
        try {
            PrintWriter out = new PrintWriter(new FileWriter(dataSource + "/Backup/DataExport.txt"));
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
            for (Order order : orders.values()) {
                out.println(order+","+order.getDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                out.flush();
            }
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // Order Handling --------------------------------------------------

    @Override
    public Order getOrder(Integer id) {
        return orders.get(id.hashCode());
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
    public boolean removeOrder(Integer id) {
        try {
            orders.remove(id.hashCode());
            writeData();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }


    // State and Product Handling ----------------------------------------
    @Override
    public State accessState(String abr) {
        return states.get(abr.hashCode());
    }

    @Override
    public Product accessProduct(String type) {
        if (type.isBlank()) return null;
        return products.get(type.hashCode());
    }

    @Override
    public Set<String> getStateAbrs() {
        return states.values().stream().map(State::getAbr).collect(Collectors.toSet());
    }

    @Override
    public Set<Product> getProducts() {
        return new HashSet<>(products.values());
    }
}
