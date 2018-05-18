package com.payexpress.electric.mvp.ui.activity.gov;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.adapter.GovAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

public class GovActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GovAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov);
        String[] titles = {getString(R.string.str_001), getString(R.string.str_002),
                getString(R.string.str_003), getString(R.string.str_004),
                getString(R.string.str_005), getString(R.string.str_006),
                getString(R.string.str_007), getString(R.string.str_008)};
        int[] imgs = {R.mipmap.ic_shebao, R.mipmap.ic_shuiwu, R.mipmap.ic_fangchan,
                R.mipmap.ic_gongan, R.mipmap.ic_gongjijin, R.mipmap.ic_yiliao,
                R.mipmap.ic_jiaoyu, R.mipmap.ic_zhengwuxinxi};
        mRecyclerView = findViewById(R.id.gov_rv);
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GovAdapter(titles, imgs, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "xx", Toast.LENGTH_SHORT).show();
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
