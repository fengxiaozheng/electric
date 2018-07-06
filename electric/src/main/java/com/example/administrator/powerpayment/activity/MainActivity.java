package com.example.administrator.powerpayment.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        startActivity(new Intent(this, LifeChooseActivity.class));
    }

    public void toGov(View view) {
        startActivity(new Intent(this, GovernmentActivity.class));
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