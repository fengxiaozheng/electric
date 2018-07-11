package com.example.administrator.powerpayment.activity.mvp.presenter.payment;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.IRfidParam;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteTimesContract.Model;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteTimesContract.View;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardListRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesAllParams;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesListReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesRes;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.RewriteTimesAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.RewriteTimesDialog;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.text.DecimalFormat;
import java.util.List;

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
public class RewriteTimesPresenter extends BasePresenter<Model, View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    List<RewriteTimesInfo> infos;
    @Inject
    RewriteTimesAdapter mAdapter;
    @Inject
    StatusUIManager mUiManager;
    @Inject
    DecimalFormat format;
    @Inject
    RewriteTimesDialog dialog;

    private StringBuilder builder;
    private StringBuilder sb;

    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file31, file32, file4, file41, file42, file5, idata, rand, code_id;
    private String userCode = "";
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;
    private String user_no, pay_count;


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
                    postRewrite();
                    break;
                case 3:
                    System.out.println("    卡片数据读取--失败！");
                    System.out.println("    " + Hxtostring(idata));
                    if (dialog != null) {
                        dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
                        dialog.readCardError();
                    }
                    break;
                case 19:
                    System.out.println("     次数补写成功");
                    dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
                    dialog.success();
                    break;
                case 20:
                    System.out.println("      次数补写失败");
                    if (dialog != null) {
                        dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.VISIBLE);
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
    public RewriteTimesPresenter(Model model, View view) {
        super(model, view);
    }


    public void initStateUI(android.view.View contentView) {
        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultEmptyStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_EMPTY,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    public void getListData(RewriteTimesAllParams data) {
        RewriteTimesListReq req = new RewriteTimesListReq();
        req.setFile1(data.getFile1());
        req.setFile2(data.getFile2());
        req.setFile3(data.getFile3());
        req.setFile4(data.getFile4());
        req.setFile5(data.getFile5());
        mModel.getRewriteData(JSON.toJSONString(req))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RewriteCardListRes>(mErrorHandler) {
                    @Override
                    public void onNext(RewriteCardListRes res) {
                        if (mRootView != null) {
                            if (res.isSuccess()) {
                                if (res.getRows().size() > 0) {
                                    for (RewriteTimesInfo info : res.getRows()) {
                                        builder = new StringBuilder();
                                        sb = new StringBuilder();
                                        info.setPay_date(builder
                                                .append(info.getPay_date().substring(0, 4))
                                                .append("-")
                                                .append(info.getPay_date().substring(4, 6))
                                                .append("-")
                                                .append(info.getPay_date().substring(6, 8))
                                                .append(info.getPay_date().substring(8, 17))
                                                .toString());
                                        builder.delete(0, 19);
                                        info.setPay_amount(builder
                                                .append(format.format(Double.parseDouble(info.getPay_amount())))
                                                .append("元")
                                                .toString());
                                        info.setWritable_amount(sb
                                                .append(format.format(Double.parseDouble(info.getWritable_amount())))
                                                .append("元")
                                                .toString());
                                    }
                                    infos.addAll(res.getRows());
                                    mAdapter.notifyDataSetChanged();
                                }
                                mRootView.success(res);
                            } else {
                                if (mRootView != null) {
                                    mRootView.fail(res.getRet_msg());
                                }
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

    public void startRewrite(String userNo, String payCount) {
        dialog.findViewById(R.id.iv_close).setVisibility(android.view.View.INVISIBLE);
        dialog.shown();
        isopen = true;
        user_no = userNo;
        pay_count = payCount;
        readCardMsg(0);
    }

    private void postRewrite() {
        RewriteTimesReq req = new RewriteTimesReq();
        req.setFile1(StringUtils.byteArrayToStr(file1));
        req.setFile2(StringUtils.byteArrayToStr(file2));
        req.setFile31(StringUtils.byteArrayToStr(file31));
        req.setFile32(StringUtils.byteArrayToStr(file32));
        req.setFile41(StringUtils.byteArrayToStr(file41));
        req.setFile42(StringUtils.byteArrayToStr(file42));
        req.setFile5(StringUtils.byteArrayToStr(file5));
        req.setUser_card_info(Hxtostring(idata));
        req.setUser_seq(Hxtostring(code_id));
        req.setUser_random(StringUtils.byteArrayToStr(rand));
        req.setGrid_user_code(user_no);
        req.setPay_count(pay_count);
        mModel.startRewriteTimes(JSON.toJSONString(req))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RewriteTimesRes>(mErrorHandler) {
                    @Override
                    public void onNext(RewriteTimesRes rewriteCardRes) {
                        if (mRootView != null) {
                            if (rewriteCardRes.isSuccess()) {
                                writeToCard(rewriteCardRes);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(mRootView.getActivity(), rewriteCardRes.getRet_msg(), Toast.LENGTH_SHORT).show();
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

    private void writeToCard(RewriteTimesRes res) {
        Message m = Message.obtain();
        if (Psamcmd.UserProving(iPSAMNum2, rand, hexToBytes(res.getUser_key()))) {
            if (Psamcmd.PowerProving(iPSAMNum2, hexToBytes(res.getAuth_key()))) {
                if (Psamcmd.BuyPowerProving(iPSAMNum2, hexToBytes(res.getOut_auth_key()))) {
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
        super.onDestroy();
        this.mErrorHandler = null;
        isopen = false;
        infos = null;
        mAdapter = null;
        mUiManager = null;
        builder = null;
        sb = null;
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }

}
