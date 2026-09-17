package com.canteen.exceptions;

/**
 * Custom exception thrown when a user has insufficient wallet balance to place an order.
 */
public class InsufficientBalanceException extends Exception {
    private final double availableBalance;
    private final double requiredAmount;

    public InsufficientBalanceException(double availableBalance, double requiredAmount) {
        super(String.format("Insufficient balance! Available: ₹%.2f, Required: ₹%.2f", availableBalance, requiredAmount));
        this.availableBalance = availableBalance;
        this.requiredAmount = requiredAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequiredAmount() {
        return requiredAmount;
    }
}
