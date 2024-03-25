package com.wileybros.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {

    void print(String string, Object... args);
    void printLn(String string, Object... args);
    String readString(String prompt);
    Integer readInt(String prompt);
    BigDecimal readBigDecimal(String prompt);
    LocalDate readLocalDate(String prompt);
}
