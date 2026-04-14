package com.travora.app.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.travora.app.R;
import com.travora.app.ui.recommendations.RecommendationsActivity;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_location);

        View appName = findViewById(R.id.app_name);
        View heroBlock = findViewById(R.id.hero_block);
        View bottomBlock = findViewById(R.id.bottom_block);
        Button startButton = findViewById(R.id.location_button);

        animateIn(appName, 0);
        animateIn(heroBlock, 200);
        animateIn(bottomBlock, 400);

        if (startButton != null) {
            startButton.setOnClickListener(v -> {
                Intent intent = new Intent(LocationActivity.this, RecommendationsActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            });
        }
    }

    private void animateIn(View view, long delayMs) {
        view.setTranslationY(60f);
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delayMs)
                .setDuration(500)
                .start();
    }
}
