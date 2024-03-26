package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface Service {

    // 1) Displaying by Date  ----------------------
    Set<Order> getOrdersOnData(LocalDate date);

    // 2) Adding Order ----------------------------
    boolean addOrder(Order order);
    Order createOrder(String customerName, String abr, String type, BigDecimal area, LocalDate futureDate);

    // 3) Updating Order --------------------------
    boolean updateOrder(Order order);
    Order combineOrder(Integer id, String customerName, String abr, String type, BigDecimal area);

    // 4) Removing Order --------------------------
    boolean removeOrder(Order order);
    Order getOrder(Integer id);

    // 5) Exporting all Data ----------------------
    boolean exportAllData();

    // General pass methods -----------------------
    Set<String> getStateAbrs();
    Set<Product> getProducts();
}
