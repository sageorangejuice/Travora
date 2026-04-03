package com.travora.app.network;

import com.travora.app.model.LoginRequest;
import com.travora.app.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // Auth
    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

}