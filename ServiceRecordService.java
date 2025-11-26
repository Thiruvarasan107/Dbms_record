service/ServiceRecordService.java

package service;

import db.DBConnection;

import java.sql.*;

public class ServiceRecordService {

    public static int addServiceRecord(int vehicleId, Date serviceDate, String problemDescription, String workDone, String status, double cost) {
        String sql = "INSERT INTO service_record (vehicle_id, service_date, problem_description, work_done, status, cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, vehicleId);
            ps.setDate(2, serviceDate);
            ps.setString(3, problemDescription);
            ps.setString(4, workDone);
            ps.setString(5, status);
            ps.setDouble(6, cost);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    System.out.println("✅ Service record added successfully! [Service ID: " + id + "]");
                    return id;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding service record: " + e.getMessage());
        }
        return -1;
    }

    public static void updateStatus(int serviceId, String newStatus) {
        String sql = "UPDATE service_record SET status = ? WHERE service_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, serviceId);
            int updated = ps.executeUpdate();
            if (updated > 0) System.out.println("✅ Status updated successfully!");
            else System.out.println("Service ID not found.");
        } catch (SQLException e) {
            System.err.println("Error updating status: " + e.getMessage());
        }
    }

    public static void showAllServiceRecords() {
        String sql = "SELECT s.*, v.vehicle_number, v.model, v.manufacturer, c.name " +
                     "FROM service_record s " +
                     "JOIN vehicle v ON s.vehicle_id = v.vehicle_id " +
                     "JOIN customer c ON v.customer_id = c.customer_id " +
                     "ORDER BY s.service_date DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- ALL SERVICE RECORDS ---");
            System.out.printf("%-4s | %-10s | %-10s | %-20s | %-20s | %-10s | %s%n", 
                    "ID", "Veh No", "Date", "Problem", "Work Done", "Status", "Cost");
            System.out.println("--------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-4d | %-10s | %-10s | %-20s | %-20s | %-10s | %.2f%n",
                        rs.getInt("service_id"),
                        rs.getString("vehicle_number"),
                        rs.getDate("service_date").toString(),
                        trim(rs.getString("problem_description"),20),
                        trim(rs.getString("work_done"),20),
                        rs.getString("status"),
                        rs.getDouble("cost"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching service records: " + e.getMessage());
        }
    }

    private static String trim(String s, int len) {
        if (s == null) return "";
        return s.length() <= len ? s : s.substring(0, len-3) + "...";
    }

    public static void showSummaryForCustomer(int customerId) {
        String sqlCustomer = "SELECT name, phone, address FROM customer WHERE customer_id = ?";
        String sqlSummary = "SELECT v.vehicle_number, v.vehicle_type, v.model, v.manufacturer, " +
                "s.service_date, s.problem_description, s.work_done, s.status, s.cost " +
                "FROM vehicle v JOIN service_record s ON v.vehicle_id = s.vehicle_id " +
                "WHERE v.customer_id = ? ORDER BY s.service_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pcs = con.prepareStatement(sqlCustomer)) {
            pcs.setInt(1, customerId);
            try (ResultSet rc = pcs.executeQuery()) {
                if (!rc.next()) {
                    System.out.println("Customer not found.");
                    return;
                }
                String name = rc.getString("name");
                String phone = rc.getString("phone");
                String address = rc.getString("address");

                System.out.println("\n================ SUMMARY FOR " + name.toUpperCase() + " ================");
                System.out.println("Contact: " + phone + " | Address: " + address);
                System.out.println("------------------------------------------------------------------");
                System.out.printf("%-12s | %-10s | %-10s | %-20s | %-20s | %-10s | %s%n",
                        "Vehicle No", "Type", "Date", "Problem", "Work Done", "Status", "Cost");
                System.out.println("---------------------------------------------------------------------------------------------");

                try (PreparedStatement pss = con.prepareStatement(sqlSummary)) {
                    pss.setInt(1, customerId);
                    try (ResultSet rs = pss.executeQuery()) {
                        double total = 0.0;
                        int count = 0;
                        while (rs.next()) {
                            System.out.printf("%-12s | %-10s | %-10s | %-20s | %-20s | %-10s | %.2f%n",
                                    rs.getString("vehicle_number"),
                                    rs.getString("vehicle_type"),
                                    rs.getDate("service_date") == null ? "" : rs.getDate("service_date").toString(),
                                    trim(rs.getString("problem_description"),20),
                                    trim(rs.getString("work_done"),20),
                                    rs.getString("status"),
                                    rs.getDouble("cost"));
                            total += rs.getDouble("cost");
                            count++;
                        }
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("Total Services: " + count);
                        System.out.printf("Total Cost: ₹%.2f%n", total);
                        System.out.println("==================================================================");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating customer summary: " + e.getMessage());
        }
    }
}
