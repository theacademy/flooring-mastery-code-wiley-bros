package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.Set;

public interface Dao {

    boolean readData();
    boolean writeData();
    boolean exportData();

    Set<Order> getOrdersOnDate(LocalDate date);
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean removeOrder(String id);

}
