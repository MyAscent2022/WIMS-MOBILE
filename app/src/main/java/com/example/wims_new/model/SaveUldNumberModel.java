package com.example.wims_new.model;

import java.util.List;

public class SaveUldNumberModel {
    UldModel ulds;
    String uldNumber;
    String[] mawbs;
    String flightNumber;
    String uldType;



    public UldModel getUlds() {
        return ulds;
    }

    public void setUlds(UldModel ulds) {
        this.ulds = ulds;
    }

    public String getUldNumber() {
        return uldNumber;
    }

    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

    public String[] getMawbs() {
        return mawbs;
    }

    public void setMawbs(String[] mawbs) {
        this.mawbs = mawbs;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getUldType() {
        return uldType;
    }

    public void setUldType(String uldType) {
        this.uldType = uldType;
    }
}
