import java.util.ArrayList;

public class Player {
    
    // Attributes
    private ArrayList<Item> inventory;
    private int health, positionX, positionY;
    private Room currentRoom;

    public Player(Room currentRoom) {
        this.inventory = new ArrayList<>();
        this.health = 100;
        this.positionX = 0;
        this.positionY = 0;
        this.currentRoom = currentRoom;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damageAmt) {
        health -= damageAmt;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /**
     * Allows the Player to walk in a specified direction
     * @param direction direction to walk toward
     */
    public boolean walk(Direction direction) {
        if (direction.equals((Direction.NORTH))) {
            positionY ++;
        } else if (direction.equals(Direction.EAST)) {
            positionX ++;
        } else if (direction.equals(Direction.SOUTH)) {
            positionY --;
        } else if (direction.equals(Direction.WEST)) {
            positionX --;
        } else {
            System.out.println("Enter a valid direction.");
            return false;
        }
        System.out.println("You walk to the " + String.valueOf(direction).toLowerCase() + ".");
        return true;
    }

    public Item takeItem(Item item) {
        inventory.add(item);
        System.out.println("You grab the " + item.getName() + ".");
        return item;
    }

    public Item dropItem(Item item) {
        if(!inventory.contains(item)) {
            System.out.println("You don't have this item.");
        }
        inventory.remove(item);
        System.out.println("You drop the " + item.getName() + ".");
        return(item);
    }

    public void printInventory() {
        System.out.println("Inventory items: ");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("- " + inventory.get(i).getName());
        }
    }

    public static void main(String[] args) {
        Room testRoom = new Room("Room", "A big room");
        Key randomKey = new Key();
        Knife randomKnife = new Knife("Sharp knife");
        Player player = new Player(testRoom);
        player.inventory.add(randomKey);
        player.inventory.add(randomKnife);
        player.printInventory();
    }
}
