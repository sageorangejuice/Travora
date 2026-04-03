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

public class StartActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_start);

        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // optional: prevents going back
        });
    }
}
