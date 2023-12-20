package com.example.wims_new.ui.storeCargo.storage.view.Model;

import java.io.Serializable;
import java.util.List;

public class StorageResponse implements Serializable {

    String message;
    boolean status;
    int statusCode;
    List<StorageModel> storages;

    RackDetailsData data;

    public RackDetailsData getData() {
        return data;
    }

    public void setData(RackDetailsData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<StorageModel> getStorages() {
        return storages;
    }

    public void setStorages(List<StorageModel> storages) {
        this.storages = storages;
    }
}
