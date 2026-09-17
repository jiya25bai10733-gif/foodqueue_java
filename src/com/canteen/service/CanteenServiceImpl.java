package com.canteen.service;

import com.canteen.exceptions.InsufficientBalanceException;
import com.canteen.exceptions.ItemNotFoundException;
import com.canteen.model.BeverageItem;
import com.canteen.model.FoodItem;
import com.canteen.model.MenuItem;
import com.canteen.model.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of CanteenService.
 * Handles menu catalog, thread-safe orders, wallet balances, and file I/O persistence.
 */
public class CanteenServiceImpl implements CanteenService {
    private static final String DATA_FILE = "data/orders.txt";

    private final Map<Integer, MenuItem> menu = new LinkedHashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private double walletBalance = 500.00; // Initial demo wallet balance
    private int nextOrderId = 101;

    public CanteenServiceImpl() {
        initDefaultMenu();
        loadData();
    }

    private void initDefaultMenu() {
        menu.put(1, new FoodItem(1, "Crispy Samosa", 15.00, true));
        menu.put(2, new FoodItem(2, "Veg Grilled Sandwich", 40.00, true));
        menu.put(3, new FoodItem(3, "Cheese Maggi", 50.00, true));
        menu.put(4, new FoodItem(4, "Chicken Roll", 80.00, false));
        menu.put(5, new BeverageItem(5, "Iced Cold Coffee", 60.00, "Cold"));
        menu.put(6, new BeverageItem(6, "Masala Ginger Tea", 15.00, "Hot"));
    }

    @Override
    public void displayMenu() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-24s | %-10s | %-16s | %s%n", "ID", "Item Name", "Price", "Category", "Est. Prep");
        System.out.println("--------------------------------------------------------------------------------");
        for (MenuItem item : menu.values()) {
            System.out.printf("%-4d | %-24s | ₹%-9.2f | %-16s | ~%d min%n",
                    item.getId(), item.getName(), item.getPrice(), item.getCategory(), item.getPreparationTime());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Override
    public MenuItem getItemById(int id) throws ItemNotFoundException {
        MenuItem item = menu.get(id);
        if (item == null) {
            throw new ItemNotFoundException(id);
        }
        return item;
    }

    @Override
    public synchronized Order placeOrder(String customerName, int itemId, int quantity) 
            throws ItemNotFoundException, InsufficientBalanceException {
        
        MenuItem item = getItemById(itemId);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Order quantity must be at least 1.");
        }

        double totalAmount = item.getPrice() * quantity;
        if (walletBalance < totalAmount) {
            throw new InsufficientBalanceException(walletBalance, totalAmount);
        }

        // Deduct wallet balance safely
        walletBalance -= totalAmount;

        // Create new order
        Order order = new Order(nextOrderId++, customerName, item.getId(), item.getName(), quantity, totalAmount);
        orders.add(order);

        // Auto-save state
        saveData();

        return order;
    }

    @Override
    public synchronized Order getOrder(int orderId) {
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public synchronized List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public synchronized List<Order> getPendingOrders() {
        List<Order> pending = new ArrayList<>();
        for (Order o : orders) {
            if (!"READY".equalsIgnoreCase(o.getStatus()) && !"COLLECTED".equalsIgnoreCase(o.getStatus())) {
                pending.add(o);
            }
        }
        return pending;
    }

    @Override
    public synchronized double getWalletBalance() {
        return walletBalance;
    }

    @Override
    public synchronized void topUpWallet(double amount) {
        if (amount > 0) {
            walletBalance += amount;
            saveData();
        }
    }

    @Override
    public synchronized void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts[0].equalsIgnoreCase("BALANCE") && parts.length >= 2) {
                    this.walletBalance = Double.parseDouble(parts[1]);
                } else if (parts[0].equalsIgnoreCase("ORDER") && parts.length >= 9) {
                    int oId = Integer.parseInt(parts[1]);
                    String customer = parts[2];
                    int itmId = Integer.parseInt(parts[3]);
                    String itmName = parts[4];
                    int qty = Integer.parseInt(parts[5]);
                    double total = Double.parseDouble(parts[6]);
                    String status = parts[7];
                    String time = parts[8];

                    orders.add(new Order(oId, customer, itmId, itmName, qty, total, status, time));
                    if (oId >= nextOrderId) {
                        nextOrderId = oId + 1;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("[Storage Warning] Error loading " + DATA_FILE + ": " + e.getMessage());
        }
    }

    @Override
    public synchronized void saveData() {
        File file = new File(DATA_FILE);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("# CANTEEN DATA STORAGE\n");
            writer.write(String.format("BALANCE|%.2f%n", walletBalance));
            for (Order o : orders) {
                writer.write(String.format("ORDER|%d|%s|%d|%s|%d|%.2f|%s|%s%n",
                        o.getOrderId(),
                        o.getCustomerName(),
                        o.getItemId(),
                        o.getItemName(),
                        o.getQuantity(),
                        o.getTotalPrice(),
                        o.getStatus(),
                        o.getOrderTime()));
            }
        } catch (IOException e) {
            System.err.println("[Storage Warning] Error saving " + DATA_FILE + ": " + e.getMessage());
        }
    }
}
