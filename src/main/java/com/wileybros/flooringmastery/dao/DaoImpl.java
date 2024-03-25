package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class DaoImpl implements Dao {
    @Override
    public boolean readData() {
        return false;
    }

    @Override
    public boolean writeData() {
        return false;
    }

    @Override
    public boolean exportData() {
        return false;
    }

    @Override
    public State getState(String abr) {
        return null;
    }

    @Override
    public Product getProduct(String type) {
        return null;
    }

    @Override
    public Set<Order> getOrdersOnDate(LocalDate date) {
        return null;
    }

    @Override
    public boolean distinctID(String id) {
        return false;
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public boolean removeOrder(String id) {
        return false;
    }
}
