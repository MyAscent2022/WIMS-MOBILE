package com.example.wims_new.ui.receiveCargo.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wims_new.R;
import com.example.wims_new.databinding.ActivityMainMenuBinding;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.ui.receiveCargo.viewModel.ReceiveCargoViewModel;
import com.example.wims_new.utils.FunctionInterface;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class ReceiveCargo extends AppCompatActivity {

    private ActivityReceiveCargoBinding binding;
    private ReceiveCargoViewModel viewModel;
    private List<FlightsModel> flights;
    private FlightsModel selectedFlights;
    private List<UldModel> ulds;
    private UldModel selectedUlds;
    private List<MawbModel> mawbs;
    private MawbModel selectedMawbs;
    private int layout_id = 1;
    private String fname = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_receive_cargo);
        init();
        eventListener();
        uldList();
        mawbList();
        hawbList();
        hawbDetails();
        cargoImages();
        uldImages();
//        backBtn();
    }

    private void init() {
        binding = ActivityReceiveCargoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ReceiveCargoViewModel();
        viewModel.toShowLayout(binding, 1);
        viewModel.landedFlights(this, this, binding);

    }

    public void eventListener() {
        binding.flightListLayout.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFlights = flights.get(i);
                viewModel.toShowLayout(binding, 2);
                viewModel.populateFlightDetails(binding, selectedFlights);
            }
        });
    }

    public void backBtn() {
        binding.headerLayout.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 0);
            }
        });
    }

    public void uldList() {
        binding.flightDetailsLayout.enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.uldLayout.flightNumber.setText(selectedFlights.getFlightNumber());

                viewModel.toShowLayout(binding, 3);
                viewModel.uldPerFlight(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getFlightNumber());
            }
        });
    }

    public void mawbList() {
        binding.uldLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUlds = ulds.get(i);
                binding.mawbListLayout.uldNo.setText(selectedUlds.getUldNo());
                binding.mawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
                binding.mawbListLayout.uldType.setText("");
                viewModel.toShowLayout(binding,5);
                viewModel.getMawbList(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getRegistryNumber());
            }
        });
    }

    public void hawbList(){
        binding.mawbListLayout.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMawbs = mawbs.get(i);

                binding.hawbListLayout.uldNo.setText(selectedUlds.getUldNo());
                binding.hawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
                binding.hawbListLayout.uldType.setText("");

                binding.hawbListLayout.mawbNo.setText(selectedMawbs.getMawbNumber());
                viewModel.toShowLayout(binding, 6);
                viewModel.getHawb(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getRegistryNumber(), selectedFlights.getMawbNumber());
            }
        });
    }

    public void hawbDetails() {
        binding.hawbListLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMawbs = mawbs.get(i);
                viewModel.toShowLayout(binding, 7);
                viewModel.populatedHawbDetails(binding, selectedMawbs);
            }
        });
    }

    public void uldImages() {
        binding.mawbListLayout.uldImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 4);
            }
        });

        binding.hawbListLayout.uldImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 4);
            }
        });
    }

    public void cargoImages() {
        binding.mawbDetails.cargoImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 8);
                binding.uldImagesLayout.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View mview = getLayoutInflater().inflate(R.layout.cargo_images_line, null);

                        ImageView picture = mview.findViewById(R.id.picture);

                        picture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                    }
                });
            }
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {

        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fname = System.currentTimeMillis() + ".png";
//        File f = new File(android.os.Environment.getExternalStorageDirectory() + "/DCIM/" + )
    }

    public void getFlights(List<FlightsModel> flights) {
        this.flights = flights;
    }

    public void getUlds(List<UldModel> ulds) {
        this.ulds = ulds;
    }

    public void getMawbs(List<MawbModel> mawbs) {
        this.mawbs = mawbs;
    }


    public FunctionInterface.Function doNothing = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {

        }
    };
}