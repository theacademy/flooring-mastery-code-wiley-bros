import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceImplTest {
    private Dao dao;
    private ServiceImpl service;

    @BeforeEach
    public void setUp() {
        dao = new DaoImpl();
        service = new ServiceImpl(dao);
    }

    @Test
    public void testAddOrder() {
        Order order = service.createOrder("TestCustomerAddOrderService", "TX", "Laminate", new BigDecimal(5000), LocalDate.now().plusDays(1));
        assertTrue(service.addOrder(order));
    }

    @Test
    public void testGetOrdersOnData(){
        LocalDate date = LocalDate.now();
        Set<Order> ordersOnDate = service.getOrdersOnData(date);

        assertTrue(ordersOnDate.isEmpty());

        Order order = service.createOrder("TestCustomerGetOrdersOnDataOrderService", "TX", "Laminate", new BigDecimal(5000), LocalDate.now().plusDays(1));
        assertTrue(service.addOrder(order));

        Set<Order> ordersOnDatePlusOne = service.getOrdersOnData(date.plusDays(1));
        assertFalse(ordersOnDatePlusOne.isEmpty());
    }

    @Test
    public void testUpdateOrder(){
        Order order = service.createOrder("TestCustomerUpdateOrderService", "CA", "Tile", new BigDecimal(3000), LocalDate.now());
        assertTrue(service.addOrder(order));

        Order updatedOrder = service.combineOrder(order.getId(), "UpdatedCustomerName", null, null, new BigDecimal(4000));
        assertTrue(service.updateOrder(updatedOrder));

        Order retrievedOrder = service.getOrder(order.getId());
        assertEquals("UpdatedCustomerName", retrievedOrder.getCustomerName());
        assertEquals(new BigDecimal(4000), retrievedOrder.getArea());
    }

    @Test
    public void testRemoveOrder(){
        Order order = service.createOrder("TestCustomerRemoveOrderService", "CA", "Wood", new BigDecimal(200), LocalDate.now().plusDays(1));
        assertTrue(service.addOrder(order));

        assertTrue(service.removeOrder(order));

        assertNull(service.getOrder(order.getId()));
    }

    @Test
    public void testExportAllData() {
        assertTrue(service.exportAllData());
    }

    @Test
    public void testGetStateAbrs() {
        Set<String> stateAbrs = service.getStateAbrs();
        assertFalse(stateAbrs.isEmpty());
    }

    @Test
    public void testGetProducts() {
        Set<Product> products = service.getProducts();
        assertFalse(products.isEmpty());
    }
}