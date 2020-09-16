/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zaid Yazadi
 * Class for Room entries that contain all the details to be inserted into
 * the database.
 */
public class RoomEntry {

    //Class variables
    private final String NAME;
    private final int SEATS;

    /**
     * Constructor to initialize the variables
     *
     * @param name - room name
     * @param seats - # of seats in room
     */
    public RoomEntry(String name, int seats) {
        this.NAME = name;
        this.SEATS = seats;
    }

    /**
     * Getter methods for private class variables
     *
     * @return - proper class variable
     */
    public String getName() {
        return this.NAME;
    }

    public int getSeats() {
        return this.SEATS;
    }

}
