package com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dto.Order;
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
