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
        State state = new State("TX", "Texas", new BigDecimal("0.6"));
        Product product = new Product("Carpet", new BigDecimal(14), new BigDecimal(59));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("10-10-2030", formatter);

        Order order = new Order(999, "David", state, product, new BigDecimal(10), date);


        assertTrue(dao.addOrder(order)); //Add the order

        String expected = "999,David,TX,0.6,Carpet,10.00,14.00,59.00,140.00,590.00,4.38,734.38"; // This is the expected result

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
        State state = dao.accessState("CA");
        Product product = dao.accessProduct("Tile");
        LocalDate date = LocalDate.parse("15-10-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Order order = new Order(998, "Alex", state, product, new BigDecimal(25000), date);

        dao.addOrder(order);

        Set<Order> ordersOnDate = dao.getOrdersOnDate(date);
        assertTrue(ordersOnDate.contains(order));

        dao.removeOrder(order.getId());
    }

    @Test
    public void testUpdateOrder(){
        State state = dao.accessState("TX");
        Product product = dao.accessProduct("Laminate");
        LocalDate orderDate = LocalDate.parse("2025-07-26", DateTimeFormatter.ISO_DATE);

        Order orderToUpdate = new Order(dao.getNextID(), "TestUpdateOrder", state, product, new BigDecimal(5000), orderDate);

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
        State state = dao.accessState("TX");
        Product product = dao.accessProduct("Laminate");
        LocalDate orderDate = LocalDate.parse("2030-07-26", DateTimeFormatter.ISO_DATE);

        Order order = new Order(dao.getNextID(), "TestUpdateOrder", state, product, new BigDecimal(5000), orderDate);

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
