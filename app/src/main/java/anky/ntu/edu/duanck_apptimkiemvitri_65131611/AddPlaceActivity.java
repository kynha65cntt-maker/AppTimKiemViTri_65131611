package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPlaceActivity extends AppCompatActivity {
    EditText name, desc;
    Button btnSave;

    DatabaseReference database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_place);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        btnSave = findViewById(R.id.btnSave);
        database = FirebaseDatabase.getInstance().getReference("Places");
        btnSave.setOnClickListener(v -> savePlace());

    }
    private void savePlace() {
        String id = databse.push().getKey();
        Place place = new Place(id, name.getText().toString(), desc.getText().toString());
        databae.child(id).setValue(place);
    }
}