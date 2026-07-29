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

public class RecommendationsFragment extends Fragment {

    private SharedPreferences budgetPrefs, expensePrefs, trackerPrefs;
    private TextView textRecommendations;

    public RecommendationsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        textRecommendations = view.findViewById(R.id.text_recommendation);

        budgetPrefs = requireContext().getSharedPreferences("budget_data", Context.MODE_PRIVATE);
        expensePrefs = requireContext().getSharedPreferences("expenses_data", Context.MODE_PRIVATE);
        trackerPrefs = requireContext().getSharedPreferences("spending_tracker_data", Context.MODE_PRIVATE);

        generateRecommendations();

        return view;
    }

    private void generateRecommendations() {
        float totalBudget = getTotalFromPrefs(budgetPrefs);
        float totalExpenses = getTotalFromPrefs(expensePrefs);
        float totalSpending = getTotalFromPrefs(trackerPrefs);

        StringBuilder rec = new StringBuilder("📊 Financial Overview\n\n");

        rec.append("• Total Budget Set: $").append((int) totalBudget).append("\n");
        rec.append("• Total Expenses Recorded: $").append((int) totalExpenses).append("\n");
        rec.append("• Total Daily Spending: $").append((int) totalSpending).append("\n\n");

        rec.append("🧠 Smart Suggestions:\n");

        if (totalBudget == 0 && totalExpenses == 0 && totalSpending == 0) {
            rec.append("• Start by entering your monthly budget, expenses, and daily spending.\n");
        }

        if (totalExpenses > totalBudget) {
            rec.append("• You're spending more than your budget. Consider reducing categories like dining out or subscriptions.\n");
        } else if (totalExpenses > 0) {
            rec.append("• Great job! You're staying within your budget. Keep tracking daily spending.\n");
        }

        if (totalSpending > totalBudget * 0.5) {
            rec.append("• Your daily spending is more than 50% of your budget. Review your spending habits weekly.\n");
        }

        if (totalExpenses > 0 && totalExpenses < totalBudget * 0.3) {
            rec.append("• You have a healthy buffer. Consider allocating extra funds toward savings or debt repayment.\n");
        }

        rec.append("\n💡 Tip: Review your Budgeting and Expenses tabs weekly for smarter money moves!");

        textRecommendations.setText(rec.toString());
    }

    private float getTotalFromPrefs(SharedPreferences prefs) {
        float total = 0;
        for (String key : prefs.getAll().keySet()) {
            try {
                total += prefs.getFloat(key, 0);
            } catch (Exception ignored) {}
        }
        return total;
    }
}
