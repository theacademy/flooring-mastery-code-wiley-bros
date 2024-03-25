package com.wileybros.flooringmastery.service;

import com.wileybros.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.Set;

public interface Service {

    Set<Order> getOrdersOnData(LocalDate date);
<<<<<<< Updated upstream
    boolean addOrder(Integer id, Object[] args);
=======
    boolean addOrder(Object[] args, LocalDate futureDate);
>>>>>>> Stashed changes
    boolean updateOrder(Integer id, Object[] args);
    boolean removeOrder(Integer id);
    boolean exportAllData();
}
