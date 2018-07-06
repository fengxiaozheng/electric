package com.example.administrator.powerpayment.activity.mvp.ui.adapter;

import android.view.View;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RecordInfo;
import com.example.administrator.powerpayment.activity.mvp.ui.holder.PayRecordItemHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/5/25.
 */

public class PayRecordAdapter extends DefaultAdapter<RecordInfo> {

    public PayRecordAdapter(List<RecordInfo> infos) {
        super(infos);
    }
    @Override
    public BaseHolder<RecordInfo> getHolder(View v, int viewType) {
        return new PayRecordItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_payrecord;
    }
}
