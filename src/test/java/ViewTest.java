import com.wileybros.flooringmastery.ui.UserIO;
import com.wileybros.flooringmastery.ui.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testAskDate() {
        LocalDate date = LocalDate.now();
        when(userIO.readLocalDate(anyString(), any())).thenReturn(date);
        LocalDate askedDate = view.askDate();
        assertEquals(date, askedDate);
        verify(userIO).readLocalDate(anyString(), any());
    }
//    @Test
//    public void testDisplaySuccess() {
//        String successMessage = "Order placed successfully";
//
//        view.displaySuccess(successMessage);
//
//        verify(userIO).printLn("Action Success " + successMessage);
//    }

    @Test
    public void testAskCustomerName() {
        when(userIO.readString(anyString())).thenReturn("John Doe");

        String customerName = view.askCustomerName();

        verify(userIO).readString(anyString());
    }
    @Test
    public void testAskOrderID() {
        when(userIO.readInt(anyString())).thenReturn(100);
        int orderId = view.askOrderID();
        assertEquals(100, orderId);
        verify(userIO).readInt(anyString());
    }
    @Test
    public void testAskOrderArea() {
        when(userIO.readInt(anyString())).thenReturn(200);
        BigDecimal area = view.askOrderArea();
        assertEquals(BigDecimal.valueOf(200), area);
        verify(userIO).readInt(anyString());
    }
}
