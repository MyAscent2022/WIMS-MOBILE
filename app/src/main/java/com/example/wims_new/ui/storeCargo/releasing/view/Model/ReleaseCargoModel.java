package com.example.wims_new.ui.storeCargo.releasing.view.Model;

import java.io.Serializable;

public class ReleaseCargoModel implements Serializable {

    int id;
    String mawbNumber;
    String hawbNumber;
    int actualPcs;
    String location;
    String releaseStatus;
    String paidDt;
    String rackName;
    String status;
    String layerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getActualPcs() {
        return actualPcs;
    }

    public void setActualPcs(int actualPcs) {
        this.actualPcs = actualPcs;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getPaidDt() {
        return paidDt;
    }

    public void setPaidDt(String paidDt) {
        this.paidDt = paidDt;
    }

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
