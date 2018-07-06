package com.example.administrator.powerpayment.activity.mvp.presenter.payment;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.IRfidParam;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardRes;
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

/**
 * Created by fengxiaozheng
 * on 2018/5/16.
 */
@FragmentScope
public class SmartPowerPresenter extends BasePresenter<SmartPowerContract.Model,
        SmartPowerContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AlertDialog dialog;
    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file4, file5, idata, rand, code_id;
    private String userCode = "";
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;
    private QuerySmartCardReq req;


    private Handler mMainHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("    检查到卡片。");
                    System.out.println("    " + Hxtostring(code_id));
                    System.out.println("    " + StringUtils.byteArrayToStr(code_id));
                    dialog.updateInfo();
                    break;
                case 2:
                    System.out.println("    卡片数据读取--成功。usercode=" + userCode);
                    System.out.println("    " + Hxtostring(idata));
                    getCardInfo();
                    break;
                case 3:
                    System.out.println("    卡片数据读取--失败！");
                    System.out.println("    " + Hxtostring(idata));
                    if (dialog != null) {
                        dialog.fail();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Inject
    public SmartPowerPresenter(SmartPowerContract.Model model,
                               SmartPowerContract.View view) {
        super(model, view);
    }

    public void querySmartCardInfo() {
        dialog.shown();
        isopen = true;
        readCardMsg(0);
    }

    private void getCardInfo() {
        if (req == null) {
            req = new QuerySmartCardReq();
        }
        req.setG5(StringUtils.byteArrayToStr(file1));
        req.setG6(StringUtils.byteArrayToStr(file2));
        req.setG7(StringUtils.byteArrayToStr(file3));
        req.setG8(StringUtils.byteArrayToStr(file4));
        req.setG9(StringUtils.byteArrayToStr(file5));
        req.setSesb_no("51401");
        mModel.querySmartCardInfo(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<QuerySmartCardRes>(mErrorHandler) {
                    @Override
                    public void onNext(QuerySmartCardRes querySmartCardRes) {
                        if (mRootView != null) {
                            dialog.dismiss();
                            if (querySmartCardRes.isSuccess()) {
                                mRootView.success(querySmartCardRes);
                            } else {
                                mRootView.fail(querySmartCardRes.getRet_msg());
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

    private void readCardMsg(final int w) {
        isReadOk = false;
        if (!dialog.isShowing()) return;
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
        }).start();
    }

    @Override
    public void onDestroy() {
        isopen = false;
        mErrorHandler = null;
        if (dialog !=null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = null;
        super.onDestroy();
    }
}
