package com.example.cashtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SpendingTrackerFragment extends Fragment {

    private CalendarView calendarView;
    private TextView textSelectedDate, textSpendingResult, textTotalMonthSpent;
    private EditText editSpendingAmount;
    private SharedPreferences sharedPreferences;
    private String selectedDate = "";

    public SpendingTrackerFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spending_tracker, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        textSelectedDate = view.findViewById(R.id.text_selected_date);
        editSpendingAmount = view.findViewById(R.id.edit_spending_amount);
        textSpendingResult = view.findViewById(R.id.text_spending_result);
        textTotalMonthSpent = view.findViewById(R.id.text_total_month_spent);
        Button btnSaveSpending = view.findViewById(R.id.btn_save_spending);

        sharedPreferences = requireContext().getSharedPreferences("spending_data", Context.MODE_PRIVATE);

        selectedDate = getTodayDate();
        textSelectedDate.setText("Date: " + selectedDate);
        loadSpending(selectedDate);
        calculateMonthlyTotal(selectedDate);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            textSelectedDate.setText("Date: " + selectedDate);
            loadSpending(selectedDate);
            calculateMonthlyTotal(selectedDate);
        });

        btnSaveSpending.setOnClickListener(v -> {
            saveSpending(selectedDate);
            loadSpending(selectedDate);
            calculateMonthlyTotal(selectedDate);
        });

        return view;
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void saveSpending(String date) {
        String input = editSpendingAmount.getText().toString().trim();
        float amount = input.isEmpty() ? 0 : Float.parseFloat(input);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(date, amount);
        editor.apply();
    }

    private void loadSpending(String date) {
        float amount = sharedPreferences.getFloat(date, 0);
        textSpendingResult.setText("Spent: $" + (int) amount);
        editSpendingAmount.setText(amount > 0 ? String.valueOf(amount) : "");
    }

    private void calculateMonthlyTotal(String date) {
        String month = date.substring(0, 7);
        float total = 0;
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith(month)) {
                total += (Float) entry.getValue();
            }
        }
        textTotalMonthSpent.setText("Total Spent This Month: $" + (int) total);
    }
}
