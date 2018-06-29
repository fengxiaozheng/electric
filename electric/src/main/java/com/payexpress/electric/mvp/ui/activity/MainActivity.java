package com.payexpress.electric.mvp.ui.activity;

import android.content.ComponentName;
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
        starGApp3();
    }

    private void starGApp3() {
        try {
            Intent intent = new Intent();
//            ComponentName cn = new ComponentName("com.usw.adplayer", "com.xindian.imediaplayer.LauncherActivity");//本独
            ComponentName cn = new ComponentName("com.usw.adplayer", "com.usw.adplayer.LauncherActivity");
            intent.setComponent(cn);
            intent.setAction("android.intent.action.MAIN");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
