package com.example.wims_new.model;

import java.util.List;

public class SaveUldNumberModel {
    UldModel ulds;
    String uldNumber;
    String[] mawbs;



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
}
