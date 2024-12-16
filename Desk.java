/**
 * Represents the Desk class
 */
public class Desk extends Container {
    
    // Attributes
    private Key accessItem;

    // Constructor
    public Desk(String name, String description, boolean isLocked, boolean isOpen, Key accessItem) {
        super(name, description, isLocked, isOpen);
        this.accessItem = accessItem;
    }

    // Methods

    /**
     * Returns a Desk's accessItem
     * @return the accessItem
     */
    public Key getKey() {
        return accessItem;
    }

    /**
     * Unlocks a Desk, provided the Desk is locked and the Key used matches the accessItem
     * @param key a Key to unlock the Desk
     */
    public void unlock(Key key) {
        if (!isLocked()) {
            System.out.println("The " + getName() + " isn't locked.");
            return;
        } else if (!key.equals(accessItem)) {
            System.out.println("You need the key that unlocks this " + getName());
            return;
        }
        key.use();
        super.unlock();
    }

}
