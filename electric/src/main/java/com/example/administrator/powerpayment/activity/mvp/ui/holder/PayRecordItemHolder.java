package com.example.administrator.powerpayment.activity.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RecordInfo;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/25.
 */

public class PayRecordItemHolder extends BaseHolder<RecordInfo> {

    @BindView(R.id.pr_trans_date)
    TextView trans_date;
    @BindView(R.id.pr_trans_no)
    TextView trans_no;
    @BindView(R.id.pr_trans_amount)
    TextView trans_amount;

    public PayRecordItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(RecordInfo data, int position) {
        Observable.just(data.getTrans_date())
                .subscribe(s -> {
                    trans_date.setText(s);
                });
        Observable.just(data.getGrid_trans_no())
                .subscribe(s -> {
                    trans_no.setText(s);
                });
        Observable.just(data.getAmount())
                .subscribe(s -> {
                    trans_amount.setText(s);
                });
    }
}
