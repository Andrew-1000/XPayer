package com.example.testproject;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.testproject.utils.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;


public class ProjectPayer extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private LinearLayout power, water, mobile, landline, cabletv, internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_payer);
        power = findViewById(R.id.power);
        water = findViewById(R.id.water);
        water.setOnClickListener(v-> waterPay());
        mobile = findViewById(R.id.mobile);
        mobile.setOnClickListener(v-> mobilePay());
        landline = findViewById(R.id.landline);
        landline.setOnClickListener(v-> landlinePay());
        cabletv = findViewById(R.id.cabletv);
        cabletv.setOnClickListener(v-> cableTvPay());
        internet = findViewById(R.id.internet);
        internet.setOnClickListener(v-> internetPay());
        power.setOnClickListener(v -> openElectricityPay());

        mAuth = FirebaseAuth.getInstance();


        initToolbar();
    }

    private void cableTvPay() {
        startActivity(new Intent(getApplicationContext(), CableTVList.class));
        finish();
    }

    private void landlinePay() {
        startActivity(new Intent(ProjectPayer.this, PostPayList.class));
        finish();
    }

    private void mobilePay() {
        startActivity(new Intent(ProjectPayer.this, MobileList.class));
        finish();
    }

    private void waterPay() {
        startActivity(new Intent(ProjectPayer.this, WaterList.class));
        finish();
    }

    private void internetPay() {
        startActivity(new Intent(getApplicationContext(), InternetList.class));
        finish();
    }

    private void openElectricityPay() {
        startActivity(new Intent(ProjectPayer.this, UtilityList.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, SimpleStart.class));
            finish();
        } else {
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.indigo_500), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.indigo_500));
        Tools.changeOverflowMenuIconColor(toolbar,getResources().getColor(R.color.indigo_500));
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
        Toasty.info(getApplicationContext(), "Logged Out", Toasty.LENGTH_LONG).show();
        startActivity(new Intent(ProjectPayer.this, SimpleStart.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        recreate();
    }
}