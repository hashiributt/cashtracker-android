package com.example.cashtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DashboardFragment extends Fragment {

    private EditText editIncome, editSavings, editCashNet, editInvestment;
    private TextView textIncomeLabel, textSavingsLabel, textCashNetLabel, textInvestmentLabel, textTotalMoney;
    private SharedPreferences sharedPreferences;

    public DashboardFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sharedPreferences = requireContext().getSharedPreferences("dashboard_data", Context.MODE_PRIVATE);

        editIncome = view.findViewById(R.id.edit_income);
        editSavings = view.findViewById(R.id.edit_savings);
        editCashNet = view.findViewById(R.id.edit_cash_net);
        editInvestment = view.findViewById(R.id.edit_investment);

        textIncomeLabel = view.findViewById(R.id.text_income_label);
        textSavingsLabel = view.findViewById(R.id.text_savings_label);
        textCashNetLabel = view.findViewById(R.id.text_cash_net_label);
        textInvestmentLabel = view.findViewById(R.id.text_investment_label);
        textTotalMoney = view.findViewById(R.id.text_total_money);

        loadSavedData();

        setLiveUpdate(editIncome, "income", textIncomeLabel, "Income");
        setLiveUpdate(editSavings, "savings", textSavingsLabel, "Savings");
        setLiveUpdate(editCashNet, "cashNet", textCashNetLabel, "Cash Net");
        setLiveUpdate(editInvestment, "investment", textInvestmentLabel, "Investment");

        return view;
    }

    private void setLiveUpdate(EditText editText, String key, TextView labelView, String label) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                float value = getFloat(editText);
                labelView.setText(label + ": $" + (int) value);
                sharedPreferences.edit().putFloat(key, value).apply();
                calculateTotal();
            }
        });
    }

    private float getFloat(EditText editText) {
        String input = editText.getText().toString().trim();
        if (input.isEmpty()) return 0;
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void loadSavedData() {
        float income = sharedPreferences.getFloat("income", 0);
        float savings = sharedPreferences.getFloat("savings", 0);
        float cashNet = sharedPreferences.getFloat("cashNet", 0);
        float investment = sharedPreferences.getFloat("investment", 0);

        editIncome.setText(income > 0 ? String.valueOf(income) : "");
        editSavings.setText(savings > 0 ? String.valueOf(savings) : "");
        editCashNet.setText(cashNet > 0 ? String.valueOf(cashNet) : "");
        editInvestment.setText(investment > 0 ? String.valueOf(investment) : "");

        textIncomeLabel.setText("Income: $" + (int) income);
        textSavingsLabel.setText("Savings: $" + (int) savings);
        textCashNetLabel.setText("Cash Net: $" + (int) cashNet);
        textInvestmentLabel.setText("Investment: $" + (int) investment);

        calculateTotal();
    }

    private void calculateTotal() {
        float total = getFloat(editIncome)
                + getFloat(editSavings)
                + getFloat(editCashNet)
                + getFloat(editInvestment);
        textTotalMoney.setText("Total Money: $" + (int) total);
    }
}
