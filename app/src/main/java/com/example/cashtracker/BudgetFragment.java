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

import java.text.NumberFormat;
import java.util.Locale;

public class BudgetFragment extends Fragment {

    private EditText editHousing, editUtilities, editGroceries, editTransportation, editEntertainment, editMiscellaneous;
    private TextView textTotalBudget;
    private SharedPreferences sharedPreferences;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public BudgetFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        sharedPreferences = requireContext().getSharedPreferences("budget_data", Context.MODE_PRIVATE);

        editHousing = view.findViewById(R.id.edit_housing);
        editUtilities = view.findViewById(R.id.edit_utilities);
        editGroceries = view.findViewById(R.id.edit_groceries);
        editTransportation = view.findViewById(R.id.edit_transportation);
        editEntertainment = view.findViewById(R.id.edit_entertainment);
        editMiscellaneous = view.findViewById(R.id.edit_miscellaneous);
        textTotalBudget = view.findViewById(R.id.text_total_budget);

        setCurrencyFormatter(editHousing);
        setCurrencyFormatter(editUtilities);
        setCurrencyFormatter(editGroceries);
        setCurrencyFormatter(editTransportation);
        setCurrencyFormatter(editEntertainment);
        setCurrencyFormatter(editMiscellaneous);

        loadSavedData();
        calculateTotalBudget();

        return view;
    }

    private void setCurrencyFormatter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    editText.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,]", "");
                    if (!cleanString.isEmpty()) {
                        try {
                            long parsed = Long.parseLong(cleanString);
                            String formatted = formatCurrency(parsed);
                            current = formatted;
                            editText.setText(formatted);
                            editText.setSelection(formatted.length());
                            calculateTotalBudget();
                            saveData();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    editText.addTextChangedListener(this);
                }
            }
        });
    }

    private String formatCurrency(double amount) {
        NumberFormat noDecimalFormat = NumberFormat.getCurrencyInstance(Locale.US);
        noDecimalFormat.setMaximumFractionDigits(0);
        return noDecimalFormat.format(amount);
    }

    private float getFloatFromFormattedText(EditText editText) {
        String text = editText.getText().toString().replaceAll("[$,]", "");
        if (text.isEmpty()) return 0;
        return Float.parseFloat(text);
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("housing", getFloatFromFormattedText(editHousing));
        editor.putFloat("utilities", getFloatFromFormattedText(editUtilities));
        editor.putFloat("groceries", getFloatFromFormattedText(editGroceries));
        editor.putFloat("transportation", getFloatFromFormattedText(editTransportation));
        editor.putFloat("entertainment", getFloatFromFormattedText(editEntertainment));
        editor.putFloat("miscellaneous", getFloatFromFormattedText(editMiscellaneous));
        editor.apply();
    }

    private void loadSavedData() {
        float housing = sharedPreferences.getFloat("housing", -1);
        float utilities = sharedPreferences.getFloat("utilities", -1);
        float groceries = sharedPreferences.getFloat("groceries", -1);
        float transportation = sharedPreferences.getFloat("transportation", -1);
        float entertainment = sharedPreferences.getFloat("entertainment", -1);
        float miscellaneous = sharedPreferences.getFloat("miscellaneous", -1);

        editHousing.setText(housing >= 0 ? formatCurrency(housing) : "");
        editUtilities.setText(utilities >= 0 ? formatCurrency(utilities) : "");
        editGroceries.setText(groceries >= 0 ? formatCurrency(groceries) : "");
        editTransportation.setText(transportation >= 0 ? formatCurrency(transportation) : "");
        editEntertainment.setText(entertainment >= 0 ? formatCurrency(entertainment) : "");
        editMiscellaneous.setText(miscellaneous >= 0 ? formatCurrency(miscellaneous) : "");
    }

    private void calculateTotalBudget() {
        float total = getFloatFromFormattedText(editHousing)
                + getFloatFromFormattedText(editUtilities)
                + getFloatFromFormattedText(editGroceries)
                + getFloatFromFormattedText(editTransportation)
                + getFloatFromFormattedText(editEntertainment)
                + getFloatFromFormattedText(editMiscellaneous);
        textTotalBudget.setText("Total Budget: " + formatCurrency(total));
    }
}
