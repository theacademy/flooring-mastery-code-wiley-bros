import com.wileybros.flooringmastery.dao.Dao;
import com.wileybros.flooringmastery.dao.DaoImpl;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Order order = service.createOrder(1, "TestCustomer", "TX", "Laminate",
                new BigDecimal(5000), LocalDate.now().plusDays(1));

        boolean result = service.addOrder(order);

        assertTrue(result);
    }
}
