import java.util.ArrayList;
import java.util.Random;
/*
 * Represents the Creature class
 */
public class Creature {

    // Attributes
    private String type;
    private int health;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    // Constructor
    public Creature(String type, Room currentRoom) {
        this.type = type;
        this.currentRoom = currentRoom;
        health = 100;
        this.inventory = new ArrayList<>();
    }

    // Methods

    /**
     * Returns a Creature's type
     * @return the type
     */
    public String getType() {
        return type.toLowerCase();
    }

    /**
     * Returns a Creature's health
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns true if a Creature's health is above 0
     * @return returns true if health is above 0, otherwise false
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Returns a Creature's inventory
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Adds an Item to a Creature's inventory
     * @param item the intended Item
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Removes an Item from a Creature's inventory, provided it can be found there
     * @param item the desired Item
     * @return the dropped Item, or null
     */
    public Item dropItem(Item item) {
        if(!inventory.contains(item)) {
            System.out.println("You don't have this item.");
            return null;
        }
        inventory.remove(item);
        System.out.println("You drop the " + item.getName() + ".");
        return(item);
    }

    /**
     * Removes all Item's from a Creature's inventory, provided it is not empty
     */
    public void dropAllItems() {
        if (inventory.isEmpty()) {
            System.out.println("There's nothing to drop.");
            return;
        } else {
            for (int i = inventory.size() - 1; i >= 0 ; i--) {
                Item item = inventory.get(i);
                inventory.remove(i);
                if (currentRoom != null) {
                    System.out.println("The " + getType() + " has dropped a(n) " + item.getName() + ".");
                    currentRoom.addItem(item);
                } else {
                    System.out.println("WARNING: The " + getType() + " doesn't have an assigned room.");
                }
                
            }
        }
        
    }

    /**
     * Given an Item, checks whether the Item is in a Creature's inventory
     * @param item the desired Item
     * @return true if the Item is in the Creature's inventory, false otherwise
     */
    public boolean inInventory(Item item) {
        return inventory.contains(item);
    }

    /**
     * Given an String, checks whether an Item with a name matching the String is in a Creature's inventory
     * @param item the name of the desired Item
     * @return true if the Item is in the Creature's inventory, false otherwise
     */
    public boolean inInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a String representing an Item name, returns an Item with a matching name in the Creatures inventory, or null if none are found
     * @param itemName the name of the desired Item
     * @return the Item if such an Item exists in the Creature's inventory, null otherwise
     */
    public Item getInventoryItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().toLowerCase().equals(itemName)) {
                return item;
            }
        } 
        return null;
    }

    /**
     * Prints all Items in a Creature's inventory
     */
    public void printInventory() {
        System.out.println("Inventory items: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("- " + inventory.get(i).getName());
        }
    }

    /**
     * Allows a Creature to attack a Player and deal a random damage amount
     * @param player the target of the attack
     */
    public void attack(Player player) {
        Random rand = new Random();
        int damageAmt = rand.nextInt(101);
        player.takeDamage(damageAmt);
    }

    /**
     * Decreases a Creature's health by a specified amount
     * @param damage the desired damage amount
     */
    public void takeDamage(int damage) {
        if(damage <= 0) {
            System.out.println("Damage must positive.");
            return;
        } if(health <= 0 || health - damage <= 0) {
            die();
            return;
        }
        health -= damage;
        System.out.println("The creature appears to have been weakened.");
    }

    /**
     * Sets a Creature's health to 0 and drops all of its Items
     */
    public void die() {
        System.out.println("The " + type.toLowerCase() + " has died. Farewell, you strange thing.");
        health = 0;
        dropAllItems();

    }
}
