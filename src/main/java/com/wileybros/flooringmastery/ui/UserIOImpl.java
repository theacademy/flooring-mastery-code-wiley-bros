package com.wileybros.flooringmastery.ui;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class UserIOImpl implements UserIO {

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
        return null;
    }

    @Override
    public Integer readInt(String prompt) {
        return null;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        return null;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        return null;
    }
}
