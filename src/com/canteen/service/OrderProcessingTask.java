package com.canteen.service;

import com.canteen.model.Order;

import java.util.List;

/**
 * Background daemon thread that automatically processes orders through kitchen stages.
 * Exactly mirrors the InterestAccrualTask daemon thread pattern from banking-transaction-engine.
 */
public class OrderProcessingTask extends Thread {
    private final CanteenService canteenService;
    private volatile boolean running = true;

    public OrderProcessingTask(CanteenService canteenService) {
        super("OrderProcessing-Daemon");
        this.canteenService = canteenService;
        setDaemon(true); // Daemon thread exits automatically when main program terminates
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Sleep for 7 seconds between kitchen queue checks
                Thread.sleep(7000);

                List<Order> orders = canteenService.getAllOrders();
                boolean updated = false;

                for (Order o : orders) {
                    if ("WAITING".equalsIgnoreCase(o.getStatus())) {
                        o.setStatus("PREPARING");
                        updated = true;
                        break; // Process one transition per tick for realistic pacing
                    } else if ("PREPARING".equalsIgnoreCase(o.getStatus())) {
                        o.setStatus("READY");
                        updated = true;
                        break;
                    }
                }

                if (updated) {
                    canteenService.saveData();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopTask() {
        this.running = false;
        this.interrupt();
    }
}
