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
public class ServiceImpl implements Service{
    private Dao dao;

    public ServiceImpl(Dao dao) {
        this.dao = dao;
        dao.readData();
    }

    @Override
    public Set<Order> getOrdersOnData(LocalDate date) {
        return dao.getOrdersOnDate(date);
    }

    @Override
    public boolean addOrder(Object[] args, LocalDate futureDate) {
        String customerName = (String) args[0];
        String stateAbr = (String) args[1];
        String productType = (String) args[2];
        BigDecimal area = (BigDecimal) args[3];
        State state = dao.accessState(stateAbr);
        Product product = dao.accessProduct(productType);

        Order newOrder = new Order(dao.getNextID(), customerName, state, product, area, futureDate);
        return dao.addOrder(newOrder);
    }

    @Override
    public boolean updateOrder(Integer id, Object[] args) {
        String customerName = args[0].toString();
        String stateAbr = args[1].toString();
        String productType = args[2].toString();
        BigDecimal area = new BigDecimal(args[3].toString());
        State state = dao.accessState(stateAbr);
        Product product = dao.accessProduct(productType);

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
