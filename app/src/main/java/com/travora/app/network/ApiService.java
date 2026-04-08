package com.travora.app.network;

import com.travora.app.model.LoginRequest;
import com.travora.app.model.LoginResponse;
import com.travora.app.model.Places;
import com.travora.app.model.RegistrationRequest;
import com.travora.app.model.RegistrationResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/v1/auth/register")
    Call<RegistrationResponse> register(@Body RegistrationRequest request);

    @GET("api/places/dining")
    Call<List<Places>> getTopDining();

    @GET("api/places/activities")
    Call<List<Places>> getTopActivities();

}