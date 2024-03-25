package com.wileybros.flooringmastery.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class UserIOImpl implements UserIO {

    final private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String string, Object... args) {
        System.out.printf(string, args);
    }

    @Override
    public void printLn(String string, Object... args) {
        print(string + "\n", args);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    @Override
    public Integer readInt(String prompt) {
        return Integer.parseInt(readString(prompt));
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        return new BigDecimal(readString(prompt));
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        return LocalDate.parse(readString(prompt));
    }
}
