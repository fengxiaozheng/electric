package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.payexpress.electric.R;

public class CommonPowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_power);
        findViewById(R.id.c_pay).setOnClickListener(view ->
        startActivity(new Intent(this, CPCheckUserActivity.class)));
    }

    public void backHome(View view) {
        finish();
    }

    public void back(View view) {
        finish();
    }
}
