package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.IRfidParam;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerSmartPowerComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.SmartPowerModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartUserInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.SmartPowerPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.BasePaymentFragment;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.AlertDialog;
import com.jess.arms.di.component.AppComponent;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.powerpayment.activity.app.utils.StringUtils.Hxtostring;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartPowerFragment extends BasePaymentFragment<SmartPowerPresenter>
        implements SmartPowerContract.View {

    @BindView(R.id.s_hasCard)
    LinearLayout s_hasCard;
    @BindView(R.id.s_noCard)
    LinearLayout s_noCard;
    @BindView(R.id.s_check)
    LinearLayout s_check;
    @BindView(R.id.s_query_balance)
    LinearLayout balance;

    private boolean isFirst = false;

    @Inject
    AlertDialog dialog;
    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file4, file5, idata, rand, code_id;
    private String userCode = "";
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;
    private MyHandler mMainHandler;
    private int f;

    public SmartPowerFragment() {
        // Required empty public constructor
    }

    public static SmartPowerFragment newInstance() {
        return new SmartPowerFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSmartPowerComponent.builder()
                .appComponent(appComponent)
                .smartPowerModule(new SmartPowerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smart_power, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.s_hasCard)
    void hasCard() {
        f = 0;
        querySmartCardInfo();
    }

    @OnClick(R.id.s_noCard)
    void noCard() {
        start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(3),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_check)
    void check() {
        f = 2;
        querySmartCardInfo();
    }

    @OnClick(R.id.s_query_balance)
    void query() {
        f = 4;
        querySmartCardInfo();
    }

    @Override
    public void success(QuerySmartCardRes res) {
        dialog.dismiss();
        SmartUserInfo info = new SmartUserInfo();
        info.setFlag(2);
        info.setUserNo(res.getG7());
        info.setUserName(res.getG8());
        info.setUserAddress(res.getG9());
        start(SmartPowerFragment.this, SPUserInfoFragment.newInstance(info),
                "SPUserInfoFragment");
    }

    @Override
    public void fail(String msg) {
        dialog.dismiss();
        start(SmartPowerFragment.this,
                ReadCardErrorFragment.newInstance(msg), "ReadCardErrorFragment");
    }

    @Override
    public void queryBalanceSuccess(CardBalanceRes res) {
        dialog.dismiss();
        start(this,
                CPBalanceFragment.newInstance(res.getBalance(), true), "CPBalanceFragment");
    }

    @Override
    public void queryBalanceFail(String msg) {
        dialog.dismiss();
        start(this,
                CPBalanceFragment.newInstance(msg, true), "CPBalanceFragment");
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    private void querySmartCardInfo() {
        //f：2--购电记录；4:--余额查询
        if (isFirst) {
            com.halio.Rfid.powerOff();
            com.halio.Rfid.closeCommPort();
            com.halio.Rfid.powerOn();
            com.halio.Rfid.openCommPort();
            Psamcmd.ResetGPIO(55);
            Psamcmd.ResetGPIO(94);
        }
        if (mMainHandler == null) {
            mMainHandler = new MyHandler(this);
        }
        isFirst = false;
        dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
        dialog.shown();
        isopen = true;
        if (2 == f || 4 == f) {
            Button button = dialog.findViewById(R.id.no_card);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v ->  {
                    button.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                    start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(f),
                    "CPCheckUserFragment");});
            dialog.findViewById(R.id.iv_close).setOnClickListener(v ->{
                button.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            });
        }
        readCardMsg();
    }

    private void readCardMsg() {
        isReadOk = false;
        if (!dialog.isShowing()) return;
        new MyThread(this).start();
    }


    private void doHandler(int what) {
        switch (what) {
            case 1:
                System.out.println("    检查到卡片。");
                System.out.println("    " + Hxtostring(code_id));
                System.out.println("    " + StringUtils.byteArrayToStr(code_id));
                dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.INVISIBLE);
                dialog.findViewById(R.id.no_card).setVisibility(android.view.View.INVISIBLE);
                dialog.updateInfo();
                break;
            case 2:
                System.out.println("    卡片数据读取--成功。usercode=" + userCode);
                System.out.println("    " + Hxtostring(idata));
                if (2 == f) {
                    dialog.dismiss();
                    start(this,
                            CPRecordFragment.newInstance(false, userCode), "CPRecordFragment");
                }else if (4 == f) {
                    if (mPresenter != null) {
                        mPresenter.getBalance(userCode);
                    }
                } else {
                    if (mPresenter != null) {
                        mPresenter.getCardInfo(StringUtils.byteArrayToStr(file1),
                                StringUtils.byteArrayToStr(file2),
                                StringUtils.byteArrayToStr(file3),
                                StringUtils.byteArrayToStr(file4),
                                StringUtils.byteArrayToStr(file5));
                    }
                }
                break;
            case 3:
                System.out.println("    卡片数据读取--失败！");
                System.out.println("    " + Hxtostring(idata));
                if (dialog != null) {
                    dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
                    dialog.findViewById(R.id.no_card).setVisibility(android.view.View.INVISIBLE);
                    dialog.fail();
                }
                break;
            default:
                break;
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<Fragment> reference;

        public MyHandler(Fragment fragment) {
            reference = new WeakReference<Fragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (reference.get() != null) {
                if (reference.get() instanceof SmartPowerFragment) {
                    ((SmartPowerFragment) reference.get()).doHandler(msg.what);
                }
            }
        }
    }

    private static class MyThread extends Thread {
        private WeakReference<Fragment> reference;

        public MyThread(Fragment fragment) {
            reference = new WeakReference<Fragment>(fragment);
        }

        @Override
        public void run() {
            super.run();
            if (reference.get() != null) {
                if (reference.get() instanceof SmartPowerFragment) {
                    ((SmartPowerFragment) reference.get()).doThread(0);
                }
            }
        }
    }

    private void doThread(int w) {

        byte iPSAMNum = IRfidParam.PSAM_NUM_2;
        byte iPSAMMode = IRfidParam.PSAM_MODE_9600;
        int isCCard = 0;
        for (int a = 0; a < 80 && isopen; a++) {
            isCCard = Psamcmd.getversion();
            if (isCCard == 1) {//插卡
                code_id = Psamcmd.reset(iPSAMNum, iPSAMMode);//得到卡片ID
                Message m = Message.obtain();
                m.what = 1;////检测到卡片
                mMainHandler.sendMessage(m);
                if (code_id.length == 1) {
                    break;
                } else {
                    iPSAMNum2 = iPSAMNum;
                    idata = Psamcmd.readCardInfo(iPSAMNum2);//读取卡片128字节信息
                    bytes = Psamcmd.readCardstate(iPSAMNum2);
//                            if (bytes.length > 1 && bytes[1] == IRfidParam.NewUser[1] && bytes[0] == IRfidParam.NewUser[0]) {
//                                isNewCard = false;
////                                    isNewCard = true;
//                            } else if (bytes.length > 1 && bytes[1] == IRfidParam.OldUser[1] && bytes[0] == IRfidParam.OldUser[0]) {
//                                isNewCard = false;
//                            }
                    Psamcmd.selectW(iPSAMNum2);//选择文件
                    file1 = Psamcmd.readDataInfo(iPSAMNum2);//读取file1
                    file2 = Psamcmd.readWallet(iPSAMNum2);//读取file2
                    file3 = Psamcmd.readCAF(iPSAMNum2);//读取file3
                    file4 = Psamcmd.readSAF(iPSAMNum2);
                    file5 = Psamcmd.readWB(iPSAMNum2);
                    rand = Psamcmd.getRand(iPSAMNum2);//读取随机数
                    if (file1 != null && file2 != null && file1.length > 10) {
                        userCode = Psamcmd.getUser(file1);
                        isReadOk = true;
                        break;
                    }
                }
            } else if (isCCard == 2) {
                com.halio.Rfid.powerOff();
                com.halio.Rfid.closeCommPort();
                com.halio.Rfid.powerOn();
                com.halio.Rfid.openCommPort();
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message m = Message.obtain();
        m.obj = w;
        if (isReadOk) {
            m.what = 2;//读卡成功
        } else {
            m.what = 3;//读卡失败
        }
        mMainHandler.sendMessage(m);

    }

    @Override
    public void onDestroy() {
        isopen = false;
        if (dialog != null) {
            dialog.findViewById(R.id.no_card).setVisibility(View.INVISIBLE);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = null;
        super.onDestroy();
    }
}
