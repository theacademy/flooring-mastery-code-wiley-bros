import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.dto.Product;
import com.wileybros.flooringmastery.dto.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        dao.addOrder(order);
        assertTrue(dao.addOrder(order));

        LocalDate dateOfOrder = LocalDate.parse("10-10-2030", formatter);
        assertEquals(dao.getOrdersOnDate(dateOfOrder).toString(), "[1,David,TX,0.6,Carpet,10,14,59,140,590,4.38,734.38]");
    }

    @Test
    public void testAccessState(){
        State texas = dao.accessState("TX");
        State newTexas = new State("TX", "Texas", new BigDecimal("4.45"));
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
    }

    @Test
    public void testUpdateOrder(){

    }
}
