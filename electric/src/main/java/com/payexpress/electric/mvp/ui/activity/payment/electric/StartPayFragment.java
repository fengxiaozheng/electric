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

import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.Psamcmd;
import com.payexpress.electric.di.component.payment.DaggerElectricPayComponent;
import com.payexpress.electric.di.module.payment.EleCtricPayModule;
import com.payexpress.electric.mvp.contract.payment.ElectricPayContract;
import com.payexpress.electric.mvp.model.entity.paymentEntity.ElectricPayInfo;
import com.payexpress.electric.mvp.presenter.payment.ElectricPayPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;

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
public class StartPayFragment extends BasePaymentFragment<ElectricPayPresenter>
        implements ElectricPayContract.PayView, QRCodeView.Delegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private ElectricPayInfo mParam;

    @BindView(R.id.zbarview)
    ZBarView mQRCodeView;
    private MaterialDialog dialog;

    private boolean isFirst = false;



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
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success() {
        activity.dismissDialog();
        mParam.setSuccess(true);
        activity.start(StartPayFragment.this,
                PayResultFragment.newInstance(mParam), "PayResultFragment");


    }

    @Override
    public void fail(String msg) {
        activity.dismissDialog();
        mParam.setSuccess(false);
        mParam.setWriteCard(msg);
        activity.start(StartPayFragment.this,
                PayResultFragment.newInstance(mParam), "PayResultFragment");


    }

    @Override
    public void checkPay(int flag, String transNo) {
        activity.dismissDialog();

        dialog = new MaterialDialog(activity);
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
    public void payFail() {
        activity.dismissDialog();

        dialog = new MaterialDialog(activity);
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_content,
                        null);
        dialog.setCanceledOnTouchOutside(false);
        TextView user = view.findViewById(R.id.dg_user_no);
        TextView s = view.findViewById(R.id.content_fail);
        TextView a = view.findViewById(R.id.dg_amount);
        s.setText("支付失败，请点击确认重新扫码支付");
        user.setText(String.format("电力用户号：%s", mParam.getUser_no()));
        a.setText(String.format("购电金额：%s", mParam.getPay_amount()));
        dialog.setTitle("提示")
                .setView(view)
                .setPositiveButton("确认", v -> {
                    dialog.dismiss();
                    mQRCodeView.startSpot();
                })
                .show();
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
        switch (mParam.getFlag()) {
            case 0:
                startPay(0, result);
                  break;
            case 1:
                startPay(1, result);
                break;
            case 2:
                if (isFirst) {
                    com.halio.Rfid.powerOff();
                    com.halio.Rfid.closeCommPort();
                    com.halio.Rfid.powerOn();
                    com.halio.Rfid.openCommPort();
                    Psamcmd.ResetGPIO(55);
                    Psamcmd.ResetGPIO(94);
                }
                isFirst = false;
                startPay(2, result);
                default:
                    break;
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
        if (dialog != null) {
            dialog.dismiss();
        }
        activity.dismissDialog();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void startPay(int flag, String result) {
        if (mPresenter != null) {
            mPresenter.qrCodePay(flag, mParam.getTelPhone(), mParam.getUser_no(), mParam.getPay_amount(), result);
        }
    }

}
