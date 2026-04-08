package com.travora.app.ui.homepage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.travora.app.R;
import com.travora.app.ui.authentication.LoginActivity;
import com.travora.app.ui.recommendations.RecommendationsActivity;
import android.content.Intent;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_start);

        startButton = findViewById(R.id.start_button);

        if (startButton != null) {
            startButton.setOnClickListener(v -> {
                Log.d("StartActivity", "Let's Go Button clicked!");
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
        } else {
            Log.e("StartActivity", "Start button not found in layout!");
        }
    }
}
