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

public class ExpensesFragment extends Fragment {

    private EditText editFood, editInsurance, editBills, editHealth, editMedical, editSubscriptions;
    private TextView textFoodLabel, textInsuranceLabel, textBillsLabel, textHealthLabel, textMedicalLabel, textSubscriptionsLabel, textTotalExpenses;
    private SharedPreferences sharedPreferences;

    public ExpensesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        sharedPreferences = requireContext().getSharedPreferences("expenses_data", Context.MODE_PRIVATE);

        editFood = view.findViewById(R.id.edit_food);
        editInsurance = view.findViewById(R.id.edit_insurance);
        editBills = view.findViewById(R.id.edit_bills);
        editHealth = view.findViewById(R.id.edit_health);
        editMedical = view.findViewById(R.id.edit_medical);
        editSubscriptions = view.findViewById(R.id.edit_subscriptions);

        textFoodLabel = view.findViewById(R.id.text_food_label);
        textInsuranceLabel = view.findViewById(R.id.text_insurance_label);
        textBillsLabel = view.findViewById(R.id.text_bills_label);
        textHealthLabel = view.findViewById(R.id.text_health_label);
        textMedicalLabel = view.findViewById(R.id.text_medical_label);
        textSubscriptionsLabel = view.findViewById(R.id.text_subscriptions_label);
        textTotalExpenses = view.findViewById(R.id.text_total_expenses);

        loadSavedData();

        setLiveUpdate(editFood, "food", textFoodLabel, "Food");
        setLiveUpdate(editInsurance, "insurance", textInsuranceLabel, "Insurance");
        setLiveUpdate(editBills, "bills", textBillsLabel, "Bills");
        setLiveUpdate(editHealth, "health", textHealthLabel, "Health");
        setLiveUpdate(editMedical, "medical", textMedicalLabel, "Medical");
        setLiveUpdate(editSubscriptions, "subscriptions", textSubscriptionsLabel, "Subscriptions");

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
        return input.isEmpty() ? 0 : Float.parseFloat(input);
    }

    private void loadSavedData() {
        float food = sharedPreferences.getFloat("food", 0);
        float insurance = sharedPreferences.getFloat("insurance", 0);
        float bills = sharedPreferences.getFloat("bills", 0);
        float health = sharedPreferences.getFloat("health", 0);
        float medical = sharedPreferences.getFloat("medical", 0);
        float subscriptions = sharedPreferences.getFloat("subscriptions", 0);

        editFood.setText(food > 0 ? String.valueOf(food) : "");
        editInsurance.setText(insurance > 0 ? String.valueOf(insurance) : "");
        editBills.setText(bills > 0 ? String.valueOf(bills) : "");
        editHealth.setText(health > 0 ? String.valueOf(health) : "");
        editMedical.setText(medical > 0 ? String.valueOf(medical) : "");
        editSubscriptions.setText(subscriptions > 0 ? String.valueOf(subscriptions) : "");

        textFoodLabel.setText("Food: $" + (int) food);
        textInsuranceLabel.setText("Insurance: $" + (int) insurance);
        textBillsLabel.setText("Bills: $" + (int) bills);
        textHealthLabel.setText("Health: $" + (int) health);
        textMedicalLabel.setText("Medical: $" + (int) medical);
        textSubscriptionsLabel.setText("Subscriptions: $" + (int) subscriptions);

        calculateTotal();
    }

    private void calculateTotal() {
        float total = getFloat(editFood)
                + getFloat(editInsurance)
                + getFloat(editBills)
                + getFloat(editHealth)
                + getFloat(editMedical)
                + getFloat(editSubscriptions);
        textTotalExpenses.setText("Total Expenses: $" + (int) total);
    }
}
