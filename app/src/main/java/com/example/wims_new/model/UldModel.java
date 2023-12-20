package com.example.wims_new.model;

import java.io.Serializable;

public class UldModel implements Serializable {
    long id;
    String uldNo;
    String uldType;
    int uldTypeId;
    int uldStatus;
    String flightNumber;
    int totalCount;
    int shortLanded;
    int totalExpected;
    int totalWeight;
    UldTypeModel uldTypeModel;


    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
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

    public String getUldType() {
        return uldType;
    }

    public void setUldType(String uldType) {
        this.uldType = uldType;
    }

    public int getUldTypeId() {
        return uldTypeId;
    }

    public void setUldTypeId(int uldTypeId) {
        this.uldTypeId = uldTypeId;
    }

    public int getUldStatus() {
        return uldStatus;
    }

    public void setUldStatus(int uldStatus) {
        this.uldStatus = uldStatus;
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

    public UldTypeModel getUldTypeModel() {
        return uldTypeModel;
    }

    public void setUldTypeModel(UldTypeModel uldTypeModel) {
        this.uldTypeModel = uldTypeModel;
    }
}
