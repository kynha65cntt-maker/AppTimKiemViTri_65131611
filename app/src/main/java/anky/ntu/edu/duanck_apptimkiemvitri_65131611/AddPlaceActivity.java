package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import static anky.ntu.edu.duanck_apptimkiemvitri_65131611.MapsActivity.LOCATION_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place.Place;

public class AddPlaceActivity extends AppCompatActivity {
    private EditText name, desc;
    private Button btnSave;
    private textView txtLocation;
    private DatabaseReference database;

    private FusedLocationProviderClient fusedLocationClient;

    private double currentLat = 0.0;
    private double currentLng = 0.0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_place);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        btnSave = findViewById(R.id.btnSave);
        txtLocation = findViewById(R.id.txtLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        database = FirebaseDatabase.getInstance().getReference("Places");

        double intentLat = getIntent().getDoubleExtra("selectedLat", 0.0);
        double intentLng = getIntent().getDoubleExtra("selectedLng", 0.0);

        if (intentLat != 0.0 || intentLng != 0.0) {
            currentLat = intentLat;
            currentLng = intentLng;
            txtLocation.setText("Tọa độ được chọn:\nLat = " + currentLat + "\nLng = " + currentLng);
        } else {
            getCurrentLocation();
        }

        btnSave.setOnClickListener(v -> savePlace());

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE
            );
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLat = location.getLatitude();
                currentLng = location.getLongitude();

                txtLocation.setText("Tọa độ GPS hiện tại:\nLat = " + currentLat + "\nLng = " + currentLng);
            } else {
                txtLocation.setText("Không lấy được vị trí. Hãy bật GPS và thử lại.");
            }
        }).addOnFailureListener(e ->
                txtLocation.setText("Lỗi lấy vị trí: " + e.getMessage())
        );
    }
    private void savePlace() {

        String placeName = name.getText().toString().trim();
        String placeDesc = desc.getText().toString().trim();

        if (placeName.isEmpty()) {
            name.setError("Nhập tên địa điểm");
            name.requestFocus();
            return;
        }

        if (placeDesc.isEmpty()) {
            desc.setError("Nhập mô tả");
            desc.requestFocus();
            return;
        }

        if (currentLat == 0.0 && currentLng == 0.0) {
            Toast.makeText(this, "Chưa lấy được GPS thật. Hãy bật vị trí và thử lại.", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = database.push().getKey();
        Place place = new Place(placeName, placeDesc, currentLat, currentLng);

        if (id != null) {
            database.child(id).setValue(place)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Lưu địa điểm thành công", Toast.LENGTH_SHORT).show();
                        finish(); // quay về map
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi lưu dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền vị trí", Toast.LENGTH_SHORT).show();
            }
        }
    }
}