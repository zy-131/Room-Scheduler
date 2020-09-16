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

/**
 *
 * @author Zaid Yazadi 
 * Class that handles retrieving and inserting room info
 * into database
 */
public class RoomQueries {

    //Class variables
    private static Connection con;
    private static ArrayList<RoomEntry> possibleRooms = new ArrayList<>();

    /**
     * Gets all available rooms for the particular date
     *
     * @param date - date of request
     * @return - possibleRooms (all available rooms)
     */
    public static ArrayList<RoomEntry> getAllPossibleRooms(Date date) {
        con = DBConnection.getConnection();
        possibleRooms.clear();
        try {
            PreparedStatement rooms = con.prepareStatement("Select * from Rooms");
            ResultSet res = rooms.executeQuery();

            while (res.next()) {
                ArrayList<String> x = new ArrayList<>();
                x.add(res.getString(1));
                x.add(res.getString(2));
                if (ReservationQueries.getRoomsReservedByDate(date).isEmpty()
                        || ReservationQueries.getRoomsReservedByDate(date).indexOf(x.get(0)) < 0) {
                    RoomEntry r = new RoomEntry(x.get(0), Integer.parseInt(x.get(1)));
                    possibleRooms.add(r);
                }
            }
        } catch (SQLException e) {

        }
        return possibleRooms;
    }

    /**
     * Adds room to database
     *
     * @param room - RoomEntry object to use to add in database
     */
    public static void addRoom(RoomEntry room) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement add = con.prepareStatement("INSERT INTO Rooms (NAME, SEATS)"
                    + "VALUES(?, ?)");
            add.setString(1, room.getName());
            add.setInt(2, room.getSeats());
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete room from database
     *
     * @param room - RoomEntry object to use to find and delete from database
     */
    public static void dropRoom(String room) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement drop = con.prepareStatement("DELETE from Rooms WHERE Name = ?");
            drop.setString(1, room);
            drop.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
