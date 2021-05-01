package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TwoFactorAuthentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor_authentication);
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, SimpleStart.class);
        startActivity(back);
    }
}