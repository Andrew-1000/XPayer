package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SimpleStart extends AppCompatActivity {
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_start);
        parent_view = findViewById(android.R.id.content);
        progressBar = findViewById(R.id.progress_bar);
        fab = findViewById(R.id.fab);

        ((View) findViewById(R.id.sign_up_for_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(parent_view, "Sign Up for an account", Snackbar.LENGTH_LONG).show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAction();
            }
        });
    }

    private void findAction() {
        Intent twoFactor = new Intent(this, Verification.class);
        twoFactor.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        progressBar.setVisibility(View.VISIBLE);
        fab.setAlpha(0f);
        startActivity(twoFactor);
        Toast.makeText(this, "Code sent to your Phone", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                fab.setAlpha(1f);

            }
        }, 1000);


    }
}