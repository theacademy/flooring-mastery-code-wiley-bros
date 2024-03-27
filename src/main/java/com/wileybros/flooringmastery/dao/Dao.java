package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface Dao {

    // File Handling ---------------------------

    /**
     * Reads the data from the files.
     *
     * @return true if the data was successfully read, false otherwise.
     */
    boolean readData();
    
    /**
     * Writes the data to files.
     *
     * @return true if the data was successfully written, false otherwise.
     */
    boolean writeData();
    
    /**
     * Exports the data to a file in the backup folder.
     *
     * @return true if the data was successfully exported, false otherwise.
     */
    boolean exportData();

    // Order Handling ---------------------------

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve.
     * @return the order with the specified ID, or null if not found.
     */
    Order getOrder(Integer id);
    
    /**
     * Retrieves all orders on a specific date.
     *
     * @param date the date to filter the orders.
     * @return a set of orders placed on the specified date.
     */
    Set<Order> getOrdersOnDate(LocalDate date);
    
    /**
     * Retrieves the next available ID for a new order.
     *
     * @return the next available ID.
     */
    Integer getNextID();
    
    /**
     * Adds a new order or updates an existing order.
     *
     * @param order the order to add.
     * @return true if the order was successfully added, false otherwise.
     */
    boolean addOrder(Order order);
    
    /**
     * Removes an order by its ID.
     *
     * @param id the ID of the order to remove.
     * @return true if the order was successfully removed, false otherwise.
     */
    boolean removeOrder(Integer id);

    // State and Product Handling ---------------
    
    /**
     * Retrieves a state by its abbreviation.
     *
     * @param abr the abbreviation of the state.
     * @return the state with the specified abbreviation, or null if not found.
     */
    State accessState(String abr);
    
    /**
     * Retrieves a product by its type.
     *
     * @param type the type of the product.
     * @return the product with the specified type, or null if not found.
     */
    Product accessProduct(String type);
    
    /**
     * Retrieves all state abbreviations.
     *
     * @return a set of all state abbreviations.
     */
    Set<String> getStateAbrs();
    
    /**
     * Retrieves all products.
     *
     * @return a set of all products.
     */
    Set<Product> getProducts();
}
