package com.example.wims_new.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class RackModel implements Serializable {
    long id;
    int refRackId;
    String mawbNumber;
    String hawbNumber;
    float volumeMawb;
    int storedById;
    Timestamp storedDt;
    int releasedById;
    Timestamp releasedDt;
    int refLayerId;
    String locationNumber;
    int noOfPieces;
    int releasingStatusId;
    Boolean isPaid;
    int txnMawbId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRefRackId() {
        return refRackId;
    }

    public void setRefRackId(int refRackId) {
        this.refRackId = refRackId;
    }

    public String getMawbNumber() {
        return mawbNumber;
    }

    public void setMawbNumber(String mawbNumber) {
        this.mawbNumber = mawbNumber;
    }

    public String getHawbNumber() {
        return hawbNumber;
    }

    public void setHawbNumber(String hawbNumber) {
        this.hawbNumber = hawbNumber;
    }

    public float getVolumeMawb() {
        return volumeMawb;
    }

    public void setVolumeMawb(float volumeMawb) {
        this.volumeMawb = volumeMawb;
    }

    public int getStoredById() {
        return storedById;
    }

    public void setStoredById(int storedById) {
        this.storedById = storedById;
    }

    public Timestamp getStoredDt() {
        return storedDt;
    }

    public void setStoredDt(Timestamp storedDt) {
        this.storedDt = storedDt;
    }

    public int getReleasedById() {
        return releasedById;
    }

    public void setReleasedById(int releasedById) {
        this.releasedById = releasedById;
    }

    public Timestamp getReleasedDt() {
        return releasedDt;
    }

    public void setReleasedDt(Timestamp releasedDt) {
        this.releasedDt = releasedDt;
    }

    public int getRefLayerId() {
        return refLayerId;
    }

    public void setRefLayerId(int refLayerId) {
        this.refLayerId = refLayerId;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public int getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(int noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public int getReleasingStatusId() {
        return releasingStatusId;
    }

    public void setReleasingStatusId(int releasingStatusId) {
        this.releasingStatusId = releasingStatusId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public int getTxnMawbId() {
        return txnMawbId;
    }

    public void setTxnMawbId(int txnMawbId) {
        this.txnMawbId = txnMawbId;
    }
}
