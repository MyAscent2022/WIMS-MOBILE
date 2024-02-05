package com.example.wims_new.ui.mainMenu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wims_new.R;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityMainMenuBinding;
import com.example.wims_new.ui.Login.Model.UserResponse;
import com.example.wims_new.ui.Login.view.LoginPage;
import com.example.wims_new.ui.Login.viewModel.LoginViewModel;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;
import com.example.wims_new.utils.FunctionInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity {

    private ActivityMainMenuBinding binding;
    int role_id = 0;
    private Bundle receiveBundle;
    private LoginViewModel viewModel;
    private boolean isLogoutInitiated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);

        init();
    }

    private void init(){
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        receiveBundle = getIntent().getExtras();
        viewModel = new LoginViewModel();

        binding.receiveCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainMenu.this, ReceiveCargo.class);
                startActivity(in);
            }
        });

        binding.storeCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainMenu.this, StoreCargoMenu.class);
                startActivity(in);
//                if (role_id == 4 || role_id == 1) {
//                    Intent in = new Intent(MainMenu.this, StoreCargoMenu.class);
//                    startActivity(in);
//                } else {
//                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//                    alertsAndLoaders.showAlert(1, "","Invalid Action", MainMenu.this, null);
//                }

            }
        });

        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidActivity()) {
                    showLogoutDialog();
                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (isValidActivity()) {
            showLogoutDialog();
        }
    }

    private boolean isValidActivity() {
        return !isFinishing() && !isDestroyed();
    }

    private void showLogoutDialog() {
        AlertDialog dialog = new AlertDialog.Builder(MainMenu.this)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            viewModel.getUserLogout(MainMenu.this, MainMenu.this);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
    }

    public FunctionInterface.Function goToLoginPage = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            Bundle bundle = new Bundle();
            Intent in = new Intent(MainMenu.this, LoginPage.class);
            startActivity(in);
//            new FunctionsMethods().goToActivity(LoginActivity.this, new ActivityList().getActivityList().get(2), LoginActivity.this, true);
        }
    };

}