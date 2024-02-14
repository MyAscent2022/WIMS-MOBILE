package com.example.wims_new.ui.receiveCargo.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wims_new.LocalDB.LocalDBHelper;
import com.example.wims_new.R;
import com.example.wims_new.common.FunctionsMethods;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityMainMenuBinding;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.model.CargoCategoryModel;
import com.example.wims_new.model.CargoConditionModel;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.model.HawbModel;
import com.example.wims_new.model.MawbDetails;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.model.SaveUldNumberModel;
import com.example.wims_new.model.UldContainerTypeModel;
import com.example.wims_new.model.UldImages;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.model.UldTypeModel;
import com.example.wims_new.model.UldTypesModel;
import com.example.wims_new.model.UploadImageModel;
import com.example.wims_new.ui.Login.viewModel.LoginViewModel;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.ui.receiveCargo.adapter.FlightListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.ImageListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.MawbDialogAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.MawbListAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.UldImagesAdapter;
import com.example.wims_new.ui.receiveCargo.adapter.UldListAdapter;
import com.example.wims_new.ui.receiveCargo.viewModel.ReceiveCargoViewModel;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.CargoImagesAdapter;
import com.example.wims_new.utils.FunctionInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReceiveCargo extends AppCompatActivity {

    private ActivityReceiveCargoBinding binding;
    private ReceiveCargoViewModel viewModel;
    private List<FlightsModel> flights, searchFlights;
    private FlightsModel selectedFlights;
    private List<UldModel> ulds, searchUlds;
    private UldModel selectedUlds;
    private List<MawbModel> mawbs;
    private CargoConditionModel selectedCondition;
    private List<CargoConditionModel> conditionList;
    private List<HawbModel> hawbs;
    private String[] uld_arr_id;
    private List<MawbModel> mawbList;
    private List<HawbModel> hawbList;
    private List<UploadImageModel> images;

    private List<UldTypesModel> uldList;
    private List<UldContainerTypeModel> containerList;
    private MawbModel selectedMawbs;
    private HawbModel selectedHawbs;
    private int layout_id = 1;
    private String fname = "";
    Uri u;
    private Uri imageUri;
    private Uri docUri;
    final Context context = this;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] bytes;
    private Bitmap bitmap, bio;
    private static String FOLDER_NAME = "WIMS_IMAGES", select_b64 = "", cargoFname = "";

    boolean is_pic1 = false;
    private LoginViewModel loginViewModel;

    boolean is_uld = false, is_uploaded = false;

    List<Uri> uri;
    List<UldImages> uldImages;
    String[] fnames;
    AlertDialog dialog = null;
    SaveUldNumberModel saveUldNumberModel, updateUldNumberModel;

    String uld_no_txt ="";
    String uld_type_txt ="";
    boolean has_hawb =false;
    ListView listView;
    AutoCompleteTextView uld_type;
    AutoCompleteTextView update_uld_type;
//    Spinner uld_type, update_uld_type;
    String uld_type1 = "", update_uld_type1 = "";

    LocalDBHelper db;

    MawbDetails mDetailsModel;

    String[] status = {"Normal", "As is"};

    int uld_id = 0;
    boolean is_uld_image = false;
    private MainMenu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_receive_cargo);
        init();
        eventListener();

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (layout_id == 1){
            super.onBackPressed();
//            Intent in = new Intent(ReceiveCargo.this, MainMenu.class);
//            startActivity(in);
        }else {
            layout_id--;
            toShowLayout();
        }
    }




//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        layout_id--;
//        toShowLayout();
//    }

    private void init() {
        binding = ActivityReceiveCargoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = new LocalDBHelper(this);
        images = new ArrayList<>();

        viewModel = new ReceiveCargoViewModel();
        toShowLayout();
        viewModel.landedFlights(context, this, binding);

        loginViewModel = new LoginViewModel();

        uri = new ArrayList<>();
        fnames = new String[2];

        binding.mawbDetails.cargoCategory.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, viewModel.getCargoCategory(context,this,binding)));
        binding.mawbDetails.cargoClass.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, viewModel.getCargoClass(context,this,binding)));
//        binding.mawbDetails.cargoStatus.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, viewModel.getCargoStatus(context,this,binding)));
        binding.mawbDetails.cargoStatus.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,status));


    }

//    public void getStatus (ArrayList<String> status) {
//        this.status = status;
//    }

    public void eventListener() {

        binding.headerLayout.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id--;
                toShowLayout();
            }
        });

        binding.uldLayout.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUlds = new ArrayList<>();
                if (newText.equals("") || TextUtils.isEmpty(newText)) {
                    searchUlds = ulds;
                } else {
                    for (UldModel u : ulds) {

                        if ((u.getUldNumber() != null && u.getUldNumber().toUpperCase().contains(newText.toUpperCase(Locale.ROOT))) || (u.getType() != null && u.getType().toUpperCase().contains(newText.toUpperCase(Locale.ROOT)))) {
                            searchUlds.add(u);
                        }

                    }
                }
                viewData2(ReceiveCargo.this, binding);
                return false;
            }
        });

        binding.flightListLayout.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //viewData(newText);

                searchFlights = new ArrayList<>();
                if (newText.equals("") || TextUtils.isEmpty(newText)) {
                    searchFlights = flights;
                } else {
                    for (FlightsModel f : flights) {

                        if ((f.getFlightNumber() != null && f.getFlightNumber().toUpperCase().contains(newText.toUpperCase(Locale.ROOT))) || (f.getTravelStatus() != null && f.getTravelStatus().toUpperCase().contains(newText.toUpperCase(Locale.ROOT)))) {
                            searchFlights.add(f);
                        }

                    }
                }
                viewData(ReceiveCargo.this, binding);
                return false;
            }
        });

        binding.uldLayout.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ulds != null && ulds.size() > 0) {
                    viewModel.uldPerFlight(context, ReceiveCargo.this, binding, selectedFlights.getFlightNumber());
                } else {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(1, "", "No Data Found", context, null);

                }
                binding.uldLayout.refreshLayout.setRefreshing(false);
            }
        });

        binding.flightListLayout.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (flights.size() > 0){
                    viewModel.landedFlights(context, ReceiveCargo.this, binding);
                } else {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(1, "", "No Data Found", context, null);
                    binding.flightListLayout.refreshLayout.setRefreshing(false);
                }
            }
        });

        binding.flightListLayout.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layout_id = 2;
//                selectedFlights = flights.get(i);
                selectedFlights = searchFlights.get(i);
                toShowLayout();
                viewModel.populateFlightDetails(binding, selectedFlights);
            }
        });

        binding.headerLayout.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                layout_id--;
//                toShowLayout();

                if (layout_id == 1){
                    Intent in = new Intent(ReceiveCargo.this, MainMenu.class);
                    startActivity(in);
                }else {
                    layout_id --;
                    toShowLayout();
                }

            }
        });

        binding.flightDetailsLayout.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id--;
                toShowLayout();
            }
        });

        binding.flightDetailsLayout.enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//
                viewModel.uldPerFlight(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getFlightNumber());
                binding.uldLayout.flightNumber.setText(selectedFlights.getFlightNumber());
//                binding.uldLayout.noOfUld.setText();
                layout_id = 3;
                toShowLayout();

//               selectedUlds = ulds.get(i);
//                binding.mawbListLayout.uldNo.setText(selectedUlds.getUldNo());
//                binding.mawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
//                binding.mawbListLayout.uldType.setText("");
//
//                System.out.println("ULD NUMBERRRRRR >>>>>>>>>>" + selectedUlds.getUldNo());
//
//                toShowLayout();
//                viewModel.getMawbList(ReceiveCargo.this, ReceiveCargo.this, binding, selectedUlds.getUldNo());

            }
        });

        binding.uldLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                layout_id = 4;
//                selectedUlds = ulds.get(i);
                selectedUlds = searchUlds.get(i);
                binding.mawbListLayout.uldNo.setText(selectedUlds.getUldNumber());
                binding.mawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
                binding.mawbListLayout.uldType.setText(selectedUlds.getType());


                toShowLayout();
                viewModel.getMawbList(ReceiveCargo.this, ReceiveCargo.this, binding, selectedUlds.getUldNumber(),true,selectedFlights.getFlightNumber(), false);
            }
        });

        binding.mawbListLayout.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMawbs = mawbs.get(i);

                binding.hawbListLayout.uldNo.setText(selectedUlds.getUldNumber());
                binding.hawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
                binding.hawbListLayout.uldType.setText(selectedUlds.getType());

                binding.hawbListLayout.mawbNo.setText(selectedMawbs.getMawbNumber());

                viewModel.getHawb(ReceiveCargo.this, ReceiveCargo.this, binding, selectedMawbs.getMawbNumber());
            }
        });

        binding.hawbListLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layout_id = 6;
                has_hawb = true;
                selectedHawbs = hawbs.get(i);
                toShowLayout();
                viewModel.getCargoCategory(ReceiveCargo.this, ReceiveCargo.this, binding);
                viewModel.getCargoClass(ReceiveCargo.this, ReceiveCargo.this, binding);
//                viewModel.getCargoStatus(ReceiveCargo.this, ReceiveCargo.this, binding);
//                viewModel.getCargoConditionList(ReceiveCargo.this, ReceiveCargo.this, binding);
                viewModel.populatedHawbDetails(binding, selectedMawbs, selectedHawbs);
            }
        });

        binding.mawbListLayout.uldImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_uld = true;
                is_uld_image = true;
//                layout_id = 10;
                layout_id = 7;
                binding.cargoImagesLayout.uldNumber.setText(selectedUlds.getUldNumber());

//                viewModel.getUldImages(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getFlightNumber(), selectedUlds.getUldNumber());
                viewModel.getCargoConditionList(ReceiveCargo.this, ReceiveCargo.this, binding);
                toShowLayout();
            }
        });

        binding.hawbListLayout.uldImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                is_uld = true;
                is_uld_image = true;
                layout_id = 7;
                binding.cargoImagesLayout.uldNumber.setText(selectedUlds.getUldNumber());
                viewModel.getCargoConditionList(ReceiveCargo.this, ReceiveCargo.this, binding);
                toShowLayout();
            }
        });


        binding.mawbDetails.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id--;
                toShowLayout();
            }
        });

        binding.mawbDetails.uploadCargoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_uld_image = false;
                is_uld = false;
                db = new LocalDBHelper(context);
                viewModel.getCargoConditionList(ReceiveCargo.this, ReceiveCargo.this, binding);

                db.deleteMawbDetails();
                if(has_hawb){

                    db.insertMawbDetails(binding.mawbDetails.actualPcs.getText().toString().trim(),
                            binding.mawbDetails.weight.getText().toString().trim(),
                            binding.mawbDetails.volume.getText().toString().trim(),
                            binding.mawbDetails.length.getText().toString().trim(),
                            binding.mawbDetails.width.getText().toString().trim(),
                            binding.mawbDetails.height.getText().toString().trim(),
                            binding.mawbDetails.cargoCategory.getText().toString().trim(),
                            binding.mawbDetails.cargoClass.getText().toString().trim(),
                            binding.mawbDetails.cargoStatus.getText().toString().trim(),
                            selectedMawbs.getMawbNumber(),
                            selectedHawbs.getHawbNumber(),
                            selectedFlights.getFlightNumber());

                }else{
                    db.insertMawbDetails(binding.mawbDetails.actualPcs.getText().toString().trim(),
                            binding.mawbDetails.weight.getText().toString().trim(),
                            binding.mawbDetails.volume.getText().toString().trim(),
                            binding.mawbDetails.length.getText().toString().trim(),
                            binding.mawbDetails.width.getText().toString().trim(),
                            binding.mawbDetails.height.getText().toString().trim(),
                            binding.mawbDetails.cargoCategory.getText().toString().trim(),
                            binding.mawbDetails.cargoClass.getText().toString().trim(),
                            binding.mawbDetails.cargoStatus.getText().toString().trim(),
                            selectedMawbs.getMawbNumber(),
                            "",
                            selectedFlights.getFlightNumber());
                }

                layout_id = 7;
                toShowLayout();
            }
        });

        binding.uldImagesLayout.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });


        binding.cargoImagesLayout.picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_pic1 = true;
                askCameraPermission();
            }
        });


        binding.cargoImagesLayout.picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_pic1 = false;
                askCameraPermission();
            }
        });

        binding.cargoImagesLayout.deleteImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteImages(f1);
            }
        });

        binding.cargoImagesLayout.deleteImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                askCameraPermission();
            }
        });

        binding.cargoImagesLayout.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_uld_image) {
                    layout_id = 4;
                    toShowLayout();
                } else {
                    layout_id--;
                    toShowLayout();
                }
            }
        });


        binding.cargoImagesLayout.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_uld_image) {
                    is_uld = true;
                    AlertsAndLoaders loaders = new AlertsAndLoaders();
                    loaders.showAlert(4,"Are you sure?", "You want to upload this ULD?", ReceiveCargo.this,uploadCargo);
                } else {
                    is_uld = false;
                    binding.mawbDetails.uploadCargoImageLayout.setVisibility(View.GONE);
                    binding.mawbDetails.viewCargoImageLayout.setVisibility(View.VISIBLE);
                    binding.mawbDetails.listTitle.setVisibility(View.VISIBLE);
                    binding.mawbDetails.listView.setVisibility(View.VISIBLE);

                    ImageListAdapter adapter = new ImageListAdapter(ReceiveCargo.this, R.layout.uploaded_image_line, uri);
                    binding.mawbDetails.listView.setAdapter(adapter);

                    mDetailsModel = new MawbDetails();

                    System.out.println("=======================LAMAN NG db======================");
                    System.out.println(db.getMawbDetails());
                    System.out.println(db.getMawbDetails().getCargoStatus());
                    mDetailsModel = db.getMawbDetails();

                    binding.mawbDetails.actualPcs.setText(String.valueOf(mDetailsModel.getActualPcs()));
                    binding.mawbDetails.weight.setText(String.valueOf(mDetailsModel.getWeight()));
                    binding.mawbDetails.volume.setText(String.valueOf(mDetailsModel.getVolume()));
                    binding.mawbDetails.length.setText(String.valueOf(mDetailsModel.getLength()));
                    binding.mawbDetails.width.setText(String.valueOf(mDetailsModel.getWidth()));
                    binding.mawbDetails.height.setText(String.valueOf(mDetailsModel.getHeight()));
                    binding.mawbDetails.cargoCategory.setText(mDetailsModel.getCargoCategory());
                    binding.mawbDetails.cargoClass.setText(mDetailsModel.getCargoClass());
                    binding.mawbDetails.cargoStatus.setText(mDetailsModel.getCargoStatus());

                    layout_id = 6;
                    is_uploaded = true;
                    toShowLayout();
                }

            }
        });

        binding.mawbDetails.viewCargoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageListAdapter adapter = new ImageListAdapter(ReceiveCargo.this, R.layout.uploaded_image_line, uri);
                binding.cargoImageListLayout.listView.setAdapter(adapter);

                layout_id = 8;
                toShowLayout();
            }
        });


        binding.mawbDetails.confirmCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null || uri.isEmpty()) {
                    // Show an alert or handle the case where no picture is uploaded
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", "Please upload at least one picture", ReceiveCargo.this, null);
                    return;
                } else {
                    AlertsAndLoaders loaders = new AlertsAndLoaders();
                    loaders.showAlert(4,"Are you sure?", "You want to confirm this cargo?", ReceiveCargo.this,saveCargo);
                }

                //viewModel.to_upload(uri, fnames, context, binding);
            }
        });

        binding.uldLayout.addUld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUldDialog();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                viewModel.getContainerTypes(ReceiveCargo.this, ReceiveCargo.this, binding);
            }
        });

        binding.mawbListLayout.editUldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditUldDialog();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                viewModel.getContainerTypes(ReceiveCargo.this, ReceiveCargo.this, binding);

            }
        });

        binding.hawbListLayout.editUldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditUldDialog();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                viewModel.getContainerTypes(ReceiveCargo.this, ReceiveCargo.this, binding);
            }
        });



    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            askCameraPermission1();
        }
    }

    private void askCameraPermission1() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            // Create a file to save the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
// Handle error
            }
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(this,
                        this.getApplicationContext().getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT).format(new Date());

        String imageFileName = timeStamp;
        cargoFname = imageFileName;
        if (is_pic1) {
            fnames[0] = cargoFname;
        } else {
            fnames[1] = cargoFname;
        }


        File storageDir = getCacheDir();
        File image = File.createTempFile(imageFileName, ".png", storageDir);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            int newWidth = 700; // Replace with your desired width
            int newHeight = 1000;
            Bitmap imageBitmap = null;

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            docUri = imageUri;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            bytes = stream.toByteArray();
            if (is_pic1) {
                uri.add(docUri);
                binding.cargoImagesLayout.picture1.setImageURI(docUri);
                binding.cargoImagesLayout.picture1.setBackground(null);


            } else {
                uri.add(docUri);
                binding.cargoImagesLayout.picture2.setImageURI(docUri);
                binding.cargoImagesLayout.picture2.setBackground(null);
            }

            select_b64 = Base64.encodeToString(bytes, Base64.DEFAULT);
//                btn_save1.setEnabled(viewModel.enabled_save_btn_dialog(form_name_of_docs, form_category_exposed_dropdown, select_b64));


        }
    }

    private void deleteImages(File file) {
        String[] projection = {MediaStore.Images.Media._ID};
        String sortOrder = MediaStore.Images.Media._ID + " DESC";
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            cursor.close();
            // Delete the image using the uri

            int deletedRows = getContentResolver().delete(uri, null, null);
            if (deletedRows > 0) {
                System.out.println("DELETED Image>>>>");
                file.delete();
            }
        }
    }

    public void getFlights(List<FlightsModel> flights) {
        this.flights = flights;
    }

    public void getSearchFlights(List<FlightsModel> searchFlights) {
        this.searchFlights = searchFlights;
    }

    public void getUlds(List<UldModel> ulds) {
        this.ulds = ulds;
    }

    public void getSearchUlds(List<UldModel> searchUlds) {
        this.searchUlds = searchUlds;
    }

    public void getMawbs(List<MawbModel> mawbs) {
        this.mawbs = mawbs;
    }

    public void getHawbs(List<HawbModel> hawbs) {
        this.hawbs = hawbs;
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
//            Bundle bundle = new Bundle();
            layout_id = 4;
            toShowLayout();
        }
    };

    public FunctionInterface.Function backToMain = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
//            Bundle bundle = new Bundle();
            Intent in = new Intent(ReceiveCargo.this, MainMenu.class);
            startActivity(in);
        }
    };

    private void toShowLayout() {
        viewModel.toShowLayout(binding, layout_id, is_uld, is_uploaded);
    }



//    -- ADD MAWB LIST + SAVING OF ULD
    private void showAddUldDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveCargo.this);
        View mview = getLayoutInflater().inflate(R.layout.add_uld_details_dialog, null);
        TextInputEditText uld_no;
        CardView btn_cancel;
        LinearLayout save_uld;
        uld_no = mview.findViewById(R.id.uld_no);
        uld_type = mview.findViewById(R.id.uld_type);
        listView = mview.findViewById(R.id.listView);
        btn_cancel = mview.findViewById(R.id.btn_cancel);
        save_uld = mview.findViewById(R.id.save_uld);

        UldModel model = new UldModel();
        saveUldNumberModel = new SaveUldNumberModel();
        ulds = new ArrayList<>();
        mawbList = new ArrayList<>();

//        -- SET ULD TYPES IN DROP DOWN
        uld_type.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, viewModel.getContainerTypes(context,this,binding)));
        uld_type.setThreshold(1);
//        -- SET LIST OF MAWBS
        viewModel.getMawbList(context,this,binding,"",false,selectedFlights.getFlightNumber(), true);


        uld_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                uld_type1 = uld_type.getText().toString().trim();
            }
        });
        uld_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                uld_type1 = uld_type.getSelectedItem().toString().trim();
//                uld_type1 = uld_type.getText().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_uld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MawbModel> mawbs = mawbList;
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();

                uld_no_txt = uld_no.getText().toString().trim();
                System.out.println("uld_no_txt " + uld_no_txt);

                if (uld_no.getText().toString() == "" || uld_no.getText().toString().equals("")){
                    alertsAndLoaders.showAlert(2, "", "Please enter ULD NUMBER", ReceiveCargo.this, null);
                }else if (uld_type.getText().toString().trim() == "" || uld_type.getText().toString().equals("")){
                    alertsAndLoaders.showAlert(2, "", "Please select ULD TYPE", ReceiveCargo.this, null);
                }else if (getCheckedMawbList().size() < 0){
                    alertsAndLoaders.showAlert(2,"","Please select MAWB NUMBER",ReceiveCargo.this, null);
                }else {
//                    model.setUldNo(uld_no_txt);
                    saveUldNumberModel.setUldNumber(uld_no_txt);
                    model.setFlightNumber(selectedFlights.getFlightNumber());
//                    model.setUldStatus(3);
                    model.setUldTypeId(getUldContainers());
                    saveUldNumberModel.setMawbs(mawb_arr());
                    saveUldNumberModel.setUlds(model);

                    alertsAndLoaders.showAlert(4, "Are you sure?", "You want to add this ULD number?", ReceiveCargo.this, saveULD);
                    dialog.cancel();
                }


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        builder.setView(mview);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void showEditUldDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveCargo.this);
        View mview = getLayoutInflater().inflate(R.layout.edit_uld_details_dialog, null);
        TextInputEditText update_uld_no;
        CardView btn_cancel;
        LinearLayout save_uld;
        update_uld_no = mview.findViewById(R.id.update_uld_no1);
        update_uld_type = mview.findViewById(R.id.update_uld_type);
        btn_cancel = mview.findViewById(R.id.btn_cancel);
        save_uld = mview.findViewById(R.id.save_uld);

        UldModel model = new UldModel();
        updateUldNumberModel = new SaveUldNumberModel();
        ulds = new ArrayList<>();


        update_uld_type.setText(selectedUlds.getType());
        update_uld_no.setText(selectedUlds.getUldNumber());

        update_uld_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.getContainerTypes(context,this,binding)));
        update_uld_type.setDropDownHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        update_uld_type.setThreshold(1);

        update_uld_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update_uld_type1 = update_uld_type.getText().toString().trim();
            }
        });

//        update_uld_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                update_uld_type1 = update_uld_type.getText().toString().trim();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        save_uld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();

                if (update_uld_no.getText().toString() == null || update_uld_no.getText().toString().equals(null)){
                    alertsAndLoaders.showAlert(2, "", "Please enter ULD NUMBER", ReceiveCargo.this, null);
                }else if (update_uld_type.getText().toString().trim() == null || update_uld_type.getText().toString().equals(null)){
                    alertsAndLoaders.showAlert(2, "", "Please select ULD TYPE", ReceiveCargo.this, null);
                }else {
                    updateUldNumberModel.getUlds().setUldNumber(update_uld_no.getText().toString().trim());
                }
                model.setUldNumber(update_uld_no.getText().toString());
                model.setUldTypeId(getUldContainers());
                updateUldNumberModel.setUlds(model);
                alertsAndLoaders.showAlert(4, "Are you sure?", "You want to update this ULD number?", ReceiveCargo.this, updateULD);
                dialog.cancel();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        builder.setView(mview);
        dialog = builder.create();
        dialog.show();
    }

    FunctionInterface.Function saveULD = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            viewModel.saveUldNumber(context,binding,ReceiveCargo.this,saveUldNumberModel);
        }

    };

    FunctionInterface.Function updateULD = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            viewModel.updateUldNumber(context,binding,ReceiveCargo.this,updateUldNumberModel, uld_no_txt);
        }

    };

    public FunctionInterface.Function goToUldList = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
//            layout_id = 3;
            layout_id = 2;
            toShowLayout();
        }

    };

    public FunctionInterface.Function saveCargo = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {

            if(has_hawb){
                viewModel.insertMawbDetails(ReceiveCargo.this, ReceiveCargo.this, binding, uri, fnames, selectedMawbs.getMawbNumber(), db.getMawbDetails().getFlight_number(), selectedHawbs.getHawbNumber(), selectedHawbs.getId());
            }else{
                viewModel.insertMawbDetails(ReceiveCargo.this, ReceiveCargo.this, binding, uri, fnames, selectedMawbs.getMawbNumber(), db.getMawbDetails().getFlight_number(), "", 0);

            }

//            viewModel.to_upload(uri, fnames, context, binding);


        }

    };

    public FunctionInterface.Function uploadCargo = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
            is_uld_image = false;
            viewModel.saveUldImage(ReceiveCargo.this, ReceiveCargo.this, binding, uri, selectedFlights.getFlightNumber(), selectedUlds.getUldNumber());
        }

    };


    public void getMawb(List<MawbModel> mawbs){
        this.mawbList = mawbs;
        MawbDialogAdapter adapter = new MawbDialogAdapter(this, R.layout.select_mawb_list_line, mawbList);
        listView.setAdapter(adapter);
    }

    public void setUriToNull() {
        uri = new ArrayList<>();
    }

    public void getUldTypes(List<UldTypesModel> types){
        this.uldList = types;
//        MawbListAdapter adapter = new MawbListAdapter(this, R.layout.select_mawb_list_line, mawbList);
//        listView.setAdapter(adapter);
    }

    public void getUldContainerTypes(List<UldContainerTypeModel> con) {
        this.containerList = con;
    }

    public int getUldContainers() {
        for(UldContainerTypeModel c : containerList) {
            if (c.getType().equals(uld_type1) || c.getType().equals(update_uld_type1)) {
                return c.getId();
            }
        }
        return 0;
    }

//    public int getUldTypeId(){
//        for(UldTypesModel m : uldList){
//            if(m.getType().equals(uld_type.getText().toString().trim()) || m.getType().equals(update_uld_type.getText().toString().trim())){
//                return m.getId();
//            }
//        }
//        return 0;
//    }

    private List<MawbModel> getCheckedMawbList() {
        List<MawbModel> list = new ArrayList<>();
        for (MawbModel a : mawbList) {
                list.add(a);

        }
        return list;
    }

    public String[] mawb_arr(){
        int count = 0;
        for(MawbModel m: mawbList){
            if (m.isSelected()){
                count++;
            }
        }
        String[] arr = new String[count];
        count = 0;
        for(MawbModel m: mawbList){
            if (m.isSelected()){
                arr[count] = m.getMawbNumber();
                count++;
            }
        }
        return arr;
    }

    class Task_saveImages extends AsyncTask<String, Integer, String> implements Runnable {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... string) {
            viewModel.to_upload(uri,fnames,context,binding);
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        public void run() {

        }
    }

    public void viewData(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            FlightListAdapter adapter = new FlightListAdapter(activity, R.layout.card_flight_line, searchFlights);
            binding.flightListLayout.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewData2(Activity activity, ActivityReceiveCargoBinding binding) {
        try {
            UldListAdapter adapter = new UldListAdapter(activity, R.layout.uld_line_layout, searchUlds);
            binding.uldLayout.listLayout.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}