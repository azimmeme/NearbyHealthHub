package net.hafiz.mymap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import net.hafiz.mymap.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap;


    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityMapsBinding binding;

    MarkerOptions marker;

    LatLng centerlocation;

    Vector<MarkerOptions> markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        centerlocation = new LatLng(6.452138, 100.277798);

        markerOptions = new Vector<>();

        markerOptions.add(new MarkerOptions().title("Klinik Jejawi")
                .position(new LatLng(6.4455, 100.2379))
                .snippet("Tengah dok buka")
        );
        markerOptions.add(new MarkerOptions().title("Hospital Kangar")
                .position(new LatLng(6.4409, 100.1914))
                .snippet("24 Jam")
        );
        markerOptions.add(new MarkerOptions().title("UK Uitm Arau")
                .position(new LatLng(6.4370, 100.2798))
                .snippet("Tengah dok buka")
        );
        markerOptions.add(new MarkerOptions().title("Klinik Kesihatan Pauh")
                .position(new LatLng(6.4455, 100.3030))
                .snippet("24 Jam")
        );
        markerOptions.add(new MarkerOptions().title("KPJ Perlis Specialist Hospital")
                .position(new LatLng(6.43904, 100.18610))
                .snippet("Tengah dok buka")
        );
        markerOptions.add(new MarkerOptions().title("Klinik Desa Santan")
                .position(new LatLng(6.47827, 100.23005))
                .snippet("8am - 6pm")
        );


    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentlocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        //mMap.addMarker(marker);

        LatLng sydney = new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Current Location"));
        for(MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        enableMyLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,8));

        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this, "Location permission is denied, please allow permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enableMyLocation() {

        //String perms[] = {"android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                //Log.d("hafizxx","permission granted");
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission

            //Log.d("hafizxx","permission denied");
            ActivityCompat.requestPermissions(this,perms ,200);

        }
    }
}