// import com.wileybros.flooringmastery.ui.UserIO;
// import com.wileybros.flooringmastery.ui.View;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.math.BigDecimal;
// import java.time.LocalDate;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;



// public class ViewTest {
//     @Mock
//     private UserIO userIO;
//     private View view;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//         view = new View(userIO);
//     }

//     @Test
//     public void testAskDate() {
//         LocalDate date = LocalDate.now();
//         when(userIO.readLocalDate(anyString(), any())).thenReturn(date);
//         LocalDate askedDate = view.askDate();
//         assertEquals(date, askedDate);
//         verify(userIO).readLocalDate(anyString(), any());
//     }
// //    @Test
// //    public void testDisplaySuccess() {
// //        String successMessage = "Order placed successfully";
// //
// //        view.displaySuccess(successMessage);
// //
// //        verify(userIO).printLn("Action Success " + successMessage);
// //    }

//     @Test
//     public void testAskCustomerName() {
//         when(userIO.readString(anyString())).thenReturn("John Doe");

//         String customerName = view.askCustomerName();

//         verify(userIO).readString(anyString());
//     }
//     @Test
//     public void testAskOrderID() {
//         when(userIO.readInt(anyString())).thenReturn(100);
//         int orderId = view.askOrderID();
//         assertEquals(100, orderId);
//         verify(userIO).readInt(anyString());
//     }
//     @Test
//     public void testAskOrderArea() {
//         when(userIO.readInt(anyString())).thenReturn(200);
//         BigDecimal area = view.askOrderArea();
//         assertEquals(BigDecimal.valueOf(200), area);
//         verify(userIO).readInt(anyString());
//     }
// }
import com.wileybros.flooringmastery.ui.UserIO;
import com.wileybros.flooringmastery.ui.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ViewTest {
    @Mock
    private UserIO userIO;
    private View view;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        view = new View(userIO);
    }

    @Test
    public void testDisplayMenu() {
        when(userIO.readString(anyString())).thenReturn("1");
        String menuChoice = view.displayMenu();
        assertEquals("1", menuChoice);
        verify(userIO, times(6)).printLn(anyString());
        verify(userIO).readString(anyString());
    }

    @Test
    public void testWelcomeBanner() {
        view.welcomeBanner();
        verify(userIO).printLn(anyString());
    }

    @Test
    public void testDisplaySuccess() {
        String successMessage = "Operation successful";
        view.displaySuccess(successMessage);
        verify(userIO).printLn("Operation Success : " + successMessage);
    }

    @Test
    public void testDisplayFailure() {
        String failureMessage = "Operation failed";
        view.displayFailure(failureMessage);
        verify(userIO).printLn("Operation Failure : " + failureMessage);
    }

    @Test
    public void testDisplayError() {
        String errorMessage = "An error occurred";
        view.displayError(errorMessage);
        verify(userIO).printLn("Error : " + errorMessage);
    }

    @Test
    public void testDisplayExit() {
        view.displayExit();
        verify(userIO).printLn("Goodbye!");
    }

    @Test
    public void testAskDate() {
        LocalDate date = LocalDate.now();
        when(userIO.readLocalDate(anyString(), any())).thenReturn(date);
        LocalDate askedDate = view.askDate();
        assertEquals(date, askedDate);
        verify(userIO).readLocalDate(anyString(), any());
    }

    @Test
    public void testAskFutureDate() {
        LocalDate date = LocalDate.now().plusDays(1);
        when(userIO.readLocalDate(anyString(), any())).thenReturn(date);
        LocalDate askedDate = view.askFutureDate();
        assertEquals(date, askedDate);
        verify(userIO, atLeastOnce()).readLocalDate(anyString(), any());
    }

    @Test
    public void testAskOrderID() {
        when(userIO.readInt(anyString())).thenReturn(100);
        int orderId = view.askOrderID();
        assertEquals(100, orderId);
        verify(userIO).readInt(anyString());
    }

    @Test
    public void testAskCustomerName() {
        when(userIO.readString(anyString())).thenReturn("John Doe");
        String customerName = view.askCustomerName();
        assertEquals("John Doe", customerName);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testAskOrderState() {
        Set<String> stateAbrs = new HashSet<>();
        stateAbrs.add("NY");
        stateAbrs.add("CA");
        when(userIO.readString(anyString())).thenReturn("NY");
        String stateAbr = view.askOrderState(stateAbrs);
        assertEquals("NY", stateAbr);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testAskOrderProduct() {
        Set<Product> products = new HashSet<>();
        Product product1 = new Product("Carpet", BigDecimal.valueOf(5.99), BigDecimal.valueOf(2.50));
        Product product2 = new Product("Tile", BigDecimal.valueOf(7.99), BigDecimal.valueOf(3.50));
        products.add(product1);
        products.add(product2);
        when(userIO.readString(anyString())).thenReturn("Carpet");
        String productType = view.askOrderProduct(products);
        assertEquals("Carpet", productType);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testAskOrderArea() {
        when(userIO.readInt(anyString())).thenReturn(200);
        BigDecimal area = view.askOrderArea();
        assertEquals(BigDecimal.valueOf(200), area);
        verify(userIO).readInt(anyString());
    }

    @Test
    public void testPlaceOrderConfirmation() {
        Order order = new Order();
        when(userIO.readString(anyString())).thenReturn("Y");
        boolean confirmation = view.placeOrderConfirmation(order);
        assertTrue(confirmation);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testUpdateOrderConfirmation() {
        Order order = new Order();
        when(userIO.readString(anyString())).thenReturn("Y");
        boolean confirmation = view.updateOrderConfirmation(order);
        assertTrue(confirmation);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testRemoveOrderConfirmation() {
        Order order = new Order();
        when(userIO.readString(anyString())).thenReturn("Y");
        boolean confirmation = view.removeOrderConfirmation(order);
        assertTrue(confirmation);
        verify(userIO).readString(anyString());
    }

    @Test
    public void testDisplayOrder() {
        Order order = new Order();
        order.setId(1);
        order.setCustomerName("John Doe");
        order.setState(new State("NY", "New York", BigDecimal.valueOf(0.08)));
        order.setProduct(new Product("Carpet", BigDecimal.valueOf(5.99), BigDecimal.valueOf(2.50)));
        order.setArea(BigDecimal.valueOf(200));
        order.setMaterialCost(BigDecimal.valueOf(1198));
        order.setLabourCost(BigDecimal.valueOf(500));
        order.setTax(BigDecimal.valueOf(119.84));
        order.setTotal(BigDecimal.valueOf(1817.84));

        view.displayOrder(order);

        verify(userIO).printLn(anyString());
    }

    @Test
    public void testDisplayOrders() {
        Set<Order> orders = new HashSet<>();
        Order order1 = new Order();
        order1.setId(1);
        order1.setCustomerName("John Doe");
        order1.setState(new State("NY", "New York", BigDecimal.valueOf(0.08)));
        order1.setProduct(new Product("Carpet", BigDecimal.valueOf(5.99), BigDecimal.valueOf(2.50)));
        order1.setArea(BigDecimal.valueOf(200));
        order1.setMaterialCost(BigDecimal.valueOf(1198));
        order1.setLabourCost(BigDecimal.valueOf(500));
        order1.setTax(BigDecimal.valueOf(119.84));
        order1.setTotal(BigDecimal.valueOf(1817.84));
        orders.add(order1);

        Order order2 = new Order();
        order2.setId(2);
        order2.setCustomerName("Jane Smith");
        order2.setState(new State("CA", "California", BigDecimal.valueOf(0.09)));
        order2.setProduct(new Product("Tile", BigDecimal.valueOf(7.99), BigDecimal.valueOf(3.50)));
        order2.setArea(BigDecimal.valueOf(300));
        order2.setMaterialCost(BigDecimal.valueOf(2397));
        order2.setLabourCost(BigDecimal.valueOf(1050));
        order2.setTax(BigDecimal.valueOf(244.23));
        order2.setTotal(BigDecimal.valueOf(3691.23));
        orders.add(order2);

        view.displayOrders(orders);

        verify(userIO, times(2)).printLn(anyString());
    }
}