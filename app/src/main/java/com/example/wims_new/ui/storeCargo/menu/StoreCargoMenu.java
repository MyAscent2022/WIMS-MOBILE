package com.example.wims_new.ui.storeCargo.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityStoreCargoMenuBinding;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.StorageCargo;

public class StoreCargoMenu extends AppCompatActivity {

    ActivityStoreCargoMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        eventHandler();

    }

    private void init (){

        binding = ActivityStoreCargoMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    private void eventHandler() {

        binding.storeCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(StoreCargoMenu.this, StorageCargo.class);
                startActivity(in);
            }
        });

        binding.releaseCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(StoreCargoMenu.this, RackLocation.class);
                startActivity(in);
            }
        });

        binding.headerLayout.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}