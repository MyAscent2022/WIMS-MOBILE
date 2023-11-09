package com.example.wims_new.model;

import java.io.Serializable;
import java.util.List;

public class MawbResponse implements Serializable {
    String message;
    String status;
    int statusCode;
    MawbData data;
    CargoConditionData conditionData;
    List<CargoConditionModel> condition;

    public List<CargoConditionModel> getCondition() {
        return condition;
    }

    public void setCondition(List<CargoConditionModel> condition) {
        this.condition = condition;
    }

    public CargoConditionData getConditionData() {
        return conditionData;
    }

    public void setConditionData(CargoConditionData conditionData) {
        this.conditionData = conditionData;
    }

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

    public MawbData getData() {
        return data;
    }

    public void setData(MawbData data) {
        this.data = data;
    }
}
