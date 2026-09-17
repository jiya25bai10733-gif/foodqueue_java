package com.canteen.service;

import com.canteen.exceptions.InsufficientBalanceException;
import com.canteen.exceptions.ItemNotFoundException;
import com.canteen.model.MenuItem;
import com.canteen.model.Order;

import java.util.List;

/**
 * Service interface defining core canteen operations.
 */
public interface CanteenService {
    void displayMenu();
    MenuItem getItemById(int id) throws ItemNotFoundException;
    Order placeOrder(String customerName, int itemId, int quantity) 
            throws ItemNotFoundException, InsufficientBalanceException;
    Order getOrder(int orderId);
    List<Order> getAllOrders();
    List<Order> getPendingOrders();
    double getWalletBalance();
    void topUpWallet(double amount);
    void loadData();
    void saveData();
}
