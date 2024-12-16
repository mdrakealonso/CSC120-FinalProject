/**
 * Represents the Item class
 */
abstract class Item {
    
    // Attributes
    private String name;
    private String description;

    // Constructor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Methods

    /**
     * Returns an Item's name in lower case
     */
    public String getName() {
        return name.toLowerCase();
    }

    /**
     * Returns an Item's description
     * @return the Item description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Prints an Item's description
     */
    public void printDescription() {
        System.out.println(description);
    }

    /**
     * An abstract use method
     */
    abstract void use();
}
