package com.travora.app.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.travora.app.R;
import com.travora.app.ui.recommendations.RecommendationsActivity;
import com.travora.app.viewmodel.LoginViewModel;
import com.travora.app.model.UserManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button sign_in;
    private Button sign_up;
    private Button forgot_password;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_login);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in_button);
        sign_up = findViewById(R.id.sign_up_button);
        forgot_password = findViewById(R.id.forgot_password_button);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getLoginResult().observe(this, loginResponse -> {
            if (loginResponse != null && loginResponse.isSuccess()) {
                UserManager.saveToPrefs(LoginActivity.this, loginResponse.getUsername());

                Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        sign_in.setOnClickListener(view -> {
            String username = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                loginViewModel.loginUser(username, password);
            } else {
                Toast.makeText(LoginActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        sign_up.setOnClickListener(view -> {
            Intent toRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(toRegistration);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        forgot_password.setOnClickListener(view ->
            Toast.makeText(LoginActivity.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
        );
    }
}
