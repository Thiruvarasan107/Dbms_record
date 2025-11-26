main/VehicleServiceManagement.java


package main;

import db.DBInit; // optional
import model.ServiceRecord;
import service.CustomerService;
import service.VehicleService;
import service.ServiceRecordService;

import java.sql.Date;
import java.util.Scanner;

public class VehicleServiceManagement {

    public static void main(String[] args) {
        // Optional: create tables from Java (if you want)
        // DBInit.createTablesIfNotExists();

        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("=============================================");
        System.out.println("     ADVANCED VEHICLE SERVICE MANAGEMENT");
        System.out.println("=============================================");

        do {
            showMenu();
            System.out.print("Enter your choice: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addCustomerFlow(sc);
                    break;
                case 2:
                    addVehicleFlow(sc);
                    break;
                case 3:
                    addServiceRecordFlow(sc);
                    break;
                case 4:
                    updateServiceStatusFlow(sc);
                    break;
                case 5:
                    ServiceRecordService.showAllServiceRecords();
                    break;
                case 6:
                    System.out.print("Enter Customer ID: ");
                    int cid = Integer.parseInt(sc.nextLine());
                    ServiceRecordService.showSummaryForCustomer(cid);
                    break;
                case 7:
                    System.out.println("Exiting... Thank you for using Vehicle Service Management System!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 7);

        sc.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add Customer");
        System.out.println("2. Add Vehicle");
        System.out.println("3. Add Service Record");
        System.out.println("4. Update Service Status");
        System.out.println("5. View All Service Records");
        System.out.println("6. View Summary for a Customer");
        System.out.println("7. Exit");
        System.out.println("---------------------------------------------");
    }

    private static void addCustomerFlow(Scanner sc) {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();
        CustomerService.addCustomer(name, phone, address);
    }

    private static void addVehicleFlow(Scanner sc) {
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Vehicle Number: ");
            String vehicleNumber = sc.nextLine();
            System.out.print("Enter Vehicle Type (Car/Bike/Truck): ");
            String vehicleType = sc.nextLine();
            System.out.print("Enter Model: ");
            String model = sc.nextLine();
            System.out.print("Enter Manufacturer: ");
            String manufacturer = sc.nextLine();
            VehicleService.addVehicle(customerId, vehicleNumber, vehicleType, model, manufacturer);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Operation cancelled.");
        }
    }

    private static void addServiceRecordFlow(Scanner sc) {
        try {
            System.out.print("Enter Vehicle ID: ");
            int vehicleId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Service Date (YYYY-MM-DD) or press Enter for today: ");
            String dateStr = sc.nextLine().trim();
            Date serviceDate = (dateStr.isEmpty()) ? new Date(System.currentTimeMillis()) : Date.valueOf(dateStr);
            System.out.print("Enter Problem Description: ");
            String problem = sc.nextLine();
            System.out.print("Enter Work Done: ");
            String workDone = sc.nextLine();
            System.out.print("Enter Status (Fixed/In Progress/Pending): ");
            String status = sc.nextLine();
            System.out.print("Enter Cost: ");
            double cost = Double.parseDouble(sc.nextLine());
            ServiceRecordService.addServiceRecord(vehicleId, serviceDate, problem, workDone, status, cost);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format or number. Use YYYY-MM-DD for date and numeric for cost.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input. Operation cancelled.");
        }
    }

    private static void updateServiceStatusFlow(Scanner sc) {
        try {
            System.out.print("Enter Service ID to update: ");
            int sid = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new status (Fixed/In Progress/Pending): ");
            String newStatus = sc.nextLine();
            ServiceRecordService.updateStatus(sid, newStatus);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Service ID.");
        }
    }
}
