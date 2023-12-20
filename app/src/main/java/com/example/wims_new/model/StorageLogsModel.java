package com.example.wims_new.model;

public class StorageLogsModel {
    String userId;
    String flightNumber;
    String registryNumber;
    String storagerStatus;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public String getStoragerStatus() {
        return storagerStatus;
    }

    public void setStoragerStatus(String storagerStatus) {
        this.storagerStatus = storagerStatus;
    }
}
