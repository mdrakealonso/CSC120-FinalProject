public class Knife extends Item {
    
    // Attributes

    // Constructor
    public Knife(String name) {
        super(name);
    }

    // Methods

    @Override
    public void use() {
        System.out.println("You slash the knife through the air.");
    }

    public void attack(Creature creature) {
        System.out.println("You slash at the " + creature.getType() + ".");
    }
}