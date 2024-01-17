package com.example.wims_new.model;

import java.util.List;

public class UldContainerData {
    List<UldTypesModel> types;
    List<UldModel> uldList;
    List<UldContainerTypeModel> containers;

    public List<UldTypesModel> getTypes() {
        return types;
    }

    public void setTypes(List<UldTypesModel> types) {
        this.types = types;
    }

    public List<UldModel> getUldList() {
        return uldList;
    }

    public void setUldList(List<UldModel> uldList) {
        this.uldList = uldList;
    }

    public List<UldContainerTypeModel> getContainers() {
        return containers;
    }

    public void setContainers(List<UldContainerTypeModel> containers) {
        this.containers = containers;
    }
}
