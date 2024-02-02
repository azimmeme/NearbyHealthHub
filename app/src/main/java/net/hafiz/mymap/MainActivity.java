package net.hafiz.mymap;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Button button;
    Button sendlocation;
    double latitude, longitude;
    String latitudeString, longitudeString, address, fullName;
    Location location;
    TextView textView_location, textViewGreeting;
    LocationManager locationManager;



    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        textView_location = findViewById(R.id.textView);
        sendlocation = findViewById(R.id.sendlocation);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        // Inside the onClick() method of sendlocation button
        sendlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                retrieveFullNameFromServer();
                if (ContextCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 1000, 10,
                            new LocationListener() {
                                @Override
                                public void onLocationChanged(@NonNull Location location) {
                                    MainActivity.this.location = location;
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                    latitudeString = String.format("%.6f", latitude);
                                    longitudeString = String.format("%.6f", longitude);

                                    // Get the address
                                    try {
                                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                        if (addresses.size() > 0) {
                                            String address = addresses.get(0).getAddressLine(0);
                                            sendLocationToServer(latitudeString, longitudeString, address,fullName);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    locationManager.removeUpdates(this);
                                }
                            }
                    );
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MapsActivity.class);
                startActivity(intent);


            }
        });



        retrieveFullNameFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about_us:
                Intent aboutUsIntent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(aboutUsIntent);
                return true;
            case R.id.menu_logout:
                // Handle logout
                clearUserSession();
                // Redirect to LoginActivity
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();  // Optional: finish the MainActivity to prevent going back
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void retrieveFullNameFromServer() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Send a request to your PHP script to get the full name based on the username
        // Modify the URL accordingly
        String url = "http://172.20.10.4/mymap/get_fullname.php?username=" + username;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Check if the response contains the full name
                        if (!response.isEmpty()) {
                            // Set the value of fullName
                            fullName = response;

                            // Display the full name in a TextView or any other UI component
                            TextView textViewGreeting = findViewById(R.id.textViewGreeting);
                            textViewGreeting.setText("Hello, " + fullName + " !\nWelcome to Nearby Health Hub");
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error retrieving full name: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    // Add a new method to send location to server
    private void sendLocationToServer(String latitude, String longitude, String address, String fullName) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://172.20.10.4/mymap/location.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Location sent successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error Response: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("lat", latitude);
                paramV.put("lng", longitude);
                paramV.put("address", address);
                paramV.put("fullname", fullName);

                // Include user agent in the 'user_agent' parameter
                String userAgent = System.getProperty("http.agent");
                paramV.put("user_agent", userAgent);

                // Include date/time information in the 'datetime' parameter
                String dateTime = getCurrentDateTime();
                paramV.put("datetime", dateTime);


                return paramV;
            }
        };
        queue.add(stringRequest);
    }

    // Helper method to get the current date and time
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




    private void clearUserSession() {
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        try{
            locationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainActivity.this);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+ location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        try{
            Geocoder geocoder= new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address>addresses= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            String address= addresses.get(0).getAddressLine(0);

            textView_location.setText("Current Location:  " + address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}