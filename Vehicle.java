model/Vehicle.java


package model;

public class Vehicle {
    private int vehicleId;
    private int customerId;
    private String vehicleNumber;
    private String vehicleType;
    private String model;
    private String manufacturer;

    public Vehicle() {}

    public Vehicle(int vehicleId, int customerId, String vehicleNumber, String vehicleType, String model, String manufacturer) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.model = model;
        this.manufacturer = manufacturer;
    }

    // getters & setters
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
}
