package com.canteen.exceptions;

/**
 * Custom exception thrown when a requested food or beverage item is not found in the menu.
 */
public class ItemNotFoundException extends Exception {
    private final int itemId;

    public ItemNotFoundException(int itemId) {
        super("Menu item with ID " + itemId + " was not found!");
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
