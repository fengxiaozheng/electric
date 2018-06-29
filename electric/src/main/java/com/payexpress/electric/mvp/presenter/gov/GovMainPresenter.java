package com.payexpress.electric.mvp.presenter.gov;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.View;
import com.payexpress.electric.mvp.model.api.Api;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.ElectricUser;
import com.payexpress.electric.mvp.model.entity.LoginRes;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */
@FragmentScope
public class GovMainPresenter extends BasePresenter<Model, View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MainFragmentAdapter mAdapter;
    @Inject
    List<MainFragmentItemInfo> infos;
    @Inject
    StatusUIManager mUiManager;

    @Inject
    public GovMainPresenter(Model model, View view) {
        super(model, view);
    }

    public void getTermArea() {
        String id = StringUtils.getToken(mRootView.getActivity());
        if (id != null) {
            System.out.println("GovMain数据：AccessTokenFactory的存储在pres文件中的id" + id);
            finallyStep();
        } else {
            getData();
        }


    }

    private void getData() {
        String uuid = StringUtils.getUuid(mRootView.getActivity());
        StringBuilder sb = new StringBuilder();
        sb.append(uuid).append("-citizen");
        String psd = StringUtils.MD5(sb.toString());
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Api.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GovService service = retrofit.create(GovService.class);
        System.out.println("md5数据："+psd);
        ElectricUser user = new ElectricUser();
        user.setUsername(uuid);
        user.setPassword(psd);
        Call<LoginRes> observable = service.getLoginInfo(user);
        observable.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (mRootView != null) {
                    if (response.body() != null) {
                        System.out.println("数据1：" + JSON.toJSONString(response.body()));
                        if (response.body().isSuccess()) {
                            StringUtils.setToken(mRootView.getActivity(), response.body().getToken());
                            finallyStep();
                        } else {
                            mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                            mUiManager.show(DefaultStatus.STATUS_EMPTY);
                        }
                    } else {
                        System.out.println("BaseActivity数据情况返回空了");
                        mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                        mUiManager.show(DefaultStatus.STATUS_EMPTY);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                System.out.println("数据错误："+JSON.toJSONString(t));
                if (mRootView != null) {
                    mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                    mUiManager.show(DefaultStatus.STATUS_SERVER_ERROR);
                }
            }
        });
    }

    private void finallyStep() {
        mModel.getGovMainFragmentItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovMainRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovMainRes govMainRes) {
                        if (mRootView != null) {
                            mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                            if (govMainRes.isSuccess()) {
                                infos.addAll(govMainRes.getGovernmentServices());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mUiManager.show(DefaultStatus.STATUS_LOGIC_FAIL);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                            mUiManager.show(DefaultStatus.STATUS_SERVER_ERROR);
                        }
                    }
                });
    }

    public void initStateUI(android.view.View contentView) {
        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultEmptyStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_EMPTY,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));

        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultServerErrorStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_SERVER_ERROR,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));

        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultLogicFailStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_LOGIC_FAIL,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.infos = null;
        this.mUiManager = null;
        this.mErrorHandler = null;
    }
}
