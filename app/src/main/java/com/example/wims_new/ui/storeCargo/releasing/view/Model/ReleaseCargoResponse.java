package com.example.wims_new.ui.storeCargo.releasing.view.Model;

import com.google.android.gms.common.api.Result;

import java.io.Serializable;

public class ReleaseCargoResponse implements Serializable {
    String message;
    boolean status;
    int statusCode;

    ReleaseCargoData data;

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

    public ReleaseCargoData getData() {
        return data;
    }

    public void setData(ReleaseCargoData data) {
        this.data = data;
    }
}
