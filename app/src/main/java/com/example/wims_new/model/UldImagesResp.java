package com.example.wims_new.model;

import java.io.Serializable;

public class UldImagesResp implements Serializable {
    String message;
    String status;
    int statusCode;
    UldImagesData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public UldImagesData getData() {
        return data;
    }

    public void setData(UldImagesData data) {
        this.data = data;
    }
}
