package com.wileybros.flooringmastery.ui;

import org.springframework.stereotype.Component;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class UserIOImpl implements UserIO {

    final private Scanner scanner = new Scanner(System.in);

    /**
     * Prints a formatted string to the console.
     *
     * @param string the string to be printed
     * @param args   the arguments to be formatted into the string
     */
    @Override
    public void print(String string, Object... args) {
        System.out.printf(string, args);
    }

    /**
     * Prints a formatted string followed by a new line to the console.
     *
     * @param string the string to be printed
     * @param args   the arguments to be formatted into the string
     */
    @Override
    public void printLn(String string, Object... args) {
        print(string + "\n", args);
    }

    /**
     * Reads a string input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the string input from the user
     */
    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    /**
     * Reads an integer input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the integer input from the user, or null if the input is blank
     */
    @Override
    public Integer readInt(String prompt) {
        String result = readString(prompt);
        if (result.isBlank()) return null;
        return Integer.parseInt(result);
    }

    /**
     * Reads a BigDecimal input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the BigDecimal input from the user, or null if the input is blank
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        String num = readString(prompt);
        if (num.isBlank()) return null;
        return new BigDecimal(num);
    }

    /**
     * Reads a LocalDate input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @param format the DateTimeFormatter to be used for parsing the input
     * @return the LocalDate input from the user
     */
    @Override
    public LocalDate readLocalDate(String prompt, DateTimeFormatter format) {
        return LocalDate.parse(readString(prompt), format);
    }
}
