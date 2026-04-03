package com.travora.app.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.travora.app.R;

import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button sign_in;
    private Button sign_up;
    private Button forgot_password;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_login);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in_button);
        sign_up = findViewById(R.id.sign_up_button);
        forgot_password = findViewById(R.id.forgot_password_button);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    Intent toReccomendations = new Intent(LoginActivity.this, ReccomendationsActivity.class);
                    startActivity(toReccomendations);
                }
                System.out.println("login successful");
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(toRegistration);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Feature Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
