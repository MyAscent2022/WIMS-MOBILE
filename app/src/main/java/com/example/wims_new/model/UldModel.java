package com.example.wims_new.model;

import java.io.Serializable;

public class UldModel implements Serializable {
    long id;
    String uldNo;
    int uldType;
    String flightNumber;
    int totalCount;
    int shortLanded;
    int totalExpected;
    UldTypeModel uldTypeModel;

    public UldTypeModel getUldTypeModel() {
        return uldTypeModel;
    }

    public void setUldTypeModel(UldTypeModel uldTypeModel) {
        this.uldTypeModel = uldTypeModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUldNo() {
        return uldNo;
    }

    public void setUldNo(String uldNo) {
        this.uldNo = uldNo;
    }

    public int getUldType() {
        return uldType;
    }

    public void setUldType(int uldType) {
        this.uldType = uldType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getShortLanded() {
        return shortLanded;
    }

    public void setShortLanded(int shortLanded) {
        this.shortLanded = shortLanded;
    }

    public int getTotalExpected() {
        return totalExpected;
    }

    public void setTotalExpected(int totalExpected) {
        this.totalExpected = totalExpected;
    }
}
