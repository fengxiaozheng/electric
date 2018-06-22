package com.payexpress.electric.mvp.presenter.gov;

import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.contract.gov.GovLocationContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovLocationContract.View;
import com.payexpress.electric.mvp.model.entity.govEntity.GovLocationInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovLocationRes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
public class GovLocationPresenter extends BasePresenter<Model, View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GovLocationPresenter(Model model, View view) {
        super(model, view);
    }

    public void getGovLocation() {
        mModel.getGovLocation()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovLocationRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovLocationRes govLocationRes) {
                        if (govLocationRes.isSuccess()) {
                            mRootView.success(govLocationRes);
                        } else {
                            mRootView.fail(govLocationRes.getMessage());
                        }
                    }
                });
    }

    public ArrayList<MarkerOptions> getMarkerOptionsT(List<GovLocationInfo> data) {
        ArrayList<MarkerOptions> arr = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MarkerOptions options = new MarkerOptions();
            android.view.View view = android.view.View.inflate(mRootView.getActivity(), R.layout.custom_view, null);
            TextView textView = view.findViewById(R.id.title);
            textView.setText(data.get(i).getAddress());
            //图标
            options.icon(BitmapDescriptorFactory.fromView(view));
            //位置
            options.position(new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude()));
        //    StringBuffer buffer = new StringBuffer();
//            buffer.append(data.get(i).getAddress());
//            //标题
//            options.title(buffer.toString());
//            //子标题
//            options.snippet("");
            //设置多少帧刷新一次图片资源
            options.period(60);
            arr.add(options);
        }
        return arr;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}
