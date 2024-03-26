import com.wileybros.flooringmastery.controller.Controller;
import com.wileybros.flooringmastery.dto.Order;
import com.wileybros.flooringmastery.service.Service;
import com.wileybros.flooringmastery.ui.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ControllerTest {
    @Mock
    private View view;

    @Mock
    private Service service;

    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller(view, service);
    }

    @Test
    void testDisplayDateSpecificOrders() {
        LocalDate testDate = LocalDate.of(2024, 3, 26);
        Set<Order> orders = new HashSet<>();
        when(view.askDate()).thenReturn(testDate);
        when(service.getOrdersOnData(testDate)).thenReturn(orders);

        controller.run();

        verify(view).askDate();
        verify(service).getOrdersOnData(testDate);
        verify(view).displayOrders(orders);
    }

}
