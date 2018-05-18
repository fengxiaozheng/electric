package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.presenter.RewirteCardPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteCardFragment extends BaseFragment<RewirteCardPresenter, PaymentActivity> {

    @BindView(R.id.re_times)
    LinearLayout mLinearLayout;


    public RewriteCardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewriteCardFragment newInstance() {
        return new RewriteCardFragment();
    }



    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewrite_card, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.re_times)
    void click() {
        activity.start(RewriteCardFragment.this, new RewriteInputFragment(),
                "RewriteInputFragment");
    }
}
