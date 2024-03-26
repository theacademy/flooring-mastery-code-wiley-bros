package main.java.com.wileybros.flooringmastery.dao;

import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DaoImplTest {
    private Dao dao;

    @BeforeEach
    public void setUp(){
        dao = new DaoImpl();
        dao.readData();
    }

//    public Order(Integer id, String customerName, State state, Product product, BigDecimal area,
//                 LocalDate date) {
//        this.id = id;
//        this.customerName = customerName;
//        this.state = state;
//        this.product = product;
//        this.area = area;
//        this.date = date;
//    }


    @Test
    public void testAddOrder(){
        Order order = new Order(1);
        order.setCustomerName("David");
        order.setState(new State("TX", "Texas", new BigDecimal("0.6")));
        order.setProduct(new Product("Carpet", new BigDecimal(14), new BigDecimal(59)));
        order.setArea(new BigDecimal(10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("10-10-2030", formatter);
        order.setDate(date);

        dao.addOrder(order);
        assertTrue(dao.addOrder(order));

        LocalDate dateOfOrder = LocalDate.parse("10-10-2030", formatter);
        assertEquals(dao.getOrdersOnDate(dateOfOrder).toString(), "[1,David,TX,0.6,Carpet,10,14,59,140,590,4.38,734.38]");
    }

    @Test
    public void testReadData() {
        assertTrue(dao.readData()); // Check if data reading is successful
    }


}
