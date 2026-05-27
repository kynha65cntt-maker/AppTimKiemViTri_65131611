package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnAdd = findViewById(R.id.btnAdd);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPlaceActivity.class);
            startActivity(intent);
        });
    }

    }
}