package com.example.wims_new.ui.receiveCargo.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    Uri u;
    private Uri imageUri;
    private Uri docUri;

    private byte[] bytes;
    private Bitmap bitmap, bio;
    private static String FOLDER_NAME = "WIMS_IMAGES", select_b64 = "", docFname = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    boolean is_pic1 = false;

    private final String[] cargo_condition = {"MALE", "FEMALE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_receive_cargo);
        init();
        eventListener();

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
        binding.headerLayout.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 0);
            }
        });

        binding.flightDetailsLayout.enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.uldLayout.flightNumber.setText(selectedFlights.getFlightNumber());

                viewModel.toShowLayout(binding, 3);
                viewModel.uldPerFlight(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getFlightNumber());
            }
        });

        binding.uldLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUlds = ulds.get(i);
                binding.mawbListLayout.uldNo.setText(selectedUlds.getUldNo());
                binding.mawbListLayout.flightNo.setText(selectedUlds.getFlightNumber());
                binding.mawbListLayout.uldType.setText("");
                viewModel.toShowLayout(binding, 5);
                viewModel.getMawbList(ReceiveCargo.this, ReceiveCargo.this, binding, selectedFlights.getRegistryNumber());
            }
        });

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

        binding.hawbListLayout.listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMawbs = mawbs.get(i);
                viewModel.toShowLayout(binding, 7);
                viewModel.getCargoConditionList(ReceiveCargo.this, ReceiveCargo.this, binding);
                viewModel.populatedHawbDetails(binding, selectedMawbs);
            }
        });

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


        binding.mawbDetails.cargoImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toShowLayout(binding, 8);
            }
        });

        binding.uldImagesLayout.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add list view of another image line
            }
        });


        binding.cargoImagesLayout.picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_pic1 =true;
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
        docFname = imageFileName;

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
                if(is_pic1){
                    binding.cargoImagesLayout.picture1.setImageURI(docUri);
                }else{
                    binding.cargoImagesLayout.picture2.setImageURI(docUri);
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