import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class DaoImplTest {

    private Dao dao;

    @BeforeEach
    public void setUp(){
        dao = new DaoImpl();
        dao.readData();
    }

    @Test
    public void testReadData() {
        assertTrue(dao.readData());
    }

    @Test
    public void testAddOrder(){
        Order order = new Order(999);
        order.setCustomerName("David");
        order.setState(new State("TX", "Texas", new BigDecimal("0.6")));
        order.setProduct(new Product("Carpet", new BigDecimal(14), new BigDecimal(59)));
        order.setArea(new BigDecimal(10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("10-10-2030", formatter);
        order.setDate(date);

        assertTrue(dao.addOrder(order)); //Add the order

        String expected = "999,David,TX,0.6,Carpet,10,14,59,140,590,4.38,734.38"; // This is the expected result

        Set<Order> ordersOnDate = dao.getOrdersOnDate(date);
        assertTrue(ordersOnDate.contains(order)); // Test if the order has benn added

        String actual = ordersOnDate.stream() // Start the stream of orders
                .map(Order::toString) // Convert each order to string
                .findFirst()
                .orElse("");

        assertEquals(actual, expected);

        dao.removeOrder(order.getId());
    }

    @Test
    public void testAccessState(){
        State texas = dao.accessState("TX");
        State newTexas = new State("TX", "Texas", new BigDecimal("4.45"));

        assertEquals(texas.getAbr(), newTexas.getAbr());
        assertEquals(texas.getName(), newTexas.getName());
        assertEquals(texas.getTaxRate(), newTexas.getTaxRate());
        assertEquals(texas, newTexas);
    }

    @Test
    public void testAccessProduct(){
        Product wood = dao.accessProduct("Wood");
        Product newWood = new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"));
        assertEquals(wood, newWood);
    }

    @Test
    public void testGetOrdersOnDate(){
        Order order = new Order(998);
        order.setCustomerName("Alex");
        order.setState(dao.accessState("CA"));
        order.setProduct(dao.accessProduct("Tile"));
        order.setArea(new BigDecimal(25000));
        LocalDate date = LocalDate.parse("15-10-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        order.setDate(date);
        dao.addOrder(order);

        Set<Order> ordersOnDate = dao.getOrdersOnDate(date);
        assertTrue(ordersOnDate.contains(order));

        dao.removeOrder(order.getId());
    }

    @Test
    public void testUpdateOrder(){
        Order orderToUpdate = new Order(dao.getNextID());
        orderToUpdate.setCustomerName("TestUpdateOrder");
        orderToUpdate.setState(dao.accessState("TX"));
        orderToUpdate.setProduct(dao.accessProduct("Laminate"));
        orderToUpdate.setArea(new BigDecimal(5000));
        LocalDate orderDate = LocalDate.parse("2025-07-26", DateTimeFormatter.ISO_DATE);
        orderToUpdate.setDate(orderDate);

        assertTrue(dao.addOrder(orderToUpdate));

        orderToUpdate.setArea(new BigDecimal(6000));
        dao.updateOrder(orderToUpdate);

        Set<Order> setOrdersOnDate = dao.getOrdersOnDate(orderDate);
        Order updatedOrder = setOrdersOnDate.stream()
                .filter(order -> order.getId().equals(orderToUpdate.getId()))
                .findFirst() // Add this to get the first matching order
                .orElse(null); // Add this to handle the case where no matching order is found
        dao.writeData();
        assertEquals(new BigDecimal(6000), updatedOrder.getArea(), "Order area not updated.");

        dao.removeOrder(orderToUpdate.getId());
    }

    @Test
    public void testRemoveOrder(){
        Order order = new Order(dao.getNextID());
        order.setCustomerName("TestUpdateOrder");
        order.setState(dao.accessState("TX"));
        order.setProduct(dao.accessProduct("Laminate"));
        order.setArea(new BigDecimal(5000));
        LocalDate orderDate = LocalDate.parse("2030-07-26", DateTimeFormatter.ISO_DATE);
        order.setDate(orderDate);

        dao.addOrder(order);

        assertTrue(dao.getOrdersOnDate(orderDate).contains(order));

        assertTrue(dao.removeOrder(order.getId()));

        assertFalse(dao.getOrdersOnDate(orderDate).contains(order));

        dao.removeOrder(order.getId());
    }
    @AfterEach
    public void tearDown(){
        dao.writeData();
    }

}
