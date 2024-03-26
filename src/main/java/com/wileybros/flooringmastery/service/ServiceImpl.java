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
    }

    // 1) Displaying by Date  ----------------------

    @Override
    public Set<Order> getOrdersOnData(LocalDate date) {
        return dao.getOrdersOnDate(date);
    }

    // 2) Adding Order ----------------------------

    @Override
    public boolean addOrder(Order order) {
        if (order == null) return false;
        Order newOrder = new Order(dao.getNextID(), order.getCustomerName(), order.getState(), order.getProduct(),
                order.getArea(),order.getDate());
        return dao.addOrder(newOrder);
    }

    @Override
    public Order createOrder(String customerName, String abr, String type, BigDecimal area, LocalDate futureDate) {
        if (customerName == null || abr == null || type == null || area == null) return null;
        return new Order(null, customerName, dao.accessState(abr), dao.accessProduct(type), area, futureDate);
    }

    // 3) Updating Order --------------------------

    @Override
    public boolean updateOrder(Order order) {
        return dao.addOrder(order);
    }

    @Override
    public Order combineOrder(Integer id, String customerName, String abr, String type, BigDecimal area) {
        Order original = getOrder(id);
        return new Order(
                id,
                customerName == null ? original.getCustomerName() : customerName,
                abr == null ? original.getState() : dao.accessState(abr),
                type == null ? original.getProduct() : dao.accessProduct(type),
                area == null ? original.getArea() : area,
                original.getDate()
        );
    }

    // 4) Removing Order --------------------------

    @Override
    public boolean removeOrder(Order order) {
        if (order == null) return false;
        return dao.removeOrder(order.getId());
    }
    @Override
    public Order getOrder(Integer id) {
        return dao.getOrder(id);
    }

    // 5) Exporting all Data ----------------------

    @Override
    public boolean exportAllData() {
        return dao.exportData();
    }

    // General pass methods -----------------------

    @Override
    public Set<String> getStateAbrs() {
        return dao.getStateAbrs();
    }

    @Override
    public Set<Product> getProducts() {
        return dao.getProducts();
    }

}
