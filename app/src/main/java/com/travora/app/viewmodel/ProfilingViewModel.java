package com.travora.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travora.app.model.RegistrationRequest;
import com.travora.app.model.RegistrationResponse;
import com.travora.app.repository.AuthRepository;

public class ProfilingViewModel extends ViewModel {

    private final MutableLiveData<RegistrationResponse> registerResult = new MutableLiveData<>();
    private final AuthRepository repository = new AuthRepository();

    public MutableLiveData<RegistrationResponse> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String email, String phoneNumber, String password,
                         String budget, String diet, String activity, String dining) {
        RegistrationRequest request = new RegistrationRequest(
                username, email, phoneNumber, password, budget, diet, activity, dining
        );
        repository.register(request, registerResult);
    }
}
