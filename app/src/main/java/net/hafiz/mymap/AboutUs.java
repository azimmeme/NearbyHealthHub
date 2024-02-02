package net.hafiz.mymap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Find the TextView by its ID
        TextView urlTextView = findViewById(R.id.urlTextView);

        // Create a SpannableString with an underline
        SpannableString spannableString = new SpannableString("Click here to open GitHub");
        spannableString.setSpan(new URLSpan("https://www.github.com"), 0, spannableString.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the SpannableString to the TextView
        urlTextView.setText(spannableString);
        urlTextView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

        // Set an onClickListener for the TextView
        urlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Replace "https://www.github.com" with your actual GitHub URL
                    String githubUrl = "https://www.github.com";

                    // Create an Intent to open the URL in a browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                    startActivity(intent);

                    // Display a Snackbar
                    showSnackbar("Opening GitHub URL...");

                } catch (Exception e) {
                    // Handle exceptions by printing the stack trace
                    e.printStackTrace();
                    // Display a Snackbar with an error message
                    showSnackbar("Error opening URL. Please try again.");
                }
            }
        });
    }

    private void showSnackbar(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
