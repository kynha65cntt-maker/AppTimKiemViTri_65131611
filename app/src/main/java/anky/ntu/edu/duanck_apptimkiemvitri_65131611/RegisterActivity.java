package anky.ntu.edu.duanck_apptimkiemvitri_65131611;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmailRegister, edtPasswordRegister, edtConfirmPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = edtEmailRegister.getText().toString().trim();
        String password = edtPasswordRegister.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmailRegister.setError("Email không được để trống");
            edtEmailRegister.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailRegister.setError("Email không hợp lệ");
            edtEmailRegister.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPasswordRegister.setError("Mật khẩu không được để trống");
            edtPasswordRegister.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPasswordRegister.setError("Mật khẩu phải từ 6 ký tự");
            edtPasswordRegister.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            edtConfirmPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Đăng ký thất bại: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
