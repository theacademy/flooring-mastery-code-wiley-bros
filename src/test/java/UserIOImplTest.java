package com.wileybros.flooringmastery.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserIOImplTest {
    private UserIOImpl userIO;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUp() {
        userIO = new UserIOImpl();

        // Set up input and output streams for testing
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void tearDown() {
        // Restore original input and output streams
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void testPrint() {
        userIO.print("Hello, %s!", "World");
        assertEquals("Hello, World!", testOut.toString().trim());
    }

    @Test
    public void testPrintLn() {
        userIO.printLn("Hello, %s!", "World");
        assertEquals("Hello, World!", testOut.toString().trim());
    }

    @Test
    public void testReadString() {
        String input = "Test Input";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        String result = userIO.readString("Enter a string: ");
        assertEquals(input, result);
    }

    @Test
    public void testReadInt() {
        String input = "42";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Integer result = userIO.readInt("Enter an integer: ");
        assertEquals(Integer.valueOf(input), result);
    }

    @Test
    public void testReadIntBlankInput() {
        String input = "";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        Integer result = userIO.readInt("Enter an integer: ");
        assertNull(result);
    }

    @Test
    public void testReadBigDecimal() {
        String input = "3.14";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        BigDecimal result = userIO.readBigDecimal("Enter a decimal number: ");
        assertEquals(new BigDecimal(input), result);
    }

    @Test
    public void testReadBigDecimalBlankInput() {
        String input = "";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        BigDecimal result = userIO.readBigDecimal("Enter a decimal number: ");
        assertNull(result);
    }

    @Test
    public void testReadLocalDate() {
        String input = "2022-01-01";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate result = userIO.readLocalDate("Enter a date (yyyy-MM-dd): ", formatter);
        assertEquals(LocalDate.parse(input, formatter), result);
    }
}