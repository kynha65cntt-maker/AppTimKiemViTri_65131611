package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import anky.ntu.edu.duanck_apptimkiemvitri_65131611.ui.place.Place;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Place.newInstance())
                    .commitNow();
        }
    }
}