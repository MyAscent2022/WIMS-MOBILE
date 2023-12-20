package com.example.wims_new.ui.storeCargo.storage.view.Model;

import java.io.Serializable;

public class StorageModel implements Serializable {

    int id;
    int actualPcs;
    int cargoStatus;
    int numberOfPackages;
    int mawbPackages;
    int rackUtilId;
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

    public int getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(int cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
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
}
