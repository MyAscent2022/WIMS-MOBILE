package com.example.wims_new.ui.receiveCargo.viewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.LocalDB.LocalDBHelper;
import com.example.wims_new.R;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.model.CargoActLogsModel;
import com.example.wims_new.model.CargoCategoryModel;
import com.example.wims_new.model.CargoClassModel;
import com.example.wims_new.model.CargoConditionModel;
import com.example.wims_new.model.CargoStatusModel;
import com.example.wims_new.model.ConfirmCargoModel;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.model.FlightsResponse;
import com.example.wims_new.model.HawbDetails;
import com.example.wims_new.model.HawbModel;
import com.example.wims_new.model.MawbDetails;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.ResBody;
import com.example.wims_new.model.SaveUldNumberModel;
import com.example.wims_new.model.UldContainerResponse;
import com.example.wims_new.model.UldContainerTypeModel;
import com.example.wims_new.model.UldImages;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.model.UldResponse;
import com.example.wims_new.model.UldTypeModel;
import com.example.wims_new.model.UldTypesModel;
import com.example.wims_new.ui.receiveCargo.adapter.FlightListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.HawbListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.MawbListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.UldListAdapter;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.utils.RotateImage;
import com.example.wims_new.utils.SharedPref;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveCargoViewModel {

    private SweetAlertDialog dialog;
    private FlightsResponse resp;
    private List<FlightsModel> flights, searchFlights;
    private UldResponse uldResp;
    private List<UldModel> ulds, searchUlds;
    private MawbResponse mawbResp;
    private UldContainerResponse containerResp;
    private SaveUldNumberModel saveUldNumberModel;
    private List<MawbModel> mawbs;
    private List<HawbModel> hawbs;
    private List<CargoCategoryModel> category;
    private List<CargoClassModel> cargoClass;
    private List<CargoStatusModel> status;
    private List<CargoConditionModel> condition;
    private List<UldTypesModel> uld_types;
    private List<UldContainerTypeModel> containerTypes;
    private List<UldTypesModel> uld_id;
    private MutableLiveData<List<UldModel>> uldLiveData = new MutableLiveData<>();
    LocalDBHelper db;
    String flight_no = "";
    String registry_no = "";
    String uld_no = "";
    int user_id = 0;
    String[] hawb_count_ar;

    public void landedFlights(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        resp = new FlightsResponse();
        SharedPref util = new SharedPref();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        System.out.println("USER ID >>>>>>>>>>>>>>>>>>>>>>>>>" + util.readPrefString(context, util.USER_ID));
        Call<FlightsResponse> call = services.searchFlights(Integer.parseInt(util.readPrefString(context, util.USER_ID)));
//        Call<FlightsResponse> call = services.searchFlights(35);
        call.enqueue(new Callback<FlightsResponse>() {
            @Override
            public void onResponse(Call<FlightsResponse> call, Response<FlightsResponse> response) {
                dialog.cancel();

                try {
                    flights = new ArrayList<>();
                    searchFlights = new ArrayList<>();
                    resp = response.body();
                    if (response.code() == 200) {
                        flights = resp.getData().getFlights();
                        searchFlights = flights;
                        viewData(activity, binding);
                        binding.flightListLayout.refreshLayout.setRefreshing(false);
                    } else {
//                        alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                    }
                    activity.getFlights(flights);
                    activity.getSearchFlights(searchFlights);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FlightsResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });
    }

    public void uldPerFlight(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String flight_number) {
        uldResp = new UldResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<UldResponse> call = services.searchUlds(flight_number);
        call.enqueue(new Callback<UldResponse>() {
            @Override
            public void onResponse(Call<UldResponse> call, Response<UldResponse> response) {
                dialog.cancel();

                try {
                    ulds = new ArrayList<>();
                    if (response.code() == 200) {
                        uldResp = response.body();
                        if (resp.getStatusCode() == 200) {
                            ulds = uldResp.getData().getUldList();
                            searchUlds = ulds;
                            binding.uldLayout.noOfUld.setText(ulds.size() + "");
                            viewData2(activity, binding);
                            uldLiveData.setValue(ulds);
                        } else {
                            alertsAndLoaders.showAlert(1, "", uldResp.getMessage(), context, activity.doNothing);
                        }
                        binding.uldLayout.refreshLayout.setRefreshing(false);
                    } else {
                        alertsAndLoaders.showAlert(1, "", uldResp.getMessage(), context, activity.doNothing);
                    }
                    activity.getUlds(ulds);
                    activity.getSearchUlds(searchUlds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UldResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getMawbList(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String uld_no, boolean isUld, String flight_no, boolean is_dialog) {
        mawbResp = new MawbResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getMawbs(uld_no, isUld, flight_no);
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    mawbs = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {

                            if (is_dialog) {
                                mawbs = mawbResp.getData().getMawbs();
//                                for(int i = 0; i < mawbs.size(); i++){
//                                    getHawb(context,activity,binding, mawbs.get(i).getMawbNumber());
//
//                                }
                                activity.getMawb(mawbs);
                            } else {
                                mawbs = mawbResp.getData().getMawbs();
                                viewData3(activity, binding);
                            }

                        } else {
//                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    }
                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getHawb(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String mawb_number) {
        mawbResp = new MawbResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "Loading...", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getHawbs(mawb_number);
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    int layout_id = 0;
                    hawbs = new ArrayList<>();

                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            hawbs = mawbResp.getData().getHawbs();
//                            binding.mawbDetails.hawb.setText(hawb_number);
                            layout_id = 5;
                            toShowLayout(binding, layout_id, false, false);
                            viewData4(activity, binding);
                        } else {
                            binding.mawbDetails.mawbNo.setText(mawb_number);
                            binding.mawbDetails.hawb.setText(mawb_number);
//                        -- ADD RECEIVER NAME!!!!
//                        binding.mawbDetails.checker.setText(selectedMawbs.getConsigneeName());
//                        -- ADD LOCATION NUMBER!!!!!!!!!!
//                        binding.mawbDetails.locationNo.setText("");
                            getCargoCategory(context, activity, binding);
                            getCargoClass(context, activity, binding);
//                            getCargoStatus(context, activity, binding);

                            layout_id = 6;
                            toShowLayout(binding, layout_id, false, false);
                        }
                    } else {

                    }
                        activity.getHawbs(hawbs);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {

            }
        });
    }

    public void toShowLayout(ActivityReceiveCargoBinding binding, int layout_id, boolean is_uld, boolean is_uploaded) {

        binding.flightListLayout.getRoot().setVisibility(View.GONE);
        binding.flightDetailsLayout.getRoot().setVisibility(View.GONE);
        binding.uldLayout.getRoot().setVisibility(View.GONE);
        binding.mawbListLayout.getRoot().setVisibility(View.GONE);
        binding.hawbListLayout.getRoot().setVisibility(View.GONE);
        binding.mawbDetails.getRoot().setVisibility(View.GONE);
        binding.cargoImagesLayout.getRoot().setVisibility(View.GONE);
        binding.headerLayout.getRoot().setVisibility(View.VISIBLE);
        System.out.println("LAYOUT ID >>>>>>>>>>>>>>>>>>>>>>" +  layout_id);
        if (layout_id == 0) {

        } else if (layout_id == 1) {
            binding.flightListLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 2) {
            binding.flightDetailsLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 3) {
            binding.uldLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 4) {
            binding.mawbListLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 5) {


            binding.hawbListLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 6) {
            binding.mawbDetails.getRoot().setVisibility(View.VISIBLE);

            if (is_uploaded) {
                binding.mawbDetails.uploadCargoImageLayout.setVisibility(View.GONE);
                binding.mawbDetails.listView.setVisibility(View.VISIBLE);
                binding.mawbDetails.listTitle.setVisibility(View.VISIBLE);
            }
//            binding.mawbDetails.getRoot().setVisibility(View.VISIBLE);

        } else if (layout_id == 7) {

            binding.cargoImagesLayout.getRoot().setVisibility(View.VISIBLE);
            if (is_uld) {
                binding.cargoImagesLayout.uldTitle.setVisibility(View.VISIBLE);
                binding.cargoImagesLayout.uldNumber.setVisibility(View.VISIBLE);
                binding.cargoImagesLayout.cargoTitle.setVisibility(View.GONE);
            } else {
                binding.cargoImagesLayout.uldTitle.setVisibility(View.GONE);
                binding.cargoImagesLayout.uldNumber.setVisibility(View.GONE);
                binding.cargoImagesLayout.cargoTitle.setVisibility(View.VISIBLE);
            }

        } else if (layout_id == 8) {

            binding.cargoImagesLayout.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 9) {
            binding.cargoImageListLayout.getRoot().setVisibility(View.VISIBLE);
        }
    }

    public void viewData(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            FlightListAdapter adapter = new FlightListAdapter(activity, R.layout.card_flight_line, flights);
            binding.flightListLayout.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewData2(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            UldListAdapter adapter = new UldListAdapter(activity, R.layout.uld_line_layout, ulds);
            binding.uldLayout.listLayout.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewData3(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            MawbListAdapter adapter = new MawbListAdapter(activity, R.layout.mawb_line, mawbs);
            binding.mawbListLayout.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewData4(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            HawbListAdapter adapter = new HawbListAdapter(activity, R.layout.pending_line, hawbs);
            binding.hawbListLayout.listLayout.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateFlightDetails(ActivityReceiveCargoBinding binding, FlightsModel selectedFlights) {

        flight_no = selectedFlights.getFlightNumber();
        registry_no = selectedFlights.getRegistryNumber();

        binding.flightDetailsLayout.flightNo.setText(flight_no);
        binding.flightDetailsLayout.travelStatus.setText(selectedFlights.getTravelStatus());

        binding.flightDetailsLayout.flightNumber.setText(flight_no);
        binding.flightDetailsLayout.airline.setText(selectedFlights.getAirline());
        binding.flightDetailsLayout.registryNo.setText(registry_no);
//        binding.flightDetailsLayout.entryNo.setText(selectedFlights.getEntryNumber());
        binding.flightDetailsLayout.arrivalDt.setText(selectedFlights.getArrivalDate());
        binding.flightDetailsLayout.flightStatus.setText(selectedFlights.getFlightStatus());
    }

    public void populatedHawbDetails(ActivityReceiveCargoBinding binding, MawbModel selectedMawbs, HawbModel selectedHawbs) {
//        binding.mawbDetails.status.setText(selectedMawbs.getCargoStatus());
        binding.mawbDetails.mawbNo.setText(selectedMawbs.getMawbNumber());
        binding.mawbDetails.checker.setText(selectedMawbs.getConsigneeName());
        binding.mawbDetails.hawb.setText(selectedHawbs.getHawbNumber());
        binding.mawbDetails.locationNo.setText("");

    }

    public void getCargoConditionList(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        mawbResp = new MawbResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getCargoCondition();
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    condition = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            condition = mawbResp.getCondition();

                            binding.cargoImagesLayout.spinner1.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cargoCondition(condition)));
                            binding.cargoImagesLayout.spinner2.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cargoCondition(condition)));

                        } else {
                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                    }
//                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    private String[] cargoCondition(List<CargoConditionModel> condition) {
        String[] ar = new String[condition.size()];
        for (int i = 0; i < condition.size(); i++) {
            ar[i] = condition.get(i).getCondition();
        }
        return ar;
    }

    private int cargoCategory(List<CargoCategoryModel> category, String selectedCategory) {
        for (CargoCategoryModel c : category) {
            if (selectedCategory.equals(c.getDescription())) {
                return c.getId();
            }
        }
        return 0;
    }

    private int cargoCondi(List<CargoConditionModel> condition, String selectedCondition) {
        for (CargoConditionModel c : condition) {
            if (selectedCondition.equals(c.getCondition())) {
                return c.getId();
            }
        }
        return 0;
    }

    public void insertMawbDetails(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, List<Uri> uri, String[] fname, String mawb_number, String flight_number, String hawb_number, int hawb_id) {
        ConfirmCargoModel response = new ConfirmCargoModel();
        MawbDetails mawbDetails = new MawbDetails();
        SharedPref util = new SharedPref();
        db = new LocalDBHelper(context);

        mawbDetails = db.getMawbDetails();
        System.out.println("ACTUAL PCS >>>>>>>>>>>>>> " + mawbDetails.getActualPcs());

//        mawbDetails.setCargoCategory(binding.mawbDetails.cargoCategory.getText().toString());
//        System.out.println("setCargoCategoryId " + binding.mawbDetails.cargoCategory.getText().toString());
//
//        mawbDetails.setCargoClass(binding.mawbDetails.cargoClass.getText().toString());
//        System.out.println("setCargoClassId " + binding.mawbDetails.cargoClass.getText().toString());

        mawbDetails.setCargoStatus(binding.mawbDetails.cargoStatus.getEditableText().toString());
        mawbDetails.setUserId(Integer.valueOf(util.readPrefString(context, util.USER_ID)));

        response.setMawbDetails(mawbDetails);
        //response.setFiles(getFilePart(uri,context));

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "Loading. . .", "", context, null);
        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.saveMawbDetails(response, mawb_number,flight_number, hawb_number, Integer.valueOf(util.readPrefString(context, util.USER_ID)), binding.mawbDetails.cargoCategory.getText().toString(), binding.mawbDetails.cargoClass.getText().toString());
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {

                try {
                    dialog.cancel();
                    MawbResponse res = new MawbResponse();
                    res = response.body();

                    if (res.getStatusCode() == 200) {

                        uploadImage(context, activity, binding, uri, dialog, hawb_id, mawb_number);

                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(2, "", jObjError.get("message").toString(), context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }

    public void saveUldImage(Context context, ReceiveCargo activity,  ActivityReceiveCargoBinding binding, List<Uri> uri, SweetAlertDialog dialog, String flight_number, String uld_number) {

        //response.setFiles(getFilePart(uri,context));



        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<Integer> call = services.saveUldImage(getFilePart(uri,context), flight_number, uld_number, binding.cargoImagesLayout.spinner1.getSelectedItem().toString(), binding.cargoImagesLayout.spinner2.getSelectedItem().toString(), binding.cargoImagesLayout.remarks.getText().toString(), binding.cargoImagesLayout.remarks2.getText().toString());


        SweetAlertDialog finalDialog = dialog;
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                try {
                    finalDialog.cancel();
                    Integer res = response.body();

                    if (res == 1) {
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(0, "Success!", "Success", context, activity.backToMenu);
//                        to_upload(uri,fname,context, binding);
                    } else {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(2, "", "Error", context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }





    public void uploadImage(Context context, ReceiveCargo activity,  ActivityReceiveCargoBinding binding, List<Uri> uri, SweetAlertDialog dialog, int hawb_id, String mawb_number) {

        //response.setFiles(getFilePart(uri,context));



        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<Integer> call = services.uploadImage(getFilePart(uri,context), hawb_id, mawb_number, binding.cargoImagesLayout.spinner1.getSelectedItem().toString(), binding.cargoImagesLayout.spinner2.getSelectedItem().toString(), binding.cargoImagesLayout.remarks.getText().toString(), binding.cargoImagesLayout.remarks2.getText().toString());


        SweetAlertDialog finalDialog = dialog;
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                try {
                    finalDialog.cancel();
                    Integer res = response.body();

                    if (res == 1) {
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(0, "Success!", "Success", context, activity.backToMenu);
//                        to_upload(uri,fname,context, binding);
                    } else {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(2, "", "Error", context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }


    public void to_upload(List<Uri> uri, String[] fname, Context context, ActivityReceiveCargoBinding binding) {
        db = new LocalDBHelper(context);
        try {
            OkHttpClient.Builder b = new OkHttpClient.Builder();
            b.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES); // read timeout
            OkHttpClient client = new OkHttpClient();
            client = b.build();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
            MultipartBody.Builder builder = bodyBuilder.setType(MultipartBody.FORM);
            bodyBuilder.setType(MultipartBody.FORM)
//                    .addFormDataPart("txn_cargo_manifest_details_id", getIntent().getStringExtra("device_id")) -- GET MAWB ID
                    .addFormDataPart("cargo_conditon_id", String.valueOf(3))
//                    -- GET CARGO CONDITION ID
                    .addFormDataPart("remarks", binding.cargoImagesLayout.remarks.getText().toString())
                    .addFormDataPart("mawb_number", db.getMawbDetails().getMawb_number())
                    .addFormDataPart("hawb_number", db.getMawbDetails().getHawb_number())
                    .addFormDataPart("flight_number", db.getMawbDetails().getFlight_number())
//                    .addFormDataPart("uld_type_id", String.valueOf(2))
//                    -- GET ULD TYPE ID

                    .addFormDataPart("actual_pcs", String.valueOf(db.getMawbDetails().getActualPcs()))
                    .addFormDataPart("weight", String.valueOf(db.getMawbDetails().getWeight()))
                    .addFormDataPart("volume", String.valueOf(db.getMawbDetails().getVolume()))
                    .addFormDataPart("length", String.valueOf(db.getMawbDetails().getLength()))
                    .addFormDataPart("width", String.valueOf(db.getMawbDetails().getWidth()))
                    .addFormDataPart("height", String.valueOf(db.getMawbDetails().getHeight()))
                    .addFormDataPart("cargo_category", String.valueOf(binding.mawbDetails.cargoCategory.getEditableText().toString()))
                    .addFormDataPart("cargo_class", String.valueOf(binding.mawbDetails.cargoClass.getEditableText().toString()))
                    .addFormDataPart("cargo_status", String.valueOf(binding.mawbDetails.cargoStatus.getEditableText().toString()));

            String[] ext = context.getContentResolver().getType(uri.get(0)).split("/");
            File f1=compressFile(uri.get(0),  context);

            bodyBuilder.setType(MultipartBody.FORM)
                    .addFormDataPart("file[]", fname[0]+"."+ext[1],
                            RequestBody.create(MediaType.parse("application/octet-stream"), f1));

            String[] ext1 = context.getContentResolver().getType(uri.get(1)).split("/");
            File f2=compressFile(uri.get(1),  context);

            bodyBuilder.setType(MultipartBody.FORM)
                    .addFormDataPart("file[]", fname[1]+"."+ext1[1],
                            RequestBody.create(MediaType.parse("application/octet-stream"), f2));

           // MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = bodyBuilder.build();
            String authToken = Credentials.basic(BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
            Request request = new Request.Builder()
                    .url("http://192.168.254.201:33913/wims_api/" + "save_hawb_image")


                    .method("POST", body)
//                    .addHeader("Authorization", authToken)
                    .build();
            Gson gson = new Gson();
            ResponseBody responseBody = client.newCall(request).execute().body();
            ResBody res = new ResBody();
            String resp = responseBody.string();
            System.out.println("my resp " + resp);
            try {
                res = gson.fromJson(resp, ResBody.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File compressFile(Uri imageUri, Context context) {
        File finalFile = null;
        try {
            //Bitmap bmap = toRotateBitmap(imageUri, filename);
            Bitmap bmap=new RotateImage().toRotateBitmap(imageUri,context);
            Bitmap imageBitmap = bmap;

            int newWidth = 900;
            int newHeight = 1200;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true);
            finalFile = new File(context.getCacheDir() + "/" + new RotateImage().getFileNameFromUri(imageUri,context));

            FileOutputStream fos = new FileOutputStream(finalFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFile;
    }
    private List<MultipartBody.Part> getFilePart(List<Uri> uri, Context context){
        List<MultipartBody.Part> filePart = new ArrayList<>();
        for(Uri u:uri){
            File file=compressFile(u,context);
            filePart.add(MultipartBody.Part.createFormData("file[]", new RotateImage().getFileNameFromUri(u, context), RequestBody.create(MediaType.parse("application/octet-stream"), file)));
        }

        return filePart;
    }

    /*private File compressFile(Uri imageUri, String filename,Context context) {
        File finalFile = null;
        try {


            Bitmap imageBitmap =  MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

            finalFile = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/ECTSIMAGES/" + filename);
            FileOutputStream fos = new FileOutputStream(finalFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFile;
    }*/

   /* private File compressFile(Uri imageUri, String filename, Context context) {
        File finalFile = null;
        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);


            int newWidth = 900; // Replace with your desired width
            int newHeight = 1200; // Replace with your desired height
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true);
            finalFile = new File(context.getCacheDir() + "/" + filename);

            FileOutputStream fos = new FileOutputStream(finalFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFile;
    }*/

    public ArrayList<String> getUldTypes(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        mawbResp = new MawbResponse();
        ArrayList<String> uld_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<UldResponse> call = services.getUldTypes();
        call.enqueue(new Callback<UldResponse>() {
            @Override
            public void onResponse(Call<UldResponse> call, Response<UldResponse> response) {
                dialog.cancel();

                try {
                    uld_types = new ArrayList<>();
                    if (response.code() == 200) {
                        uldResp = response.body();
                        if (uldResp.getStatusCode() == 200) {
                            uld_types = uldResp.getData().getTypes();
                            for (int i = 0; i < uld_types.size(); i++) {
                                uld_arr.add(uld_types.get(i).getType());
                            }
                            activity.getUldTypes(uld_types);
                        } else {
//                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
//                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UldResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return uld_arr;
    }

    public ArrayList<String> getContainerTypes(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        containerResp = new UldContainerResponse();
        ArrayList<String> container_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<UldContainerResponse> call = services.getContainerTypes();
        call.enqueue(new Callback<UldContainerResponse>() {
            @Override
            public void onResponse(Call<UldContainerResponse> call, Response<UldContainerResponse> response) {
                dialog.cancel();

                try {
                    uld_types = new ArrayList<>();
                    if (response.code() == 200) {
                        containerResp = response.body();
                        if (containerResp.getStatusCode() == 200) {
                            containerTypes = containerResp.getData().getContainers();
                            for (int i = 0; i < containerTypes.size(); i++) {
                                container_arr.add(containerTypes.get(i).getType());
                            }
                            activity.getUldContainerTypes(containerTypes);
                        } else {
//                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
//                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UldContainerResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return container_arr;
    }


    public ArrayList<String> getCargoCategory(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        mawbResp = new MawbResponse();
        ArrayList<String> category_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getCargoCategory();
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    category = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            category = mawbResp.getData().getCategory();
                            for (int i = 0; i < category.size(); i++) {
                                category_arr.add(category.get(i).getDescription());
                            }
                        } else {
                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
//                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return category_arr;
    }

    public ArrayList<String> getCargoClass(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        mawbResp = new MawbResponse();
        ArrayList<String> class_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getCargoClass();
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    cargoClass = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            cargoClass = mawbResp.getData().getCargoClass();
                            for (int i = 0; i < cargoClass.size(); i++) {
                                class_arr.add(cargoClass.get(i).getClassdesc());
                            }
                        } else {
                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
//                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return class_arr;
    }

    public void getCargoStatus(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        mawbResp = new MawbResponse();
        ArrayList<String> status_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getCargoStatus();
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    status = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            status = mawbResp.getData().getStatus();
                            for (int i = 0; i < status.size(); i++) {
                                status_arr.add(status.get(i).getDescription());
                            }
                        } else {
                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
//                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }

    public void saveUldNumber(Context context, ActivityReceiveCargoBinding binding, ReceiveCargo activity, SaveUldNumberModel save) {
//        save = new SaveUldNumberModel();
        AlertsAndLoaders alert = new AlertsAndLoaders();
        SweetAlertDialog sDialog = alert.showAlert(3, "", "Please wait", context, null);
        ApiCall service = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<UldResponse> call = service.saveUldNumber(save);
        call.enqueue(new Callback<UldResponse>() {
            @Override
            public void onResponse(Call<UldResponse> call, Response<UldResponse> response) {

                try {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    sDialog.cancel();
                    UldResponse res = new UldResponse();
                    res = response.body();

                    if (response.code() == 200) {
//                        -- SUCCESS MESSAGE
                        alertsAndLoaders.showAlert(0, "", "Successfully added ULD!", context, activity.goToUldList);
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
            public void onFailure(Call<UldResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }

    public void updateUldNumber(Context context, ActivityReceiveCargoBinding binding, ReceiveCargo activity, SaveUldNumberModel update, String lastUldNumber) {
//        save = new SaveUldNumberModel();
        AlertsAndLoaders alert = new AlertsAndLoaders();
        SweetAlertDialog sDialog = alert.showAlert(3, "", "Please wait", context, null);
        ApiCall service = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<UldResponse> call = service.updateUldNumber(update, lastUldNumber);
        call.enqueue(new Callback<UldResponse>() {
            @Override
            public void onResponse(Call<UldResponse> call, Response<UldResponse> response) {

                try {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    sDialog.cancel();
                    UldResponse res = new UldResponse();
                    res = response.body();

                    if (response.code() == 200) {
//                        -- SUCCESS MESSAGE
                        alertsAndLoaders.showAlert(0, "", "Successfully updated ULD!", context, activity.goToUldList);
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
            public void onFailure(Call<UldResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }


}
