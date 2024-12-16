/**
 * Represents the Weapon class
 */
public class Weapon extends Item {
    
    // Attributes
    private int condition;
    private WeaponType type;

    // Constructor
    public Weapon(String name, String description, WeaponType type) {
        super(name, description);
        this.condition = 100;
        this.type = type;
    }

    // Methods

    /**
     * Get the Weapon's type
     * @return the WeaponType
     */
    public WeaponType getType() {
        return type;
    }

    /**
     * Get the Weapon's condition
     * @return the current condition
     */
    public int getCondition() {
        return condition;
    }

    /**
     * Returns true if a Weapon's condition is above 0
     * @return true if the condition is above 0, false otherwise
     */
    public boolean workingCondition() {
        return condition > 0;
    }

    /**
     * Allows a Weapon to be used
     */
    @Override
    public void use() {
        if (type.equals(WeaponType.KNIFE) || type.equals(WeaponType.SWORD) ) {
            System.out.println("You slash at the " + getName() + " through the air.");
        } else if (type.equals(WeaponType.HAMMER)) {
            System.out.println("You slam the hammer down with all your might.");
        }
        
    }

    /**
     * Allows a Weapon to be used on a Creature
     * @param creature the Creature target
     */
    public void use(Creature creature) {
        if (type.equals(WeaponType.KNIFE) || type.equals(WeaponType.SWORD) ) {
            System.out.println("You slash at the " + creature.getType() + ".");
        } else if (type.equals(WeaponType.HAMMER)) {
            System.out.println("With all your might, you slam the hammer down on the " + creature.getType() + ".");
        }
        
    }

    /**
     * Allows a Weapon to be used to attack a Creature, decreasing the Weapon's condition
     * @param creature the target of the attack
     */
    public void attack(Creature creature) {
        if (!workingCondition()) {
            System.out.println("Your " + getName() + " appears to be damaged beyond repair. Good luck attacking anything with that...");
            return;
        }
        condition -= 20;
        use(creature);
    }
}
