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
 * Class for wait list entries that contain all the details
 * to be inserted into the database.
 */
public class WaitlistEntry {

    // Class variables
    private  String name;
    private  Date date;
    private  int seats;
    private  Timestamp timestamp;

    /**
     * Constructor that initializes all class variables
     *
     * @param n - faculty name
     * @param d - date requested
     * @param s - seats requested
     * @param t - timestamp of request
     */
    public WaitlistEntry(String n, Date d, int s, Timestamp t) {
        name = n;
        date = d;
        seats = s;
        timestamp = t;
    }

    /**
     * Getter methods for private class variables
     *
     * @return - Proper class variables
     */
    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getSeats() {
        return seats;
    }

    public Timestamp getTime() {
        return timestamp;
    }
}
