package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovWebFragment extends GovFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam;
    private boolean isMain;

    private X5WebView mWebView;
    private com.tencent.smtt.sdk.WebSettings mWebSettings;
    private Handler handler;
    private DownTimer downTimer;
    private AlertDialog dialog;
    private int currentProgress = 0;


    public GovWebFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GovWebFragment newInstance(boolean isGovMain, String param) {
        GovWebFragment fragment = new GovWebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        args.putBoolean(ARG_PARAM1, isGovMain);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
            isMain = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gov_web, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = view.findViewById(R.id.gov_web);
        ProgressBar bar = view.findViewById(R.id.web_bar);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getLayoutParams();
        if (isMain) {
            if (mParam.contains("civic-station/redirect/index")) {
                if (activity.findViewById(R.id.layout_bottom) != null) {
                    activity.findViewById(R.id.layout_bottom).setVisibility(View.GONE);
                }
                handler = new Handler();
            }else {
                lp.bottomMargin = -140;
            }
            lp.height = 1080;
            lp.topMargin = -140;
        }else {
            lp.height = 820;
            lp.topMargin = 10;
        }
        mWebView.setLayoutParams(lp);
        mWebSettings = mWebView.getSettings();
        mWebView.loadUrl(mParam);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setLoadsImagesAutomatically(true);
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    view.loadUrl(request.getUrl().toString());
//                }
//                return true;
//            }
//        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                currentProgress = i;
                if (100 == i) {
                    bar.setVisibility(View.GONE);
                } else {
                    bar.setVisibility(View.VISIBLE);
                    bar.setProgress(i);
                }
            }
        });
        mWebView.addJavascriptInterface(this, "javaNative");
        downTimer = new DownTimer(this, 30 * 1000, 1000);
        downTimer.start();
    }

    private void show() {
        dialog = new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage("网络延迟较高，是否继续等待")
                .setPositiveButton("继续等待", (dialog1, which) -> {
                    downTimer.start();
                })
                .setNegativeButton("退出", (dialog12, which) -> {
                    back();
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(28);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(28);
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            //通过反射修改title字体大小和颜色
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(32);
            //通过反射修改message字体大小和颜色
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(28);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    //销毁Webview
    @Override
    public void onDestroy() {
        activity.findViewById(R.id.layout_bottom).setVisibility(View.VISIBLE);
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        if (downTimer != null) {
            downTimer.cancel();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @JavascriptInterface
    public void closeWeb() {
        handler.post(() -> {
            back();
            activity.findViewById(R.id.layout_bottom).setVisibility(View.VISIBLE);
        });

    }

    @JavascriptInterface
    public void goToHome() {
        handler.post(() ->
                backHome());
    }

    private static class DownTimer extends CountDownTimer {
        WeakReference<Fragment> reference;

        public DownTimer(Fragment f, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            reference = new WeakReference<Fragment>(f);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (reference.get() != null) {
                if (reference.get() instanceof GovWebFragment) {
                    if (((GovWebFragment) reference.get()).currentProgress < 100) {
                        ((GovWebFragment) reference.get()).show();
                    }
                }
            }
        }
    }

}
