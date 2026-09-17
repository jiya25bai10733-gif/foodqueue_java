package com.canteen.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model representing a customer food order.
 * Similar to Transaction in banking systems, encapsulates transactional state.
 */
public class Order {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int orderId;
    private String customerName;
    private int itemId;
    private String itemName;
    private int quantity;
    private double totalPrice;
    private String status; // WAITING, PREPARING, READY
    private String orderTime;

    public Order(int orderId, String customerName, int itemId, String itemName, int quantity, double totalPrice) {
        this(orderId, customerName, itemId, itemName, quantity, totalPrice, "WAITING", 
             LocalDateTime.now().format(FORMATTER));
    }

    public Order(int orderId, String customerName, int itemId, String itemName, int quantity, 
                 double totalPrice, String status, String orderTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public synchronized String getStatus() {
        return status;
    }

    public synchronized void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return String.format("Order #%-4d | %-12s | %-18s | Qty: %-2d | Total: ₹%-7.2f | Status: %-10s | %s",
                orderId, customerName, itemName, quantity, totalPrice, status, orderTime);
    }
}
