package com.payexpress.electric.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.payment.RecordInfo;
import com.payexpress.electric.mvp.ui.holder.PayRecordItemHolder;

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
