package com.example.testproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class SimpleStart extends AppCompatActivity {
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private View parent_view;

    private TextView processOtp;

    private EditText phoneNumber;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_start);
        parent_view = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.progress_bar);
        fab = findViewById(R.id.fab);
        processOtp = findViewById(R.id.processOtp);
        phoneNumber = findViewById(R.id.phoneNumber);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.sign_up_for_account).setOnClickListener(v -> Snackbar.make(parent_view, "Sign Up for an account", Snackbar.LENGTH_LONG).show());

        fab.setOnClickListener(v -> {
            Toasty.info(SimpleStart.this, "Authorizing....", Toasty.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
            fab.setAlpha(0f);
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                fab.setAlpha(1f);


            }, 10000);

            String pNumber = phoneNumber.getText().toString().trim();
            if (!pNumber.isEmpty()) {
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(pNumber)
                        .setTimeout(60L , TimeUnit.SECONDS)
                        .setActivity(SimpleStart.this)
                        .setCallbacks(mCallBacks)
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            } else
            {
                processOtp.setText(R.string.coder);
                Toasty.error(getApplicationContext(), "Please Provide Country Code and Phone Number", Toasty.LENGTH_LONG).show();
                processOtp.setTextColor(Color.WHITE);
                processOtp.setVisibility(View.VISIBLE);
            }
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                processOtp.setText(e.getMessage());
                processOtp.setTextColor(Color.WHITE);
                processOtp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                processOtp.setText(R.string.sente);
                Toasty.success(SimpleStart.this, "Code has been sent!", Toasty.LENGTH_LONG).show();
                processOtp.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> {

                        Intent otpIntent = new Intent(SimpleStart.this, Verification.class);
                        otpIntent.putExtra("auth", s);
                        startActivity(otpIntent);
                    }, 10000);

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user !=null) {
            sendtoMain();
        }
    }

    private void sendtoMain() {

        Intent mainIntent = new Intent(SimpleStart.this, ProjectPayer.class);
        startActivity(mainIntent);
        Toasty.info(getApplicationContext(), "Welcome Back!", Toasty.LENGTH_LONG).show();
        finish();
    }
    private void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
             if (task.isSuccessful()) {
                 sendtoMain();
                 Toasty.success(SimpleStart.this, "Logged in Successfully!", Toasty.LENGTH_LONG).show();
             } else {
                 processOtp.setText(Objects.requireNonNull(task.getException()).getMessage());
                 processOtp.setTextColor(Color.WHITE);
                 processOtp.setVisibility(View.VISIBLE);
             }
        });
    }

}