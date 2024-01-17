package com.example.wims_new.ui.storeCargo.storage.view.Model;

import java.io.Serializable;

public class StorageModel implements Serializable {

    int id;
    int actualPcs;
    String cargoStatus;
    int rackUtilId;
    String rackName;
    String layerName;
    String classdesc;
    String hawbNumber;
    String mawbNumber;
    String flightNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRackUtilId() {
        return rackUtilId;
    }

    public void setRackUtilId(int rackUtilId) {
        this.rackUtilId = rackUtilId;
    }

    public int getActualPcs() {
        return actualPcs;
    }

    public void setActualPcs(int actualPcs) {
        this.actualPcs = actualPcs;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getClassdesc() {
        return classdesc;
    }

    public void setClassdesc(String classdesc) {
        this.classdesc = classdesc;
    }

    public String getHawbNumber() {
        return hawbNumber;
    }

    public void setHawbNumber(String hawbNumber) {
        this.hawbNumber = hawbNumber;
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

    public String getRackName() {
        return rackName;
    }

    public void setRackName(String rackName) {
        this.rackName = rackName;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
}
