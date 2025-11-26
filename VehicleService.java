service/VehicleService.java

package service;

import db.DBConnection;

import java.sql.*;

public class VehicleService {

    public static int addVehicle(int customerId, String vehicleNumber, String vehicleType, String model, String manufacturer) {
        String sql = "INSERT INTO vehicle (customer_id, vehicle_number, vehicle_type, model, manufacturer) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setString(2, vehicleNumber);
            ps.setString(3, vehicleType);
            ps.setString(4, model);
            ps.setString(5, manufacturer);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    System.out.println("✅ Vehicle added successfully! [Vehicle ID: " + id + "]");
                    return id;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
        return -1;
    }

    public static void showVehicles() {
        String sql = "SELECT v.vehicle_id, v.vehicle_number, v.vehicle_type, v.model, v.manufacturer, c.name " +
                     "FROM vehicle v JOIN customer c ON v.customer_id = c.customer_id";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- Vehicles ---");
            System.out.printf("%-5s | %-12s | %-8s | %-15s | %-12s | %s%n", "ID", "Number", "Type", "Model", "Make", "Owner");
            System.out.println("--------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-12s | %-8s | %-15s | %-12s | %s%n",
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getString("model"),
                        rs.getString("manufacturer"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage());
        }
    }
}
