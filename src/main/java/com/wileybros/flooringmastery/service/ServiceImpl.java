package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class ServiceImpl implements Service{
    private Dao dao;

    @Autowired
    public ServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public Set<Order> getOrdersOnData(LocalDate date) {
        return dao.getOrdersOnDate(date);
    }

    @Override
    public boolean addOrder(Object[] args, LocalDate futureDate) {
        String customerName = args[0].toString();
        String stateAbr = args[1].toString();
        String productType = args[2].toString();
        BigDecimal area = new BigDecimal(args[3].toString());
        State state = dao.getState(stateAbr);
        Product product = dao.getProduct(productType);

        Order newOrder = new Order(dao.getNextID(), customerName, state, product, area, futureDate);
        return dao.addOrder(newOrder);
    }

    @Override
    public boolean updateOrder(Integer id, Object[] args) {
        String customerName = args[0].toString();
        String stateAbr = args[1].toString();
        String productType = args[2].toString();
        BigDecimal area = new BigDecimal(args[3].toString());
        State state = dao.getState(stateAbr);
        Product product = dao.getProduct(productType);

        Order orderToUpdate = new Order(id, customerName, state, product, area, null);
        return dao.updateOrder(orderToUpdate);
    }

    @Override
    public boolean removeOrder(Integer id) {
        return dao.removeOrder(id);
    }

    @Override
    public boolean exportAllData() {
        return false;
    }
}
