package com.example.wims_new.model;

import java.io.Serializable;

public class UldTypeModel implements Serializable {
    long id;
    String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
