/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author Zaid Yazadi 
 * Class for interactions with Dates table in database
 */
public class Dates {

    // Class variables
    private static Connection con;
    private static ArrayList<Date> datesList = new ArrayList<Date>();

    /**
     * Adds date to database
     *
     * @param date - Date object to add
     */
    public static void addDate(Date date) {
        con = DBConnection.getConnection();
        try {
            PreparedStatement add = con.prepareStatement("INSERT INTO Dates (Date)"
                    + "VALUES(?)");
            add.setDate(1, date);
            add.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all the dates from the database
     *
     * @return - datesList (ArrayList of Dates)
     */
    public static ArrayList<Date> getAllDates() {
        con = DBConnection.getConnection();
        datesList.clear();
        try {
            PreparedStatement retrieval = con.prepareStatement("SELECT * from Dates");
            ResultSet res = retrieval.executeQuery();

            while (res.next()) {
                datesList.add(res.getDate(1));
            }
        } catch (SQLException e) {
            ;
        }
        return datesList;
    }
}
