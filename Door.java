/**
 * Represents the Door class
 */
public class Door {
    
    // Attributes
    private boolean isLocked;
    private Item accessItem;
    private String description;

    // Constructor with accessItem only
    public Door(Item accessItem) {
        this.isLocked = true;
        this.accessItem = accessItem;
        this.description = "A door";
    }

    // Full constructor
    public Door(boolean isLocked, Item accessItem, String description) {
        this.isLocked = isLocked;
        this.accessItem = accessItem;
        this.description = description;
    }

    // Methods

    /**
     * Returns true if a Door is locked
     * @return true if locked, false otherwise
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Returns a Door's key
     * @return the Key to unlock the Door
     */
    public Item getKey() {
        return accessItem;
    }

    /**
     * Returns a Door's description
     * @return the Door's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Unlocks a Door, provided it is locked and the Key being used matches the Door Key
     * @param item a Key to unlock the door
     * @return true if the door has been succesfully unlocked, false otherwise
     */
    public boolean unlockDoor(Item item) {
        if (!isLocked) {
            System.out.println("The door is already unlocked.");
            return true;
        } if (item.equals(accessItem)) {
            isLocked = false;
            accessItem.use();
            return true;
        } else {
            System.out.println("You need something different to unlock this door.");
            return false;
        }
    }

    /**
     * Unlocks a Door with a Keypad, provided it is locked and the code entered matches the Door code
     * @param code a code to unlock the door
     * @return true if the door has been succesfully unlocked, false otherwise
     */
    public void unlockKeypadDoor(String code) {
        if (!isLocked) {
            System.out.println("The door is already unlocked.");
            return;
        } if (accessItem instanceof Keypad) {
            Keypad keypad = (Keypad) accessItem;
            keypad.unlock(code);
            if (!keypad.isLocked()) {
                isLocked = false;
                System.out.println("You've unlocked the door.");
            }
        } else {
            System.out.println("You need something to unlock this door.");
            return;
        }
    }
}
