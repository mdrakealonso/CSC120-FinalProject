import java.util.ArrayList;

public class Room {
    
    // Attributes
    private String name;
    private String description;
    private ArrayList<Item> items;
    private ArrayList<Creature> creatures;
    private ArrayList<Door> doors;

    // Full constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.creatures = new ArrayList<>();
        this.doors = new ArrayList<>();
    }

    // Methods
    public String getName() {
        return name;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public String addItem(Item item) {
        items.add(item);
        return item.getName();
    }

    public boolean removeItem(Item item) {
        if(!items.contains(item)) {
            System.out.println("This item can't be located. Try something else.");
            return false;
        }
        items.remove(item);
        return true;
    }

    public String addCreature(Creature creature) {
        creatures.add(creature);
        return creature.getType();
    }

    public boolean removeCreature(Creature creature) {
        if(!creatures.contains(creature)) {
            System.out.println("This creature can't be located. Try something else.");
            return false;
        }
        creatures.remove(creature);
        return true;
    }

    public void listItems() {
        System.out.println("Looking around, you see:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("a " + items.get(i).getName());
        }
    }

    public void listCreatures() {
        for (int i = 0; i < creatures.size(); i++) {
            System.out.println(creatures.get(i).getType());
        }
    }

    public String toString() {
        return name + " " + description;
    }

    public static void main(String[] args) {
        Knife testKnife = new Knife("Very sharp knife");
        Key testKey = new Key("Shiny key");
        Room testRoom = new Room("Name", "Description");
        testRoom.addItem(testKnife);
        testRoom.addItem(testKey);
        testRoom.listItems();
    }
}
