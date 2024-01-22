package com.example.wims_new.model;

import java.io.Serializable;

public class UldImages implements Serializable {
    int id;
    String filePath;
    String fileName;
    String remarks;
    int uldConditionId;
    String flight_number;
    String uldNumber;
    String uldCondition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getUldConditionId() {
        return uldConditionId;
    }

    public void setUldConditionId(int uldConditionId) {
        this.uldConditionId = uldConditionId;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getUldNumber() {
        return uldNumber;
    }

    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

    public String getUldCondition() {
        return uldCondition;
    }

    public void setUldCondition(String uldCondition) {
        this.uldCondition = uldCondition;
    }
}
