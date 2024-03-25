package com.wileybros.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String type;
    private BigDecimal costPSqF;
    private BigDecimal labourPSqF;

    public Product(String type, BigDecimal costPSqF, BigDecimal labourPSqF) {
        this.type = type;
        this.costPSqF = costPSqF;
        this.labourPSqF = labourPSqF;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCostPSqF() {
        return costPSqF;
    }

    public BigDecimal getLabourPSqF() {
        return labourPSqF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) return Objects.equals(o, type);
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public String toString() {
        return type + "," + costPSqF + "," + labourPSqF;
    }

    public static Product parseProduct(String string) {
        String[] args = string.split(",");
        return new Product(args[0], new BigDecimal(args[1]), new BigDecimal(args[2]));
    }
}
