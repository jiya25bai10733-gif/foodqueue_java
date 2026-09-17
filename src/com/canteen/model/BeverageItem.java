package com.canteen.model;

/**
 * Concrete class representing hot or cold beverages.
 * Demonstrates Inheritance and Polymorphic behavior.
 */
public class BeverageItem extends MenuItem {
    private String temperature; // "Hot" or "Cold"

    public BeverageItem(int id, String name, double price, String temperature) {
        super(id, name, price);
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getCategory() {
        return temperature + " Beverage";
    }

    @Override
    public int getPreparationTime() {
        return 2; // Average 2 minutes prep time for drinks
    }

    @Override
    public String toString() {
        return String.format("[%d] %-20s ₹%-6.2f [%s]   | Prep: %d min", 
                getId(), getName(), getPrice(), temperature.toUpperCase(), getPreparationTime());
    }
}
