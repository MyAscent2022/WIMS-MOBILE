package com.example.wims_new.ui.Login.viewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.MainActivity;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.ui.Login.Model.UserModel;
import com.example.wims_new.ui.Login.Model.UserResponse;
import com.example.wims_new.ui.Login.view.LoginPage;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel {

    private SweetAlertDialog dialog;
    private UserResponse resp;
    private UserModel user;

    public void getUserLogin(String username, String passkey, Context context, LoginPage activity) {
        resp = new UserResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "Logging in, please wait. . .", "", context, null);

        try {
            ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
            Call<UserResponse> call = services.getUser(username, passkey);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    dialog.cancel();

                    try {
                        if (response.code() == 200) {
                            resp = response.body();
                            if (resp.getStatusCode() == 200) {
                                user = resp.getData();
                                SharedPref sharedPref = new SharedPref();

                                sharedPref.writePrefString(context, sharedPref.USER_ID, String.valueOf(user.getId()));
                                Intent intent = new Intent(context, MainMenu.class);
                                intent.putExtra("role_id", user.getRoleId());
                                activity.startActivity(intent);

                            } else {
                                alertsAndLoaders.showAlert(1, "","", context, activity.goToMainMenu);
                            }
                        } else {
                            alertsAndLoaders.showAlert(1, "","", context, activity.goToMainMenu);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("Error: ", t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
