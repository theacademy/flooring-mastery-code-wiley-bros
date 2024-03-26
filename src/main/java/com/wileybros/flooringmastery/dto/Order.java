package com.wileybros.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.UP;

public class Order {
    private Integer id;
    private String customerName;
    private State state;
    private Product product;
    private BigDecimal area;
    private LocalDate date;

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer id, String customerName, State state, Product product, BigDecimal area,
                 LocalDate date) {
        this.id = id;
        this.customerName = customerName;
        this.state = state;
        this.product = product;
        this.area = area;
        this.date = date;
    }

    // Clever Getters
    public BigDecimal getMaterialCost() {
        return area.multiply(product.getCostPSqF());
    }

    public BigDecimal getLabourCost() {
        return area.multiply(product.getLabourPSqF());
    }

    public BigDecimal getTax() {
        return getLabourCost().add(getMaterialCost()).multiply(state.getTaxRate()).divide(new BigDecimal("100"));
    }

    public BigDecimal getTotal() {
        return getLabourCost().add(getMaterialCost()).add(getTax());
    }

    // Dumb Getters
    public Integer getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public State getState() {
        return state;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getArea() {
        return area;
    }

    public LocalDate getDate() {
        return date;
    }

    // Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) return Objects.equals(o, id);
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id).append(",");
        sb.append(customerName).append(",");
        sb.append(state.getAbr()).append(",").append(state.getTaxRate()).append(",");
        sb.append(product.getType()).append(",");
        sb.append(area.setScale(2, UP)).append(",");
        sb.append(product.getCostPSqF().setScale(2, UP)).append(",").append(product.getLabourPSqF()
                .setScale(2, UP)).append(",");
        sb.append(getMaterialCost().setScale(2, UP)).append(",").append(getLabourCost()
                .setScale(2, UP)).append(",");
        sb.append(getTax().setScale(2, UP)).append(",").append(getTotal().setScale(2, UP));
        return sb.toString();
    }

    // Test Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
