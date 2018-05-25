package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.di.component.DaggerElectricPayComponent;
import com.payexpress.electric.di.module.EleCtricPayModule;
import com.payexpress.electric.mvp.contract.ElectricPayContract;
import com.payexpress.electric.mvp.model.entity.payment.ElectricPayInfo;
import com.payexpress.electric.mvp.presenter.ElectricPayPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import me.drakeet.materialdialog.MaterialDialog;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartPayFragment extends BaseFragment<ElectricPayPresenter, PaymentActivity>
        implements ElectricPayContract.PayView, QRCodeView.Delegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private ElectricPayInfo mParam;

    @BindView(R.id.zbarview)
    ZBarView mQRCodeView;



    public StartPayFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StartPayFragment newInstance(ElectricPayInfo info) {
        StartPayFragment fragment = new StartPayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (ElectricPayInfo) getArguments().getSerializable(ARG_PARAM);
        }
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerElectricPayComponent.builder()
                .appComponent(appComponent)
                .eleCtricPayModule(new EleCtricPayModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_pay, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mQRCodeView.setDelegate(this);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success() {
        activity.dismissDialog();
        activity.start(StartPayFragment.this,
                PayResultFragment.newInstance(mParam), "PayResultFragment");


    }

    @Override
    public void fail(String msg) {
        activity.dismissDialog();
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkPay(int flag, String transNo) {
        activity.dismissDialog();

        MaterialDialog dialog = new MaterialDialog(activity);
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_content,
                        null);
        dialog.setCanceledOnTouchOutside(false);
        TextView user = view.findViewById(R.id.dg_user_no);
        TextView a = view.findViewById(R.id.dg_amount);
        user.setText(String.format("电力用户号：%s", mParam.getUser_no()));
        a.setText(String.format("购电金额：%s", mParam.getPay_amount()));
        dialog.setTitle("提示")
                .setView(view)
                .setPositiveButton("已支付", v -> {
                        dialog.dismiss();
                        activity.showDialog();
                        mPresenter.checkPayStatus(true, flag, transNo);
                })
                .setNegativeButton("取消", v -> {
                    dialog.dismiss();
                }).show();
    }


    @Override
    public void showMessage(@NonNull String message) {

    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.stopSpot();
        activity.showDialog();
        if (mParam.getFlag() == 0) {
            mPresenter.qrCodePay(0, mParam.getUser_no(), mParam.getPay_amount(), result);
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(activity, "打开相机错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    public void onStop() {
        if (mQRCodeView != null) {
            mQRCodeView.stopCamera();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mQRCodeView != null) {
            mQRCodeView.stopCamera();
        }
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

}
