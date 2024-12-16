/**
 * Represents the Paper class
 */
public class Paper extends Item {

    // Attributes
    String textContent;
    
    // Full constructor
    public Paper(String name, String description, String textContent) {
        super(name, description);
        this.textContent = textContent;
    }

    /**
     * Use the Paper
     */
    public void use() {
        System.out.println("You can't use this.");
    }

    /**
     * Prints the contents of the Paper
     */
    public void readPaper() {
        System.out.println("The " + getName() + " reads: \n    ");
        System.out.println(textContent);
    }
}
