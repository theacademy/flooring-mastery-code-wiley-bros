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

/**
 * The implementation of the Dao interface that provides data access and manipulation operations for the flooring application.
 * This class handles reading and writing data from/to files, as well as managing orders, states, and products.
 */
@Component
public class DaoImpl implements Dao {
    private Map<Integer, State> states;
    private Map<Integer, Product> products;
    private Map<Integer, Order> orders;
    final private String dataSource;
    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMddyyyy");
    private Integer lastMaxID = 0;

    /**
     * Constructs a new DaoImpl object and reads the data from the files using default.
     */
    public DaoImpl() {
        dataSource = "data";
        readData();
    }

    /**
     * Constructs a new DaoImpl object and reads the data from the files from custom folder.
     *
     * @param dataSource the override for a custom data folder
     */
    public DaoImpl(String dataSource) {
        this.dataSource = dataSource;
        readData();
    }

    // File Handling --------------------------------------------------

    /**
     * Reads the data from the files using the readOrderData, readProductData
     * and readStateData methods.
     *
     * @return true if the data was successfully read, false otherwise.
     */
    @Override
    public boolean readData() {
        boolean firstStep = readProductData() && readStateData();
        if (firstStep && readOrderData()) return true;
        return false;
    }

    /**
     * Reads the order data from the files.
     *
     * @return true if the order data was successfully read, false otherwise.
     */
    private boolean readOrderData() {
        try {
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

    /**
     * Reads the state data from the file.
     *
     * @return true if the state data was successfully read, false otherwise.
     */
    private boolean readStateData() {
        try {
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

    /**
     * Reads the product data from the file.
     *
     * @return true if the product data was successfully read, false otherwise.
     */
    private boolean readProductData() {
        try {
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

    /**
     * Writes the data to files.
     *
     * @return true if the data was successfully written, false otherwise.
     */
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

    /**
     * Exports the data to a file in the backup folder.
     *
     * @return true if the data was successfully exported, false otherwise.
     */
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

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return the order with the specified ID, or null if not found.
     */
    @Override
    public Order getOrder(Integer id) {
        return orders.get(id.hashCode());
    }

    /**
     * Retrieves all orders on a specific date.
     *
     * @param date the date to filter the orders.
     * @return a set of orders placed on the specified date.
     */
    @Override
    public Set<Order> getOrdersOnDate(LocalDate date) {
        return orders.values().stream().filter(o -> o.getDate().equals(date)).collect(Collectors.toSet());
    }

    /**
     * Retrieves the next available ID for a new order.
     *
     * @return the next available ID.
     */
    @Override
    public Integer getNextID() {
        return ++lastMaxID;
    }

    /**
     * Adds a new order or updates an existing order.
     *
     * @param order the order to add.
     * @return true if the order was successfully added, false otherwise.
     */
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

    /**
     * Removes an order by its ID.
     *
     * @param id the ID of the order to remove.
     * @return true if the order was successfully removed, false otherwise.
     */
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

    /**
     * Retrieves a state by its abbreviation.
     *
     * @param abr the abbreviation of the state.
     * @return the state with the specified abbreviation, or null if not found.
     */
    @Override
    public State accessState(String abr) {
        return states.get(abr.hashCode());
    }

    /**
     * Retrieves a product by its type.
     *
     * @param type the type of the product.
     * @return the product with the specified type, or null if not found.
     */
    @Override
    public Product accessProduct(String type) {
        if (type.isBlank()) return null;
        return products.get(type.hashCode());
    }

    /**
     * Retrieves all state abbreviations.
     *
     * @return a set of all state abbreviations.
     */
    @Override
    public Set<String> getStateAbrs() {
        return states.values().stream().map(State::getAbr).collect(Collectors.toSet());
    }

    /**
     * Retrieves all products.
     *
     * @return a set of all products.
     */
    @Override
    public Set<Product> getProducts() {
        return new HashSet<>(products.values());
    }
}
