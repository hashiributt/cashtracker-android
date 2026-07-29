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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Map;

public class RemindersFragment extends Fragment {

    private EditText editReminderTitle;
    private TextView textSelectedDate, textSavedReminders;
    private Button btnPickDate, btnSaveReminder, btnClearReminders;
    private SharedPreferences sharedPreferences;
    private String selectedDate = "";

    public RemindersFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        sharedPreferences = requireContext().getSharedPreferences("reminders_data", Context.MODE_PRIVATE);

        editReminderTitle = view.findViewById(R.id.edit_reminder_title);
        textSelectedDate = view.findViewById(R.id.text_selected_date);
        textSavedReminders = view.findViewById(R.id.text_saved_reminders);
        btnPickDate = view.findViewById(R.id.btn_pick_date);
        btnSaveReminder = view.findViewById(R.id.btn_save_reminder);
        btnClearReminders = view.findViewById(R.id.btn_clear_reminders);

        btnPickDate.setOnClickListener(v -> openDatePicker());

        btnSaveReminder.setOnClickListener(v -> {
            saveReminder();
            loadReminders();
        });

        btnClearReminders.setOnClickListener(v -> {
            clearReminders();
            loadReminders();
        });

        loadReminders();

        return view;
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                    textSelectedDate.setText("Due Date: " + selectedDate);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveReminder() {
        String title = editReminderTitle.getText().toString().trim();
        if (!title.isEmpty() && !selectedDate.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(title, selectedDate);
            editor.apply();
            editReminderTitle.setText("");
            textSelectedDate.setText("Due Date: ");
            selectedDate = "";
        }
    }

    private void loadReminders() {
        Map<String, ?> reminders = sharedPreferences.getAll();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, ?> entry : reminders.entrySet()) {
            builder.append("Reminder: ").append(entry.getKey())
                    .append("\nDue Date: ").append(entry.getValue().toString())
                    .append("\n\n");
        }
        textSavedReminders.setText(builder.toString().isEmpty() ? "No Reminders Set." : builder.toString());
    }

    private void clearReminders() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
