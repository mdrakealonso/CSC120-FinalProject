/**
 * Represents the Key class
 */
public class Key extends Item{
    
    // Attributes
    private KeyType type;

    // Constructor
    public Key(String name, String description, KeyType type) {
        super(name, description);
        this.type = type;
    }

    // Methods

    /**
     * Returns a Key's type
     * @return
     */
    public KeyType getType() {
        return type;
    }

    /**
     * Allows a Key to be used
     */
    @Override
    public void use() {
        if (getType().equals(KeyType.KEYCARD)) {
            System.out.println("You hold the keycard to the door, and it beeps satisfactorily. You've unlocked the door.");
            return;
        } else {
            System.out.println("Sliding the " + getName() + " into the lock, you hear a small click.\nYou've unlocked the door.");
        }
    }
}
