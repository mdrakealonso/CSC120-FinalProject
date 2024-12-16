import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

/**
 * Represents the Game class
 */
public class Game {

    // Attributes
    private Player player;
    private Room currentRoom, startRoom, laboratory, vivarium, workshop, outside;
    private ArrayList<Room> listRooms;
    private MutableNetwork<Room, Door> building;
    private Scanner userInput;

    // Constructor
    public Game() {
        initializeGame();
    }

    // Methods

    /**
     * Creates a new instance of the Player class
     */
    private void initializePlayer() {
        this.player = new Player(startRoom);
    }

    /**
     * Initializes the Building Network
     */
    private void initializeBuilding() {
        this.building = NetworkBuilder.undirected()
            .allowsSelfLoops(false)
            .build();
    }

    /**
     * Initializes Building Rooms
     */
    private void initializeRooms() {
        // Room containers
        Safe labSafe = new Safe("Lab safe", "A small safe in the labratory.", true, false, "743");
        Desk labDesk = new Desk("Lab desk", "A white desk in the labratory.", false, false, null);
        Desk workshopDesk = new Desk("Workshop desk", "A wooden desk in the workshop.", false, false, null);

        // Rooms
        startRoom = new Room("Entry", "You're in a grimy room, surrounded by shadows.\nAn orange door lies to your left, and a blue one to your right.\nThe number 743 has been scribbled on the wall next to you.", null, null);
        laboratory = new Room("Laboratory", "You're in a brightly-lit room resembling a laboratory.\nYou see a safe to your right, and a white desk sitting ahead.", labSafe, labDesk);
        vivarium = new Room("Vivarium", "You're in a large room with just enough light to make out a purple door in front of you.", null, null);
        workshop = new Room("Workshop", "You're in a cluttered room, papers and hardware strewn about.\nA wooden desk sits in front of you.\nA green door lies to your left, closed.", null, workshopDesk);
        outside = new Room("Outside", "You're in a large field, a winding road visible ahead.", null, null);

        this.listRooms = new ArrayList<>(List.of(startRoom, laboratory, vivarium, workshop, outside));
        currentRoom = startRoom;

        building.addNode(startRoom);
        building.addNode(vivarium);
        building.addNode(laboratory);
        building.addNode(workshop);
        building.addNode(outside);
    }

    /**
     * Initializes Room objects
     */
    private void initializeObjects() {
        // Creature
        Creature mutant = new Creature("Mutant", vivarium);
        vivarium.addCreature(mutant);

        // Keys
        Key key = new Key("Key", "A shiny purple key", KeyType.KEY);
        mutant.addItem(key);
        Key keycard = new Key("Keycard", "An orange keycard", KeyType.KEYCARD);
        laboratory.getDesk().storeItem(keycard);
        Keypad keypad = new Keypad("Keypad", "A keypad", true, "985");

        // Weapons
        Weapon sharpKnife = new Weapon("Knife", "A heavy, very sharp-looking knife", WeaponType.KNIFE);
        startRoom.addItem(sharpKnife);

        // Medicine
        Medicine healthPills = new Medicine("Pills", "A bottle of pills that claim to make you nice and healthy");
        laboratory.getSafe().storeItem(healthPills);
        Medicine antidote = new Medicine("Syringe", "A syringe full of thick, purple liquid. It sparkles in the light");
        workshop.getDesk().storeItem(antidote);

        // Notes
        Paper labNotes = new Paper("Paper", "A wrinkled scrap of paper covered in barely legible handwriting", 
        ("  'Day 26:\n" +
        "   The subject has become hostile and dangerous.\n" +
        "   Visible increase in muscle density.\n" +
        "   The effects of the [REDACTED] are unprecedented.\n" +
        "   Extreme caution advised.'"));
        laboratory.getDesk().storeItem(labNotes);
        Paper codeNote = new Paper("Note", "A sticky note with a code on it.","985");
        mutant.addItem(codeNote);
        Paper workshopNotes = new Paper("Paper", "A yellowed paper with scrawling writing.", 
        "   'If you see This, GET OUT before it's too late.\n" +
        "   it might alrdy B...\n" +
        "   the air...'");
        workshop.getDesk().storeItem(workshopNotes);

        // Doors
        Door orangeDoor = new Door(true, keycard, "An orange door");
        Door blueDoor = new Door(false, null, "A blue door");
        Door purpleDoor = new Door(true, key, "A purple door");
        Door greenDoor = new Door(true, keypad, "A green door");
        building.addEdge(vivarium, workshop, purpleDoor);
        building.addEdge(startRoom, laboratory, blueDoor);
        building.addEdge(startRoom, vivarium, orangeDoor);
        building.addEdge(workshop, outside, greenDoor);
    }

    /**
     * Initalizes Game
     */
    private void initializeGame() {
        initializeBuilding();
        initializeRooms();
        initializePlayer();
        initializeObjects();
    }

    /**
     * Gets input from user
     * @return the input message in lower case
     */
    public String getUserInput() {
        return userInput.nextLine().toLowerCase();
    }

    /**
     * Allows an event to happen in a Room, depending on the Room given
     * @param room a Room
     */
    public void roomEvent(Room room) {
        if (room.getName().equals("Vivarium") && room.hasCreature()) {
            System.out.println("You notice a tall, boil-covered creature looming in the corner. Seeing you, it opens its mouth to show two sets of teeth.");
            //room.getCreature(0).attack(player);
        } else if (room.getName().equals("Outside")) {
            System.out.println("But something's not right...\nIt's like you're losing control of your limbs...\nYour mind starts to fog...");
        }
    }

    /**
     * Show game commands available in a given room
     * @param room A Room
     */
    public void showCommands(Room room) {
        System.out.println("Available commands:");
        System.out.println("> Movement: GO");
        System.out.println("> Interaction: TAKE, DROP, USE, OPEN");
        if (room.hasCreature()) {
            System.out.println("> Combat: ATTACK");
        }
        System.out.println("> Investigation: LOOK, EXAMINE, INVESTIGATE, READ");
        System.out.println("> Information: HELP, INVENTORY");

    }

    /**
     * Given a string, returns an Item with a name matching the String, or null if no such Item is found
     * @param name The name of the desired Item
     * @return The corresponding Item, or null
     */
    private Item findItem(String name) {
        Item item = player.getCurrentRoom().getItem(name);
        if (item != null) {
            return item;
        }

        Desk desk = player.getCurrentRoom().getDesk();
        if (desk != null && !desk.isLocked() && desk.isOpen()) {
            item = desk.getItem(name);
            if (item != null) {
                return item;
            }
        }

        Safe safe = player.getCurrentRoom().getSafe();
        if (safe != null && !safe.isLocked() && safe.isOpen()) {
            item = safe.getItem(name);
            if (item != null) {
                return item;
            }
        }

        return null;
    }

    /**
     * Given a description, returns the Door matching the description that is in the building, or null if no Door is found
     * @param description a Door description
     * @return the Door matching the description, or null if no Door is found
     */
    private Door getDoor(String description) {
        for (Door door : building.incidentEdges(player.getCurrentRoom())) {
            if (door.getDescription().toLowerCase().contains(description.toLowerCase())) {
                return door;
            }
        }
        return null;
    }

    /**
     * Given a Door, returns the building Rooms connected to the Door, or null if none are found
     * @param door a Door
     * @return the connected Rooms, or null if none are found
     */
    private Room getConnectedRoom(Door door) {
        for (Room room : building.adjacentNodes(player.getCurrentRoom())) {
            Optional<Door> edge = building.edgeConnecting(player.getCurrentRoom(), room);
                if (edge.isPresent() && edge.get().equals(door)) {
                    return room;
                }
        }
        return null;
    }

    /**
     * Given a Room name, returns true if there exists a Room with that name in the ArrayList of Rooms
     * @param name a Room name
     * @return true if there is a Room with that name, false otherwise
     */
    private boolean isRoomName(String name) {
        for (Room room : listRooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Given a String, returns the Room with the matching name from the list of rooms, provided one can be found
     * @param name a Room name
     * @return the corresponding Room, or null
     */
    private Room getMatchingRoom(String name) {
        for (Room room : listRooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Allows a Player to move to a Room, provided the destination is connected to the current Room and the connecting Door is unlocked
     * @param destination the intended Room
     */
    private void moveToRoom(Room destination) {
        Door connectingDoor = building.edgeConnectingOrNull(currentRoom, destination);

        if (connectingDoor == null) {
            System.out.println("You can't go there from here.");
            return;
        } else {
            Item doorKey = connectingDoor.getKey();
            if (connectingDoor.isLocked()) {
                if (player.inInventory(doorKey)) {
                    connectingDoor.unlockDoor(doorKey);
                } else {
                    System.out.println("The door is locked.");
                    return;
                }
            }
        }

        currentRoom = destination;
        player.setCurrentRoom(destination);
        System.out.println("------------- " + currentRoom.getName().toUpperCase() + " -------------");
        currentRoom.printDescription();
        roomEvent(currentRoom);
    }

    /**
     * Given a String, allows a Player to move to a destination, provided that destination exists and is accessible
     * @param destination the intended destination
     */
    private void handleGo(String destination) {
        if (destination.equals("back")) {
            if (player.getCurrentRoom().equals(outside)) {
                System.out.println("You can't go back.");
                return;
            } else if (player.getPreviousRoom() != null) {
                moveToRoom(player.getPreviousRoom());
            } else {
                System.out.println("You have nowhere to go back to.");
            }
        } else if (isRoomName(destination)) {
            Room room = getMatchingRoom(destination);
            if (room != null) {
                moveToRoom(room);
            }
        } else if (destination.equals("door")) {
            handleOpen(destination);
        } else {
            System.out.println("That doesn't make sense.");
        }
    }

    /**
     * Given a String, allows a Player to take the Item with that name, provided the Item is accessible; If the Item is Medicine, allows a Player to use the Medicine
     * @param objective the name of the Item to take
     */
    private void handleTake(String objective) {
        if (objective.equals("medicine") || objective.equals("pills") || objective.equals("syringe")) {
            if (player.inInventory(objective)) {
                Item medicine = player.getInventoryItem(objective);
                if (medicine != null) {
                    player.takeMedicine((Medicine)medicine);
                    return;
                }
            }
        } else if (objective.equals("paper") || objective.equals("note")) {
            System.out.println("The " + objective + " appears to be stuck. Sorry.");
            return;
        }
        player.takeItem(objective);
    }

    /**
     * Given a String, allows a Player to drop the Item with that name from their inventory
     * @param objective the name of the Item to drop
     */
    private void handleDrop(String objective) {
        player.dropItem(objective);
    }

    /**
     * Given a String, allows a Player to use the Item with that name, provided the Item exists and is accessible
     * @param objective the name of the Item to use
     */
    private void handleUse(String objective) {
        Item item = player.getInventoryItem(objective);

        if (item == null) {
            System.out.println("You don't have any " + objective + " in your inventory.");
            return;
        }

        if (item instanceof Medicine) {
            player.takeMedicine((Medicine)item);
        } else if (item instanceof Weapon) {
            Creature creatureTarget = player.getCurrentRoom().getCreature(0);
            if (creatureTarget != null) {
                player.attack(creatureTarget, objective);
            } else {
                System.out.println("There's nothing to use the " + objective + " on.");
            }
        } else if (item instanceof Key) {
            System.out.println("Which door do you want to use it on?");
            String userInput = getUserInput();
            Door door = getDoor(userInput);
            if (door != null) {
                if (!door.isLocked()) {
                    System.out.println("The door isn't locked.");
                } else {
                    door.unlockDoor(item);
                }
            } else {
                System.out.println("That's not a door.");
            }
        } else {
            System.out.println("You're not making sense.");
        }
    }

    /**
     * Given a String, allows a Player to unlock the locked object with that name, 
     *  provided the object is accessible, currently locked, and the Player has the required access Item
     * @param objective the object to unlock
     */
    private void handleUnlock(String objective) {
        if (objective.equals("door")) {
            System.out.println("Which door?");
            String userResponse = getUserInput();
            if (userResponse.isEmpty()) {
                System.out.println("Enter a door color.");
                userResponse = getUserInput();
            }
            
            Door desiredDoor = null;
            for (Door door : building.incidentEdges(player.getCurrentRoom())) {
                if (door.getDescription().toLowerCase().contains(userResponse)) {
                    desiredDoor = door;
                }
            } 
            
            if (desiredDoor == null) {
                System.out.println("That doesn't seem to be a door.");
            } else if (!desiredDoor.isLocked()) {
                System.out.println("The door's not locked.");
            } else if (desiredDoor.getKey() instanceof Keypad) {
                System.out.println("What's the code?");
                String guess = getUserInput();
                desiredDoor.unlockKeypadDoor(guess);
            } else {
                Item requiredKey = desiredDoor.getKey();
                if (player.inInventory(requiredKey)) {
                    desiredDoor.unlockDoor(requiredKey);
                } else {
                    System.out.println("You need the key.");
                }
                                        }
        } else if (objective.equals("safe")) {
            if (player.getCurrentRoom().getSafe() == null) {
                System.out.println("There doesn't appear to be a safe in this room.");
            } else {
                Safe roomSafe = player.getCurrentRoom().getSafe();
                System.out.println("What's the combination?");
                String guess = getUserInput();
                roomSafe.unlock(guess);
            }
        } else if (objective.equals("desk")) {
            if (player.getCurrentRoom().getDesk() == null) {
                System.out.println("There doesn't appear to be a desk in this room.");
            } else {
                Desk roomDesk = player.getCurrentRoom().getDesk();
                if (roomDesk.isLocked()) {
                    if (player.inInventory(roomDesk.getKey())) {
                        roomDesk.unlock(roomDesk.getKey());
                    } else {
                        System.out.println("You need something to unlock this desk.");
                    }
                } else {
                    roomDesk.open();
                }
            }
        } else {
            System.out.println("That's not something you can unlock.");
        }
    }

    /**
     * Given a String, allows a Player to open the object with that name,
     *  provided the object exists, is openable, and, if locked, the Player has the access Item
     * @param objective the object to open
     */
    private void handleOpen(String objective) {
        if (objective.equals("door")) {
            System.out.println("Which door?");
            String userInput = getUserInput();
            Door door = getDoor(userInput);
            if (door != null) {
                Room connectedRoom = getConnectedRoom(door);
                if (!door.isLocked()) {
                    if (connectedRoom != null) {
                        moveToRoom(getConnectedRoom(door));
                    } else {
                        System.out.println("That doesn't lead anywhere.");
                    }
                } else if (door.getKey() instanceof Keypad) {
                    System.out.println("What's the code?");
                    String guess = getUserInput();
                    door.unlockKeypadDoor(guess);
                    if (!door.isLocked()) {
                        moveToRoom(getConnectedRoom(door));
                    }
                } else if (player.inInventory(door.getKey())) {
                    door.unlockDoor(door.getKey());
                    moveToRoom(getConnectedRoom(door));
                } else {
                    System.out.println("You need something to unlock this door.");
                }
            } else {
                System.out.println("That's not a door.");
            }
        } else if (objective.equals("safe")) {
            Safe roomSafe = player.getCurrentRoom().getSafe();
            if (roomSafe.isLocked()) {
                System.out.println("What's the combination?");
                String guess = getUserInput();
                roomSafe.unlock(guess);
            } else {
                roomSafe.open();
            }
        } else if (objective.equals("desk")) {
            Desk roomDesk = player.getCurrentRoom().getDesk();
            if (roomDesk != null) {
                if (roomDesk.isLocked()) {
                    if (player.inInventory(roomDesk.getKey())) {
                        roomDesk.unlock(roomDesk.getKey());
                    } else {
                        System.out.println("You need something to unlock this desk.");
                    }
                } else {
                    roomDesk.open();
                }
            } else {
                System.out.println("There's no desk in here.");
            }
        } else {
            System.out.println("You can't open that.");
        }
    }

    /**
     * Allows a Player to attack a Creature, provided a Creature exists and the Player has a Weapon
     * @param objective the object to attack
     */
    private void handleAttack(String objective) {
        Creature creature = player.getCurrentRoom().getCreature(0);

        if (creature == null) {
            System.out.println("There's nothing to attack.");
            return;
        }

        if (player.hasWeapon()) {
            Weapon weapon = player.getAnyWeapon();
            if (weapon != null) {
                player.attack(creature, weapon.getName());
                if (creature.isAlive()) {
                System.out.println("The " + creature.getType() + " attacks!");
                creature.attack(player);
                }
            }
        } else {
            System.out.println("You're weaponless! The " + creature.getType() + " attacks!");
            creature.attack(player);
        }

        if (!creature.isAlive()) {
            currentRoom.removeCreature(creature);
        }
    }

    /**
     * Allows a Player to look at their surroundings or an Item
     * @param objective the thing to look at
     */
    private void handleLook(String objective) {
        if (objective.equals("around")) {
            player.getCurrentRoom().printDescription();
        } else {
            handleExamine(objective);
        }
    }

    /**
     * Allows a Player to investigate their surroundings or an Item
     * @param objective the thing to look at
     */
    private void handleExamine(String objective) {
        if (objective.equals("room")) {
            player.getCurrentRoom().listItems();
        } else if (objective.equals("safe")) {
            if (player.getCurrentRoom().getSafe() == null) {
                System.out.println("There doesn't appear to be a safe in this room.");
            } else {
                Safe roomSafe = player.getCurrentRoom().getSafe();
                roomSafe.printDescription();
                if (!roomSafe.isLocked()) {
                    roomSafe.listItems();
                }
            }
        } else if (objective.equals("desk")) {
            if (player.getCurrentRoom().getDesk() == null) {
                System.out.println("There doesn't appear to be a desk in this room.");
            } else {
                Desk roomDesk = player.getCurrentRoom().getDesk();
                roomDesk.printDescription();
                if (!roomDesk.isLocked()) {
                    roomDesk.listItems();
                }
            }
        } else if (!objective.isEmpty()) {
            Item item = player.getInventoryItem(objective);
            if (item != null) {
                System.out.println("It looks like " + item.getDescription().toLowerCase());
                return;
            }

            item = player.getCurrentRoom().getItem(objective);
            if (item != null) {
                player.takeItem(item);
                System.out.println("It looks like " + item.getDescription().toLowerCase());
                return;
            }
            System.out.println("You're not making sense.");
        } else {
            System.out.println("You're not making sense.");
        }
    }

    /**
     * Given a String, allows the Item with that name to be read, provided it is a readable object
     * @param objective the Item to read
     */
    public void handleRead(String objective) {
        if (objective.equals("note") || objective.equals("paper")) {
            Paper paper = (Paper) findItem(objective);
            if (paper != null) {
                paper.readPaper();
            } else {
                System.out.println("There's no " + objective + " to read.");
            }
        } else {
            System.out.println("That's not something to read.");
        }
    }
    
    /**
     * Given an action and a target for the action, processes the action accordingly
     * @param action the intended action
     * @param objective the target for the action
     */
    private void processCommand(String action, String objective) {
        
        objective = objective.toLowerCase();

        switch (action.toLowerCase()) {
            case "go":
                handleGo(objective);
                break;
            case "get":
            case "grab":
            case "take":
                handleTake(objective);
                break;
            case "drop":
                handleDrop(objective);
                break;
            case "use":
                handleUse(objective);
                break;
            case "unlock":
                handleUnlock(objective);
                break;
            case "open":
                handleOpen(objective);
                break;
            case "attack":
                handleAttack(objective);
                break;
            case "look":
                handleLook(objective);
                break;
            case "examine":
            case "investigate":
                handleExamine(objective);
                break;
            case "read":
                handleRead(objective);
                break;
            default:
                System.out.println("That doesn't make sense. Type HELP for a list of valid commands.");
        }
    }

    /**
     * Allows a Game to be played
     */
    private void play() {

        // Flag
        boolean stillPlaying = true;

        // User input
        userInput = new Scanner(System.in);

        // Storage for user's responses
        String userResponse = "";

        int responseCount = 0;

        startRoom.printDescription();

        do {

            userResponse = getUserInput();

            if (userResponse.isEmpty()) {
                System.out.println("You didn't enter anything.");
                continue;
            } else if (userResponse.equals("help")) {
                showCommands(currentRoom);
                continue;
            } else if (userResponse.equals("inventory")) {
                player.printInventory();
                continue;
            }
            
            String[] splitResponse = userResponse.split(" ");
            String actionWord = splitResponse[0];
            String objective = splitResponse[splitResponse.length - 1];

            processCommand(actionWord, objective);

            if (currentRoom.equals(outside)) {
                responseCount += 1;
                if (responseCount == 1 && player.inInventory("syringe")) {
                    System.out.println("Something in your inventory might help...");
                }
                if (responseCount >= 3 && player.isInfected()) {
                    player.die();
                }
            }

            if (!player.isAlive()) {
                stillPlaying = false;
            } else if (player.getCurrentRoom().getName().equals("Outside") && !player.isInfected()) {
                stillPlaying = false;
            }
        } while (stillPlaying);

        // Tidy up
        userInput.close();

        if (!player.isAlive()) {
            System.out.println("Oh, that's unfortunate. You're dead.");
        } else {
            System.out.println("You feel your head clearing. Seems like that worked, whatever it was.");
            System.out.println("You feel the sun on your face as you stand outside.\nThe sky looks more blue than usual.");
        }

    }

    /**
     * Main method for the Game class
     * @param args array for command-line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
