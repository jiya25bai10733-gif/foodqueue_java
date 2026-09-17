package com.canteen;

import com.canteen.exceptions.InsufficientBalanceException;
import com.canteen.exceptions.ItemNotFoundException;
import com.canteen.model.Order;
import com.canteen.service.CanteenService;
import com.canteen.service.CanteenServiceImpl;
import com.canteen.service.OrderProcessingTask;

import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point for the Canteen Ordering & Queue System CLI.
 * Built entirely with java.util.Scanner for lightweight console interaction.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CanteenService canteenService;

    public static void main(String[] args) {
        canteenService = new CanteenServiceImpl();

        // Start the background daemon thread for kitchen queue processing
        OrderProcessingTask kitchenTask = new OrderProcessingTask(canteenService);
        kitchenTask.start();

        // Register shutdown hook to safely save data on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[SYSTEM] Saving all canteen data safely before exit...");
            canteenService.saveData();
            kitchenTask.stopTask();
        }));

        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option (1-7): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleViewMenu();
                    break;
                case "2":
                    handlePlaceOrder();
                    break;
                case "3":
                    handleTrackOrder();
                    break;
                case "4":
                    handleViewAllOrders();
                    break;
                case "5":
                    handleCheckBalance();
                    break;
                case "6":
                    handleTopUpWallet();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println(">> Invalid option! Please enter a number between 1 and 7.");
            }
            System.out.println();
        }

        System.out.println("Thank you for using the Canteen Ordering System. Have a great day!");
    }

    private static void printWelcomeBanner() {
        System.out.println("================================================================================");
        System.out.println("          CANTEENFLOW — COLLEGE CANTEEN ORDERING & QUEUE SYSTEM                 ");
        System.out.println("================================================================================");
    }

    private static void printMenu() {
        System.out.println("---------------- [ MAIN MENU ] ----------------");
        System.out.println("1. View Canteen Menu");
        System.out.println("2. Place Food / Beverage Order");
        System.out.println("3. Track Order Status (Queue Tracker)");
        System.out.println("4. View All Order History");
        System.out.println("5. Check Campus Wallet Balance");
        System.out.println("6. Top-Up Wallet Balance");
        System.out.println("7. Exit Application");
        System.out.println("-----------------------------------------------");
    }

    private static void handleViewMenu() {
        System.out.println("\n--- [ CANTEEN MENU ] ---");
        canteenService.displayMenu();
    }

    private static void handlePlaceOrder() {
        System.out.println("\n--- [ PLACE NEW ORDER ] ---");
        canteenService.displayMenu();

        try {
            System.out.print("Enter Customer Name: ");
            String customerName = scanner.nextLine().trim();
            if (customerName.isEmpty()) {
                customerName = "Student";
            }

            System.out.print("Enter Menu Item ID: ");
            int itemId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine().trim());

            Order order = canteenService.placeOrder(customerName, itemId, qty);
            System.out.println("\n>> ORDER PLACED SUCCESSFULLY!");
            System.out.printf("   Order ID    : #%d%n", order.getOrderId());
            System.out.printf("   Item        : %s (x%d)%n", order.getItemName(), order.getQuantity());
            System.out.printf("   Total Price : ₹%.2f%n", order.getTotalPrice());
            System.out.printf("   Queue Status: %s%n", order.getStatus());
            System.out.printf("   Remaining Wallet Balance: ₹%.2f%n", canteenService.getWalletBalance());
            System.out.println("   (Tip: Status will update from WAITING -> PREPARING -> READY in background)");

        } catch (NumberFormatException e) {
            System.out.println(">> [INPUT ERROR] Please enter valid numbers for Item ID and Quantity.");
        } catch (ItemNotFoundException e) {
            System.out.println(">> [ERROR] " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println(">> [TRANSACTION REJECTED] " + e.getMessage());
            System.out.println("   Please top up your wallet via Option 6.");
        } catch (IllegalArgumentException e) {
            System.out.println(">> [ERROR] " + e.getMessage());
        }
    }

    private static void handleTrackOrder() {
        System.out.println("\n--- [ TRACK ORDER STATUS ] ---");
        System.out.print("Enter Order ID: ");
        try {
            int orderId = Integer.parseInt(scanner.nextLine().trim());
            Order order = canteenService.getOrder(orderId);
            if (order != null) {
                System.out.println(">> " + order);
            } else {
                System.out.println(">> Order #" + orderId + " not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println(">> [INPUT ERROR] Invalid Order ID format.");
        }
    }

    private static void handleViewAllOrders() {
        System.out.println("\n--- [ ALL ORDERS HISTORY ] ---");
        List<Order> orders = canteenService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println(">> No orders placed yet.");
            return;
        }
        for (Order o : orders) {
            System.out.println("  * " + o);
        }
    }

    private static void handleCheckBalance() {
        System.out.println("\n--- [ CAMPUS WALLET ] ---");
        System.out.printf("Current Balance: ₹%.2f%n", canteenService.getWalletBalance());
    }

    private static void handleTopUpWallet() {
        System.out.println("\n--- [ TOP-UP WALLET ] ---");
        System.out.printf("Current Balance: ₹%.2f%n", canteenService.getWalletBalance());
        System.out.print("Enter amount to deposit (₹): ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println(">> Deposit amount must be greater than zero.");
                return;
            }
            canteenService.topUpWallet(amount);
            System.out.printf(">> Wallet topped up successfully! New Balance: ₹%.2f%n", canteenService.getWalletBalance());
        } catch (NumberFormatException e) {
            System.out.println(">> [INPUT ERROR] Invalid amount format.");
        }
    }
}
