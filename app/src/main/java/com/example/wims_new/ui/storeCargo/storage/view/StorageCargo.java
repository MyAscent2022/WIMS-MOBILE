package com.example.wims_new.ui.storeCargo.storage.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.wims_new.R;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityStorageCargoBinding;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackDetailsModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.example.wims_new.ui.storeCargo.storage.view.viewModel.StorageCargoViewModel;
import com.example.wims_new.utils.FunctionInterface;

import java.util.ArrayList;
import java.util.List;

public class StorageCargo extends AppCompatActivity {

    ActivityStorageCargoBinding binding;
    StorageCargoViewModel viewModel;
    List<StorageModel> storage;
    List<RackModel> racks;
   RackDetailsModel rackDetails;
   StorageModel selectedCargo;
    Context context;
    int layout_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        eventHandler();

    }

    public void init() {
        binding = ActivityStorageCargoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new StorageCargoViewModel();
        toShowLayout();
        viewModel.getStoreCargo(this,binding,this);

    }

    private void eventHandler(){


        binding.mawbList.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCargo = storage.get(i);
                layout_id = 2;

                binding.mawbGallery.flightNo.setText(selectedCargo.getFlightNumber());
                binding.mawbGallery.mawbNo.setText(selectedCargo.getMawbNumber());
                binding.mawbGallery.hawbNo.setText(selectedCargo.getHawbNumber());
                binding.mawbGallery.flightClass.setText(selectedCargo.getClassdesc());
                binding.mawbGallery.pcs.setText(String.valueOf(selectedCargo.getActualPcs()));

                toShowLayout();
            }
        });

        binding.mawbGallery.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id = 1;
                toShowLayout();
            }
        });

        binding.mawbGallery.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id = 3;


                viewModel.getRacks(StorageCargo.this,StorageCargo.this, binding);
                viewModel.getLayers(StorageCargo.this,StorageCargo.this, binding);
//                binding.cargoDetails.rackName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.getRacks(StorageCargo.this,this, binding)));
//                binding.cargoDetails.layerName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.getLayers(StorageCargo.this,this, binding)));

                if (selectedCargo.getHawbNumber() == null){
                    viewModel.getCargoDetails(StorageCargo.this,StorageCargo.this,binding,false,selectedCargo.getMawbNumber(), "");
                }else{
                    viewModel.getCargoDetails(StorageCargo.this,StorageCargo.this,binding,true,"", selectedCargo.getHawbNumber());
                }
                toShowLayout();
            }
        });

        binding.cargoDetails.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertsAndLoaders loaders = new AlertsAndLoaders();
                loaders.showAlert(4,"Are you sure?","You want to update storage?", StorageCargo.this,saveCargoStorage);
            }
        });

        binding.cargoDetails.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id = 2;
                toShowLayout();
            }
        });


    }

    private void toShowLayout() {
        viewModel.toShowLayout(binding, layout_id);
    }

    public void getStorage(List<StorageModel> storage) {
        this.storage = storage;
    }

    public void getRacks(List<RackModel> racks) {
        this.racks = racks;
    }

    public void getRackDetails(RackDetailsModel rackDetails) {
        this.rackDetails = rackDetails;
    }

    public FunctionInterface.Function doNothing = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {

        }
    };

    public FunctionInterface.Function goToCargoList = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            layout_id = 1;
            toShowLayout();
        }

    };

    public FunctionInterface.Function saveCargoStorage = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
           viewModel.saveRacks(StorageCargo.this,binding,StorageCargo.this,
                   binding.cargoDetails.rackName.getText().toString().trim(), binding.cargoDetails.layerName.getText().toString().trim(), selectedCargo.getRackUtilId());
        }

    };

    public FunctionInterface.Function backToMenu = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            Intent in = new Intent(StorageCargo.this, MainMenu.class);
            startActivity(in);
        }
    };
}