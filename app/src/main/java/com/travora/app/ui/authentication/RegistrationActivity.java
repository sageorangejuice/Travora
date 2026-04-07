package com.travora.app.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.travora.app.R;
import com.travora.app.viewmodel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullNameInput;
    private EditText emailInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;
    private EditText rePasswordInput;
    private Button signUpButton;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_registration);

        fullNameInput = findViewById(R.id.full_name);
        emailInput = findViewById(R.id.email);
        phoneNumberInput = findViewById(R.id.phone_number);
        passwordInput = findViewById(R.id.password);
        rePasswordInput = findViewById(R.id.re_password);
        signUpButton = findViewById(R.id.sign_up_button);

        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        registrationViewModel.getRegistrationResult().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                Toast.makeText(this, "Account created! Please sign in.", Toast.LENGTH_SHORT).show();
                Intent toLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                toLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toLogin);
                finish();
            } else {
                String msg = (response != null && response.getMessage() != null)
                        ? response.getMessage()
                        : "Registration failed. Please try again.";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        signUpButton.setOnClickListener(view -> {
            String fullName = fullNameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phoneNumber = phoneNumberInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String rePassword = rePasswordInput.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()
                    || password.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(rePassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }
            registrationViewModel.registerUser(fullName, email, phoneNumber, password);
        });
    }
}
