/*
 * The Creature class
 */
public class Creature {

    // Attributes
    private String type;
    private int health;

    // Simple constructor
    public Creature(String type) {
        this.type = type;
        health = 100;
    }

    // Methods
    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean takeDamage(int damage) {
        if(damage <= 0) {
            System.out.println("Damage must positive.");
            return isAlive();
        }
        if(!isAlive()) {
            System.out.println("The " + type.toLowerCase() + " is dead.");
            return isAlive();
        }
        health -= damage;
        if(health <= 0) {
            die();
        } 
        return isAlive();
    }

    public void die() {
        if(!isAlive()) {
            System.out.println("The " + type.toLowerCase() + " is already dead.");
            return;
        }
        System.out.println("The " + type.toLowerCase() + " has died. Farwell, you strange thing.");
        health = 0;
    }

    public static void main(String[] args) {
        Creature mutant = new Creature("Mutant");
        mutant.takeDamage(90);
        System.out.println(mutant.getHealth());
        mutant.takeDamage(15);
        mutant.takeDamage(15);
        System.out.println(mutant.getHealth());
    }
}
