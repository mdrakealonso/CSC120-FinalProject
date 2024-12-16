/**
 * Represents the Keypad class
 */
public class Keypad extends Item {
    
    // Attributes
    boolean isLocked;
    String combination;

    // Constructor
    public Keypad(String name, String description, boolean isLocked, String combination) {
        super(name, description);
        this.isLocked = isLocked;
        this.combination = combination;
    }

    // Methods

    /**
     * Returns true if a Keypad is locked
     * @return true if locked, false otherwise
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Returns a Keypad's combination
     * @return the combination to unlock
     */
    public String getCombination() {
        return combination;
    }

    /**
     * Use the Keypad
     */
    public void use() {
        System.out.println("You can't use this.");
    }

    /**
     * Unlocks a Keypad, provided the combination guess is correct
     * @param guess a combination guess
     * @return true if unlocked, otherwise false
     */
    public boolean unlock(String guess) {
        if (combination.equals(guess)) {
            isLocked = false;
            return true;
        }
        System.out.println("Wrong code.");
        return false;
    }
}
