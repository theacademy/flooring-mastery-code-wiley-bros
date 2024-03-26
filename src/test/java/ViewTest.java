import com.wileybros.flooringmastery.ui.UserIO;
import com.wileybros.flooringmastery.ui.UserIOImpl;
import com.wileybros.flooringmastery.ui.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import static org.mockito.Mockito.*;

public class ViewTest {
    private View view;
    private UserIO userIO;

    @BeforeEach
    public void setUp() {
        userIO = new UserIOImpl();
        view = new View();
    }

    @Test
    public void testWelcomeBanner() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        view.welcomeBanner();
        String expectedOutput = "\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "* <<Flooring Program>>\n";
        assertEquals(expectedOutput, outContent.toString());
        System.setOut(System.out);
    }

    @Test
    public void testDisplayMenu() {
        String menu = view.displayMenu();
        assertTrue(menu.contains("1. Display Orders"));
        assertTrue(menu.contains("2. Add an Order"));
        assertTrue(menu.contains("3. Edit an Order"));
        assertTrue(menu.contains("4. Remove an Order"));
        assertTrue(menu.contains("5. Export All Data"));
        assertTrue(menu.contains("6. Quit"));
    }

    @Test
    public void testAskDate() {
        LocalDate date = view.askDate();
        assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Test
    public void testAskOrderID() {
//        UserIO userIO = mock(UserIO.class);
//        int orderId = view.askOrderID();
//        assertEquals(100, orderId);
    }
}
