package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private EditText editVerifyPassword;
    private MaterialButton buttonSignUp;
    private TextView textSignIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editVerifyPassword = findViewById(R.id.editVerifyPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textSignIn = findViewById(R.id.textSignIn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // Events
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(
                        editEmail.getText().toString(),
                        editPassword.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Add new user to Realtime Database
                            // id, email, username, phone, photo
                            DatabaseReference refUser =
                                    mDatabase
                                            .getReference()
                                            .child("user");
                            DatabaseReference refUserId =
                                    refUser.child(mAuth.getCurrentUser().getUid());
                            UserModel user = new UserModel(
                                    mAuth.getCurrentUser().getUid(),
                                    editEmail.getText().toString(),
                                    "",
                                    "",
                                    ""
                            );
                            refUserId.setValue(user);

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sign up successful",
                                    Toast.LENGTH_LONG
                            ).show();

                            // Chuyển hướng về SignInActivity sau khi đăng ký thành công
                            Intent intent = new Intent(
                                    SignUpActivity.this,
                                    SignInActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Log.d("FIREBASE", task.getException().getMessage());
                        }
                    }
                });
            }
        });

        // Sự kiện cho TextView để chuyển đến SignInActivity
        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển hướng về SignInActivity khi người dùng nhấn
                Intent intent = new Intent(
                        SignUpActivity.this,
                        SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
