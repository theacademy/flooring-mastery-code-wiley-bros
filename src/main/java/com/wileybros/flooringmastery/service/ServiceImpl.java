package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;
import org.springframework.cglib.core.Local;
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
