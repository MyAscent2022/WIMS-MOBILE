package com.example.wims_new.apiCall;


import com.example.wims_new.model.CargoActLogsModel;
import com.example.wims_new.model.CargoImagesModel;
import com.example.wims_new.model.ConfirmCargoModel;
import com.example.wims_new.model.FlightsResponse;
import com.example.wims_new.model.HawbDetails;
import com.example.wims_new.model.HawbModel;
import com.example.wims_new.model.ImagesResponse;
import com.example.wims_new.model.MawbDetails;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.SaveUldNumberModel;
import com.example.wims_new.model.UldContainerResponse;
import com.example.wims_new.model.UldImagesResp;
import com.example.wims_new.model.UldImagesResponse;
import com.example.wims_new.model.UldModel;
import com.example.wims_new.model.UldResponse;
import com.example.wims_new.ui.Login.Model.UserResponse;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoData;
import com.example.wims_new.ui.storeCargo.releasing.view.Model.ReleaseCargoResponse;
import com.example.wims_new.ui.storeCargo.storage.view.Model.RackResponse;
import com.example.wims_new.ui.storeCargo.storage.view.Model.StorageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("user_login")
    Call<UserResponse> getUser(@Query("username") String username, @Query("passkey") String passkey);

    @GET("user_logout")
    Call<UserResponse> logoutUser(@Query("user_id") int user_id);

    @GET("flights")
    Call<FlightsResponse> searchFlights(@Query("user_id") int user_id);

    @GET("ulds_per_flight")
    Call<UldResponse> searchUlds(@Query("flight_number") String flight_number);

    @GET("mawbs_per_uld")
    Call<MawbResponse> getMawbs(@Query("uld_number") String uldNumber, @Query("is_uld") boolean isUld, @Query("flight_number") String flightNumber);

    @GET("get_cargo_condition")
    Call<MawbResponse> getCargoCondition();

    @GET("hawbs_per_mawb")
    Call<MawbResponse> getHawbs(@Query("mawb_number") String mawb_number);


    @POST("confirm_cargo")
    Call<MawbResponse> saveMawbDetails(@Body ConfirmCargoModel confirmCargoModel, @Query("mawb_number") String mawb_number, @Query("flight_number") String flightNumber, @Query("hawb_number") String hawb_number, @Query("user_id") int user_id, @Query("cargo_category") String cargo_category, @Query("cargo_class") String cargo_class);


    @Multipart
    @POST("upload_image")
    Call<Integer> uploadImage(@Part List<MultipartBody.Part> files, @Query("hawb_id") int hawb_id, @Query("mawb_number") String mawb_number, @Query("cargo_condition1") String cargo_condition1, @Query("cargo_condition2") String cargo_condition2, @Query("remarks1") String remarks1, @Query("remarks2") String remarks2);

    @Multipart
    @POST("upload_storage_image")
    Call<Integer> uploadStorageImage(@Part List<MultipartBody.Part> files, @Query("hawb_id") int hawb_id, @Query("mawb_number") String mawb_number, @Query("list_image_details") String list_image_details);

    @Multipart
    @POST("save_uld_image")
    Call<UldImagesResponse> saveUldImage(@Part List<MultipartBody.Part> files, @Query("uld_condition1") String uld_condition1, @Query("uld_condition2") String uld_condition2, @Query("flight_number") String flight_number, @Query("uld_number") String uld_number, @Query("remarks1") String remarks1, @Query("remarks2") String remarks2);

    @GET("get_uld_type")
    Call<UldResponse>getUldTypes();

    @GET("get_uld_container_type")
    Call<UldContainerResponse> getContainerTypes();

    @GET("get_cargo_category")
    Call<MawbResponse>getCargoCategory();

    @GET("get_cargo_class")
    Call<MawbResponse>getCargoClass();

    @GET("get_cargo_status")
    Call<MawbResponse>getCargoStatus();

    @POST("save_uld_number")
    Call<UldResponse> saveUldNumber(@Body SaveUldNumberModel saveUld);

    @POST("update_uld_number")
    Call<UldResponse> updateUldNumber(@Body SaveUldNumberModel updateUld,@Query("uld_number") String uldNumber);

    @POST("assign_rack")
    Call<StorageResponse> saveRacks(@Body CargoActLogsModel cargoActLogsModel, @Query("mawb_number") String mawb_number, @Query("flight_number") String flight_number, @Query("hawb_number") String hawb_number, @Query("rack_name") String rack_name,@Query("layer_name") String layer_name, @Query("rack_util_id") int rack_util_id, @Query("user_id") int user_id);

    @GET("store_cargo_mawbs")
    Call<StorageResponse>getStoreCargo();

    @GET("get_ref_rack")
    Call<RackResponse>getRacks();

    @GET("releasing_cargo")
    Call<ReleaseCargoResponse>getReleasingCargo();

    @GET("get_rack_details")
    Call<StorageResponse>getRackDetails(@Query("is_hawb") boolean isHawb, @Query("hawb_number") String hawb_number, @Query("mawb_number") String mawb_number);

    @POST("update_storager_status")
    Call<ReleaseCargoResponse> updateStoragerStatus(@Query("hawb_number") String hawbNumber,@Query("mawb_number") String mawbNumber, @Query("user_id") int user_id);

    @GET("get_cargo_images")
    Call<ImagesResponse> getCargoImages(@Query("cargo_activity_log_id") int cargoActivityLogId);

    @GET("get_uld_images")
    Call<UldImagesResp> getUldImages(@Query("flight_number") String flight_number, @Query("uld_number") String uld_number);

    @POST("save_releasing_cargo")
    Call<ReleaseCargoResponse> saveReleaseCargo(@Query("mawb_number") String mawb_number, @Query("hawb_number") String hawb_number, @Query("flight_number") String flight_number, @Query("user_id") int user_id);
}
