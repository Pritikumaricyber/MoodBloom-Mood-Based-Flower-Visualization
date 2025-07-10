package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/moodbloom_db"; // your DB name
    private static final String USER = "username"; // your MySQL username
    private static final String PASSWORD = "your password"; // your MySQL password

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure MySQL JDBC driver is loaded
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
