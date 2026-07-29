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

public class MoneyTransferFragment extends Fragment {

    private EditText editSender, editRecipient, editAmount;
    private TextView textSelectedDate, textTransferHistory;
    private Button btnPickDate, btnSaveTransfer;
    private SharedPreferences sharedPreferences;
    private String selectedDate = "";

    public MoneyTransferFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_transfer, container, false);

        sharedPreferences = requireContext().getSharedPreferences("transfer_data", Context.MODE_PRIVATE);

        editSender = view.findViewById(R.id.edit_sender);
        editRecipient = view.findViewById(R.id.edit_recipient);
        editAmount = view.findViewById(R.id.edit_amount);
        textSelectedDate = view.findViewById(R.id.text_selected_date);
        textTransferHistory = view.findViewById(R.id.text_transfer_history);
        btnPickDate = view.findViewById(R.id.btn_pick_date);
        btnSaveTransfer = view.findViewById(R.id.btn_save_transfer);

        btnPickDate.setOnClickListener(v -> openDatePicker());

        btnSaveTransfer.setOnClickListener(v -> {
            saveTransfer();
            loadTransfers();
        });

        loadTransfers();

        return view;
    }

    private void openDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDate = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                    textSelectedDate.setText("Transfer Date: " + selectedDate);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveTransfer() {
        String sender = editSender.getText().toString().trim();
        String recipient = editRecipient.getText().toString().trim();
        String amount = editAmount.getText().toString().trim();

        if (!sender.isEmpty() && !recipient.isEmpty() && !amount.isEmpty() && !selectedDate.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String transferKey = sender + "->" + recipient + " on " + selectedDate;
            editor.putString(transferKey, "$" + amount);
            editor.apply();

            editSender.setText("");
            editRecipient.setText("");
            editAmount.setText("");
            textSelectedDate.setText("Transfer Date: ");
            selectedDate = "";
        }
    }

    private void loadTransfers() {
        Map<String, ?> transfers = sharedPreferences.getAll();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, ?> entry : transfers.entrySet()) {
            builder.append(entry.getKey())
                    .append(" - Amount: ").append(entry.getValue().toString())
                    .append("\n\n");
        }
        textTransferHistory.setText(builder.toString().isEmpty() ? "No Transfers Recorded." : builder.toString());
    }
}
