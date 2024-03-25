package com.wileybros.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String abr;
    private String name;
    private BigDecimal taxRate;

    public String getAbr() {
        return abr;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public State(String abr, String name, BigDecimal taxRate) {
        this.abr = abr;
        this.name = name;
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) Objects.equals(o, abr);
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return Objects.equals(abr, state.abr);
    }

    @Override
    public int hashCode() {
        return abr != null ? abr.hashCode() : 0;
    }

    @Override
    public String toString() {
        return  abr + "," + name + "," + taxRate;
    }

    public static State parseState(String string) {
        String[] args = string.split(",");
        return new State(args[0], args[1], new BigDecimal(args[2]));
    }
}
