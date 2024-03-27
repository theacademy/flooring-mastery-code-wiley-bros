import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DaoImplTest {
    private DaoImpl dao;

    @BeforeEach
    public void setUp() {
        dao = new DaoImpl();
    }

    @Test
    public void testReadData() {
        assertTrue(dao.readData());
    }

    @Test
    public void testWriteData() {
        assertTrue(dao.writeData());
    }

    @Test
    public void testExportData() {
        assertTrue(dao.exportData());
    }

    @Test
    public void testGetOrder() {
        Order order = new Order(1, "John Doe", new State("OH", "Ohio", new BigDecimal("6.25")),
                new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")),
                new BigDecimal("100"), LocalDate.now());
        dao.addOrder(order);

        Order retrievedOrder = dao.getOrder(order.hashCode());
        assertEquals(order, retrievedOrder);
    }

    @Test
    public void testGetOrdersOnDate() {
        LocalDate date = LocalDate.now();
        Order order1 = new Order(1, "John Doe", new State("TX", "Texas", new BigDecimal("4.45")),
                new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")),
                new BigDecimal("100"), date);
        Order order2 = new Order(2, "Jane Smith", new State("CA", "California", new BigDecimal("25.00")),
                new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15")),
                new BigDecimal("200"), date);
        dao.addOrder(order1);
        dao.addOrder(order2);

        Set<Order> orders = dao.getOrdersOnDate(date);
        Set<Order> expectedOrders = new HashSet<>();
        expectedOrders.add(order1);
        expectedOrders.add(order2);

        assertEquals(expectedOrders, orders);
    }
    @Test
    public void testGetNextID() {
        Integer nextID = dao.getNextID();
        assertNotNull(nextID);
    }

    @Test
    public void testAddOrder() {
        Order order = new Order(1, "John Doe", new State("OH", "Ohio", new BigDecimal("6.25")),
                new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")),
                new BigDecimal("100"), LocalDate.now());

        assertTrue(dao.addOrder(order));
        Order retrievedOrder = dao.getOrder(order.hashCode());
        assertEquals(order, retrievedOrder);
    }

    @Test
    public void testRemoveOrder() {
        Order order = new Order(1, "John Doe", new State("OH", "Ohio", new BigDecimal("6.25")),
                new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")),
                new BigDecimal("100"), LocalDate.now());
        dao.addOrder(order);

        assertTrue(dao.removeOrder(order.hashCode()));
        assertNull(dao.getOrder(order.hashCode()));
    }

    @Test
    public void testAccessState() {
        State state = new State("TX", "Texas", new BigDecimal("4.45"));
        State retrievedState = dao.accessState("TX");
        assertEquals(state, retrievedState);
    }

    @Test
    public void testAccessProduct() {
        Product product = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        Product retrievedProduct = dao.accessProduct("Carpet");
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testGetStateAbrs() {
        Set<String> stateAbrs = dao.getStateAbrs();
        assertNotNull(stateAbrs);
        assertFalse(stateAbrs.isEmpty());
    }

    @Test
    public void testGetProducts() {
        Set<Product> products = dao.getProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }
}
