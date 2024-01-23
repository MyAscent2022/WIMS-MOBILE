package com.example.wims_new.model;

import java.io.Serializable;

public class UldModel implements Serializable {
    long id;
    String uldNumber;
    int totalCount;
    String flightNumber;
    int uldTypeId;
    int totalExpected;
    String type;
    int totalMawb;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUldNumber() {
        return uldNumber;
    }

    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getUldTypeId() {
        return uldTypeId;
    }

    public void setUldTypeId(int uldTypeId) {
        this.uldTypeId = uldTypeId;
    }

    public int getTotalExpected() {
        return totalExpected;
    }

    public void setTotalExpected(int totalExpected) {
        this.totalExpected = totalExpected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalMawb() {
        return totalMawb;
    }

    public void setTotalMawb(int totalMawb) {
        this.totalMawb = totalMawb;
    }
}
