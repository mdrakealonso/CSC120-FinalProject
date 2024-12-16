import java.util.ArrayList;

/**
 * Represents the Container class
 */
public class Container {
    
    // Attributes
    private String name;
    private String description;
    private boolean isLocked;
    private boolean isOpen;
    private ArrayList<Item> containedItems;

    // Constructor
    public Container(String name, String description, boolean isLocked, boolean isOpen) {
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.isOpen = isOpen;
        this.containedItems = new ArrayList<Item>();
    }

    // Methods

    /**
     * Returns the name of a Container object
     * @return the Container's name
     */
    public String getName() {
        return name.toLowerCase();
    }

    /**
     * Prints the description of a Container object
     */
    public void printDescription() {
        System.out.println(description);
    }

    /**
     * Returns true if a Container is locked
     * @return true if locked, false otherwise
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Unlocks a Container by setting the isLocked attribute to false
     */
    public void unlock() {
        isLocked = false;
    }

    /**
     * Returns true if a Container is open
     * @return true if open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Opens a Container by setting the isOpen attribute to false
     */
    public void setOpen() {
        isOpen = true;
    }

    /**
     * Adds a given Item to an ArrayList of Items stored in a Container
     * @param item An Item to store
     */
    public void storeItem(Item item) {
        containedItems.add(item);
    }

    /**
     * Removes an Item from an ArrayList of Items Stored in a Container, provided it's found there
     * @param item An Item to remove
     */
    public void removeItem(Item item) {
        if (!containedItems.contains(item)) {
            System.out.println("This item can't be found.");
            return;
        }
        containedItems.remove(item);
    }

    /**
     * Returns true if an Item is in a Container 
     * @param item the desired Item
     * @return true if the Item has been stored in the Container, otherwise false
     */
    public boolean hasItem(Item item) {
        return containedItems.contains(item);
    }

    /**
     * Allows an Item to be taken from a Container, provided it can be found there and the Container is not locked
     * @param item the desired Item
     */
    public void takeItem(Item item) {
        if (isLocked || !containedItems.contains(item)) {
            System.out.println("You can't have this item.");
            return;
        }
        containedItems.remove(item);
        System.out.println("You grab the " + item.getName() + ".");
    }

    /**
     * Given a String representing an Item name, returns the given Item, provided it can be found in the Container
     * @param itemName the name of the desired Item
     * @return the Item, or null
     */
    public Item getItem(String itemName) {
        for (Item item : containedItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        } 
        return null;
    }

    /**
     * Given an Item, returns the given Item, provided it can be found in the Container
     * @param itemName the desired Item
     * @return the Item, or null
     */
    public Item getItem(Item item) {
        if (!containedItems.contains(item)) {
            System.out.println("There isn't any " + item.getName() + " here.");
            return null;
        }
        return item;
    }

    /**
     * Prints a list of items in a Container, provided it's not locked
     */
    public void listItems() {
        if (isLocked) {
            return;
        } else {
            if (!containedItems.isEmpty()) {
                System.out.println("Inside the " + getName() + ", you see:");
                for (Item item : containedItems) {
                    System.out.println(item.getDescription());
                }
            } else {
                System.out.println("The " + getName() + " is empty.");
            }
        }
        
    }

    /**
     * Opens a Container, provided it's not locked
     */
    public void open() {
        if (isLocked) {
            System.out.println("The " + getName() + " is locked.");
            return;
        } else {
            System.out.println("You open the " + getName());
            isOpen = true;
            listItems();
        }
    }

}
