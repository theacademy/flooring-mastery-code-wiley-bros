package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class ServiceImpl implements Service {
    private Dao dao;

    /**
     * Constructs a ServiceImpl object with the specified Dao.
     *
     * @param dao the data access object used for interacting with the data layer
     */
    public ServiceImpl(Dao dao) {
        this.dao = dao;
    }

    // 1) Displaying by Date  ----------------------

    /**
     * Retrieves a set of orders for the specified date.
     *
     * @param date the date for which to retrieve the orders
     * @return a set of orders for the specified date
     */
    @Override
    public Set<Order> getOrdersOnData(LocalDate date) {
        return dao.getOrdersOnDate(date);
    }

    // 2) Adding Order ----------------------------

    /**
     * Adds a new order to the system.
     *
     * @param order the order to be added
     * @return true if the order was successfully added, false otherwise
     */
    @Override
    public boolean addOrder(Order order) {
        if (order == null) return false;
        Order newOrder = new Order(dao.getNextID(), order.getCustomerName(), order.getState(), order.getProduct(),
                order.getArea(), order.getDate());
        return dao.addOrder(newOrder);
    }

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
    @Override
    public Order createOrder(String customerName, String abr, String type, BigDecimal area, LocalDate futureDate) {
        if (customerName == null || abr == null || type == null || area == null) return null;
        return new Order(null, customerName, dao.accessState(abr), dao.accessProduct(type), area, futureDate);
    }

    // 3) Updating Order --------------------------

    /**
     * Updates an existing order in the system.
     *
     * @param order the updated order
     * @return true if the order was successfully updated, false otherwise
     */
    @Override
    public boolean updateOrder(Order order) {
        return dao.addOrder(order);
    }

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
    @Override
    public Order combineOrder(Integer id, String customerName, String abr, String type, BigDecimal area) {
        Order original = getOrder(id);
        if (original == null) return null;
        return new Order(
                id,
                customerName == null ? original.getCustomerName() : customerName,
                abr == null ? original.getState() : dao.accessState(abr),
                type == null ? original.getProduct() : dao.accessProduct(type),
                area == null ? original.getArea() : area,
                original.getDate()
        );
    }

    // 4) Removing Order --------------------------

    /**
     * Removes an order from the system.
     *
     * @param order the order to be removed
     * @return true if the order was successfully removed, false otherwise
     */
    @Override
    public boolean removeOrder(Order order) {
        if (order == null) return false;
        return dao.removeOrder(order.getId());
    }

    /**
     * Retrieves an order with the specified ID.
     *
     * @param id the ID of the order to retrieve
     * @return the order with the specified ID, or null if the order does not exist
     */
    @Override
    public Order getOrder(Integer id) {
        return dao.getOrder(id);
    }

    // 5) Exporting all Data ----------------------

    /**
     * Exports all data to file backup.
     *
     * @return true if the data was successfully exported, false otherwise
     */
    @Override
    public boolean exportAllData() {
        return dao.exportData();
    }

    // General pass methods -----------------------

    /**
     * Retrieves a set of state abbreviations.
     *
     * @return a set of state abbreviations
     */
    @Override
    public Set<String> getStateAbrs() {
        return dao.getStateAbrs();
    }

    /**
     * Retrieves a set of products.
     *
     * @return a set of products
     */
    @Override
    public Set<Product> getProducts() {
        return dao.getProducts();
    }
}
