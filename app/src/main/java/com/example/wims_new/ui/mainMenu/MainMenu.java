package com.example.wims_new.ui.mainMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityMainMenuBinding;
import com.example.wims_new.ui.Login.view.LoginPage;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;

public class MainMenu extends AppCompatActivity {

    private ActivityMainMenuBinding binding;

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
            }
        });

        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(MainMenu.this)
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent in = new Intent(MainMenu.this, LoginPage.class);
                            startActivity(in);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}