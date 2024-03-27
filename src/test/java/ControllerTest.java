@Test
void testRun_DisplayDateSpecificOrders() {
    // Arrange
    LocalDate date = LocalDate.now();
    Set<Order> orders = new HashSet<>();
    orders.add(new Order(/* order details */));
    when(io.readString(anyString())).thenReturn("1");
    when(io.askDate()).thenReturn(date);
    when(service.getOrdersOnData(date)).thenReturn(orders);

    // Act
    view.run();

    // Assert
    verify(io, times(1)).displayOrders(orders);
}

@Test
void testRun_AddAnOrder_Success() {
    // Arrange
    Order order = new Order(/* order details */);
    when(io.readString(anyString())).thenReturn("2");
    when(io.askCustomerName()).thenReturn("John Doe");
    when(io.askOrderState(any())).thenReturn("CA");
    when(io.askOrderProduct(any())).thenReturn("Carpet");
    when(io.askOrderArea()).thenReturn(new BigDecimal("100"));
    when(io.askFutureDate()).thenReturn(LocalDate.now());
    when(io.placeOrderConfirmation(order)).thenReturn(true);
    when(service.createOrder(any(), any(), any(), any(), any())).thenReturn(order);
    when(service.addOrder(order)).thenReturn(true);

    // Act
    view.run();

    // Assert
    verify(io, times(1)).displaySuccess("Order added");
}

@Test
void testRun_AddAnOrder_Failure() {
    // Arrange
    Order order = new Order(/* order details */);
    when(io.readString(anyString())).thenReturn("2");
    when(io.askCustomerName()).thenReturn("John Doe");
    when(io.askOrderState(any())).thenReturn("CA");
    when(io.askOrderProduct(any())).thenReturn("Carpet");
    when(io.askOrderArea()).thenReturn(new BigDecimal("100"));
    when(io.askFutureDate()).thenReturn(LocalDate.now());
    when(io.placeOrderConfirmation(order)).thenReturn(true);
    when(service.createOrder(any(), any(), any(), any(), any())).thenReturn(order);
    when(service.addOrder(order)).thenReturn(false);

    // Act
    view.run();

    // Assert
    verify(io, times(1)).displayFailure("Order not added");
}

// Add more test methods for the remaining cases in the switch statement