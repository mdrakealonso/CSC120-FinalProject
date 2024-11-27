public class Door {
    
    // Attributes
    private boolean isLocked;
    private Item accessItem;

    // Default constructor
    public Door() {
        this(true, new Key());
    }

    // Overloaded constructor with accessItem only
    public Door(Item accessItem) {
        this.isLocked = true;
        this.accessItem = accessItem;
    }

    // Full constructor
    public Door(boolean isLocked, Item accessItem) {
        this.isLocked = isLocked;
        this.accessItem = accessItem;
    }

    // Methods
    public boolean unlockDoor(Item item) {
        if (item.equals(accessItem)) {
            isLocked = true;
            System.out.println("Sliding the " + item.getName() + " into the lock, you hear a small click. You've unlocked the door.");
        } else {
            isLocked = false;
            System.out.println("The door remains locked.");
        }
        return isLocked;
    }
}
