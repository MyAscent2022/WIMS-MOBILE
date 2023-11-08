package com.example.wims_new.model;

import java.io.Serializable;

public class FlightsResponse implements Serializable {

    String message;
    String status;
    int statusCode;
    FlightsData data;

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

    public FlightsData getData() {
        return data;
    }

    public void setData(FlightsData data) {
        this.data = data;
    }
}
