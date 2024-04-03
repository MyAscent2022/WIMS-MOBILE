package com.example.wims_new.ui.storeCargo.storage.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wims_new.R;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityStorageCargoBinding;
import com.example.wims_new.model.CargoConditionModel;
import com.example.wims_new.model.CargoImagesModel;
import com.example.wims_new.model.FlightsModel;
import com.example.wims_new.model.MawbModel;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.SaveUldNumberModel;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.ui.mainMenu.MainMenu;
import com.example.wims_new.ui.receiveCargo.adapter.ImageListAdapter;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.menu.StoreCargoMenu;
import com.example.wims_new.ui.storeCargo.releasing.view.RackLocation;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.AddedImagesAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.CargoImagesAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.StorageCargoAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackDetailsModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.example.wims_new.ui.storeCargo.storage.view.viewModel.StorageCargoViewModel;
import com.example.wims_new.utils.FunctionInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StorageCargo extends AppCompatActivity {

    ActivityStorageCargoBinding binding;
    StorageCargoViewModel viewModel;
    List<StorageModel> storage,searchStorage;
    List<RackModel> racks;
    List<CargoImagesModel> images, image1;
    CargoImagesModel added_images;
    RackDetailsModel rackDetails;
    StorageModel selectedCargo;
    List<CargoConditionModel> conditionModel;
    int layout_id = 1;
    boolean is_pic1 = false, is_uploaded = false;
    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String cargoFname = "", select_b64 = "";
    String fnames = "";
    private Uri docUri;
    private byte[] bytes;
    List<Uri> uri;

    AlertDialog dialog = null;

    boolean is_adding = false;
    ImageView picture;
    String cargo_text = "";
    long cargoConditionId = 0;
    AutoCompleteTextView cargoCondition;
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
        viewModel.getStoreCargo(this, binding, this);

        conditionModel = new ArrayList<>();
        viewModel.getCargoConditionList(StorageCargo.this, StorageCargo.this);

        uri = new ArrayList<>();
//        fnames = new String[2];
        images = new ArrayList<>();
        image1 = new ArrayList<>();

    }

    private void eventHandler() {

        binding.mawbList.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchStorage = new ArrayList<>();
                if (newText.equals("") || TextUtils.isEmpty(newText)) {
                    searchStorage = storage;
                } else {
                    for (StorageModel s : storage) {
                        if ((s.getFlightNumber() != null && s.getFlightNumber().toUpperCase().contains(newText.toUpperCase(Locale.ROOT))) || (s.getMawbNumber() != null && s.getMawbNumber().toUpperCase().contains(newText.toUpperCase(Locale.ROOT)))
                        || (s.getHawbNumber() != null && s.getHawbNumber().toUpperCase().contains(newText.toUpperCase(Locale.ROOT))) || (s.getClassDesc() !=null && s.getClassDesc().toUpperCase().contains(newText.toUpperCase(Locale.ROOT)))) {
                            searchStorage.add(s);
                        }

                    }
                }
                viewData(StorageCargo.this, binding);
                return false;
            }
        });

        binding.mawbList.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getStoreCargo(StorageCargo.this, binding, StorageCargo.this);
                binding.mawbList.refreshLayout.setRefreshing(false);
            }
        });

        binding.headerLayout.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id--;
                toShowLayout();
            }
        });

        binding.mawbList.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCargo = storage.get(i);
                layout_id = 2;

                binding.mawbGallery.flightNo.setText(selectedCargo.getFlightNumber());
                binding.mawbGallery.mawbNo.setText(selectedCargo.getMawbNumber());
                binding.mawbGallery.hawbNo.setText(selectedCargo.getHawbNumber());
                binding.mawbGallery.flightClass.setText(selectedCargo.getClassDesc());
                binding.mawbGallery.pcs.setText(String.valueOf(selectedCargo.getActualPcs()));

                viewModel.getCargoImages(StorageCargo.this, StorageCargo.this, binding, selectedCargo.getId());
                System.out.println("IMAGES >>>>>>>>>>>>>>>>> " + images);
                toShowLayout();
            }
        });

        binding.cargoDetails.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id--;
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
                binding.cargoDetails.flightNo.setText(selectedCargo.getFlightNumber());
                binding.cargoDetails.mawbNo.setText(selectedCargo.getMawbNumber());
                binding.cargoDetails.hawbNo.setText(selectedCargo.getHawbNumber());
                binding.cargoDetails.totalWeight.setText("");
                binding.cargoDetails.cargoClass.setText(selectedCargo.getCargoStatus());
                binding.cargoDetails.storagePersonnel.setText("");
//                binding.cargoDetails.storedItemPcs.setText(selectedCargo.getActualPcs());
//                binding.cargoDetails.rcvPcs.setText(selectedCargo.getActualPcs());
                binding.cargoDetails.rackName.setText(selectedCargo.getRackName());
                binding.cargoDetails.layerName.setText(selectedCargo.getLayerName());

                layout_id = 4;


                viewModel.getRacks(StorageCargo.this, StorageCargo.this, binding);
                viewModel.getLayers(StorageCargo.this, StorageCargo.this, binding);
//                binding.cargoDetails.rackName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.getRacks(StorageCargo.this,this, binding)));
//                binding.cargoDetails.layerName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, viewModel.getLayers(StorageCargo.this,this, binding)));

                if (selectedCargo.getHawbNumber() == null) {
                    viewModel.getCargoDetails(StorageCargo.this, StorageCargo.this, binding, false, selectedCargo.getMawbNumber(), "");
                } else {
                    viewModel.getCargoDetails(StorageCargo.this, StorageCargo.this, binding, true, "", selectedCargo.getHawbNumber());
                }
                toShowLayout();
            }
        });

        binding.cargoImagesLayout.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.mawbGallery.uploadCargoImageLayout.setVisibility(View.GONE);
                binding.mawbGallery.viewCargoImageLayout.setVisibility(View.VISIBLE);
                binding.mawbGallery.listTitle.setVisibility(View.VISIBLE);
                binding.mawbGallery.listView1.setVisibility(View.VISIBLE);


                ImageListAdapter adapter = new ImageListAdapter(StorageCargo.this, R.layout.uploaded_image_line, uri);
                binding.mawbGallery.listView1.setAdapter(adapter);

                layout_id = 2;
                is_uploaded = true;
                toShowLayout();
            }
        });

        binding.mawbGallery.viewCargoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageListAdapter adapter = new ImageListAdapter(StorageCargo.this, R.layout.uploaded_image_line, uri);
                binding.cargoImageListLayout.listView.setAdapter(adapter);

                layout_id = 3;
                toShowLayout();
            }
        });

        binding.cargoDetails.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertsAndLoaders loaders = new AlertsAndLoaders();
                loaders.showAlert(4, "Are you sure?", "You want to update storage?", StorageCargo.this, saveCargoStorage);
            }
        });

        binding.cargoDetails.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_id = 2;
                toShowLayout();
            }
        });

        binding.mawbGallery.uploadCargoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddCargoImagesDialog();
//                viewModel.getCargoConditionList(StorageCargo.this, StorageCargo.this, binding);
//                viewModel.getCargoConditionList(StorageCargo.this, StorageCargo.this, binding);
//                layout_id = 5;
//                toShowLayout();
            }
        });

//        binding.cargoImagesLayout.picture1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                is_pic1 = true;
//                askCameraPermission();
//            }
//        });

//        binding.cargoImagesLayout.picture2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                is_pic1 = false;
//                askCameraPermission();
//            }
//        });


    }

    public FunctionInterface.Function saveStorage = new FunctionInterface.Function() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void perform() {
        }

    };

    private void askCameraPermission() {

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

//        added_images.setFileName(cargoFname);
//        images.add(added_images);

        File storageDir = getCacheDir();
        File image = File.createTempFile(cargoFname, ".png", storageDir);
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

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            bytes = stream.toByteArray();

            if (is_adding) {

                picture.setImageURI(imageUri);
                picture.setBackground(null);
                uri.add(imageUri);

            } else {
                docUri = imageUri;
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
            }


//                btn_save1.setEnabled(viewModel.enabled_save_btn_dialog(form_name_of_docs, form_category_exposed_dropdown, select_b64));

//            binding.cargoImagesLayout.picture1.setImageURI(imageUri);


        }
    }

    private void toShowLayout() {
        viewModel.toShowLayout(binding, layout_id);
    }

    public void getStorage(List<StorageModel> storage) {
        this.storage = storage;
    }

    public void getSearchStorage(List<StorageModel> searchStorage) {
        this.searchStorage = searchStorage;
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
            viewModel.saveRacks(StorageCargo.this, binding, uri,image1, StorageCargo.this,
                    binding.cargoDetails.rackName.getText().toString().trim(),
                    binding.cargoDetails.layerName.getText().toString().trim(),
                    selectedCargo.getRackUtilId(),
                    binding.cargoDetails.mawbNo.getText().toString().trim(),
                    binding.cargoDetails.hawbNo.getText().toString().trim(), 0,
                    binding.cargoDetails.flightNo.getText().toString().trim());
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


    private void showAddCargoImagesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StorageCargo.this);
        View mview = getLayoutInflater().inflate(R.layout.add_cargo_images_dialog, null);
        EditText remarks;
        CardView btn_cancel;
        LinearLayout add_image;
        picture = mview.findViewById(R.id.picture);
        cargoCondition = mview.findViewById(R.id.sCargo);
        remarks = mview.findViewById(R.id.remarks);
        btn_cancel = mview.findViewById(R.id.btn_cancel);
        add_image = mview.findViewById(R.id.add_image);

        added_images = new CargoImagesModel();
//        -- SET CARGO CONDITION IN DROP DOWN
        cargoCondition.setAdapter(new ArrayAdapter<>(StorageCargo.this, android.R.layout.simple_list_item_1, conditionList()));

//        cargoCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                // Get the selected cargo condition ID
//                cargoConditionId = id;
//                cargo_text = cargoCondition.getText().toString().trim();
//                // You can use this ID as needed
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // Handle situation where nothing is selected, if necessary
//            }
//        });

//        cargoCondition.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                cargo_text = cargoCondition.getText().toString().trim();
//            }
//        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_adding = true;
                askCameraPermission();

            }
        });


        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                added_images.setCargoCondition(cargoCondition.getText().toString().trim());
                added_images.setCargoConditionId(getConditionId());
                added_images.setRemarks(remarks.getText().toString());
                added_images.setImageUri(imageUri);
                images.add(added_images);
                image1.add(added_images);
                viewImg(StorageCargo.this, binding);
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void viewImg(Context context, ActivityStorageCargoBinding binding) {
        try {
            CargoImagesAdapter adapter1 = new CargoImagesAdapter(context, R.layout.store_cargo_images_line, images);
            binding.mawbGallery.listView.setAdapter(adapter1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getImages(List<CargoImagesModel> images){
        this.images = images;
    }
    public void getConditionList(List<CargoConditionModel> conditionModel){
        this.conditionModel = conditionModel;
    }

    private String[] conditionList(){
        String[] arr = new String[conditionModel.size()];
        int count = 0;
        System.out.println("CONDITION MODEL >>>>>> " + conditionModel.size());
        for(CargoConditionModel model : conditionModel){
            arr[count] = model.getCondition();
            count++;
        }
        return arr;
    }

    private int getConditionId(){
        for(CargoConditionModel model : conditionModel){
            if(cargoCondition.getText().toString().equals(model.getCondition())){
                return model.getId();
            }
        }
        return 0;
    }

    private void viewData(Activity activity, ActivityStorageCargoBinding binding) {
        try {
            StorageCargoAdapter adapter = new StorageCargoAdapter(activity, R.layout.mawb_line_layout, searchStorage);
            binding.mawbList.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}