package com.travora.app.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.travora.app.R;
import com.travora.app.viewmodel.ProfilingViewModel;

public class ProfilingActivity extends AppCompatActivity {

    private String selectedBudget = "";
    private String selectedDiet = "";
    private String selectedActivity = "";
    private String selectedDining = "";
    private ProfilingViewModel viewModel;

    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_profiling);

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        password = getIntent().getStringExtra("password");

        viewModel = new ViewModelProvider(this).get(ProfilingViewModel.class);

        viewModel.getRegisterResult().observe(this, response -> {
            if (response != null && response.isSuccess()) {
                Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            } else {
                String msg = (response != null && response.getMessage() != null)
                        ? response.getMessage()
                        : "Registration failed. Please try again.";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button budgetBtn = findViewById(R.id.budget_button);
        Button moderateBtn = findViewById(R.id.moderate_button);
        Button luxuryBtn = findViewById(R.id.luxury_button);

        setupSingleSelect(
                new Button[]{budgetBtn, moderateBtn, luxuryBtn},
                new String[]{"Budget", "Moderate", "Luxury"},
                value -> selectedBudget = value
        );

        Button vegBtn = findViewById(R.id.vegetarian_button);
        Button halalBtn = findViewById(R.id.halal_button);
        Button noneBtn = findViewById(R.id.none_button);

        setupSingleSelect(
                new Button[]{vegBtn, halalBtn, noneBtn},
                new String[]{"Vegetarian", "Halal", "None"},
                value -> selectedDiet = value
        );

        Button culturalBtn = findViewById(R.id.cultural_button);
        Button natureBtn = findViewById(R.id.nature_button);
        Button shoppingBtn = findViewById(R.id.shopping_button);

        setupSingleSelect(
                new Button[]{culturalBtn, natureBtn, shoppingBtn},
                new String[]{"Cultural", "Nature", "Shopping"},
                value -> selectedActivity = value
        );

        Button localBtn = findViewById(R.id.local_fave_button);
        Button casualBtn = findViewById(R.id.casual_button);
        Button fineBtn = findViewById(R.id.fine_button);

        setupSingleSelect(
                new Button[]{localBtn, casualBtn, fineBtn},
                new String[]{"Local", "Casual", "Fine Dining"},
                value -> selectedDining = value
        );

        Button finishBtn = findViewById(R.id.finish_profiling_button);

        finishBtn.setOnClickListener(v -> {
            if (selectedBudget.isEmpty() || selectedDiet.isEmpty()
                    || selectedActivity.isEmpty() || selectedDining.isEmpty()) {
                Toast.makeText(this, "Please select an option for all categories", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.register(username, email, phoneNumber, password,
                    selectedBudget, selectedDiet, selectedActivity, selectedDining);
        });
    }

    private void setupSingleSelect(Button[] buttons, String[] values, OnSelectListener listener) {
        for (int i = 0; i < buttons.length; i++) {
            int index = i;
            buttons[i].setOnClickListener(v -> {
                for (Button b : buttons) {
                    b.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    b.setTextColor(getResources().getColor(android.R.color.white));
                }
                buttons[index].setBackgroundColor(getResources().getColor(android.R.color.white));
                buttons[index].setTextColor(getResources().getColor(android.R.color.black));
                listener.onSelect(values[index]);
            });
        }
    }

    interface OnSelectListener {
        void onSelect(String value);
    }
}
