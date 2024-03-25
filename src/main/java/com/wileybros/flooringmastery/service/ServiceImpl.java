package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public class ServiceImpl implements Service{
    @Override
    public Set<Order> getOrdersOnData(LocalDate date) {
        return null;
    }

    @Override
    public boolean addOrder(Integer id, Object[] args) {
        return false;
    }

    @Override
    public boolean updateOrder(Integer id, Object[] args) {
        return false;
    }

    @Override
    public boolean removeOrder(Integer id) {
        return false;
    }

    @Override
    public boolean exportAllData() {
        return false;
    }
}
