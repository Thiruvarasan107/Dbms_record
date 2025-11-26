db/DBConnection.java



package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // update with your DB credentials if different
    private static final String URL = "jdbc:mysql://localhost:3306/vehicle_service_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";         // <- change if needed
    private static final String PASSWORD = "password"; // <- change to your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found. Add connector jar to classpath.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
