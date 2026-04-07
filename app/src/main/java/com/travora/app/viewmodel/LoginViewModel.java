package com.travora.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.travora.app.model.LoginRequest;
import com.travora.app.model.LoginResponse;
import com.travora.app.repository.AuthRepository;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    private final AuthRepository repository = new AuthRepository();

    public MutableLiveData<LoginResponse> getLoginResult() {
        return loginResult;
    }

    public void loginUser(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        repository.login(request, loginResult);
    }
}
