package com.example.wims_new.ui.storeCargo.storage.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.LocalDB.LocalDBHelper;
import com.example.wims_new.R;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityStorageCargoBinding;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoModel;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.StorageCargoAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackDetailsModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackResponse;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageResponse;
import com.example.wims_new.ui.storeCargo.storage.view.StorageCargo;
import com.example.wims_new.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageCargoViewModel {
    private SweetAlertDialog dialog;
    StorageResponse resp;
    List<StorageModel> storage;
    List<ReleaseCargoModel> releasingCargo;
    RackResponse rackResp;
    List<RackModel> refRacks;
    RackDetailsModel rackDetails;
    LocalDBHelper db;

    public void getStoreCargo(StorageCargo activity, ActivityStorageCargoBinding binding, Context context) {
        System.out.println("==================================getStoreCargo======================================");
        resp = new StorageResponse();
        dialog = new SweetAlertDialog(context);
        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = services.getStoreCargo();
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {
                dialog.cancel();

                try {
                    storage = new ArrayList<>();
                    resp = response.body();
                    System.out.println("oy response code--------------------> " + response.code());
                    if (resp.isStatus()) {
                        if (resp.getStatusCode() == 200) {
                            storage = resp.getData().getStorages();
//                        int layout_id = 1;
//                        toShowLayout(binding, layout_id);
                            viewData(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                        }
                        activity.getStorage(storage);
                    } else {
                        alertsAndLoaders.showAlert(6, "", "No Data Found", context, activity.backToMenu);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("getStoreCargo error message: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });

    }

    public void getRacks(Context context, StorageCargo activity, ActivityStorageCargoBinding binding) {
        rackResp = new RackResponse();
        ArrayList<String> rack_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<RackResponse> call = services.getRacks();
        call.enqueue(new Callback<RackResponse>() {
            @Override
            public void onResponse(Call<RackResponse> call, Response<RackResponse> response) {
//                dialog.cancel();

                try {
                    refRacks = new ArrayList<>();
                    String[] ar;
                    if (response.code() == 200) {
                        rackResp = response.body();
                        if (rackResp.getStatusCode() == 200) {
                            refRacks = rackResp.getData().getRefRacks();
                            ar = new String[refRacks.size()];
                            for (int i = 0; i < refRacks.size(); i++) {
                                rack_arr.add(refRacks.get(i).getRackName());
                                ar[i] = refRacks.get(i).getRackName();
                            }

                            binding.cargoDetails.rackName.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ar));
                        } else {
                            alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
                        alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RackResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getLayers(Context context, StorageCargo activity, ActivityStorageCargoBinding binding) {
        rackResp = new RackResponse();
        ArrayList<String> layer_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<RackResponse> call = services.getRacks();
        call.enqueue(new Callback<RackResponse>() {
            @Override
            public void onResponse(Call<RackResponse> call, Response<RackResponse> response) {

                try {
                    refRacks = new ArrayList<>();
                    String[] ar;
                    if (response.code() == 200) {
                        rackResp = response.body();
                        if (rackResp.getStatusCode() == 200) {
                            ar = new String[rackResp.getData().getRefRacks().size()];
                            refRacks = rackResp.getData().getRefRacks();
                            for (int i = 0; i < refRacks.size(); i++) {
                                layer_arr.add(refRacks.get(i).getLayerName());
                                ar[i] = refRacks.get(i).getLayerName();
                            }
                            binding.cargoDetails.layerName.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ar));

                        } else {
                            alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                        alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RackResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getCargoDetails(Context context, StorageCargo activity, ActivityStorageCargoBinding binding, boolean is_hawb, String mawb_number, String hawb_number) {
        resp = new StorageResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = services.getRackDetails(is_hawb, hawb_number, mawb_number);
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {
                dialog.cancel();

                try {
                    rackDetails = new RackDetailsModel();
                    resp = response.body();
                    if (response.code() == 200) {
                        rackDetails = resp.getData().getRackDetails();
                        binding.cargoDetails.flightNo.setText(rackDetails.getFlightNumber());
                        binding.cargoDetails.mawbNo.setText(rackDetails.getMawbNumber());
                        binding.cargoDetails.hawbNo.setText(rackDetails.getHawbNumber());
                        binding.cargoDetails.totalWeight.setText(String.valueOf(rackDetails.getWeight()));
                        binding.cargoDetails.cargoClass.setText(rackDetails.getClassdesc());
                        binding.cargoDetails.storagePersonnel.setText(rackDetails.getStoragePersonnel());
                        binding.cargoDetails.rcvPcs.setText(String.valueOf(rackDetails.getActualPcs()));

                        binding.cargoDetails.storedItemPcs.setText(String.valueOf(rackDetails.getActualPcs()));
                        binding.cargoDetails.rackName.setText(rackDetails.getRackName());
                        binding.cargoDetails.layerName.setText(rackDetails.getLayerName());

                    } else {
                        alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                    }
                    activity.getRackDetails(rackDetails);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });
    }


    public void saveRacks(Context context, ActivityStorageCargoBinding binding, StorageCargo activity, String rack_name, String layer_name, int rack_util_id) {
        SharedPref util = new SharedPref();
        AlertsAndLoaders alert = new AlertsAndLoaders();
        SweetAlertDialog sDialog = alert.showAlert(3, "", "Please wait", context, null);
        ApiCall service = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = service.saveRacks(rack_name, layer_name, rack_util_id, Integer.valueOf(util.readPrefString(context, util.USER_ID)));
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {

                try {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    sDialog.cancel();
                    StorageResponse res = new StorageResponse();
                    res = response.body();

                    if (response.code() == 200) {
//                        -- SUCCESS MESSAGE
                        alertsAndLoaders.showAlert(0, "", "Successfully updated Storage", context, activity.goToCargoList);
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
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }


    public void toShowLayout(ActivityStorageCargoBinding binding, int layout_id) {

        binding.headerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.mawbList.getRoot().setVisibility(View.GONE);
        binding.mawbGallery.getRoot().setVisibility(View.GONE);
        binding.cargoDetails.getRoot().setVisibility(View.GONE);
        if (layout_id == 0) {

        } else if (layout_id == 1) {
            binding.mawbList.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 2) {
            binding.mawbGallery.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 3) {
            binding.cargoDetails.getRoot().setVisibility(View.VISIBLE);
        }
    }

    private void viewData(Activity activity, ActivityStorageCargoBinding binding) {
        try {
            StorageCargoAdapter adapter = new StorageCargoAdapter(activity, R.layout.mawb_line_layout, storage);
            binding.mawbList.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
