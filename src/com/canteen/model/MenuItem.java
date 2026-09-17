package com.canteen.model;

/**
 * Abstract base class representing a generic canteen menu item.
 * Demonstrates Abstraction and Encapsulation.
 */
public abstract class MenuItem {
    private int id;
    private String name;
    private double price;

    public MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Polymorphic method to get the item category.
     */
    public abstract String getCategory();

    /**
     * Polymorphic method to get estimated preparation time in minutes.
     */
    public abstract int getPreparationTime();

    @Override
    public String toString() {
        return String.format("[%d] %-22s ₹%-6.2f | %s (Prep: %d min)", 
                id, name, price, getCategory(), getPreparationTime());
    }
}
