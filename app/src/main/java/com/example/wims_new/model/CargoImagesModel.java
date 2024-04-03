package com.example.wims_new.model;

import android.net.Uri;

import java.io.Serializable;

public class CargoImagesModel implements Serializable {

    long id;
    String filePath;
    String fileName;
    long cargoConditionId;
    int cargoActivityLogId;
    String remarks;
    String cargoCondition;
    Uri imageUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getCargoConditionId() {
        return cargoConditionId;
    }

    public void setCargoConditionId(long cargoConditionId) {
        this.cargoConditionId = cargoConditionId;
    }

    public int getCargoActivityLogId() {
        return cargoActivityLogId;
    }

    public void setCargoActivityLogId(int cargoActivityLogId) {
        this.cargoActivityLogId = cargoActivityLogId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCargoCondition() {
        return cargoCondition;
    }

    public void setCargoCondition(String cargoCondition) {
        this.cargoCondition = cargoCondition;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
