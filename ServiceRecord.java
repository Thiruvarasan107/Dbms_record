model/ServiceRecord.java

package model;

import java.sql.Date;

public class ServiceRecord {
    private int serviceId;
    private int vehicleId;
    private Date serviceDate;
    private String problemDescription;
    private String workDone;
    private String status;
    private double cost;

    public ServiceRecord() {}

    public ServiceRecord(int serviceId, int vehicleId, Date serviceDate, String problemDescription, String workDone, String status, double cost) {
        this.serviceId = serviceId;
        this.vehicleId = vehicleId;
        this.serviceDate = serviceDate;
        this.problemDescription = problemDescription;
        this.workDone = workDone;
        this.status = status;
        this.cost = cost;
    }

    // getters & setters
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public Date getServiceDate() { return serviceDate; }
    public void setServiceDate(Date serviceDate) { this.serviceDate = serviceDate; }

    public String getProblemDescription() { return problemDescription; }
    public void setProblemDescription(String problemDescription) { this.problemDescription = problemDescription; }

    public String getWorkDone() { return workDone; }
    public void setWorkDone(String workDone) { this.workDone = workDone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}
