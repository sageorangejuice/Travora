package com.travora.app.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.travora.app.model.LoginRequest;
import com.travora.app.model.LoginResponse;
import com.travora.app.model.RegistrationRequest;
import com.travora.app.model.RegistrationResponse;
import com.travora.app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private static final String TAG = "AuthRepository";

    public void login(LoginRequest request, MutableLiveData<LoginResponse> loginResult) {
        Log.d(TAG, "login() called - username: " + request.getUsername());
        RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "login onResponse - code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "login success - response: " + response.body().isSuccess());
                    loginResult.postValue(response.body());
                } else {
                    Log.w(TAG, "login failed - code: " + response.code() + " body: " + response.body());
                    loginResult.postValue(new LoginResponse(false));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "login onFailure - " + t.getMessage(), t);
                loginResult.postValue(new LoginResponse(false));
            }
        });
    }

    public void register(RegistrationRequest request, MutableLiveData<RegistrationResponse> registrationResult) {
        Log.d(TAG, "register() called - username: " + request.getUsername());
        RetrofitClient.getApiService().register(request).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                Log.d(TAG, "register onResponse - code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "register success: " + response.body().isSuccess() + " - " + response.body().getMessage());
                    registrationResult.postValue(response.body());
                } else {
                    Log.w(TAG, "register failed - code: " + response.code());
                    registrationResult.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e(TAG, "register onFailure - " + t.getMessage(), t);
                registrationResult.postValue(null);
            }
        });
    }
}
