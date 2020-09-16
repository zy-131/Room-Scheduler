/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author Zaid Yazadi 
 * Class that creates connection for database interaction
 */
public class DBConnection {

    // Class variables
    private static Connection connection;
    private static final String URL = "jdbc:derby://localhost:1527/RoomSchedulerDBZaidzfy1";
    private static final String USER = "java";
    private static final String PASSWORD = "java";

    /**
     * Gets the connection for the database and returns a Connection object
     *
     * @return - connection (Connection to database)
     */
    public static Connection getConnection() {
        if (connection == null) {
            System.out.println("Connecting to Database...");
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Connection Failed.");
                System.exit(1);
            }
        }
        return connection;
    }
}
