package com.wileybros.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface UserIO {

        /**
     * Prints a formatted string to the console.
     *
     * @param string the string to be printed
     * @param args   the arguments to be formatted into the string
     */
    void print(String string, Object... args);

    /**
     * Prints a formatted string followed by a new line to the console.
     *
     * @param string the string to be printed
     * @param args   the arguments to be formatted into the string
     */
    void printLn(String string, Object... args);

    /**
     * Reads a string input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the string input from the user
     */
    String readString(String prompt);

    /**
     * Reads an integer input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the integer input from the user, or null if the input is blank
     */
    Integer readInt(String prompt);

    /**
     * Reads a BigDecimal input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @return the BigDecimal input from the user, or null if the input is blank
     */
    BigDecimal readBigDecimal(String prompt);

    /**
     * Reads a LocalDate input from the user.
     *
     * @param prompt the prompt to be displayed to the user
     * @param format the DateTimeFormatter to be used for parsing the input
     * @return the LocalDate input from the user
     */
    LocalDate readLocalDate(String prompt, DateTimeFormatter format);
}
