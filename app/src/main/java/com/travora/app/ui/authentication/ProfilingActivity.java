package com.travora.app.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.travora.app.R;
import com.travora.app.ui.authentication.LoginActivity;

public class ProfilingActivity extends AppCompatActivity {

    // 🔥 STORE USER CHOICES
    private String selectedBudget = "";
    private String selectedDiet = "";
    private String selectedActivity = "";
    private String selectedDining = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_profiling); // 🔥 CHANGE THIS

        // ===== BUDGET =====
        Button budgetBtn = findViewById(R.id.budget_button);
        Button moderateBtn = findViewById(R.id.moderate_button);
        Button luxuryBtn = findViewById(R.id.luxury_button);

        setupSingleSelect(
                new Button[]{budgetBtn, moderateBtn, luxuryBtn},
                new String[]{"Budget", "Moderate", "Luxury"},
                value -> selectedBudget = value
        );

        // ===== DIET =====
        Button vegBtn = findViewById(R.id.vegetarian_button);
        Button halalBtn = findViewById(R.id.halal_button);
        Button noneBtn = findViewById(R.id.none_button);

        setupSingleSelect(
                new Button[]{vegBtn, halalBtn, noneBtn},
                new String[]{"Vegetarian", "Halal", "None"},
                value -> selectedDiet = value
        );

        // ===== ACTIVITY =====
        Button culturalBtn = findViewById(R.id.cultural_button);
        Button natureBtn = findViewById(R.id.nature_button);
        Button shoppingBtn = findViewById(R.id.shopping_button);

        setupSingleSelect(
                new Button[]{culturalBtn, natureBtn, shoppingBtn},
                new String[]{"Cultural", "Nature", "Shopping"},
                value -> selectedActivity = value
        );

        // ===== DINING =====
        Button localBtn = findViewById(R.id.local_fave_button);
        Button casualBtn = findViewById(R.id.casual_button);
        Button fineBtn = findViewById(R.id.fine_button);

        setupSingleSelect(
                new Button[]{localBtn, casualBtn, fineBtn},
                new String[]{"Local", "Casual", "Fine Dining"},
                value -> selectedDining = value
        );

        // ===== FINISH BUTTON =====
        Button finishBtn = findViewById(R.id.finish_profiling_button);

        finishBtn.setOnClickListener(v -> {

            // ❗ VALIDATION
            if (selectedBudget.isEmpty() ||
                    selectedDiet.isEmpty() ||
                    selectedActivity.isEmpty() ||
                    selectedDining.isEmpty()) {

                Toast.makeText(
                        ProfilingActivity.this,
                        "Please select an option for all categories",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // ✅ DEBUG OUTPUT (optional)
            String result =
                    "Budget: " + selectedBudget + "\n" +
                            "Diet: " + selectedDiet + "\n" +
                            "Activity: " + selectedActivity + "\n" +
                            "Dining: " + selectedDining;

            System.out.println(result);

            // 🚀 GO TO LOGIN PAGE
            Intent intent = new Intent(ProfilingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // ===== REUSABLE SELECTION FUNCTION =====
    private void setupSingleSelect(Button[] buttons, String[] values, OnSelectListener listener) {

        for (int i = 0; i < buttons.length; i++) {
            int index = i;

            buttons[i].setOnClickListener(v -> {

                // 🔄 RESET ALL BUTTONS
                for (Button b : buttons) {
                    b.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    b.setTextColor(getResources().getColor(android.R.color.white));
                }

                // ✅ HIGHLIGHT SELECTED (same as Add Review style)
                buttons[index].setBackgroundColor(getResources().getColor(android.R.color.white));
                buttons[index].setTextColor(getResources().getColor(android.R.color.black));

                // 💾 SAVE VALUE
                listener.onSelect(values[index]);
            });
        }
    }

    // ===== INTERFACE =====
    interface OnSelectListener {
        void onSelect(String value);
    }
}