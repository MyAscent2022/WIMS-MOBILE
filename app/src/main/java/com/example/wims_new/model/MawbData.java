package com.example.wims_new.model;

import java.util.List;

public class MawbData {
    List<MawbModel> mawbs;
    List<HawbModel> hawbs;
    List<CargoCategoryModel> category;
    List<CargoClassModel> cargoClass;
    List<CargoStatusModel> status;

    MawbDetails mawbDetails;

    public MawbData(MawbDetails mawbDetails) {
        this.mawbDetails = mawbDetails;
    }

    public MawbDetails getMawbDetails() {
        return mawbDetails;
    }

    public void setMawbDetails(MawbDetails mawbDetails) {
        this.mawbDetails = mawbDetails;
    }

    public List<CargoClassModel> getCargoClass() {
        return cargoClass;
    }

    public void setCargoClass(List<CargoClassModel> cargoClass) {
        this.cargoClass = cargoClass;
    }

    public List<CargoStatusModel> getStatus() {
        return status;
    }

    public void setStatus(List<CargoStatusModel> status) {
        this.status = status;
    }

    public List<CargoCategoryModel> getCategory() {
        return category;
    }

    public void setCategory(List<CargoCategoryModel> category) {
        this.category = category;
    }

    public List<HawbModel> getHawbs() {
        return hawbs;
    }

    public void setHawbs(List<HawbModel> hawbs) {
        this.hawbs = hawbs;
    }

    public List<MawbModel> getMawbs() {
        return mawbs;
    }

    public void setMawbs(List<MawbModel> mawbs) {
        this.mawbs = mawbs;
    }
}
