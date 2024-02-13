package com.example.wims_new.model;

import java.util.List;

public class UldData {
    List<UldModel> ulds;
    List<UldTypesModel> types;

    List<UldModel> uldList;
    List<UldModel> uldList1;

    public List<UldModel> getUldList() {
        return uldList;
    }

    public void setUldList(List<UldModel> uldList) {
        this.uldList = uldList;
    }

    public List<UldTypesModel> getTypes() {
        return types;
    }

    public void setTypes(List<UldTypesModel> types) {
        this.types = types;
    }

    public List<UldModel> getUlds() {
        return ulds;
    }

    public void setUlds(List<UldModel> ulds) {
        this.ulds = ulds;
    }

    public List<UldModel> getUldList1() {
        return uldList1;
    }

    public void setUldList1(List<UldModel> uldList1) {
        this.uldList1 = uldList1;
    }
}
