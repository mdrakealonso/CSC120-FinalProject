abstract class Item {
    
    // Attributes
    private String name;

    // Basic constructor
    public Item() {
        name = "item";
    }

    // Full constructor
    public Item(String name) {
        this.name = name;
    }

    // Methods
    public String getName() {
        return name.toLowerCase();
    }

    abstract void use();
}
