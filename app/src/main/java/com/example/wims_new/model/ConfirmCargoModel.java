package com.example.wims_new.model;

import java.io.Serializable;

public class ConfirmCargoModel implements Serializable {
    CargoActLogsModel cargoActLogs;
    MawbDetails mawbDetails;
    HawbDetails hawbDetails;



    public ConfirmCargoModel() {
    }

    public ConfirmCargoModel(CargoActLogsModel cargoActLogs, MawbDetails mawbDetails, HawbDetails hawbDetails) {
        this.cargoActLogs = cargoActLogs;
        this.mawbDetails = mawbDetails;
        this.hawbDetails = hawbDetails;
    }

    public CargoActLogsModel getCargoActLogs() {
        return cargoActLogs;
    }

    public void setCargoActLogs(CargoActLogsModel cargoActLogs) {
        this.cargoActLogs = cargoActLogs;
    }

    public MawbDetails getMawbDetails() {
        return mawbDetails;
    }

    public void setMawbDetails(MawbDetails mawbDetails) {
        this.mawbDetails = mawbDetails;
    }

    public HawbDetails getHawbDetails() {
        return hawbDetails;
    }

    public void setHawbDetails(HawbDetails hawbDetails) {
        this.hawbDetails = hawbDetails;
    }
}
