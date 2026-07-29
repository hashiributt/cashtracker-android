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

public class ReportsFragment extends Fragment {

    private TextView textTotalIncome, textTotalBudget, textTotalExpenses, textRemainingMoney;
    private SharedPreferences dashboardPrefs, budgetPrefs, expensesPrefs;

    public ReportsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        textTotalIncome = view.findViewById(R.id.text_total_income);
        textTotalBudget = view.findViewById(R.id.text_total_budget);
        textTotalExpenses = view.findViewById(R.id.text_total_expenses);
        textRemainingMoney = view.findViewById(R.id.text_remaining_money);

        dashboardPrefs = requireContext().getSharedPreferences("dashboard_data", Context.MODE_PRIVATE);
        budgetPrefs = requireContext().getSharedPreferences("budget_data", Context.MODE_PRIVATE);
        expensesPrefs = requireContext().getSharedPreferences("expenses_data", Context.MODE_PRIVATE);

        loadReportData();

        return view;
    }

    private void loadReportData() {
        float income = dashboardPrefs.getFloat("income", 0);
        float savings = dashboardPrefs.getFloat("savings", 0);
        float cashNet = dashboardPrefs.getFloat("cashNet", 0);
        float investment = dashboardPrefs.getFloat("investment", 0);

        float totalIncome = income + savings + cashNet + investment;

        float housing = budgetPrefs.getFloat("housing", 0);
        float utilities = budgetPrefs.getFloat("utilities", 0);
        float groceries = budgetPrefs.getFloat("groceries", 0);
        float transportation = budgetPrefs.getFloat("transportation", 0);
        float entertainment = budgetPrefs.getFloat("entertainment", 0);
        float miscellaneous = budgetPrefs.getFloat("miscellaneous", 0);

        float totalBudget = housing + utilities + groceries + transportation + entertainment + miscellaneous;

        float food = expensesPrefs.getFloat("food", 0);
        float insurance = expensesPrefs.getFloat("insurance", 0);
        float bills = expensesPrefs.getFloat("bills", 0);
        float health = expensesPrefs.getFloat("health", 0);
        float medical = expensesPrefs.getFloat("medical", 0);
        float subscriptions = expensesPrefs.getFloat("subscriptions", 0);

        float totalExpenses = food + insurance + bills + health + medical + subscriptions;

        float remaining = totalIncome - (totalBudget + totalExpenses);

        textTotalIncome.setText("Total Income: $" + (int) totalIncome);
        textTotalBudget.setText("Total Budget: $" + (int) totalBudget);
        textTotalExpenses.setText("Total Expenses: $" + (int) totalExpenses);
        textRemainingMoney.setText("Remaining Money: $" + (int) remaining);
    }
}
