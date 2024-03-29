package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface Service {

    // 1) Displaying by Date  ----------------------

    /**
     * Retrieves a set of orders for the specified date.
     *
     * @param date the date for which to retrieve the orders
     * @return a set of orders for the specified date
     */
    Set<Order> getOrdersOnData(LocalDate date);

    // 2) Adding Order ----------------------------

    /**
     * Adds a new order to the system.
     *
     * @param order the order to be added
     * @return true if the order was successfully added, false otherwise
     */
    boolean addOrder(Order order);

    /**
     * Creates a new order with the specified details.
     *
     * @param customerName the name of the customer
     * @param abr          the abbreviation of the state
     * @param type         the type of product
     * @param area         the area of the order
     * @param futureDate   the future date for the order
     * @return the created order, or null if any of the parameters are null
     */
    Order createOrder(String customerName, String abr, String type, BigDecimal area, LocalDate futureDate);

    // 3) Updating Order --------------------------

    /**
     * Updates an existing order in the system.
     *
     * @param order the updated order
     * @return true if the order was successfully updated, false otherwise
     */
    boolean updateOrder(Order order);

    /**
     * Combines the specified details with an existing order to create a new order.
     *
     * @param id           the ID of the existing order
     * @param customerName the updated customer name
     * @param abr          the updated state abbreviation
     * @param type         the updated product type
     * @param area         the updated area
     * @return the combined order, or null if the original order does not exist
     */
    Order combineOrder(Integer id, String customerName, String abr, String type, BigDecimal area);

    // 4) Removing Order --------------------------
    
    /**
     * Removes an order from the system.
     *
     * @param order the order to be removed
     * @return true if the order was successfully removed, false otherwise
     */
    boolean removeOrder(Order order);

    /**
     * Retrieves an order with the specified ID.
     *
     * @param id the ID of the order to retrieve
     * @return the order with the specified ID, or null if the order does not exist
     */
    Order getOrder(Integer id);

    // 5) Exporting all Data ----------------------

    /**
     * Exports all data to file backup.
     *
     * @return true if the data was successfully exported, false otherwise
     */
    boolean exportAllData();

    // General pass methods -----------------------

    /**
     * Retrieves a set of state abbreviations.
     *
     * @return a set of state abbreviations
     */
    Set<String> getStateAbrs();

    /**
     * Retrieves a set of products.
     *
     * @return a set of products
     */
    Set<Product> getProducts();
}
