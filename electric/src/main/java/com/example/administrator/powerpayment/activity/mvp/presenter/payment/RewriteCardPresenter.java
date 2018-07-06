package com.example.administrator.powerpayment.activity.mvp.presenter.payment;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.IRfidParam;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteCardContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardRes;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.AlertDialog;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.example.administrator.powerpayment.activity.app.utils.StringUtils.Hxtostring;
import static com.example.administrator.powerpayment.activity.app.utils.StringUtils.hexToBytes;

/**
 * Created by fengxiaozheng
 * on 2018/5/16.
 */
@FragmentScope
public class RewriteCardPresenter extends BasePresenter<RewriteCardContract.Model,
        RewriteCardContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AlertDialog dialog;

    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file31, file32, file4, file41, file42, file5, idata, rand, code_id;
    private String userCode = "";
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;

    @Inject
    public RewriteCardPresenter(RewriteCardContract.Model model,
                                RewriteCardContract.View view) {
        super(model, view);
    }


    private Handler mMainHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("    检查到卡片。");
                    System.out.println("    " + Hxtostring(code_id));
                    System.out.println("    " + StringUtils.byteArrayToStr(code_id));
                    break;
                case 2:
                    System.out.println("    卡片数据读取--成功。usercode=" + userCode);
                    System.out.println("    " + Hxtostring(idata));
                    dialog.startRewrite();
                    startRewrite();
                    break;
                case 3:
                    System.out.println("    卡片数据读取--失败！");
                    System.out.println("    " + Hxtostring(idata));
                    if (dialog != null) {
                        dialog.fail();
                    }
                    break;
                case 19:
                    System.out.println("     补写卡成功");
                    mRootView.success();
                    break;
                case 20:
                    System.out.println("      补写卡失败");
                    mRootView.fail("1111");
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    public void rewrite() {
        dialog.shown();
        isopen = true;
        readCardMsg(0);
    }

    private void startRewrite() {
        RewriteCardReq req = new RewriteCardReq();
        req.setFile1(StringUtils.byteArrayToStr(file1));
        req.setFile2(StringUtils.byteArrayToStr(file2));
        req.setFile31(StringUtils.byteArrayToStr(file31));
        req.setFile32(StringUtils.byteArrayToStr(file32));
        req.setFile41(StringUtils.byteArrayToStr(file41));
        req.setFile42(StringUtils.byteArrayToStr(file42));
        req.setFile5(StringUtils.byteArrayToStr(file5));
        req.setCard_info(Hxtostring(idata));
        req.setCard_seq(Hxtostring(code_id));
        req.setCard_random(StringUtils.byteArrayToStr(rand));
        mModel.rewriteCard(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RewriteCardRes>(mErrorHandler) {
                    @Override
                    public void onNext(RewriteCardRes rewriteCardRes) {
                        if (mRootView != null) {
                            if (rewriteCardRes.isSuccess()) {
                                rewriteCard2(rewriteCardRes);
                            } else {
                                dialog.dismiss();
                                mRootView.fail("1111");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.fail(mRootView.getActivity().getString(R.string.server_error));
                        }
                    }
                });
    }

    private void rewriteCard2(RewriteCardRes res) {
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
        dialog.dismiss();
        mMainHandler.sendMessage(m);
    }

    private void readCardMsg(final int w) {
        isReadOk = false;
        if (file31 == null) {
            file31 = new byte[129];
            file32 = new byte[129];
            file41 = new byte[129];
            file42 = new byte[129];
        }
        new Thread(() -> {
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
        }).start();
    }

    @Override
    public void onDestroy() {
        isopen = false;
        mErrorHandler = null;
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
