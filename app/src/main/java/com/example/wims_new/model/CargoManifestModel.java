package com.example.wims_new.model;

public class CargoManifestModel {
    long id;
    Long txnMawbId;
    Long txnHawbId;
    Long locationNo;
    Long actualPieces;
    float weight;
    float length;
    float width;
    float height;
    Long cargoCategoryId;
    Long excessPieces;
    Long shortLandedPieces;
    Long cargoClassId;
    Long cargoStatusId;


    public Long getTxnHawbId() {
        return txnHawbId;
    }

    public void setTxnHawbId(Long txnHawbId) {
        this.txnHawbId = txnHawbId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getTxnMawbId() {
        return txnMawbId;
    }

    public void setTxnMawbId(Long txnMawbId) {
        this.txnMawbId = txnMawbId;
    }

    public Long getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(Long locationNo) {
        this.locationNo = locationNo;
    }

    public Long getActualPieces() {
        return actualPieces;
    }

    public void setActualPieces(Long actualPieces) {
        this.actualPieces = actualPieces;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Long getCargoCategoryId() {
        return cargoCategoryId;
    }

    public void setCargoCategoryId(Long cargoCategoryId) {
        this.cargoCategoryId = cargoCategoryId;
    }

    public Long getExcessPieces() {
        return excessPieces;
    }

    public void setExcessPieces(Long excessPieces) {
        this.excessPieces = excessPieces;
    }

    public Long getShortLandedPieces() {
        return shortLandedPieces;
    }

    public void setShortLandedPieces(Long shortLandedPieces) {
        this.shortLandedPieces = shortLandedPieces;
    }

    public Long getCargoClassId() {
        return cargoClassId;
    }

    public void setCargoClassId(Long cargoClassId) {
        this.cargoClassId = cargoClassId;
    }

    public Long getCargoStatusId() {
        return cargoStatusId;
    }

    public void setCargoStatusId(Long cargoStatusId) {
        this.cargoStatusId = cargoStatusId;
    }
}
