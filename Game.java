import java.util.ArrayList;
import java.util.Scanner;
import com.google.common.graph.*;

public class Game {

    private Player player;
    private Room room;


    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        // Rooms
        Room firstRoom = new Room("Room One", "You stand in a grimy room, surrounded by shadows.\nThere appears to be a door to your left.");
        Room secondRoom = new Room("Room Two", "You've entered a large room with almost no light. You hear something, but you can't tell where it's coming from.");
        Room thirdRoom = new Room("Room Three", "... The numbers 9-8-5 are scribbled on a wall to the right.");
        Room fourthRoom = new Room("Room Four", "...");
        Room outside = new Room("Outside", "...");

        ImmutableGraph<Room> building = GraphBuilder.undirected()
        .<Room>immutable()
        .putEdge(firstRoom, secondRoom)
        .putEdge(secondRoom, thirdRoom)
        .putEdge(thirdRoom, fourthRoom)
        .putEdge(fourthRoom, outside)
        .build();

        // Key
        Key key = new Key("Shiny Key");
        firstRoom.addItem(key);

        // Knife
        Knife knife = new Knife("Heavy Knife");
        firstRoom.addItem(knife);

        // Door
        Door door = new Door(key);

        // Room
        this.room = firstRoom;
        this.player = new Player(room);
    }

    private void instructions() {
        player.getCurrentRoom().printDescription();
    }

    private void play() {

        // This is a "flag" to let us know when the loop should end
        boolean stillPlaying = true;

        // Track room number
        int room = 1;

        // We'll use this to get input from the user.
        Scanner userInput = new Scanner(System.in);

        // Storage for user's responses
        String userResponse = "";

        // For checking user responses
        ArrayList<String> actionWords = new ArrayList<String>();
        ArrayList<String> itemWords = new ArrayList<String>();
        ArrayList<String> objectWords = new ArrayList<String>();
        actionWords.add("go");
        actionWords.add("walk");
        actionWords.add("look");
        actionWords.add("open");
        actionWords.add("use");
        itemWords.add("knife");
        itemWords.add("key");
        objectWords.add("door");
        objectWords.add("creature");

        // Instructions are sometimes helpful

        // The do...while structure means we execute the body of the loop once before checking the stopping condition
        do {
            // Print room information
            instructions();

            player.getCurrentRoom().listItems();
            
            //System.out.println("You are still playing. Follow the instructions if you want to win/lose...");
            userResponse = userInput.nextLine().toLowerCase();
            
            String[] splitResponse = userResponse.split(" ");
            
            // for(int j = 0; j < splitResponse.length; j++) {
            //      String word = splitResponse[j];
            //      switch (word) {
            //         case "go":
            //             System.out.println("hello");
            //             break;
            //         case "help":
            //             instructions();
            //             System.out.println("Type a command to continue...");
            //             break;
            //          default: System.out.println("invalid");
            //             break;
            //      }
            // }
            //System.out.println(("hi").contains("j"));

            if (player.isDead() || player.getCurrentRoom().getName().equals("Outside")) {
                System.out.println("still playing is " + stillPlaying); 
                stillPlaying = false;
            }
        } while (stillPlaying);

        // Tidy up
        userInput.close();

        // Once you exit the loop, you may need to deal with various possible stopping conditions
        if (player.isDead()) {
            System.out.println("Oh, that's unfortunate. You're dead.");
        } else {
            System.out.println("You feel the sun on your face as you step outside.\nThe sky looks more blue than usual.");
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}