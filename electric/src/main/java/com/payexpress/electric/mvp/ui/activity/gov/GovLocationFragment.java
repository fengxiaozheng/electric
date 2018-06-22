package com.payexpress.electric.mvp.ui.activity.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.di.component.gov.DaggerGovLocationComponent;
import com.payexpress.electric.di.module.gov.GovLocationModule;
import com.payexpress.electric.mvp.contract.gov.GovLocationContract;
import com.payexpress.electric.mvp.model.entity.govEntity.GovLocationInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovLocationRes;
import com.payexpress.electric.mvp.presenter.gov.GovLocationPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GovLocationFragment extends BaseGovFragment<GovLocationPresenter>
    implements GovLocationContract.View, View.OnClickListener,
        AMap.OnMarkerClickListener{

    private MapView mMapView;
    private AMap mAMap;
    private CardView mCardView;
    private ImageView mClose;
    private TextView mName;
    private TextView mAddress;
    private TextView mTel;
    private TextView mTime;
    private ImageView mImg;
    private List<GovLocationInfo> infos;

    public GovLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovLocationComponent.builder()
                .appComponent(appComponent)
                .govLocationModule(new GovLocationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.mapView);
        mCardView = view.findViewById(R.id.item_detail);
        mClose = view.findViewById(R.id.item_close);
        mName = view.findViewById(R.id.lo_name);
        mAddress = view.findViewById(R.id.lo_address);
        mTel = view.findViewById(R.id.lo_tel);
        mTime = view.findViewById(R.id.lo_time);
        mImg = view.findViewById(R.id.lo_img);
        mClose.setOnClickListener(this);
        if (mPresenter != null) {
            mPresenter.getGovLocation();
        }
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMap.setOnMarkerClickListener(this);
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(30.651105,104.076261), 13));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success(GovLocationRes res) {
        infos = res.getData();
        if (mPresenter != null) {
            mAMap.addMarkers(mPresenter.getMarkerOptionsT(res.getData()), false);
        }
    }

    @Override
    public void fail(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_close) {
            mCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getLongitude() == marker.getPosition().longitude
                    && infos.get(i).getLatitude() == marker.getPosition().latitude) {
                    showDetail(infos.get(i));
            }
        }
        return true;
    }

    private void showDetail(GovLocationInfo info) {
        mName.setText(String.format("名称：%s", info.getName()));
        mAddress.setText(String.format("地址：%s", info.getAddress()));
        mTime.setText(String.format("上班时间：%s~%s", info.getWorkStart(), info.getWorkEnd()));
        mTel.setText(String.format("电话：%s", info.getTel()));
    //    Glide.with(this).load(info.getImageUrl()).into(mImg);
        mCardView.setVisibility(View.VISIBLE);
    }
}
