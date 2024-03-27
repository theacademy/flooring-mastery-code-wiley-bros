import com.wileybros.flooringmastery.ui.UserIO;
import com.wileybros.flooringmastery.ui.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewTest {

    @Mock
    private UserIO io;

    private View view;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        view = new View(io);
    }

    @Test
    void testDisplayMenu() {
        when(io.readString(anyString())).thenReturn("1");
        String menuChoice = view.displayMenu();
        assertEquals("1", menuChoice);
        verify(io, times(1)).printLn("* 1. Display Orders");
        verify(io, times(1)).printLn("* 2. Add an Order");
        verify(io, times(1)).printLn("* 3. Edit an Order");
        verify(io, times(1)).printLn("* 4. Remove an Order");
        verify(io, times(1)).printLn("* 5. Export All Data");
        verify(io, times(1)).printLn("* 6. Quit");
        verify(io, times(1)).printLn("*");
        verify(io, times(1)).printLn("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        verify(io, times(1)).readString("Please select from the above choices: ");
    }

    @Test
    void testWelcomeBanner() {
        view.welcomeBanner();
        verify(io, times(1))
                .printLn("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        verify(io, times(1)).printLn("* <<Flooring Program>>");
    }
    @Test
    void testDisplaySuccess() {
        String message = "Test Display";
        view.displaySuccess(message);
        verify(io, times(1)).printLn("Operation Success : " + message);
}

    @Test
    void testDisplayFailure() {
        String message = "Test Display";
        view.displayFailure(message);
        verify(io, times(1)).printLn("Operation Failure : " + message);
    }

    @Test
    void testDisplayError() {
        String message = "Test Display";
        view.displayError(message);
        verify(io, times(1)).printLn("Error : " + message);
    }

    @Test
    void testDisplayExit() {
        view.displayExit();
        verify(io, times(1)).printLn("Goodbye!");
    }
}