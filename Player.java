import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Player class
 */
public class Player {
    
    // Attributes
    private ArrayList<Item> inventory;
    private int health;
    private boolean infected;
    private Room currentRoom, previousRoom;

    // Constructor
    public Player(Room currentRoom) {
        this.inventory = new ArrayList<>();
        this.health = 100;
        this.infected = true;
        this.currentRoom = currentRoom;
        this.previousRoom = null;
    }

    // Methods

    /**
     * Returns a Player's current Room
     * @return the current Room, or null
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Returns a Player's previous Room
     * @return the previous Room, or null
     */
    public Room getPreviousRoom() {
        return previousRoom;
    }

    /**
     * Sets a Player's currentRoom to a different Room
     * @param newRoom the intended Room
     */
    public void setCurrentRoom(Room newRoom) {
        this.previousRoom = this.currentRoom;
        currentRoom = newRoom;
    }

    /**
     * Return's a Player's inventory
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Returns a Player's current health
     * @return the current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns true if a Player's health is above 0
     * @return true if health is greater than 0, false otherwise
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Returns true if a Player is infected
     * @return true if infected, false otherwise
     */
    public boolean isInfected() {
        return infected == true;
    }

    /**
     * "Heals" a Player by setting isInfected to false
     */
    public void heal() {
        infected = false;
    }

    /**
     * Checks whether a Player has an instance of Medicine in their inventory
     * @return true if an instance of Medicine exists in the inventory, false otherwise
     */
    public boolean hasMedicine() {
        for (Item item : inventory) {
            if (item instanceof Medicine) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first instance of Medicine in a Player's inventory
     * @return the first instance of Medicine, or null
     */
    public Item getMedicine() {
        for (Item item : inventory) {
            if (item instanceof Medicine) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns true if a Player has a Weapon in their inventory
     * @return true if an instance of a Weapon exists in the inventory, false otherwise
     */
    public boolean hasWeapon() {
        for (Item item : inventory) {
            if (item instanceof Weapon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first instance of a (working) Weapon in a Player's inventory
     * @return the first instance of a Weapon, or null
     */
    public Weapon getAnyWeapon() {
        for (Item item : inventory) {
            if (item instanceof Weapon && ((Weapon)item).workingCondition()) {
                Weapon weapon = (Weapon) item;
                return weapon;
            }
        }
        return null;
    }

    /**
     * Allows a Player to attack a Creature, dealing a random damage amount, provided they have a Weapon in their inventory
     * @param creature the target Creature
     * @param weaponName the name of the Weapon
     */
    public void attack(Creature creature, String weaponName) {
        if (creature == null) {
            System.out.println("There's nothing to attack.");
        }

        Item item = getInventoryItem(weaponName);
        if (item instanceof Weapon) {
            Weapon weapon = (Weapon) item;

            Random rand = new Random();
            int damageAmt = rand.nextInt(101);
            weapon.attack(creature);
            creature.takeDamage(damageAmt);
        }
    }

    /**
     * Decreases a Player's health
     * @param damageAmt the amount of damage
     */
    public void takeDamage(int damageAmt) {
        if(damageAmt <= 0) {
            System.out.println("Damage must positive.");
            return;
        }
        
        health -= damageAmt;
        if (isAlive()) {
            System.out.println("Your health has decreased to " + health + "%!");
        } else {
            System.out.println("You've succumbed to your injuries.");
        }
    }

    /**
     * Decreases a Player's health to 0
     */
    public void die() {
        health = 0;
    }

    /**
     * Allows a Player to take medicine, provided they have it in their inventory
     * @param medicine any Medicine
     */
    public void takeMedicine(Medicine medicine) {
        if (!inInventory(medicine)) {
            System.out.println("You don't have any of that.");
            return;
        } if (medicine.getName().equals("antidote") || medicine.getName().equals("syringe")) {
            System.out.println("You stab the syringe into your arm.");
            inventory.remove(medicine);
            heal();
            return;
        } if (getHealth() == 100 || (getHealth() + 10) > 100) {
            System.out.println("You don't need any medicine.");
            return;
        }
        medicine.use();
        health += 10;
        inventory.remove(medicine);
        System.out.println("You've regained some strength.");
    }

    /**
     * Given a String, allows a Player to add the Item with that name to their inventory, provided such an Item exists
     * @param itemName the name of the desired Item
     * @return the Item added, or null
     */
    public Item takeItem(String itemName) {
        Item item = currentRoom.getItem(itemName);

        if (item == null) {
            if (currentRoom.hasSafe()) {
                item = currentRoom.getSafe().getItem(itemName);
            } 
            
            if (currentRoom.hasDesk() && item == null) {
                item = currentRoom.getDesk().getItem(itemName);
            }
            
        }

        if (item == null) {
            System.out.println("That doesn't make sense.");
            return null;
        }

        inventory.add(item);
        System.out.println("You grab the " + itemName + ".");
        System.out.println("A new item has been added to your inventory: " + item.getDescription());
        if (currentRoom.getItem(itemName) != null) {
            currentRoom.removeRoomItem(item);
        } else if (currentRoom.getSafe() != null) {
            if (currentRoom.getSafe().getItem(itemName) != null) {
                currentRoom.getSafe().removeItem(item);
            }
        } else if (currentRoom.getDesk() != null) {
            if (currentRoom.getDesk().getItem(itemName) != null) {
                currentRoom.getDesk().removeItem(item);
            }
        }
        return item;
    }

    /**
     * Given an Item, allows a Player to add that Item to their inventory, provided such an Item exists
     * @param itemName the desired Item
     * @return the Item added, or null
     */
    public Item takeItem(Item target) {
        Item item = currentRoom.getItem(target);

        if (item == null) {
            if (currentRoom.getSafe() != null) {
                item = currentRoom.getSafe().getItem(target);
            } 
            
            if (currentRoom.getDesk() != null && item == null) {
                item = currentRoom.getDesk().getItem(target);
            }
            
        }

        if (item == null) {
            System.out.println("There's no " + target.getName() + " here.");
            return null;
        }

        inventory.add(item);
        System.out.println("You grab the " + item.getName() + ".");
        System.out.println("A new item has been added to your inventory: " + item.getDescription());
        if (currentRoom.getItem(item) != null) {
            currentRoom.removeRoomItem(item);
        } else if (currentRoom.getSafe() != null) {
            if (currentRoom.getSafe().getItem(item) != null) {
                currentRoom.getSafe().removeItem(item);
            }
        } else if (currentRoom.getDesk() != null) {
            if (currentRoom.getDesk().getItem(item) != null) {
                currentRoom.getDesk().removeItem(item);
            }
        }
        return item;
    }

    /**
     * Given an Item, allows a Player to drop that Item from their inventory, provided they have such an Item
     * @param itemName the Item to drop
     * @return the Item dropped, or null
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
     * Given a String, allows a Player to drop the Item with that name from their inventory, provided they have such an Item
     * @param itemName the name of the Item to drop
     * @return the Item dropped, or null
     */
    public Item dropItem(String itemName) {
        itemName = itemName.toLowerCase();

        if (inInventory(itemName)) {
            Item item = getInventoryItem(itemName);
            inventory.remove(item);
            System.out.println("You drop the " + itemName + ".");
            return(item);
        } else {
            System.out.println("You don't have that.");
            return null;
        }
    }

    /**
     * Given an Item, returns true if that Item exists in the Player's inventory
     * @param itemName the desired Item
     * @return true if such an Item exists, false otherwise
     */
    public boolean inInventory(Item item) {
        return inventory.contains(item);
    }

    /**
     * Given a String, returns true if an Item with the same name exists in the Player's inventory
     * @param itemName the name of the desired Item
     * @return true if such an Item exists, false otherwise
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
     * Given a String, returns an Item from the Player's inventory that has a name matching the String
     * @param itemName the name of the desired Item
     * @return the Item, or null
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
     * Prints a Player's inventory
     */
    public void printInventory() {
        System.out.println("Inventory items: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("- " + inventory.get(i).getName());
        }
    }
}
