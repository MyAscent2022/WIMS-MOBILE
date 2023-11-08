package com.example.wims_new.ui.receiveCargo.viewModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.R;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.model.FlightsResponse;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.model.UldResponse;
import com.example.wims_new.ui.receiveCargo.adapter.FlightListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.HawbListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.MawbListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.UldListAdapter;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveCargoViewModel {

    private AlertDialog dialog;
    private FlightsResponse resp;
    private List<FlightsModel> flights;
    private UldResponse uldResp;
    private List<UldModel> ulds;
    private MawbResponse mawbResp;
    private List<MawbModel> mawbs;

    public void landedFlights(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding) {
        resp = new FlightsResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(2, "Loading....", context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<FlightsResponse> call = services.searchFlights();
        call.enqueue(new Callback<FlightsResponse>() {
            @Override
            public void onResponse(Call<FlightsResponse> call, Response<FlightsResponse> response) {
                dialog.cancel();

                try {
                    flights = new ArrayList<>();
                    if (response.code() == 200) {
                        resp = response.body();
                        if (resp.getStatusCode() == 200) {
                            flights = resp.getData().getFlights();
                            viewData(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, resp.getMessage(), context, activity, activity.doNothing);
                        }
                    } else {

                    }
                    activity.getFlights(flights);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FlightsResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());
            }
        });
    }

    public void uldPerFlight(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String flight_number) {
        uldResp = new UldResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);

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
                        if (resp.getStatusCode() == 200){
                            ulds = uldResp.getData().getUlds();
                            viewData2(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, uldResp.getMessage(), context, activity, activity.doNothing);
                        }
                    } else {

                    }
                    activity.getUlds(ulds);
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

    public void getMawbList(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String registry_number) {
        mawbResp = new MawbResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getMawbs(registry_number);
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    mawbs = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            mawbs = mawbResp.getData().getMawbs();
                            viewData3(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, mawbResp.getMessage(), context, activity, activity.doNothing);
                        }
                    } else {

                    }
                    activity.getMawbs(mawbs);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getHawb(Context context, ReceiveCargo activity, ActivityReceiveCargoBinding binding, String registry_number, String mawb_number){
        mawbResp = new MawbResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getHawbs(registry_number, mawb_number);
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    mawbs = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            mawbs = mawbResp.getData().getMawbs();
                            viewData4(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, mawbResp.getMessage(), context, activity, activity.doNothing);
                        }
                    } else {

                    }
                    activity.getMawbs(mawbs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {

            }
        });
    }

    public void toShowLayout(ActivityReceiveCargoBinding binding,int layout_id){

        binding.flightListLayout.getRoot().setVisibility(View.GONE);
        binding.flightDetailsLayout.getRoot().setVisibility(View.GONE);
        binding.uldLayout.getRoot().setVisibility(View.GONE);
        binding.mawbListLayout.getRoot().setVisibility(View.GONE);
        binding.hawbListLayout.getRoot().setVisibility(View.GONE);
        binding.mawbDetails.getRoot().setVisibility(View.GONE);
        binding.cargoImagesLayout.getRoot().setVisibility(View.GONE);
        binding.headerLayout.getRoot().setVisibility(View.VISIBLE);
        if(layout_id==0){

        }else if(layout_id==1){
            binding.flightListLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==2){
            binding.flightDetailsLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==3){
            binding.uldLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==4){
            binding.uldImagesLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==5){
            binding.mawbListLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==6){
            binding.hawbListLayout.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==7){
            binding.mawbDetails.getRoot().setVisibility(View.VISIBLE);
        }else if(layout_id==8){
            binding.cargoImagesLayout.getRoot().setVisibility(View.VISIBLE);
        }
    }

    private void viewData(Activity activity, ActivityReceiveCargoBinding binding){
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
            HawbListAdapter adapter = new HawbListAdapter(activity, R.layout.pending_line, mawbs);
            binding.hawbListLayout.listLayout.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateFlightDetails(ActivityReceiveCargoBinding binding, FlightsModel selectedFlights){
        binding.flightDetailsLayout.flightNo.setText(selectedFlights.getFlightNumber());
        binding.flightDetailsLayout.travelStatus.setText(selectedFlights.getStatus());
        binding.flightDetailsLayout.tmr.setText(selectedFlights.getTbs());
        binding.flightDetailsLayout.tbs.setText(selectedFlights.getTbs());
        binding.flightDetailsLayout.tbf.setText(selectedFlights.getTff());

        binding.flightDetailsLayout.flightNumber.setText(selectedFlights.getFlightNumber());
        binding.flightDetailsLayout.registryNo.setText(selectedFlights.getRegistryNumber());
        binding.flightDetailsLayout.flightStatus.setText(selectedFlights.getStatus());
        binding.flightDetailsLayout.timeBreakdownStart.setText(selectedFlights.getTbs());
        binding.flightDetailsLayout.timeFinishFlight.setText(selectedFlights.getTff());
    }

    public void populatedHawbDetails(ActivityReceiveCargoBinding binding, MawbModel selectedMawbs) {
        binding.mawbDetails.status.setText(selectedMawbs.getCargoStatus());
        binding.mawbDetails.mawbNo.setText(selectedMawbs.getMawbNumber());
        binding.mawbDetails.checker.setText(selectedMawbs.getConsigneeName());
        binding.mawbDetails.hawb.setText(selectedMawbs.getHawbNumber());
        binding.mawbDetails.locationNo.setText("");
    }
}
