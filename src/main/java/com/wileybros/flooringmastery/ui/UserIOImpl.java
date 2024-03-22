package com.wileybros.flooringmastery.ui;

import org.springframework.stereotype.Component;

@Component
public class UserIOImpl implements UserIO {

    public void print(String string, Object... args) {
        System.out.printf(string, args);
    }

    public void printLn(String string, Object... args) {
        print(string + "\n", args);
    }
}
