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

    /**
     * Constructs an Order object with the given id.
     *
     * @param id the id of the order
     */
    public Order(Integer id) {
        this.id = id;
    }

    /**
     * Constructs an Order object with the given parameters.
     *
     * @param id           the id of the order
     * @param customerName the name of the customer
     * @param state        the state of the order
     * @param product      the product of the order
     * @param area         the area of the order
     * @param date         the date of the order
     */
    public Order(Integer id, String customerName, State state, Product product, BigDecimal area,
                 LocalDate date) {
        this.id = id;
        this.customerName = customerName;
        this.state = state;
        this.product = product;
        this.area = area;
        this.date = date;
    }

    // Clever Getters ------------------------------------------------------

    /**
     * Calculates the material cost of the order.
     *
     * @return the material cost
     */
    public BigDecimal getMaterialCost() {
        return area.multiply(product.getCostPSqF());
    }

    /**
     * Calculates the labor cost of the order.
     *
     * @return the labor cost
     */
    public BigDecimal getLabourCost() {
        return area.multiply(product.getLabourPSqF());
    }

    /**
     * Calculates the tax of the order.
     *
     * @return the tax
     */
    public BigDecimal getTax() {
        return getLabourCost().add(getMaterialCost()).multiply(state.getTaxRate()).divide(new BigDecimal("100"));
    }

    /**
     * Calculates the total cost of the order.
     *
     * @return the total cost
     */
    public BigDecimal getTotal() {
        return getLabourCost().add(getMaterialCost()).add(getTax());
    }

    // Dumb Getters  ------------------------------------------------------

    /**
     * Returns the id of the order.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns the state of the order.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Returns the product of the order.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the area of the order.
     *
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * Returns the date of the order.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    // Overrides ------------------------------------------------------

    /**
     * Checks if this order is equal to another object.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() == String.class) return Objects.equals(o, id);
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return Objects.equals(id, order.id);
    }

    /**
     * Returns the hash code of the order.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return the string representation
     */
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

    // Test Setters  ------------------------------------------------------

    /**
     * Sets the name of the customer.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets the state of the order.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Sets the product of the order.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Sets the area of the order.
     *
     * @param area the area
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * Sets the date of the order.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
