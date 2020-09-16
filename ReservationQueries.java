/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author Zaid Yazadi 
 * Class that handles inserting and retrieving reservation
 * data from database
 */
public class ReservationQueries {

    //Class variables
    private static Connection con;
    private static ArrayList<ArrayList<String>> reservations = new ArrayList<>();
    private static ArrayList<String> rooms = new ArrayList<>();

    /**
     * Helper method that converts ResultSet into ArrayList
     *
     * @param res - ResultSet from SQL statement
     */
    public static void getArrayListResults(ResultSet res) {
        try {
            while (res.next()) {
                ArrayList<String> x = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    x.add(res.getString(i));
                }
                reservations.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all reservations for a given date
     *
     * @param date - specified Date
     * @return - reservations (nested ArrayList of reservation info)
     */
    public static ArrayList<ArrayList<String>> getReservationsByDate(Date date) {
        con = DBConnection.getConnection();
        reservations.clear();
        try {
            PreparedStatement getByDates = con.prepareStatement("SELECT * from Reservations where DATE = ?");
            getByDates.setDate(1, date);
            ResultSet res = getByDates.executeQuery();

            ReservationQueries.getArrayListResults(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Retrieves list of reservations for a given faculty member
     *
     * @param name - Faculty name
     * @return - reservations (nested ArrayList of reservation info)
     */
    public static ArrayList<ArrayList<String>> getReservationsByFaculty(String name) {
        con = DBConnection.getConnection();
        reservations.clear();
        try {
            PreparedStatement getByDates = con.prepareStatement("SELECT * from Reservations where FACULTY = ? Order by DATE");
            getByDates.setString(1, name);
            ResultSet res = getByDates.executeQuery();

            ReservationQueries.getArrayListResults(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Retrieves room data from database for a specific date
     *
     * @param date - specified Date
     * @return - rooms (ArrayList w/ RoomEntry data stored as Strings)
     */
    public static ArrayList<String> getRoomsReservedByDate(Date date) {
        con = DBConnection.getConnection();
        rooms.clear();
        try {
            PreparedStatement getRooms = con.prepareStatement("Select * from Reservations where DATE = ?");
            getRooms.setDate(1, date);
            ResultSet res = getRooms.executeQuery();

            while (res.next()) {
                rooms.add(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    
    /**
     * Retrieves all the reservations by room
     * @param room - Room to check for when selecting
     * @return - ArrayList<String> with all reservations for specified room
     */
    public static ArrayList<String> getReservationsByRoom(String room){
        con = DBConnection.getConnection();
        ArrayList<String> reservations = new ArrayList<>();
        try{
            PreparedStatement retrieve = con.prepareStatement("SELECT * from Reservations where ROOM = ?");
            retrieve.setString(1, room);
            ResultSet res = retrieve.executeQuery();
            
            while(res.next()){
                reservations.add(res.getString(1));
                reservations.add(res.getString(3));
                reservations.add(res.getString(4));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Adds reservation to database
     *
     * @param reservation - ReservationEntry object to be inserted into database
     */
    public static void addReservation(ReservationEntry reservation) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement add = con.prepareStatement("INSERT INTO Reservations (FACULTY, ROOM, DATE, SEATS, TIMESTAMP) "
                    + "VALUES(?, ?, ?, ?, ?)");
            //PreparedStatement delete = con.prepareStatement("DELETE from Waitlist where Faculty = ? AND Date = ?");
            add.setString(1, reservation.getName());
            add.setString(2, reservation.getRoomName());
            add.setDate(3, reservation.getDate());
            add.setInt(4, reservation.getRoomSeats());
            add.setTimestamp(5, reservation.getTime());
            add.executeUpdate();
            WaitlistQueries.deleteWaitlistEntry(reservation.getName(), reservation.getDate());
            
//            delete.setString(1, reservation.getName());
//            delete.setDate(2, reservation.getDate());
//            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes reservation from database
     *
     * @param name - Faculty name who's reservation to delete
     * @param date - date of to-be-deleted reservation
     * @param room - room to check for when deleting reservations
     */
    public static void deleteReservation(String name, Date date, String room) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement delete = con.prepareStatement("DELETE from Reservations where (Faculty = ? AND Date = ?) OR Room = ?");
            delete.setString(1, name);
            delete.setDate(2, date);
            delete.setString(3, room);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
