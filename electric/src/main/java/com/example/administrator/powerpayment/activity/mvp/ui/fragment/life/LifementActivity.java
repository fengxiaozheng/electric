package com.example.administrator.powerpayment.activity.mvp.ui.fragment.life;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.GovAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;

public class LifementActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private GovAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        String[] titles = {getString(R.string.str_009), getString(R.string.str_010),
                getString(R.string.str_011), getString(R.string.str_012),
                getString(R.string.str_013)};
        int[] imgs = {R.mipmap.ic_wuye, R.mipmap.ic_jiazheng, R.mipmap.ic_shengxian,
                R.mipmap.ic_ershou, R.mipmap.ic_danbao};
        mRecyclerView = findViewById(R.id.life_rv);
        mLayoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 2 || position == 4) {
                    return 1;
                } else {
                    return 3;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GovAdapter(titles, imgs, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
//                Toast.makeText(this, "xx", Toast.LENGTH_SHORT).show();
                ToastUtil.show(this,"xx");
                break;
            default:
                finish();
                break;
        }
    }

    public void backHome(View view) {
        finish();
    }

    public void back(View view) {
        finish();
    }
}
