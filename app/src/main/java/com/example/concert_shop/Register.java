package com.example.concert_shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        textView = findViewById(R.id.udvAzAppban);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(fadein);
    }

    public void submitButtonClicked(View view) {
        registerUser();
    }

    public void cancelButtonClicked(View view) {
        finish();
    }

    private void registerUser() {
        String usernamestr = editTextUsername.getText().toString().trim();
        String emailstr = editTextEmail.getText().toString().trim();
        String passwordstr = editTextPassword.getText().toString().trim();
        String confirmpasswordstr = editTextConfirmPassword.getText().toString().trim();

        if (usernamestr.isEmpty() || emailstr.isEmpty() || passwordstr.isEmpty() || confirmpasswordstr.isEmpty()) {
            Toast.makeText(Register.this, "Tolts ki minden mezot!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordstr.equals(confirmpasswordstr)) {
            Toast.makeText(Register.this, "A jelszavaid nem egyeznek meg!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailstr, passwordstr)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Sikeres regisztracio!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register.this, "Regisztráció sikertelen.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}