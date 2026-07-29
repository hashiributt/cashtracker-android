package com.example.cashtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class GoalFragment extends Fragment {

    private TextInputEditText editGoalName, editGoalAmount, editGoalWeeks, editAmountNow;
    private TextView textSuggestion, textGoalStatus;
    private LinearProgressIndicator progressGoal;
    private SharedPreferences sharedPreferences;
    private final DecimalFormat format = new DecimalFormat("#,##0.##");

    public GoalFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal_setter, container, false);

        sharedPreferences = requireContext().getSharedPreferences("goal_data", Context.MODE_PRIVATE);

        editGoalName = view.findViewById(R.id.edit_goal_name);
        editGoalAmount = view.findViewById(R.id.edit_goal_amount);
        editGoalWeeks = view.findViewById(R.id.edit_goal_weeks);
        editAmountNow = view.findViewById(R.id.edit_amount_now);
        textSuggestion = view.findViewById(R.id.text_suggestion);
        textGoalStatus = view.findViewById(R.id.text_goal_status);
        progressGoal = view.findViewById(R.id.progress_goal);
        MaterialButton btnSave = view.findViewById(R.id.btn_save_goal);
        MaterialButton btnClear = view.findViewById(R.id.btn_clear_goal);

        loadSavedGoal();

        btnSave.setOnClickListener(v -> {
            saveGoalIncrementally();
            updateSuggestionAndProgress();
        });

        btnClear.setOnClickListener(v -> {
            sharedPreferences.edit().clear().apply();
            editGoalName.setText("");
            editGoalAmount.setText("");
            editGoalWeeks.setText("");
            editAmountNow.setText("");
            updateSuggestionAndProgress();
            Toast.makeText(requireContext(), "Goal cleared.", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void saveGoalIncrementally() {
        float currentTotal = sharedPreferences.getFloat("goal_saved", 0);
        float addedAmount = parseFloat(editAmountNow);
        float newTotal = currentTotal + addedAmount;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("goal_name", getText(editGoalName));
        editor.putFloat("goal_amount", parseFloat(editGoalAmount));
        editor.putInt("goal_weeks", parseInt(editGoalWeeks));
        editor.putFloat("goal_saved", newTotal);
        editor.apply();

        editAmountNow.setText("");
    }

    private void loadSavedGoal() {
        editGoalName.setText(sharedPreferences.getString("goal_name", ""));
        float amount = sharedPreferences.getFloat("goal_amount", 0);
        int weeks = sharedPreferences.getInt("goal_weeks", 0);

        if (amount > 0) editGoalAmount.setText(String.valueOf(amount));
        if (weeks > 0) editGoalWeeks.setText(String.valueOf(weeks));

        updateSuggestionAndProgress();
    }

    private void updateSuggestionAndProgress() {
        float goalAmount = parseFloat(editGoalAmount);
        int weeks = parseInt(editGoalWeeks);
        float currentSaved = sharedPreferences.getFloat("goal_saved", 0);

        if (goalAmount > 0 && weeks > 0) {
            float perWeek = goalAmount / weeks;
            textSuggestion.setText("Save $" + format.format(perWeek) + " per week to reach your goal in " + weeks + " weeks.");
        } else {
            textSuggestion.setText("Suggested savings plan will appear here...");
        }

        float progressPercent = goalAmount > 0 ? (currentSaved / goalAmount) * 100 : 0;
        progressGoal.setProgress((int) progressPercent);
        textGoalStatus.setText("Progress: $" + format.format(currentSaved) + " / $" + format.format(goalAmount));

        if (goalAmount > 0 && currentSaved >= goalAmount) {
            Toast.makeText(requireContext(), "🎉 Goal completed! You reached your target.", Toast.LENGTH_LONG).show();
        }
    }

    private float parseFloat(TextInputEditText input) {
        String s = getText(input);
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private int parseInt(TextInputEditText input) {
        String s = getText(input);
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private String getText(TextInputEditText input) {
        return input.getText() != null ? input.getText().toString().trim() : "";
    }
}
