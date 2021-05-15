package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.adapter.UtilityPaymentsAdapter;
import com.example.testproject.models.UtilityPayments;
import com.example.testproject.utils.ItemAnimation;
import com.example.testproject.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class InternetList extends AppCompatActivity {
    private View parent_view;
    private RecyclerView recyclerView;
    private UtilityPaymentsAdapter adapter;
    private List<UtilityPayments> paymentItems = new ArrayList<>();
    private int animation_type = ItemAnimation.FADE_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
    }

    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        paymentItems = DataGenerator.getInternetData(this);

        setAdapter();
    }

    private void setAdapter() {
        adapter = new UtilityPaymentsAdapter(paymentItems, this, animation_type);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UtilityPaymentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, UtilityPayments obj, int position) {
                showPaymentDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_refresh:
                setAdapter();
                break;
            case R.id.action_mode:
                showPaymentDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String[] PAYMENT_TYPE = new String[]{
            "M-PESA", "Cash"
    };
    private void toPayWithCashDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("To buy with cash..");
        builder.setMessage("Kindly visit any nearest shop to buy credit");
        builder.setCancelable(true);
        builder.show();
    }
    private void showPaymentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Buy with..");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(PAYMENT_TYPE, -1, (dialog, which) -> {
            String selected = PAYMENT_TYPE[which];
            if (selected.equalsIgnoreCase("M-PESA")) {
                startMpesaApp();
            } else if (selected.equalsIgnoreCase("Cash")) {
                toPayWithCashDialog();

            }
            getSupportActionBar().setTitle(selected);
            setAdapter();
            dialog.dismiss();
        });
        builder.show();
    }

    private void startMpesaApp() {
        Intent launchApp = getPackageManager().getLaunchIntentForPackage(
                "com.safaricom.mpesa.lifestyle");
        if (launchApp != null) {
            startActivity(launchApp);
        } else {
            Toasty.error(getApplicationContext(), "Application Not Found!",
                    Toasty.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ProjectPayer.class));
        finish();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Internet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

    }
}

