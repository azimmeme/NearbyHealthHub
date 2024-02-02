package net.hafiz.mymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Intent intent = getIntent();
        if (intent.hasExtra("fullName") && intent.hasExtra("username") && intent.hasExtra("password")) {
            String fullName = intent.getStringExtra("fullName");
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");

            editTextUsername.setText(username);
            editTextPassword.setText(password);
        }

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add logic for user authentication (e.g., check username and password)
                // For simplicity, let's assume a hardcoded username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Send user credentials to PHP script for authentication
                authenticateUser(username, password);


            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add logic for user registration (if needed)
                // For simplicity, registration is not implemented in this example
// Start the RegisterActivity when the Register button is clicked
                openRegisterActivity();            }
        });
    }

    private void authenticateUser(final String username, final String password) {
        // Perform network operations in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Specify the URL of your login PHP script
                    URL url = new URL("http://172.20.10.4/mymap/login.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Create the POST data
                    String postData = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                            + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                    // Send the POST data
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(postData);
                    writer.flush();

                    // Get the response from the server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    final StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Close connections
                    writer.close();
                    reader.close();
                    connection.disconnect();

                    // Display the response on the UI thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleAuthenticationResponse(response.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleAuthenticationResponse(String response) {
        if (response.trim().equalsIgnoreCase("Login successful")) {
            saveUserSession(editTextUsername.getText().toString());
            openMainActivity();
        } else {
            showToast("Invalid username or password");
        }
    }

    private void openRegisterActivity() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void saveUserSession(String username) {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void openMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();  // Optional: finish the LoginActivity to prevent going back
    }

    private void showToast(String message) {
        // Utility method to show toast messages
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
