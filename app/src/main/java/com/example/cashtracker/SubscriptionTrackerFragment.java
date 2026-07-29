package com.example.cashtracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubscriptionTrackerFragment extends Fragment {

    private EditText editName, editAmount;
    private Spinner spinnerFrequency;
    private TextView textDate, textTotalMonthly;
    private LinearLayout containerList;
    private String selectedDate = "";
    private SharedPreferences prefs;
    private final DecimalFormat format = new DecimalFormat("#,##0.00");

    public SubscriptionTrackerFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_tracker, container, false);

        prefs = requireContext().getSharedPreferences("subscriptions", Context.MODE_PRIVATE);

        editName = view.findViewById(R.id.edit_subscription_name);
        editAmount = view.findViewById(R.id.edit_subscription_amount);
        spinnerFrequency = view.findViewById(R.id.spinner_frequency);
        textDate = view.findViewById(R.id.text_selected_date);
        textTotalMonthly = view.findViewById(R.id.text_total_monthly);
        containerList = view.findViewById(R.id.subscription_list_container);
        Button btnPickDate = view.findViewById(R.id.btn_pick_date);
        Button btnSave = view.findViewById(R.id.btn_save_subscription);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.subscription_frequencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter);

        btnPickDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> {
            saveSubscription();
            loadSubscriptions();
        });

        loadSubscriptions();
        return view;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            selectedDate = sdf.format(calendar.getTime());
            textDate.setText("Next Billing Date: " + selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveSubscription() {
        String name = editName.getText().toString().trim();
        String amountStr = editAmount.getText().toString().trim();
        String frequency = spinnerFrequency.getSelectedItem().toString();

        if (name.isEmpty() || amountStr.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float amount = Float.parseFloat(amountStr);
            JSONObject subscription = new JSONObject();
            subscription.put("name", name);
            subscription.put("amount", amount);
            subscription.put("frequency", frequency);
            subscription.put("next_billing", selectedDate);

            JSONArray array = getSavedArray();
            array.put(subscription);

            prefs.edit().putString("subscription_list", array.toString()).apply();

            editName.setText("");
            editAmount.setText("");
            selectedDate = "";
            textDate.setText("Next Billing Date: Not Selected");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to save subscription.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSubscriptions() {
        containerList.removeAllViews();
        JSONArray array = getSavedArray();
        float total = 0;

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject sub = array.getJSONObject(i);
                String name = sub.getString("name");
                float amount = (float) sub.getDouble("amount");
                String freq = sub.getString("frequency");
                String date = sub.getString("next_billing");

                View row = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, containerList, false);
                ((TextView) row.findViewById(android.R.id.text1)).setText(name + " - " + freq);
                ((TextView) row.findViewById(android.R.id.text2)).setText("Next: " + date + " | $" + format.format(amount));
                containerList.addView(row);

                float monthly = freq.equalsIgnoreCase("Monthly") ? amount : amount / 12;
                total += monthly;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        textTotalMonthly.setText("Total Monthly Cost: $" + format.format(total));
    }

    private JSONArray getSavedArray() {
        String saved = prefs.getString("subscription_list", "[]");
        try {
            return new JSONArray(saved);
        } catch (Exception e) {
            return new JSONArray();
        }
    }
}
