package com.example.cashtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {

    private EditText editName, editUsername, editEmail, editPassword, editDob;

    public ProfileFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editName = view.findViewById(R.id.edit_name);
        editUsername = view.findViewById(R.id.edit_username);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
        editDob = view.findViewById(R.id.edit_dob);
        Button btnUpdate = view.findViewById(R.id.btn_update_profile);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_profile", Context.MODE_PRIVATE);
        editName.setText(prefs.getString("name", ""));
        editUsername.setText(prefs.getString("username", ""));
        editEmail.setText(prefs.getString("email", ""));
        editPassword.setText(prefs.getString("password", ""));
        editDob.setText(prefs.getString("dob", ""));

        btnUpdate.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", editName.getText().toString().trim());
            editor.putString("username", editUsername.getText().toString().trim());
            editor.putString("email", editEmail.getText().toString().trim());
            editor.putString("password", editPassword.getText().toString().trim());
            editor.putString("dob", editDob.getText().toString().trim());
            editor.apply();

            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
