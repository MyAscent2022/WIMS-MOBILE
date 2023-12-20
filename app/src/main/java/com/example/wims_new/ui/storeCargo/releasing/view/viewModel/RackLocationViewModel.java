package com.example.wims_new.ui.storeCargo.releasing.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.R;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityRackLocationBinding;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.databinding.ActivityStorageCargoBinding;
import com.example.wims_new.model.SaveUldNumberModel;
import com.example.wims_new.model.UldResponse;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.releasing.view.Adapter.ReleaseCargoAdapter;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoModel;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoResponse;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.StorageCargoAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageResponse;
import com.example.wims_new.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RackLocationViewModel {
    private SweetAlertDialog dialog;
    ReleaseCargoResponse resp;
    List<ReleaseCargoModel> releasingCargo;

    public void getCargoForReleasing(RackLocation activity, ActivityRackLocationBinding binding, Context context,RackLocationViewModel viewModel){
        resp = new ReleaseCargoResponse();
        dialog = new SweetAlertDialog(context);
        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog= alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<ReleaseCargoResponse> call = services.getReleasingCargo();
        call.enqueue(new Callback<ReleaseCargoResponse>() {


            @Override
            public void onResponse(Call<ReleaseCargoResponse> call, retrofit2.Response<ReleaseCargoResponse> response) {
                dialog.cancel();

                try {
                    releasingCargo = new ArrayList<>();
                    resp = response.body();
                    if(resp.isStatus()){
                        if (resp.getStatusCode() == 200) {
                            releasingCargo = resp.getData().getReleaseCargo();
                            viewDataForReleasing(context, binding, viewModel, activity);
                        } else {
                            alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                        }
                        activity.getReleaseCargoData(releasingCargo);
                    }else{
                        alertsAndLoaders.showAlert(6, "", "No data found", context, activity.backToMenu);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ReleaseCargoResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });

    }

    public void updateStoragerStatus(Context context, ActivityRackLocationBinding binding, RackLocation activity, String hawbNumber, String mawbNumber) {
        SharedPref util = new SharedPref();
        AlertsAndLoaders alert = new AlertsAndLoaders();
        SweetAlertDialog sDialog = alert.showAlert(3, "", "Please wait", context, null);
        ApiCall service = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<ReleaseCargoResponse> call = service.updateStoragerStatus(hawbNumber, mawbNumber,Integer.valueOf(util.readPrefString(context, util.USER_ID)));
        call.enqueue(new Callback<ReleaseCargoResponse>() {
            @Override
            public void onResponse(Call<ReleaseCargoResponse> call, Response<ReleaseCargoResponse> response) {

                try {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    sDialog.cancel();
                    ReleaseCargoResponse res = new ReleaseCargoResponse();
                    res = response.body();
                    if (response.code() == 200) {
                        alertsAndLoaders.showAlert(0, "", "Successfully Released", context, activity.refreshList);
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        alertsAndLoaders.showAlert(2, "", jObjError.get("message").toString(), context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<ReleaseCargoResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }

    private void viewDataForReleasing(Context context, ActivityRackLocationBinding binding, RackLocationViewModel viewModel, RackLocation activity) {
        try {
            ReleaseCargoAdapter adapter = new ReleaseCargoAdapter(context, R.layout.card_rack_line, releasingCargo, viewModel, activity, binding);
            binding.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
