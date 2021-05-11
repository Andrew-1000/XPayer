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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class SimpleStart extends AppCompatActivity {
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private View parent_view;

    private TextView processOtp;

    private EditText countryCode, phoneNumber;
    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_start);
        parent_view = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.progress_bar);
        fab = findViewById(R.id.fab);
        processOtp = findViewById(R.id.processOtp);
        countryCode = findViewById(R.id.countryCode);
        phoneNumber = findViewById(R.id.phoneNumber);
        auth = FirebaseAuth.getInstance();

        ((View) findViewById(R.id.sign_up_for_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(parent_view, "Sign Up for an account", Snackbar.LENGTH_LONG).show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Toasty.info(SimpleStart.this, "Authorizing....", Toasty.LENGTH_LONG).show();
                progressBar.setVisibility(View.VISIBLE);
                fab.setAlpha(0f);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        fab.setAlpha(1f);


                    }
                }, 10000);

                String country_code = countryCode.getText().toString().trim();
                String phone_number = phoneNumber.getText().toString().trim();
                String pNumber = "+" + country_code + "" + phone_number;
                if (!country_code.isEmpty() || !phone_number.isEmpty()) {
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent otpIntent = new Intent(SimpleStart.this, Verification.class);
                            otpIntent.putExtra("auth", s);
                            startActivity(otpIntent);
                        }
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
        finish();
    }
    private void signIn(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()) {
                     sendtoMain();
                     Toasty.success(SimpleStart.this, "Logged in Successfully!", Toasty.LENGTH_LONG).show();
                 } else {
                     processOtp.setText(task.getException().getMessage());
                     processOtp.setTextColor(Color.WHITE);
                     processOtp.setVisibility(View.VISIBLE);
                 }
            }
        });
    }





}