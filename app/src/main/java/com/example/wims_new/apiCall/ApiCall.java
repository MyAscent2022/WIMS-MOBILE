package com.example.wims_new.apiCall;


import com.example.wims_new.model.FlightsResponse;
import com.example.wims_new.model.ImagesResponse;
import com.example.wims_new.model.MawbResponse;
import com.example.wims_new.model.UldResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("flights")
    Call<FlightsResponse> searchFlights();

    @GET("ulds_per_flight")
    Call<UldResponse> searchUlds(@Query("flight_number") String flight_number);

    @GET("mawbs_per_uld")
    Call<MawbResponse> getMawbs(@Query("registry_number") String registry_number);

    @GET("get_cargo_condition")
    Call<MawbResponse> getCargoCondition();

    @GET("hawbs_per_mawb")
    Call<MawbResponse> getHawbs(@Query("registry_number") String registry_number, @Query("mawb_number") String mawb_number);



}
