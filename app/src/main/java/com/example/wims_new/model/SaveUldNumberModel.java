package com.example.wims_new.model;

import java.util.List;

public class SaveUldNumberModel {
    UldModel ulds;
    String uld_number;
    String[] mawbs;



    public UldModel getUlds() {
        return ulds;
    }

    public void setUlds(UldModel ulds) {
        this.ulds = ulds;
    }

    public String getUld_number() {
        return uld_number;
    }

    public void setUld_number(String uld_number) {
        this.uld_number = uld_number;
    }

    public String[] getMawbs() {
        return mawbs;
    }

    public void setMawbs(String[] mawbs) {
        this.mawbs = mawbs;
    }
}
