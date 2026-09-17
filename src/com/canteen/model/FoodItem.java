package com.canteen.model;

/**
 * Concrete class representing a food or meal item.
 * Demonstrates Inheritance and Method Overriding.
 */
public class FoodItem extends MenuItem {
    private boolean isVegetarian;

    public FoodItem(int id, String name, double price, boolean isVegetarian) {
        super(id, name, price);
        this.isVegetarian = isVegetarian;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    @Override
    public String getCategory() {
        return isVegetarian ? "Veg Food" : "Non-Veg Food";
    }

    @Override
    public int getPreparationTime() {
        return 5; // Average 5 minutes prep time for food
    }

    @Override
    public String toString() {
        String vegBadge = isVegetarian ? "[VEG]" : "[NON-VEG]";
        return String.format("[%d] %-20s ₹%-6.2f %-9s | Prep: %d min", 
                getId(), getName(), getPrice(), vegBadge, getPreparationTime());
    }
}
