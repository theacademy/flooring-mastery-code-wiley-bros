package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface Dao {

    // File Handling ---------------------------
    boolean readData();
    // TODO Does this delete empty dates?
    boolean writeData();
    boolean exportData();

    // Order Handling ---------------------------
    Set<Order> getOrdersOnDate(LocalDate date);
    Integer getNextID();
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean removeOrder(Integer id);

    // State and Product Handling ---------------
    State accessState(String abr);
    Product accessProduct(String type);
    Set<String> getStateAbrs();
    Set<Product> getProducts();
}
