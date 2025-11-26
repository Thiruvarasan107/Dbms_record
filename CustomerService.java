service/CustomerService.java



package service;

import db.DBConnection;

import java.sql.*;

public class CustomerService {

    public static int addCustomer(String name, String phone, String address) {
        String sql = "INSERT INTO customer (name, phone, address) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    System.out.println("✅ Customer added successfully! [Customer ID: " + id + "]");
                    return id;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
        }
        return -1;
    }

    public static void showCustomers() {
        String sql = "SELECT * FROM customer";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- Customers ---");
            System.out.printf("%-5s | %-20s | %-12s | %s%n", "ID", "Name", "Phone", "Address");
            System.out.println("-----------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-12s | %s%n",
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching customers: " + e.getMessage());
        }
    }
}
