package anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.google.firebase.database.*;

import anky.ntu.edu.duanck_apptimkiemvitri_65131611.R;
import anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place.Place;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Button btnAdd;

    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //init GPS
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Button Add
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, AddPlaceActivity.class);
            startActivity(intent);
        });

        //Load Map
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }
    // MAP READY
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkLocationPermission();
        loadPlacesFromFirebase();
    }
    // CHECK PERMISSION
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            enableUserLocation();
        }
    }

    // ENABLE GPS + MOVE CAMERA
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {

                            LatLng user = new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude()
                            );

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 17));

                            mMap.addMarker(new MarkerOptions()
                                    .position(user)
                                    .title("You are here"));
                        }
                    });
        }
    }
    //LOAD DATA TỪ FIREBASE
    private void loadPlacesFromFirebase() {

        DatabaseReference database =
                FirebaseDatabase.getInstance().getReference("places");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mMap.clear(); // clear marker cũ

                for (DataSnapshot data : snapshot.getChildren()) {

                    Place p = data.getValue(Place.class);

                    if (p != null) {
                        LatLng loc = new LatLng(p.latitude, p.longitude);

                        mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(p.name)
                                .snippet(p.description));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // PERMISSION RESULT
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                enableUserLocation();
            }
        }
    }
}
