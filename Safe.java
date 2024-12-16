/**
 * Represents the Safe class
 */
public class Safe extends Container {
    
    // Attributes
    private String combination;

    // Full Constructor
    public Safe(String name, String description, boolean isLocked, boolean isOpen, String combination) {
        super(name, description, isLocked, isOpen);
        this.combination = combination;
    }

    // Methods

    /**
     * Unlocks a Safe, provided the guess matches the Safe combination
     * @param guess A combination guess
     * @return true if the safe has been unlocked, otherwise false
     */
    public boolean unlock(String guess) {
        if (combination.equals(guess)) {
            super.unlock();
            System.out.println("You've unlocked the safe.");
            listItems();
            return true;
        }
        System.out.println("Wrong combination.");
        return false;
    }
}

