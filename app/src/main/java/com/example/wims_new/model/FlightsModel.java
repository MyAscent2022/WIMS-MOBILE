package com.example.wims_new.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class FlightsModel implements Serializable {

    long id;
    String userId;
    int flightId;
    String status;
    String cargoStatus;
    String tbs;
    String tff;
    String createdAt;
    int createdById;
    String modifiedAt;
    int modifiedById;
    int uldId;
    String mawbNumber;
    String flightNumber;
    String registryNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getTbs() {
        return tbs;
    }

    public void setTbs(String tbs) {
        this.tbs = tbs;
    }

    public String getTff() {
        return tff;
    }

    public void setTff(String tff) {
        this.tff = tff;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public int getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(int modifiedById) {
        this.modifiedById = modifiedById;
    }

    public int getUldId() {
        return uldId;
    }

    public void setUldId(int uldId) {
        this.uldId = uldId;
    }

    public String getMawbNumber() {
        return mawbNumber;
    }

    public void setMawbNumber(String mawbNumber) {
        this.mawbNumber = mawbNumber;
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
}
