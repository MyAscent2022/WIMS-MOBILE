package com.example.wims_new.ui.storeCargo.storage.view.Model;

import com.example.wims_new.model.RackData;

import java.io.Serializable;
import java.util.List;

public class RackResponse implements Serializable {

    String message;
    boolean status;
    int statusCode;

    RackData data;

    public RackData getData() {
        return data;
    }

    public void setData(RackData data) {
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

}
