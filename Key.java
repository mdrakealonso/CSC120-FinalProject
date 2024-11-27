public class Key extends Item{
    
    // Attributes

    // Basic constructor
    public Key() {
        super("key");
    }

    // Full constructor
    public Key(String name) {
        super(name);
    }

    // Methods
    @Override
    public void use() {
        System.out.println("Sliding the " + getName() + " into the lock, you hear a small click.\nYou've unlocked the door.");
    }

    public static void main(String[] args) {
        Key keycard = new Key("keycard");
        keycard.use();
    }
}
