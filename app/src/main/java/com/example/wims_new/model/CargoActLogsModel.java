package com.example.wims_new.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CargoActLogsModel {
    int id;
    int flightId;
    int mawbId;
    int hawbId;
    String location;
    String handledById;
    Timestamp receivedDatetime;
    Timestamp storedDatetime;
    Timestamp releasedDateTime;
    Timestamp paymentDateTime;
    Timestamp updatedAt;
    int releasedTypeId;
    int actualPcs;
    int createdById;
    String remarks;

    public CargoActLogsModel() {
    }

    public CargoActLogsModel(int id, int flightId, int mawbId, int hawbId, String location, String handledById, Timestamp receivedDatetime, Timestamp storedDatetime, Timestamp releasedDateTime, Timestamp paymentDateTime, Timestamp updatedAt, int releasedTypeId, int actualPcs, int createdById, String remarks) {
        this.id = id;
        this.flightId = flightId;
        this.mawbId = mawbId;
        this.hawbId = hawbId;
        this.location = location;
        this.handledById = handledById;
        this.receivedDatetime = receivedDatetime;
        this.storedDatetime = storedDatetime;
        this.releasedDateTime = releasedDateTime;
        this.paymentDateTime = paymentDateTime;
        this.updatedAt = updatedAt;
        this.releasedTypeId = releasedTypeId;
        this.actualPcs = actualPcs;
        this.createdById = createdById;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getMawbId() {
        return mawbId;
    }

    public void setMawbId(int mawbId) {
        this.mawbId = mawbId;
    }

    public int getHawbId() {
        return hawbId;
    }

    public void setHawbId(int hawbId) {
        this.hawbId = hawbId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHandledById() {
        return handledById;
    }

    public void setHandledById(String handledById) {
        this.handledById = handledById;
    }

    public Timestamp getReceivedDatetime() {
        return receivedDatetime;
    }

    public void setReceivedDatetime(Timestamp receivedDatetime) {
        this.receivedDatetime = receivedDatetime;
    }

    public Timestamp getStoredDatetime() {
        return storedDatetime;
    }

    public void setStoredDatetime(Timestamp storedDatetime) {
        this.storedDatetime = storedDatetime;
    }

    public Timestamp getReleasedDateTime() {
        return releasedDateTime;
    }

    public void setReleasedDateTime(Timestamp releasedDateTime) {
        this.releasedDateTime = releasedDateTime;
    }

    public Timestamp getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(Timestamp paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getReleasedTypeId() {
        return releasedTypeId;
    }

    public void setReleasedTypeId(int releasedTypeId) {
        this.releasedTypeId = releasedTypeId;
    }

    public int getActualPcs() {
        return actualPcs;
    }

    public void setActualPcs(int actualPcs) {
        this.actualPcs = actualPcs;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
