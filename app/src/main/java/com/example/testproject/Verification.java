package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class Verification extends AppCompatActivity {

    private Button continues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        continues = findViewById(R.id.continues);

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify() {
        startActivity(new Intent(this, ProjectPayer.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SimpleStart.class));
        finish();
    }
}