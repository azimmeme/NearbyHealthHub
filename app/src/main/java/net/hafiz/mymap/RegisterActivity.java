package net.hafiz.mymap;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextRegisterUsername;
    private EditText editTextRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);

        Button buttonRegisterUser = findViewById(R.id.buttonRegisterUser);

        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editTextFullName.getText().toString();
                String username = editTextRegisterUsername.getText().toString();
                String password = editTextRegisterPassword.getText().toString();

                // Send user details to PHP script
                registerUser(fullName, username, password);
            }
        });
    }

    private void registerUser(final String fullName, final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Specify the URL of your PHP script
                    URL url = new URL("http://172.20.10.4/mymap/register.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Create the POST data
                    String postData = URLEncoder.encode("fullName", "UTF-8") + "=" + URLEncoder.encode(fullName, "UTF-8")
                            + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
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

                    // Display the response on the UI thread (you can use runOnUiThread for this)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(response.toString());

                            // If registration is successful, navigate to LoginActivity
                            if (response.toString().trim().equalsIgnoreCase("User registered successfully")) {
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
