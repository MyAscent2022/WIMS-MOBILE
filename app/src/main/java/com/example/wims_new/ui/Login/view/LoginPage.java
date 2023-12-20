package com.example.wims_new.ui.Login.view;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.wims_new.MainActivity;
import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityLoginBinding;
import com.example.wims_new.ui.Login.viewModel.LoginViewModel;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.utils.FunctionInterface;

import java.util.Objects;


public class LoginPage extends AppCompatActivity {

    private Button btn_login;
    private Service service;
    private ProgressDialog p;
    private String message = "";
    private TextView txt_date, version;
    private com.google.android.material.textfield.TextInputEditText username, password;
    private boolean is_exit, isDeleted;
    private DownloadManager dm;
    private BroadcastReceiver receiver;
    private long enqueue;

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_page);

        init();
        eventHandler();

//        btn_login = findViewById(R.id.btn_login);

//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(LoginPage.this, MainMenu.class));
//
//            }
//        });

    }

    private void init() {

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new LoginViewModel();
    }

    private void eventHandler() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getUserLogin(binding.txtUsername.getText().toString(), binding.txtPassword.getText().toString(), LoginPage.this, LoginPage.this);
//                Intent in = new Intent(LoginPage.this, MainMenu.class);
//                startActivity(in);
            }
        });

    }

    public FunctionInterface.Function goToMainMenu = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            Bundle bundle = new Bundle();
//            new FunctionsMethods().goToActivity(LoginActivity.this, new ActivityList().getActivityList().get(2), LoginActivity.this, true);
        }
    };
//        binding.btnLogin.setOnClickListener(view -> {

//            String validate_message=viewModel.checkNullValues(binding);
//            if(TextUtils.isEmpty(validate_message)){
//                viewModel.checkUser(Objects.requireNonNull(binding.txtUsername.getText()).toString().trim(), Objects.requireNonNull(binding.txtPassword.getText()).toString().trim(),LoginPage.this,LoginPage.this);
//                //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//            }else{
//                new AlertsAndLoaders().showAlert(2,"",validate_message,LoginPage.this,null);
//            }
//        });
//    }


//    @Override
//    public void onBackPressed() {
//
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure you want to close this app?")
//                .setCancelText("No")
//                .setConfirmText("Yes")
//                .showCancelButton(true)
//                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.cancel();
//                    }
//                })
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        try {
//                            finishAffinity();
//                            System.exit(0);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                })
//                .show();
//
//    }
}