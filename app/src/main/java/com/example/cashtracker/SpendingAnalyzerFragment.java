package com.example.cashtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SpendingAnalyzerFragment extends Fragment {

    private TextView textSpendingSummary, textCategoryInsights;
    private SharedPreferences sharedPreferences;

    public SpendingAnalyzerFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spending_analyzer, container, false);

        sharedPreferences = requireContext().getSharedPreferences("expenses_data", Context.MODE_PRIVATE);

        textSpendingSummary = view.findViewById(R.id.text_spending_summary);
        textCategoryInsights = view.findViewById(R.id.text_category_insights);

        analyzeSpending();

        return view;
    }

    private void analyzeSpending() {
        float totalSpending = 0;
        float highestCategoryAmount = 0;
        String highestCategory = "None";
        String currentMonth = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());

        Map<String, ?> expenses = sharedPreferences.getAll();
        StringBuilder summaryBuilder = new StringBuilder();

        for (Map.Entry<String, ?> entry : expenses.entrySet()) {
            float amount = (Float) entry.getValue();
            totalSpending += amount;

            summaryBuilder.append(entry.getKey()).append(": $").append((int) amount).append("\n");

            if (amount > highestCategoryAmount) {
                highestCategoryAmount = amount;
                highestCategory = entry.getKey();
            }
        }

        textSpendingSummary.setText(summaryBuilder.toString().isEmpty() ? "No spending data found." : summaryBuilder.toString());
        textCategoryInsights.setText("Highest Spending: " + highestCategory + " ($" + (int) highestCategoryAmount + ")");
    }
}
