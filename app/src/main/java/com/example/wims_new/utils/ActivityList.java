package com.example.wims_new.utils;

import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.StorageCargo;

import java.util.ArrayList;
import java.util.List;

public class ActivityList {
    public List<Class> getActivityList(){
        List<Class> activityList = new ArrayList<>();
        activityList.add(MainMenu.class);
        activityList.add(ReceiveCargo.class);
        activityList.add(StorageCargo.class);
        activityList.add(RackLocation.class);

        return activityList;
    }
}
