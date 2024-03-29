package com.wileybros.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String abr;
    private String name;
    private BigDecimal taxRate;

    /**
     * Constructs a new State object with the specified abbreviation, name, and tax rate.
     *
     * @param abr     the abbreviation of the state
     * @param name    the name of the state
     * @param taxRate the tax rate of the state
     */
    public State(String abr, String name, BigDecimal taxRate) {
        this.abr = abr;
        this.name = name;
        this.taxRate = taxRate;
    }

    /**
     * Returns the abbreviation of the state.
     *
     * @return the abbreviation of the state
     */
    public String getAbr() {
        return abr;
    }

    /**
     * Returns the name of the state.
     *
     * @return the name of the state
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tax rate of the state.
     *
     * @return the tax rate of the state
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * Checks if this State object is equal to the specified object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) return Objects.equals(o, abr);
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return Objects.equals(abr, state.abr);
    }

    /**
     * Returns the hash code value for this State object.
     *
     * @return the hash code value for this State object
     */
    @Override
    public int hashCode() {
        return abr != null ? abr.hashCode() : 0;
    }

    /**
     * Returns a string representation of this State object.
     *
     * @return a string representation of this State object
     */
    @Override
    public String toString() {
        return abr + "," + name + "," + taxRate;
    }

    /**
     * Parses the specified string and returns a new State object.
     *
     * @param string the string to parse
     * @return a new State object
     */
    public static State parseState(String string) {
        String[] args = string.split(",");
        return new State(args[0], args[1], new BigDecimal(args[2]));
    }
}
