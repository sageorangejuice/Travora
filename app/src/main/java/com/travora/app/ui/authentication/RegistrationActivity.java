package com.travora.app.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.travora.app.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText emailInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;
    private EditText rePasswordInput;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_registration);

        usernameInput = findViewById(R.id.username);
        emailInput = findViewById(R.id.email);
        phoneNumberInput = findViewById(R.id.phone_number);
        passwordInput = findViewById(R.id.password);
        rePasswordInput = findViewById(R.id.re_password);
        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phoneNumber = phoneNumberInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String rePassword = rePasswordInput.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()
                    || password.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(rePassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(RegistrationActivity.this, ProfilingActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("password", password);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Go to Profile to log out", Toast.LENGTH_SHORT).show();
    }
}
