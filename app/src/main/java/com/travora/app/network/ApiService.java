package com.travora.app.network;

import com.travora.app.model.LoginRequest;
import com.travora.app.model.LoginResponse;
import com.travora.app.model.RegistrationRequest;
import com.travora.app.model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("api/v1/auth/register")
    Call<RegistrationResponse> register(@Body RegistrationRequest request);

}