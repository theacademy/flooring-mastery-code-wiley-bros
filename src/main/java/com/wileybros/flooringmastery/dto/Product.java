package com.wileybros.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String type;
    private BigDecimal costPSqF;
    private BigDecimal labourPSqF;

    /**
     * Constructs a new Product object with the specified type, cost per square foot, and labor cost per square foot.
     *
     * @param type       the type of the product
     * @param costPSqF   the cost per square foot of the product
     * @param labourPSqF the labor cost per square foot of the product
     */
    public Product(String type, BigDecimal costPSqF, BigDecimal labourPSqF) {
        this.type = type;
        this.costPSqF = costPSqF;
        this.labourPSqF = labourPSqF;
    }

    /**
     * Returns the type of the product.
     *
     * @return the type of the product
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the cost per square foot of the product.
     *
     * @return the cost per square foot of the product
     */
    public BigDecimal getCostPSqF() {
        return costPSqF;
    }

    /**
     * Returns the labor cost per square foot of the product.
     *
     * @return the labor cost per square foot of the product
     */
    public BigDecimal getLabourPSqF() {
        return labourPSqF;
    }

    /**
     * Checks if this Product object is equal to the specified object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) return Objects.equals(o, type);
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(type, product.type);
    }

    /**
     * Returns the hash code value for this Product object.
     *
     * @return the hash code value for this Product object
     */
    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    /**
     * Returns a string representation of this Product object.
     *
     * @return a string representation of this Product object
     */
    @Override
    public String toString() {
        return type + "," + costPSqF + "," + labourPSqF;
    }

    /**
     * Parses the specified string and returns a new Product object.
     *
     * @param string the string to parse
     * @return a new Product object parsed from the string
     */
    public static Product parseProduct(String string) {
        String[] args = string.split(",");
        return new Product(args[0], new BigDecimal(args[1]), new BigDecimal(args[2]));
    }
}
