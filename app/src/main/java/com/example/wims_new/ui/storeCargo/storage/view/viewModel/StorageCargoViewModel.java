package com.example.wims_new.ui.storeCargo.storage.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wims_new.BuildConfig;
import com.example.wims_new.LocalDB.LocalDBHelper;
import com.example.wims_new.R;
import com.example.wims_new.apiCall.ApiCall;
import com.example.wims_new.apiCall.ServiceGenerator;
import com.example.wims_new.common.functionsMethods.AlertsAndLoaders;
import com.example.wims_new.databinding.ActivityReceiveCargoBinding;
import com.example.wims_new.databinding.ActivityStorageCargoBinding;
import com.example.wims_new.model.CargoActLogsModel;
import com.example.wims_new.model.CargoConditionModel;
import com.example.wims_new.model.CargoImagesModel;
import com.example.wims_new.model.ImagesResponse;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.UldResponse;
import com.example.wims_new.ui.receiveCargo.view.ReceiveCargo;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoModel;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.CargoImagesAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Adapter.StorageCargoAdapter;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackDetailsModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackResponse;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageModel;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageResponse;
import com.example.wims_new.ui.storeCargo.storage.view.StorageCargo;
import com.example.wims_new.utils.RotateImage;
import com.example.wims_new.utils.SharedPref;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorageCargoViewModel {
    private SweetAlertDialog dialog;
    StorageResponse resp;
    List<StorageModel> storage;
    List<CargoImagesModel> images;
    List<ReleaseCargoModel> releasingCargo;
    RackResponse rackResp;
    ImagesResponse imgResp;
    List<RackModel> refRacks;
    RackDetailsModel rackDetails;
    private MawbResponse mawbResp;
    private List<CargoConditionModel> condition;
    LocalDBHelper db;

    public void getStoreCargo(StorageCargo activity, ActivityStorageCargoBinding binding, Context context) {
        System.out.println("==================================getStoreCargo======================================");
        resp = new StorageResponse();
        dialog = new SweetAlertDialog(context);
        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = services.getStoreCargo();
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {
                dialog.cancel();

                try {
                    storage = new ArrayList<>();
                    resp = response.body();
                    System.out.println("oy response code--------------------> " + response.code());
                    if (resp.isStatus()) {
                        if (resp.getStatusCode() == 200) {
                            storage = resp.getData().getStorages();
//                        int layout_id = 1;
//                        toShowLayout(binding, layout_id);
                            viewData(activity, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                        }
                        activity.getStorage(storage);
                    } else {
                        alertsAndLoaders.showAlert(6, "", "No Data Found", context, activity.backToMenu);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("getStoreCargo error message: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });

    }

    public void getRacks(Context context, StorageCargo activity, ActivityStorageCargoBinding binding) {
        rackResp = new RackResponse();
        ArrayList<String> rack_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<RackResponse> call = services.getRacks();
        call.enqueue(new Callback<RackResponse>() {
            @Override
            public void onResponse(Call<RackResponse> call, Response<RackResponse> response) {
//                dialog.cancel();

                try {
                    refRacks = new ArrayList<>();
                    String[] ar;
                    if (response.code() == 200) {
                        rackResp = response.body();
                        if (rackResp.getStatusCode() == 200) {
                            refRacks = rackResp.getData().getRefRacks();
                            ar = new String[refRacks.size()];
                            for (int i = 0; i < refRacks.size(); i++) {
                                rack_arr.add(refRacks.get(i).getRackName());
                                ar[i] = refRacks.get(i).getRackName();
                            }

                            binding.cargoDetails.rackName.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ar));
                        } else {
                            alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
                        alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RackResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getLayers(Context context, StorageCargo activity, ActivityStorageCargoBinding binding) {
        rackResp = new RackResponse();
        ArrayList<String> layer_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<RackResponse> call = services.getRacks();
        call.enqueue(new Callback<RackResponse>() {
            @Override
            public void onResponse(Call<RackResponse> call, Response<RackResponse> response) {

                try {
                    refRacks = new ArrayList<>();
                    String[] ar;
                    if (response.code() == 200) {
                        rackResp = response.body();
                        if (rackResp.getStatusCode() == 200) {
                            ar = new String[rackResp.getData().getRefRacks().size()];
                            refRacks = rackResp.getData().getRefRacks();
                            for (int i = 0; i < refRacks.size(); i++) {
                                layer_arr.add(refRacks.get(i).getLayerName());
                                ar[i] = refRacks.get(i).getLayerName();
                            }
                            binding.cargoDetails.layerName.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, ar));

                        } else {
                            alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
//                        DISPLAY ERROR HERE.....
                        alertsAndLoaders.showAlert(1, "", rackResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RackResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    public void getCargoDetails(Context context, StorageCargo activity, ActivityStorageCargoBinding binding, boolean is_hawb, String mawb_number, String hawb_number) {
        resp = new StorageResponse();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        dialog = alertsAndLoaders.showAlert(3, "", "", context, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = services.getRackDetails(is_hawb, hawb_number, mawb_number);
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {
                dialog.cancel();

                try {
                    rackDetails = new RackDetailsModel();
                    resp = response.body();
                    if (response.code() == 200) {
                        rackDetails = resp.getData().getRackDetails();
                        binding.cargoDetails.flightNo.setText(rackDetails.getFlightNumber());
                        binding.cargoDetails.mawbNo.setText(rackDetails.getMawbNumber());
                        binding.cargoDetails.hawbNo.setText(rackDetails.getHawbNumber());
                        binding.cargoDetails.totalWeight.setText(String.valueOf(rackDetails.getWeight()));
                        binding.cargoDetails.cargoClass.setText(rackDetails.getClassdesc());
                        binding.cargoDetails.rcvPcs.setText(String.valueOf(rackDetails.getActualPcs()));

                        binding.cargoDetails.storedItemPcs.setText(String.valueOf(rackDetails.getActualPcs()));
                        binding.cargoDetails.rackName.setText(rackDetails.getRackName());
                        binding.cargoDetails.layerName.setText(rackDetails.getLayerName());

                    } else {
                        alertsAndLoaders.showAlert(1, "", resp.getMessage(), context, activity.doNothing);
                    }
                    activity.getRackDetails(rackDetails);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                dialog.cancel();
                Log.e("Error:", t.getMessage());
            }
        });
    }


    public ArrayList<String> getCargoConditionList(Context context, StorageCargo activity) {
        mawbResp = new MawbResponse();
        ArrayList<String> cargo_condition_arr = new ArrayList<String>();
        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<MawbResponse> call = services.getCargoCondition();
        call.enqueue(new Callback<MawbResponse>() {
            @Override
            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
                dialog.cancel();

                try {
                    condition = new ArrayList<>();
                    if (response.code() == 200) {
                        mawbResp = response.body();
                        if (mawbResp.getStatusCode() == 200) {
                            condition = mawbResp.getCondition();
                            for (int i = 0; i < condition.size(); i++) {
                                cargo_condition_arr.add(condition.get(i).getCondition());
                            }
                        } else {
                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
                        alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MawbResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return cargo_condition_arr;
    }


//    public void getCargoConditionList(Context context, StorageCargo activity, ActivityStorageCargoBinding binding) {
//        mawbResp = new MawbResponse();
//
//        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
////        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", context, activity, null);
//
//        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
//        Call<MawbResponse> call = services.getCargoCondition();
//        call.enqueue(new Callback<MawbResponse>() {
//            @Override
//            public void onResponse(Call<MawbResponse> call, Response<MawbResponse> response) {
//                dialog.cancel();
//
//                try {
//                    condition = new ArrayList<>();
//                    if (response.code() == 200) {
//                        mawbResp = response.body();
//                        if (mawbResp.getStatusCode() == 200) {
//                            condition = mawbResp.getCondition();
//
//                            binding.cargoImagesLayout.spinner1.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cargoCondition(condition)));
//                            binding.cargoImagesLayout.spinner2.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cargoCondition(condition)));
//
//
//
//                        } else {
//                            alertsAndLoaders.showAlert(1, "", mawbResp.getMessage(), context, activity.doNothing);
//                        }
//                    } else {
////                        DISPLAY ERROR HERE.....
//                    }
////                    activity.getMawbs(mawbs);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MawbResponse> call, Throwable t) {
//                Log.e("Error: ", t.getMessage());
//            }
//        });
//    }

    private String[] cargoCondition(List<CargoConditionModel> condition) {
        String[] ar = new String[condition.size()];
        for (int i = 0; i < condition.size(); i++) {
            ar[i] = condition.get(i).getCondition();
        }
        return ar;
    }

    public void saveRacks(Context context, ActivityStorageCargoBinding binding, List<Uri> uri, StorageCargo activity, String rack_name, String layer_name, int rack_util_id, String mawb_number, String hawb_number, int hawb_id, String flight_number) {
        CargoActLogsModel response = new CargoActLogsModel();
        SharedPref util = new SharedPref();
        AlertsAndLoaders alert = new AlertsAndLoaders();
        SweetAlertDialog sDialog = alert.showAlert(3, "", "Please wait", context, null);
        ApiCall service = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<StorageResponse> call = service.saveRacks(response, mawb_number, flight_number, hawb_number, rack_name, layer_name, rack_util_id, Integer.valueOf(util.readPrefString(context, util.USER_ID)));
        call.enqueue(new Callback<StorageResponse>() {
            @Override
            public void onResponse(Call<StorageResponse> call, Response<StorageResponse> response) {

                try {
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    sDialog.cancel();
                    StorageResponse res = new StorageResponse();
                    res = response.body();

                    if (response.code() == 200) {
//                        -- SUCCESS MESSAGE
                        uploadImage(context, binding, activity, uri, dialog, hawb_id, mawb_number);
//                        alertsAndLoaders.showAlert(0, "", "Successfully updated Storage", context, activity.goToCargoList);
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        alertsAndLoaders.showAlert(2, "", jObjError.get("message").toString(), context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<StorageResponse> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }


    public void toShowLayout(ActivityStorageCargoBinding binding, int layout_id) {

        binding.headerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.mawbList.getRoot().setVisibility(View.GONE);
        binding.mawbGallery.getRoot().setVisibility(View.GONE);
        binding.cargoImagesLayout.getRoot().setVisibility(View.GONE);
        binding.cargoDetails.getRoot().setVisibility(View.GONE);
        binding.cargoImageListLayout.getRoot().setVisibility(View.GONE);
        if (layout_id == 0) {

        } else if (layout_id == 1) {
            binding.mawbList.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 2) {
            binding.mawbGallery.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 3) {
            binding.cargoImagesLayout.getRoot().setVisibility(View.VISIBLE);
        }else if (layout_id == 4) {
            binding.cargoDetails.getRoot().setVisibility(View.VISIBLE);
        } else if (layout_id == 5) {
            binding.cargoImagesLayout.getRoot().setVisibility(View.VISIBLE);
        }
    }

    private void viewData(Activity activity, ActivityStorageCargoBinding binding) {
        try {
            StorageCargoAdapter adapter = new StorageCargoAdapter(activity, R.layout.mawb_line_layout, storage);
            binding.mawbList.listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCargoImages(Context context, StorageCargo activity, ActivityStorageCargoBinding binding, int cargoActivityLogId) {
        imgResp = new ImagesResponse();
        ArrayList<String> img_arr = new ArrayList<String>();

        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
//        dialog = alertsAndLoaders.showAlert(2, "Loading. . .", "",context, activity, null);

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<ImagesResponse> call = services.getCargoImages(cargoActivityLogId);
        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
//                dialog.cancel();

                try {
                    images = new ArrayList<>();
                    String[] ar;
                    if (response.code() == 200) {
                        imgResp = response.body();
                        if (imgResp.getStatusCode() == 200) {
                            images = imgResp.getData().getImages();
                            viewImg(context, binding);
                        } else {
                            alertsAndLoaders.showAlert(1, "", imgResp.getMessage(), context, activity.doNothing);
                        }
                    } else {
                        alertsAndLoaders.showAlert(1, "", imgResp.getMessage(), context, activity.doNothing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }

    private void viewImg(Context context, ActivityStorageCargoBinding binding) {
        try {
            CargoImagesAdapter adapter1 = new CargoImagesAdapter(context, R.layout.store_cargo_images_line, images);
            binding.mawbGallery.listView.setAdapter(adapter1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadImage(Context context, ActivityStorageCargoBinding binding, StorageCargo activity, List<Uri> uri,SweetAlertDialog dialog, int hawb_id, String mawb_number) {

        //response.setFiles(getFilePart(uri,context));

        List<String> modifiedFilePaths = new ArrayList<>();
        for (Uri u : uri) {
            modifiedFilePaths.add(u.getPath().replace("\\", "/"));
        }

        ApiCall services = ServiceGenerator.createService(ApiCall.class, BuildConfig.API_USERNAME, BuildConfig.API_PASSWORD);
        Call<Integer> call = services.uploadStorageImage(getFilePart(uri,context), hawb_id, mawb_number, binding.cargoImagesLayout.spinner1.getSelectedItem().toString(), binding.cargoImagesLayout.spinner2.getSelectedItem().toString(), binding.cargoImagesLayout.remarks.getText().toString(), binding.cargoImagesLayout.remarks2.getText().toString());
        SweetAlertDialog finalDialog = dialog;
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                try {
                    finalDialog.cancel();
                    Integer res = response.body();

                    if (res == 1) {
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(0, "Success!", "Success", context, activity.backToMenu);
//                        to_upload(uri,fname,context, binding);
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                        alertsAndLoaders.showAlert(2, "", jObjError.get("message").toString(), context, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                    alertsAndLoaders.showAlert(2, "", e.getMessage(), context, null);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Error:", t.getMessage());

                System.out.println("Check your connection");
                Log.e("Error:", t.getMessage());
                AlertsAndLoaders alertsAndLoaders = new AlertsAndLoaders();
                alertsAndLoaders.showAlert(2, "", t.getMessage(), context, null);
            }
        });

    }

    private List<MultipartBody.Part> getFilePart(List<Uri> uri, Context context){
        List<MultipartBody.Part> filePart = new ArrayList<>();
        for(Uri u:uri){
            File file=compressFile(u,context);
            filePart.add(MultipartBody.Part.createFormData("file[]", new RotateImage().getFileNameFromUri(u, context), RequestBody.create(MediaType.parse("application/octet-stream"), file)));
        }

        return filePart;
    }

    private File compressFile(Uri imageUri, Context context) {
        File finalFile = null;
        try {
            //Bitmap bmap = toRotateBitmap(imageUri, filename);
            Bitmap bmap=new RotateImage().toRotateBitmap(imageUri,context);
            Bitmap imageBitmap = bmap;

            int newWidth = 900;
            int newHeight = 1200;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, true);
            finalFile = new File(context.getCacheDir() + "/" + new RotateImage().getFileNameFromUri(imageUri,context));

            FileOutputStream fos = new FileOutputStream(finalFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFile;
    }


}
