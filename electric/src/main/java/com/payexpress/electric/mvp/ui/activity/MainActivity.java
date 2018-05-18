package com.payexpress.electric.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.gov.GovActivity;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toBPayment(View view) {
        startActivity(new Intent(this, PaymentActivity.class));
    }

    public void toGov(View view) {
        startActivity(new Intent(this, GovActivity.class));
    }

    public void toLife(View view) {
    //    startActivity(new Intent(this, LifeActivity.class));
//        Intent uninstall_intent = new Intent();
//        uninstall_intent.setAction(Intent.ACTION_DELETE);
//        uninstall_intent.setData(Uri.parse("package:"+getApplicationInfo().packageName));
//        startActivity(uninstall_intent);
finish();
    }
}
