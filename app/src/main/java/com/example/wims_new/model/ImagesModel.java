package com.example.wims_new.model;

import java.io.Serializable;

public class ImagesModel implements Serializable {

    long id;
    String filePath;
    String fileName;
    int fileType;
    int userId;
    int mawbId;
    String registryNumber;
    int cargoConditionId;
    int uldTypeId;

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

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMawbId() {
        return mawbId;
    }

    public void setMawbId(int mawbId) {
        this.mawbId = mawbId;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public int getCargoConditionId() {
        return cargoConditionId;
    }

    public void setCargoConditionId(int cargoConditionId) {
        this.cargoConditionId = cargoConditionId;
    }

    public int getUldTypeId() {
        return uldTypeId;
    }

    public void setUldTypeId(int uldTypeId) {
        this.uldTypeId = uldTypeId;
    }
}
