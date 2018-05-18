package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.payexpress.electric.R;

public class RewriteCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewrite_card);
    }

    public void backHome(View view) {
        finish();
    }

    public void back(View view) {
        finish();
    }
}
