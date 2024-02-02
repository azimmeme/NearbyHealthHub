package net.hafiz.mymap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement your login logic here
                if (isValidLogin()) {
                    saveUserSession();
                    goToMainActivity();
                } else {
                    Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement your registration logic here
                // You may open a new activity for registration
                // or show a registration dialog
            }
        });
    }

    private boolean isValidLogin() {
        // Replace this with your actual login validation logic
        return editTextUsername.getText().toString().equals("user") &&
                editTextPassword.getText().toString().equals("password");
    }

    private void saveUserSession() {
        // Save user details in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", editTextUsername.getText().toString());
        editor.apply();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
