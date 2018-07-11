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
import android.widget.LinearLayout;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.IRfidParam;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerRewriteCardComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.RewriteCardModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteCardContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardRes;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.RewriteCardPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.BasePaymentFragment;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.AlertDialog;
import com.jess.arms.di.component.AppComponent;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.administrator.powerpayment.activity.app.utils.StringUtils.Hxtostring;
import static com.example.administrator.powerpayment.activity.app.utils.StringUtils.hexToBytes;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteCardFragment extends BasePaymentFragment<RewriteCardPresenter>
        implements RewriteCardContract.View {

    @BindView(R.id.re_times)
    LinearLayout mLinearLayout;
    @BindView(R.id.re_end)
    LinearLayout mEnd;

    private boolean isFirst = false;

    @Inject
    AlertDialog dialog;
    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file31, file32, file4, file41, file42, file5, idata, rand, code_id;
    private String userCode = "";
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;
    private MyHandler mMainHandler;


    public RewriteCardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewriteCardFragment newInstance() {
        return new RewriteCardFragment();
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRewriteCardComponent.builder()
                .appComponent(appComponent)
                .rewriteCardModule(new RewriteCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewrite_card, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.re_times)
    void click() {
        start(RewriteCardFragment.this, RewriteInputFragment.newInstance(),
                "RewriteInputFragment");
    }

    @OnClick(R.id.re_end)
    void end() {
        if (isFirst) {
            com.halio.Rfid.powerOff();
            com.halio.Rfid.closeCommPort();
            com.halio.Rfid.powerOn();
            com.halio.Rfid.openCommPort();
            Psamcmd.ResetGPIO(55);
            Psamcmd.ResetGPIO(94);
        }
        isFirst = false;
        if (mMainHandler == null) {
            mMainHandler = new MyHandler(this);
        }
        querySmartCardInfo();
    }

    @Override
    public void success() {
        next(true);
    }

    @Override
    public void fail(String msg) {
        next(false);
    }

    @Override
    public void rewriteCard2(RewriteCardRes res) {
        Message m = Message.obtain();
        if (Psamcmd.UserProving(iPSAMNum2, rand, hexToBytes(res.getUseKey()))) {
            if (Psamcmd.PowerProving(iPSAMNum2, hexToBytes(res.getAuthKey()))) {
                if (Psamcmd.BuyPowerProving(iPSAMNum2, hexToBytes(res.getOutKey()))) {
                    m.obj = 0;
                    byte b1 = Psamcmd.WriteCare(iPSAMNum2, hexToBytes(res.getFile1()),
                            hexToBytes(res.getFile2()), hexToBytes(res.getFile31()),
                            hexToBytes(res.getFile32()), hexToBytes(res.getFile41()),
                            hexToBytes(res.getFile42()), hexToBytes(res.getFile5()));
                    if (b1 == IRfidParam.SUC[0]) {
                        m.what = 19;
                    } else {
                        m.what = 20;
                    }
                } else {
                    m.what = 20;
                    m.obj = 1;
                }
            } else {
                m.what = 20;
                m.obj = 2;
            }
        } else {
            m.what = 20;
            m.obj = 3;
        }
        mMainHandler.sendMessage(m);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    private void next(boolean result) {
        dialog.dismiss();
        start(RewriteCardFragment.this,
                RewriteResultFragment.newInstance(result), "RewriteResultFragment");
    }


    private void querySmartCardInfo() {
        dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
        dialog.shown();
        isopen = true;
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
                dialog.updateInfo();
                break;
            case 2:
                System.out.println("    卡片数据读取--成功。usercode=" + userCode);
                System.out.println("    " + Hxtostring(idata));
                if (mPresenter != null) {
                    dialog.startRewrite();
                    mPresenter.startRewrite(StringUtils.byteArrayToStr(file1),
                            StringUtils.byteArrayToStr(file2),
                            StringUtils.byteArrayToStr(file31),
                            StringUtils.byteArrayToStr(file32),
                            StringUtils.byteArrayToStr(file41),
                            StringUtils.byteArrayToStr(file42),
                            StringUtils.byteArrayToStr(file5),
                            Hxtostring(idata),
                            Hxtostring(code_id),
                            StringUtils.byteArrayToStr(rand));
                }
                break;
            case 3:
                System.out.println("    卡片数据读取--失败！");
                System.out.println("    " + Hxtostring(idata));
                if (dialog != null) {
                    dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
                    dialog.fail();
                }
                break;
            case 19:
                System.out.println("     补写卡成功");
                next(true);
                break;
            case 20:
                System.out.println("      补写卡失败");
                next(false);
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
                if (reference.get() instanceof RewriteCardFragment) {
                    ((RewriteCardFragment) reference.get()).doHandler(msg.what);
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
                if (reference.get() instanceof RewriteCardFragment) {
                    ((RewriteCardFragment) reference.get()).doThread(0);
                }
            }
        }
    }

    private void doThread(int w) {
        if (file31 == null) {
            file31 = new byte[129];
            file32 = new byte[129];
            file41 = new byte[129];
            file42 = new byte[129];
        }
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
                        //    userCode = Psamcmd.getUser(file1);
                        for (int i = 0; i < 129; i++) {
                            file31[i] = file3[i];
                            file41[i] = file4[i];
                            file32[i] = file3[i+129];
                            file42[i] = file4[i+129];
                        }
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = null;
        super.onDestroy();
    }
}
