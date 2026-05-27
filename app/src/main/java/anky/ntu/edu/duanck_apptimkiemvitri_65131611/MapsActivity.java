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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @ Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng myLocation = new LatLng(10.762622, 106.660172);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
    }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("places");

    database.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            for (DataSnapshot data : snapshot.getChildren()) {
                Place p = data.getValue(Place.class);

                LatLng loc = new LatLng(p.latitude, p.longitude);
                mMap.addMarker(new MarkerOptions().position(loc).title(p.name));
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {

        }
    });
}