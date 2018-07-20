package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovLocationComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovLocationModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovLocationContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovLocationInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovLocationRes;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovLocationPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.ImageLoadingDrawable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GovLocationFragment extends BaseGovFragment<GovLocationPresenter>
    implements GovLocationContract.View, AMap.OnMarkerClickListener{

    private TextureMapView mMapView;
    private AMap mAMap;
    private List<GovLocationInfo> infos;
    private AlertDialog dialog;

    public GovLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.sdcardDir = "/sdcard/offlinemap";
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
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity,msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
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

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.location_detail,
                        null);

        TextView mName = view.findViewById(R.id.lo_name);
        TextView mAddress = view.findViewById(R.id.lo_address);
        TextView mTel = view.findViewById(R.id.lo_tel);
        TextView mTime = view.findViewById(R.id.lo_time);
        SimpleDraweeView mImg = view.findViewById(R.id.lo_img);
        Button mButton = view.findViewById(R.id.lo_ok);
        mName.setText(String.format("名称：%s", info.getName()));
        mAddress.setText(String.format("地址：%s", info.getAddress()));
        mTime.setText(String.format("上班时间：%s~%s", info.getWorkStart(), info.getWorkEnd()));
        mTel.setText(String.format("电话：%s", info.getTel()));
        Uri uri = Uri.parse(info.getUrl()+info.getImageUrl());
        GenericDraweeHierarchyBuilder builder1 =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder1
                .setProgressBarImage(new ImageLoadingDrawable())
                .build();
        mImg.setHierarchy(hierarchy);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mImg.setController(controller);

         dialog = builder.setView(view).create();
        mButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 1000;
        params.height = 650 ;
        dialog.getWindow().setAttributes(params);
    }
}
