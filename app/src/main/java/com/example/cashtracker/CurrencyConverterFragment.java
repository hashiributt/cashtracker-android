package com.example.cashtracker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterFragment extends Fragment {

    private EditText editAmount;
    private TextView textResult;
    private Map<String, Double> rates;

    public CurrencyConverterFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency_converter, container, false);

        editAmount = view.findViewById(R.id.edit_amount_usd);
        textResult = view.findViewById(R.id.text_converted_result);

        rates = new HashMap<>();
        rates.put("EUR", 0.93);
        rates.put("GBP", 0.79);
        rates.put("JPY", 147.13);
        rates.put("INR", 82.68);
        rates.put("CAD", 1.35);
        rates.put("AUD", 1.51);

        view.findViewById(R.id.btn_eur).setOnClickListener(v -> convert("EUR"));
        view.findViewById(R.id.btn_gbp).setOnClickListener(v -> convert("GBP"));
        view.findViewById(R.id.btn_jpy).setOnClickListener(v -> convert("JPY"));
        view.findViewById(R.id.btn_inr).setOnClickListener(v -> convert("INR"));
        view.findViewById(R.id.btn_cad).setOnClickListener(v -> convert("CAD"));
        view.findViewById(R.id.btn_aud).setOnClickListener(v -> convert("AUD"));

        return view;
    }

    private void convert(String currency) {
        String input = editAmount.getText().toString().trim();
        if (!input.isEmpty()) {
            double usd = Double.parseDouble(input);
            double result = usd * rates.get(currency);
            textResult.setText("Converted: " + currency + " " + String.format("%.2f", result));
        }
    }
}
