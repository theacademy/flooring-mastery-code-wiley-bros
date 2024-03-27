import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceImplTest {
    @Mock
    private Dao dao;

    private ServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ServiceImpl(dao);
    }

    @Test
    void testGetOrdersOnDate() {
        LocalDate date = LocalDate.now();
        Set<Order> expectedOrders = new HashSet<>();
        when(dao.getOrdersOnDate(date)).thenReturn(expectedOrders);

        Set<Order> actualOrders = service.getOrdersOnData(date);

        assertEquals(expectedOrders, actualOrders);
        verify(dao).getOrdersOnDate(date);
    }

    @Test
    void testAddOrder() {
        Order order = new Order();
        when(dao.getNextID()).thenReturn(1);
        when(dao.addOrder(any(Order.class))).thenReturn(true);

        boolean result = service.addOrder(order);

        assertTrue(result);
        verify(dao).getNextID();
        verify(dao).addOrder(any(Order.class));
    }

    // Add more test methods for other methods in ServiceImpl class

    // ...
}