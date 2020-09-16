/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Zaid Yazadi 
 * Class for Reservation entries that contain all the
 * details to be inserted into the database.
 */
public class ReservationEntry {

    // Class variables
    private String name;
    private String room;
    private int seats;
    private Date date;
    private Timestamp timestamp;

    /**
     * Constructor for ReservationEntry that initializes all variables
     *
     * @param n - faculty name
     * @param r - room name
     * @param s - # of seats requested
     * @param d - Date of reservation
     * @param t - Timestamp of reservation
     */
    public ReservationEntry(String n, String r, int s, Date d, Timestamp t) {
        name = n;
        room = r;
        seats = s;
        date = d;
        timestamp = t;
    }

    /**
     * Getter methods for private class variables
     *
     * @return - proper class variable
     */
    public String getName() {
        return name;
    }

    public String getRoomName() {
        return room;
    }

    public int getRoomSeats() {
        return seats;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp getTime() {
        return timestamp;
    }
}
