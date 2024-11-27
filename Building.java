import java.util.ArrayList;

public class Building {
    
    // Attributes
    private String name;
    private ArrayList<Room> rooms;
    //map?

    // Constructor
    public Building(String name) {
        ArrayList<Room> rooms = new ArrayList<Room>();
    }

    // Methods
    public String getName() {
        return name;
    }

    // public void addRoom(Room room) {

    // }

    //public Room getRoom(String name) {
    //    return rooms.get(name);
    //}
}
