package com.example.wims_new.ui.storeCargo.storage.view.Model;

import java.util.List;

public class RackDetailsData {

    RackDetailsModel rackDetails;

    List<StorageModel> storages;


    public List<StorageModel> getStorages() {
        return storages;
    }

    public void setStorages(List<StorageModel> storages) {
        this.storages = storages;
    }

    public RackDetailsModel getRackDetails() {
        return rackDetails;
    }

    public void setRackDetails(RackDetailsModel rackDetails) {
        this.rackDetails = rackDetails;
    }
}
