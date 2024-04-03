package com.example.wims_new.ui.storeCargo.releasing.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityRackLocationBinding;
import com.example.wims_new.databinding.ActivityStoreCargoMenuBinding;
import com.example.wims_new.model.HawbModel;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoModel;
import com.example.wims_new.ui.storeCargo.releasing.view.viewModel.RackLocationViewModel;
import com.example.wims_new.utils.FunctionInterface;
import com.example.wims_new.utils.SharedPref;

import java.util.List;

public class RackLocation extends AppCompatActivity {

    ActivityRackLocationBinding binding;
    RackLocationViewModel viewModel;
    List<ReleaseCargoModel> releasingCargo;
    private int layout_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init(){
        binding = ActivityRackLocationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new RackLocationViewModel();
        viewModel.getCargoForReleasing(RackLocation.this,binding, this, viewModel);

        SharedPref sharedPref = new SharedPref();
        binding.locatorId.setText(sharedPref.readPrefString(this,sharedPref.USERNAME));
        eventHandler();

    }

    private void eventHandler(){

        binding.headerLayout.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(RackLocation.this, StoreCargoMenu.class);
                startActivity(in);
            }
        });




    }

    public FunctionInterface.Function doNothing = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {

        }
    };

    public FunctionInterface.Function backToMenu = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            Intent in = new Intent(RackLocation.this, StoreCargoMenu.class);
            startActivity(in);
        }
    };

    public void getReleaseCargoData(List<ReleaseCargoModel> releasingCargo) {
        this.releasingCargo = releasingCargo;
    }


    public FunctionInterface.Function refreshList = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            Intent in = new Intent(RackLocation.this, RackLocation.class);
            startActivity(in);
        }

    };

    public FunctionInterface.Function backToMain = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
//            Bundle bundle = new Bundle();
            Intent in = new Intent(RackLocation.this, MainMenu.class);
            startActivity(in);
        }
    };

}