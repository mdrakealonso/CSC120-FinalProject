import java.util.ArrayList;

/**
 * Represents the Room class
 */
public class Room {
    
    // Attributes
    private String name;
    private String description;
    private Safe safe; // Optional
    private Desk desk; // Optional
    private ArrayList<Item> items;
    private ArrayList<Creature> creatures;

    // Full constructor
    public Room(String name, String description, Safe safe, Desk desk) {
        this.name = name;
        this.description = description;
        this.safe = safe;
        this.desk = desk;
        this.items = new ArrayList<>();
        this.creatures = new ArrayList<>();
    }

    // Methods

    /**
     * Returns the name of a Room
     * @return the Room name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if a Room has a Safe
     * @return true if a Room contains a Safe, otherwise false
     */
    public boolean hasSafe() {
        return safe != null;
    }

    /**
     * Returns true if a Room has a Desk
     * @return true if a Room contains a Desk, otherwise false
     */
    public boolean hasDesk() {
        return desk != null;
    }

    /**
     * Returns the Room Safe
     * @return the Room Safe, or null
     */
    public Safe getSafe() {
        return safe;
    }

    /**
     * Returns the Room Desk
     * @return the Room Desk, or null
     */
    public Desk getDesk() {
        return desk;
    }

    /**
     * Adds an Item to an ArrayList of Items in a Room
     * @param item the intended Item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an Item from a Room, provided it can be found there
     * @param item the desired Item
     * @return true if the Item has been removed, false otherwise
     */
    public boolean removeRoomItem(Item item) {
        if(!items.contains(item)) {
            System.out.println("This item can't be located. Try something else.");
            return false;
        }
        items.remove(item);
        return true;
    }

    /**
     * Removes an Item from a Safe, provided the Room has a Safe and the Item can be found there
     * @param item the desired Item
     * @return true if the Item has been removed, false otherwise
     */
    public boolean removeSafeItem(Item item) {
        if (hasSafe()) {
            getSafe().removeItem(item);
            return true;
        }
        return false;
        
    }

    /**
     * Removes an Item from a Desk, provided the Room has a Desk and the Item can be found there
     * @param item the desired Item
     * @return true if the Item has been removed, false otherwise
     */
    public boolean removeDeskItem(Item item) {
        if (hasDesk()) {
            getDesk().removeItem(item);
            return true;
        }
        return false;
        
    }

    /**
     * Given a String representing an Item name, returns an Item which has a name matching the String, provided it can be found
     * @param itemName an Item name
     * @return the corresponding Item, or null
     */
    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        } 
        return null;
    }

    /**
     * Given an Item, returns the matching Item, provided it can be found
     * @param item an Item name
     * @return the corresponding Item, or null
     */
    public Item getItem(Item item) {
        if (!items.contains(item)) {
            System.out.println("This item doesn't exist in this room.");
            return null;
        }
        return item;
    }


    /**
     * Adds a Creature to an ArrayList of Creatures in a Room
     * @param creature the intended Creature
     */
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    /**
     * Removes a Creature from a Room, provided it can be found there
     * @param creature the desired Creature
     * @return true if the Creature has been removed, false otherwise
     */
    public boolean removeCreature(Creature creature) {
        if(!creatures.contains(creature)) {
            System.out.println("This creature can't be located. Try something else.");
            return false;
        }
        creatures.remove(creature);
        return true;
    }

    /**
     * Returns true if there is at least one Creature in a Room
     * @return true if the Room contains at least one Creature, false otherwise
     */
    public boolean hasCreature() {
        return (creatures.size() != 0);
    }

    /**
     * From the ArrayList of Creatures, returns the Creature at the index specified 
     * @param index the index of the desired Creature
     * @return The Creature or null
     */
    public Creature getCreature(int index) {
        if (creatures.size() == 0) {
            return null;
        }
        return creatures.get(index);
    }

    /**
     * Prints the description of a Room
     */
    public void printDescription() {
        System.out.println(description);
    }

    /**
     * Prints a list of Items in a Room, including those in an unlocked and open Safe and/or Desk
     */
    public void listItems() {
        if (!items.isEmpty()) {
            System.out.println("Looking around, you see:");
            for (Item item : items) {
                System.out.println(item.getDescription());
            }
        } else {
            System.out.println("You don't see anything lying around in the room.");
        }

        if (safe != null) {
            if (safe.isLocked() || !safe.isOpen()) {
                System.out.println("There could be something in the safe.");
            } else {
                safe.listItems();
            }
        } if (desk != null) {
            if (desk.isLocked() || !safe.isOpen()) {
                System.out.println("There could be something in the desk.");
            } else {
                desk.listItems();
            }
        }
    }

    /**
     * Prints a list of Creatures in a Room
     */
    public void listCreatures() {
        for (int i = 0; i < creatures.size(); i++) {
            System.out.println(creatures.get(i).getType());
        }
    }
}
