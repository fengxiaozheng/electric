package com.payexpress.electric.mvp.presenter.payment;

import android.os.Handler;
import android.os.Message;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.app.utils.IRfidParam;
import com.payexpress.electric.app.utils.Psamcmd;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.mvp.contract.payment.ElectricPayContract;
import com.payexpress.electric.mvp.model.entity.paymentEntity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CodePayReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CodePayRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardBuyReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.SmartCardBuyCheckReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.SmartCardBuyReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.SmartCardBuyRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.TransNoReq;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.payexpress.electric.app.utils.StringUtils.Hxtostring;
import static com.payexpress.electric.app.utils.StringUtils.hexToBytes;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */
@FragmentScope
public class ElectricPayPresenter extends BasePresenter<ElectricPayContract.PayModel, ElectricPayContract.PayView> {

    @Inject
    RxErrorHandler mErrorHandler;

    private int times = 3;
    private String telPhone;
    private String user_no;
    private String smartCardBuyTransNo;

    private boolean isReadOk = false;
    private byte[] file1, file2, file3, file31, file32, file4, file41, file42, file5, idata, rand, code_id;
    byte iPSAMNum2;
    byte[] bytes;
    private boolean isopen = false;

    private Handler mMainHandler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("    检查到卡片。");
                    System.out.println("    " + Hxtostring(code_id));
                    System.out.println("    " + StringUtils.byteArrayToStr(code_id));
                    break;
                case 2:
                    System.out.println("    卡片数据读取--成功。");
                    System.out.println("    " + Hxtostring(idata));
                    startIsCardBuy();
                    break;
                case 3:
                    System.out.println("    卡片数据读取--失败！");
                    System.out.println("    " + Hxtostring(idata));
                    mRootView.fail("卡片读取失败");
                    break;
                case 18:
                    System.out.println("     写卡成功");
                    smartCardBuyCheck();
                    break;
                case 21:
                    System.out.println("     写卡失败");
                        rewriteCard();
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

    @Inject
    public ElectricPayPresenter(ElectricPayContract.PayModel model, ElectricPayContract.PayView view) {
        super(model, view);
    }


    //flag   0:普通购电，1：智能无卡购电，2：智能有卡购电
    public void  qrCodePay(int flag, String phone, String userNo, String amount, String code) {
        telPhone = phone;
        user_no = userNo;
        CodePayReq req = new CodePayReq();
        req.setG3(userNo);
        req.setAmount(amount);
        req.setBar_code(code);
        mModel.doPay(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodePayRes>(mErrorHandler) {
                    @Override
                    public void onNext(CodePayRes codePayRes) {
                        if (codePayRes.isSuccess()) {
                            if ("00".equals(codePayRes.getPay_status())) {
                                if (flag == 0) {
                                            TransNoReq req1 = new TransNoReq();
                                            req1.setTrans_no(codePayRes.getTrans_no());
                                            commonElectricBuy(req1);
                                } else if (flag == 1) {
                                    isNotCardElectricBuy(codePayRes.getTrans_no());
                                } else {
                                    isCardElectricBuy(codePayRes.getTrans_no());
                                }
                            }else if ("01".equals(codePayRes.getPay_status())){
                                mRootView.payFail();
                            } else {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        checkPayStatus(false, flag, codePayRes.getTrans_no());
                                        this.cancel();
                                    }
                                }, 10* 1000);
                            }
                        } else {
                            mRootView.payFail();
                        }
                    }
                });
    }

    public void checkPayStatus(boolean over, int flag, String transNo) {
        TransNoReq req = new TransNoReq();
        req.setTrans_no(transNo);
        getData(over, flag, req, transNo);
    }

    private void getData(boolean over, int flag, TransNoReq req, String transNo) {
        mModel.checkPayStatus(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CheckPayRes>(mErrorHandler) {
                    @Override
                    public void onNext(CheckPayRes checkPayRes) {
                        if (checkPayRes.isSuccess()) {
                            if ("00".equals(checkPayRes.getPay_status())) {
                                times = 3;
                                if (flag == 0) {
                                    commonElectricBuy(req);
                                } else if (flag == 1) {
                                    isNotCardElectricBuy(transNo);
                                } else {
                                    isCardElectricBuy(transNo);
                                }
                            } else {
                                times--;
                                if (times >0) {
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            getData(over, flag, req, transNo);
                                            this.cancel();
                                        }
                                    }, 5* 1000);

                                } else {
                                    times = 3;
                                    if ("02".equals(checkPayRes.getPay_status())){
                                        if (over) {
                                            mRootView.payFail();
                                        }else {
                                            mRootView.checkPay(flag, transNo);
                                        }
                                    } else {
                                        mRootView.payFail();
                                    }
                                }
                            }
                        }else {
                            times--;
                            if (times >0) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        getData(over, flag, req, transNo);
                                        this.cancel();
                                    }
                                }, 5* 1000);
                            } else {
                                times = 3;
                                mRootView.payFail();
                            }
                        }
                    }
                });
    }

    public void commonElectricBuy(TransNoReq req) {
        mModel.commonElectricBuy(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.success();
                        } else {
                            mRootView.fail(baseResponse.getRet_msg());
                        }
                    }
                });
    }

    private void isCardElectricBuy(String transNo) {
        smartCardBuyTransNo = transNo;
        isopen = true;
        readCardMsg(0);
    }

    private void startIsCardBuy() {
        SmartCardBuyReq req = new SmartCardBuyReq();
        req.setTrans_no(smartCardBuyTransNo);
        req.setG7(user_no);
        req.setG10(StringUtils.byteArrayToStr(file1));
        req.setG11(StringUtils.byteArrayToStr(file2));
        req.setG12(StringUtils.byteArrayToStr(file3));
        req.setG13(StringUtils.byteArrayToStr(file4));
        req.setG14(StringUtils.byteArrayToStr(file5));
        req.setG15(Hxtostring(code_id));
        req.setG16(StringUtils.byteArrayToStr(rand));
        req.setG17(Hxtostring(idata));
        mModel.hsaCardElectricBuy(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SmartCardBuyRes>(mErrorHandler) {
                    @Override
                    public void onNext(SmartCardBuyRes smartCardBuyRes) {
                        if (smartCardBuyRes.isSuccess()) {
                            smartCardBuyWriteCard(smartCardBuyRes);
                        } else {
                            mRootView.fail(smartCardBuyRes.getRet_msg());
                        }
                    }
                });
    }

    private void smartCardBuyWriteCard(SmartCardBuyRes res) {
        Message m2 = Message.obtain();
        if (Psamcmd.UserProving(iPSAMNum2, rand, hexToBytes(res.getUseKey()))) {
            if (Psamcmd.PowerProving(iPSAMNum2, hexToBytes(res.getAuthKey()))) {
                if (Psamcmd.BuyPowerProving(iPSAMNum2, hexToBytes(res.getOutKey()))) {

                        byte b1 = Psamcmd.WriteCare(iPSAMNum2, hexToBytes(res.getFile1()),
                                hexToBytes(res.getFile2()), hexToBytes(res.getFile31()),
                                hexToBytes(res.getFile32()), hexToBytes(res.getFile41()),
                                hexToBytes(res.getFile42()), hexToBytes(res.getFile5()));
                        if (b1 == IRfidParam.SUC[0]) {
                            Message m = Message.obtain();
                            m.what = 18;
                            mMainHandler.sendMessage(m);
                        } else {
                            m2.what = 21;
                            m2.obj = 0;
                        }

                } else {
                    m2.what = 21;
                    m2.obj = 1;
                }
            } else {
                m2.what = 21;
                m2.obj = 2;
            }
        } else {
            m2.what = 21;
            m2.obj = 3;
        }
        mMainHandler.sendMessage(m2);
    }

    private void rewriteCard() {
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
                        if (rewriteCardRes.isSuccess()) {
                            rewriteCard2(rewriteCardRes);
                        } else {
                            mRootView.fail("1111");
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
        mMainHandler.sendMessage(m);
    }

    private void smartCardBuyCheck() {
        SmartCardBuyCheckReq req = new SmartCardBuyCheckReq();
        req.setSesb_no("51401");
        req.setFile1(StringUtils.byteArrayToStr(file1));
        req.setFile2(StringUtils.byteArrayToStr(file2));
        req.setFile3(StringUtils.byteArrayToStr(file3));
        req.setFile4(StringUtils.byteArrayToStr(file4));
        req.setFile5(StringUtils.byteArrayToStr(file5));
        mModel.hasCardBuyCheck(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.success();
                    }
                });
    }

    private void isNotCardElectricBuy(String transNo) {
        NoCardBuyReq req = new NoCardBuyReq();
        req.setCellphone(telPhone);
        req.setTrans_no(transNo);
        mModel.noCardElectricBuy(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.success();
                        } else {
                            mRootView.fail(baseResponse.getRet_msg());
                        }
                    }
                });
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
        super.onDestroy();
    }
}
