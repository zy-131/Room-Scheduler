/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Zaid Yazadi 
 * Class for interacting w/ Faculty table in database
 */
public class Faculty {

    // Class variables
    private static Connection connection;
    private static ArrayList<String> facultyList = new ArrayList<String>();

    /**
     * Adds Faculty name to database
     *
     * @param name - name to add for the Faculty member
     */
    public static void addFaculty(String name) {
        connection = DBConnection.getConnection();
        try {
            PreparedStatement add = connection.prepareStatement("INSERT INTO Faculty"
                    + "(Name)" + "VALUES(?)");
            add.setString(1, name);
            add.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Not a unique name.");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all the Faculty names from database
     *
     * @return - facultyList (ArrayList of Faculty names)
     */
    public static ArrayList<String> getAllFaculty() {
        facultyList.clear();
        connection = DBConnection.getConnection();
        try {
            PreparedStatement retrieval = connection.prepareStatement("SELECT * FROM FACULTY");
            ResultSet res = retrieval.executeQuery();

            while (res.next()) {
                facultyList.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return facultyList;
    }
}
