package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.ui.activity.MainActivity;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

public class CPCheckUserActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private KeyboardAdapter mAdapter;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpcheck_user);
        mRecyclerView = findViewById(R.id.keyboard);
        mEditText = findViewById(R.id.cpc_user);
        mEditText.setInputType(InputType.TYPE_NULL);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
              if (position == 10) {
                  return 2;
              } else {
                  return 1;
              }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new KeyboardAdapter(KeyboardUtils.withoutPoint(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void backHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void back(View view) {
        finish();
    }
}
