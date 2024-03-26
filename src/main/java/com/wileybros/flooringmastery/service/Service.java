package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.Set;

public interface Service {

    Set<Order> getOrdersOnData(LocalDate date);
    boolean addOrder(Object[] args, LocalDate futureDate);
    boolean updateOrder(Integer id, Object[] args);
    boolean removeOrder(Integer id);
    boolean exportAllData();
    Set<String> getStateAbrs();
    Set<String> getProductTypes();
}
