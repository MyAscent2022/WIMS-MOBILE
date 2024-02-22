package com.example.wims_new.ui.storeCargo.storage.view.Model;

import com.example.wims_new.model.CargoImagesModel;

import java.util.List;

public class CargoImagesRequestModel {

    List<CargoImagesModel> imagesEntity;

    public List<CargoImagesModel> getImagesEntity() {
        return imagesEntity;
    }

    public void setImagesEntity(List<CargoImagesModel> imagesEntity) {
        this.imagesEntity = imagesEntity;
    }
}
