package com.example.wims_new.model;

import java.util.List;

public class MawbDetails {
    //    CargoManifestModel cargoManifest;
    StorageLogsModel storageLogs;

    int userId;
    int cargoCategoryId;
    int cargoClassId;
    int cargoStatusId;
    int actualPcs;
    int weight;
    float volume;
    int length;
    int width;
    int height;
    String cargoCategory;
    String cargoClass;
    String cargoStatus;
    String mawb_number;
    String hawb_number;
    String flight_number;

    public MawbDetails() {
    }

    public MawbDetails(int actualPcs, int weight, float volume, int length, int width, int height, String cargoCategory, String cargoClass, String cargoStatus, String mawb_number, String hawb_number, String flight_number) {
        this.actualPcs = actualPcs;
        this.weight = weight;
        this.volume = volume;
        this.volume = length;
        this.volume = width;
        this.volume = height;
        this.cargoCategory = cargoCategory;
        this.cargoClass = cargoClass;
        this.cargoStatus = cargoStatus;
        this.mawb_number = mawb_number;
        this.hawb_number = hawb_number;
        this.flight_number = flight_number;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCargoCategoryId() {
        return cargoCategoryId;
    }

    public void setCargoCategoryId(int cargoCategoryId) {
        this.cargoCategoryId = cargoCategoryId;
    }

    public int getCargoClassId() {
        return cargoClassId;
    }

    public void setCargoClassId(int cargoClassId) {
        this.cargoClassId = cargoClassId;
    }

    public int getCargoStatusId() {
        return cargoStatusId;
    }

    public void setCargoStatusId(int cargoStatusId) {
        this.cargoStatusId = cargoStatusId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getActualPcs() {
        return actualPcs;
    }

    public void setActualPcs(int actualPcs) {
        this.actualPcs = actualPcs;
    }

    public String getCargoCategory() {
        return cargoCategory;
    }

    public void setCargoCategory(String cargoCategory) {
        this.cargoCategory = cargoCategory;
    }

    public String getCargoClass() {
        return cargoClass;
    }

    public void setCargoClass(String cargoClass) {
        this.cargoClass = cargoClass;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getMawb_number() {
        return mawb_number;
    }

    public void setMawb_number(String mawb_number) {
        this.mawb_number = mawb_number;
    }

    public String getHawb_number() {
        return hawb_number;
    }

    public void setHawb_number(String hawb_number) {
        this.hawb_number = hawb_number;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public StorageLogsModel getStorageLogs() {
        return storageLogs;
    }

    public void setStorageLogs(StorageLogsModel storageLogs) {
        this.storageLogs = storageLogs;
    }
}
