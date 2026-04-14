package com.travora.app.ui.profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.travora.app.R;
import com.travora.app.model.UserManager;
import com.travora.app.ui.authentication.LoginActivity;
import com.travora.app.ui.recommendations.RecommendationsActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_profile);

        UserManager.loadFromPrefs(this);

        TextView usernameValue = findViewById(R.id.username_value);
        TextView avatarInitial = findViewById(R.id.avatar_initial);
        if (UserManager.getUser() != null) {
            String username = UserManager.getUser().getUsername();
            usernameValue.setText(username);
            if (!username.isEmpty()) {
                avatarInitial.setText(String.valueOf(username.charAt(0)).toUpperCase());
            }
        }

        MaterialButton logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            UserManager.clearSession(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        BottomNavigationView navView = findViewById(R.id.nav_bar);
        navView.setItemIconTintList(null);
        Menu menu = navView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            Drawable icon = item.getIcon();
            if (icon != null) {
                Drawable wrapped = DrawableCompat.wrap(icon.mutate());
                DrawableCompat.setTintList(wrapped, null);
                item.setIcon(wrapped);
            }
        }

        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, RecommendationsActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (id == R.id.nav_profile) {
                return true; // already here
            } else if (id == R.id.nav_back) {
                finish();
                return true;
            }
            return false;
        });
    }
}
