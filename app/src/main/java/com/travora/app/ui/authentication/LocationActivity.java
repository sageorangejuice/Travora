package com.travora.app.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.travora.app.R;
import com.travora.app.ui.authentication.LoginActivity;
import com.travora.app.ui.recommendations.RecommendationsActivity;
import android.content.Intent;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_location);

        startButton = findViewById(R.id.location_button);

        if (startButton != null) {
            startButton.setOnClickListener(v -> {
                Intent intent = new Intent(LocationActivity.this, RecommendationsActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
