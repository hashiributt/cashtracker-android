package com.example.cashtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editName, editUsername, editEmail, editPassword, editDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
        if (!prefs.getString("username", "").isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        if (!prefs.getString("username", "").isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        editName = findViewById(R.id.edit_full_name);
        editUsername = findViewById(R.id.edit_username);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        editDob = findViewById(R.id.edit_dob);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = editName.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String dob = editDob.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences("user_profile", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("dob", dob);
        editor.apply();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
