/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Zaid Yazadi 
 * Class that handles inserting and retrieving data from
 * database
 */
public class WaitlistQueries {

    // Class variables
    private static Connection con;
    private static ArrayList<ArrayList<String>> waitlist = new ArrayList<>();

    /**
     * Helper method to convert ResultSet into ArrayList
     *
     * @param res - ResultSet from SQL statement
     */
    public static void getArrayListResult(ResultSet res) {
        try {
            while (res.next()) {
                ArrayList<String> x = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    x.add(res.getString(i));
                }
                waitlist.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves list of waitlist entries by date from database
     *
     * @param date - Date to use when selecting
     * @return - waitlist (nested ArrayList w/ waitlist data)
     */
    public static ArrayList<ArrayList<String>> getWaitlistByDate(Date date) {
        con = DBConnection.getConnection();
        waitlist.clear();
        try {
            PreparedStatement getByDate = con.prepareStatement("SELECT * from WAITLIST where DATE = ?");
            getByDate.setDate(1, date);
            ResultSet res = getByDate.executeQuery();

            WaitlistQueries.getArrayListResult(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitlist;
    }

    /**
     * Retrieves list of waitlist entries by Faculty name from database
     *
     * @param name - Faculty name to use when selecting
     * @return - waitlist (nested ArrayList w/ waitlist data)
     */
    public static ArrayList<ArrayList<String>> getWaitlistByFaculty(String name) {
        con = DBConnection.getConnection();
        waitlist.clear();
        try {
            PreparedStatement getByName = con.prepareStatement("SELECT * from WAITLIST where Faculty = ?");
            getByName.setString(1, name);
            ResultSet res = getByName.executeQuery();

            WaitlistQueries.getArrayListResult(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitlist;
    }

    /**
     * Retrieves every waitlist entry in database
     *
     * @return - waitlist (nested ArrayList w/ all waitlist data)
     */
    public static ArrayList<ArrayList<String>> getFullWaitlist() {
        con = DBConnection.getConnection();
        waitlist.clear();
        try {
            PreparedStatement getAll = con.prepareStatement("Select * from Waitlist order by DATE, TIMESTAMP");
            ResultSet res = getAll.executeQuery();

            WaitlistQueries.getArrayListResult(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitlist;
    }

    /**
     * Adds WaitlistEntry to database
     *
     * @param entry - WaitlistEntry object to use when adding data
     */
    public static void addWaitlistEntry(WaitlistEntry entry) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement add = con.prepareStatement("INSERT INTO Waitlist (FACULTY, DATE, SEATS, TIMESTAMP) "
                    + "VALUES(?, ?, ?, ?)");
            add.setString(1, entry.getName());
            add.setDate(2, entry.getDate());
            add.setInt(3, entry.getSeats());
            add.setTimestamp(4, entry.getTime());
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes WaitlistEntry data from database
     *
     * @param name - Faculty name to use when deleting correct database entry
     * @param date - Date to use when deleting
     */
    public static void deleteWaitlistEntry(String name, Date date) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement delete = con.prepareStatement("DELETE from Waitlist where Faculty = ? and DATE = ?");
            delete.setString(1, name);
            delete.setDate(2, date);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Checks the waitlist & retrieves all the posititions that can receive a reservation
     * due to a change (ex: room dropped, reservation cancelled)
     * @param byRoom - if room is dropped follow a certain algorithm
     * @param seats - number of seats of room to check for in waitlist entries
     * @param date - date to check for in waitlist entries
     * @return - ArrayList<String> that contains all the open possible positions
     */
    public static ArrayList<String> checkWaitlist(boolean byRoom, int seats, Date date){
        con = DBConnection.getConnection();
        ArrayList<String> open = new ArrayList<>();

        if(byRoom){
            try{
                PreparedStatement retrieve = con.prepareStatement("SELECT * from Waitlist where SEATS <= ?");
                retrieve.setInt(1, seats);
                ResultSet res = retrieve.executeQuery();
                
                while(res.next()){
                    if(open.indexOf(res.getString(2)) >= 0){
                        continue;
                    }
                    open.add(res.getString(1));
                    open.add(res.getString(2));
                    open.add(res.getString(3));
                }
                return open;
            }catch(SQLException e){
                e.printStackTrace();
            }
            return open;
        } else {
            try{
                PreparedStatement retrieve = con.prepareStatement("SELECT * from Waitlist where Date = ? and SEATS <= ?"
                        + "ORDER by Timestamp");
                retrieve.setDate(1, date);
                retrieve.setInt(2, seats);
                ResultSet res = retrieve.executeQuery();
                
                if(res.next()){
                    open.add(res.getString(1));
                    open.add(res.getString(3));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return open;
        }
    }
}
