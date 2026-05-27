package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import android.location.Location;

import anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place.Place;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {

                        LatLng myLocation = new LatLng(
                                location.getLatitude(),
                                location.getLongitude()
                        );
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
                        mMap.addMarker(new MarkerOptions()
                                .position(myLocation)
                                .title("Your Location"));
                    }
                });
    }
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            for (DataSnapshot data : snapshot.getChildren()) {
                Place p = data.getValue(Place.class);

                LatLng loc = new LatLng(p.latitude, p.longitude);
                mMap.addMarker(new MarkerOptions().position(loc).title(p.name));
            }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            recreate(); // reload lại activity
        }
    }

    @Override
        public void onCancelled(DatabaseError error) {

        }
}