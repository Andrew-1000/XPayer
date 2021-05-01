package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Verification extends AppCompatActivity {

    private Button continues;
    private TextInputEditText otpEdit;
    private String OTP;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        continues = findViewById(R.id.continues);
        firebaseAuth = FirebaseAuth.getInstance();
        otpEdit = findViewById(R.id.optEdit);
        OTP = getIntent().getStringExtra("auth");


        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void signIn(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendtoMain();
                } else {
                    Toast.makeText(Verification.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendtoMain() {

        startActivity(new Intent(Verification.this, ProjectPayer.class ));
        finish();
    }

    private void verify() {
      String verification_code = otpEdit.getText().toString();
      if (!verification_code.isEmpty()) {
          PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, verification_code);
          signIn(credential);
      } else {
          Toast.makeText(Verification.this, "Please Enter OTP", Toast.LENGTH_LONG).show();
      }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUer = firebaseAuth.getCurrentUser();
        if (currentUer !=null) {
            sendtoMain();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SimpleStart.class));
        finish();
    }
}