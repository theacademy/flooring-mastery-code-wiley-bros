package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface Dao {

    boolean readData();
    boolean writeData();
    boolean exportData();

    State getState(String abr);
    Product getProduct(String type);

    Set<Order> getOrdersOnDate(LocalDate date);
    Integer getNextID();
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean removeOrder(Integer id);

}
