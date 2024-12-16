/**
 * Represents the Medicine class
 */
public class Medicine extends Item {

    // Full constructor
    public Medicine(String name, String description) {
        super(name, description);
    }

    /**
     * Allows for the consumption of Medicine
     */
    @Override
    public void use() {
        System.out.println("You take the " + getName());
    }
}
