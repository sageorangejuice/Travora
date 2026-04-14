package com.travora.app.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.travora.app.model.RegistrationRequest;
import com.travora.app.model.RegistrationResponse;
import com.travora.app.repository.AuthRepository;

public class RegistrationViewModel extends ViewModel {

    private final MutableLiveData<RegistrationResponse> registrationResult = new MutableLiveData<>();
    private final AuthRepository repository = new AuthRepository();

    public MutableLiveData<RegistrationResponse> getRegistrationResult() {
        return registrationResult;
    }

    public void registerUser(String username, String email, String phoneNumber, String password,
                             String prefBudget, String prefDiet, String prefActivity, String prefDining) {
        RegistrationRequest request = new RegistrationRequest(
                username, email, phoneNumber, password, prefBudget, prefDiet, prefActivity, prefDining);
        repository.register(request, registrationResult);
    }
}
