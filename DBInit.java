db/DBInit.java


package db;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Optional: run this once if you prefer creating tables from Java instead of MySQL Workbench.
 */
public class DBInit {
    public static void createTablesIfNotExists() {
        String sql1 = "CREATE DATABASE IF NOT EXISTS vehicle_service_db";
        String sql2 = "USE vehicle_service_db";
        String sqlCustomer = "CREATE TABLE IF NOT EXISTS customer ("
                + "customer_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(100) NOT NULL,"
                + "phone VARCHAR(20),"
                + "address VARCHAR(255)"
                + ")";
        String sqlVehicle = "CREATE TABLE IF NOT EXISTS vehicle ("
                + "vehicle_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "customer_id INT NOT NULL,"
                + "vehicle_number VARCHAR(20) NOT NULL,"
                + "vehicle_type VARCHAR(50),"
                + "model VARCHAR(100),"
                + "manufacturer VARCHAR(100),"
                + "FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE"
                + ")";
        String sqlService = "CREATE TABLE IF NOT EXISTS service_record ("
                + "service_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "vehicle_id INT NOT NULL,"
                + "service_date DATE,"
                + "problem_description TEXT,"
                + "work_done TEXT,"
                + "status VARCHAR(30),"
                + "cost DECIMAL(10,2),"
                + "FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id) ON DELETE CASCADE"
                + ")";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.execute(sqlCustomer); // if DB already exists and DB URL points to DB, this will run
            st.execute(sqlVehicle);
            st.execute(sqlService);
            System.out.println("Tables exist or were created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating tables (make sure DB 'vehicle_service_db' exists).");
            e.printStackTrace();
        }
    }
}
