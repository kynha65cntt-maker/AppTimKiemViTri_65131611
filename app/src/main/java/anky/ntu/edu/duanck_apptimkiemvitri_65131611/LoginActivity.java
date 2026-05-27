package anky.ntu.edu.duanck_apptimkiemvitri_65131611;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView txtGoRegister;
    EditText emailEditText;
    Button btnLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(v -> loginUser());
        txtGoRegister = findViewById(R.id.txtGoRegister);

        txtGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
``
    }
    private void loginUser() {
        String userEmail = emailEditText.getText().toString();
        String userPass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
