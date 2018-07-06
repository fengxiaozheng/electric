package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.administrator.powerpayment.activity.R;

import java.lang.ref.WeakReference;

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

    private WebView mWebView;
    private WebSettings mWebSettings;


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
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getLayoutParams();
        if (isMain) {
            lp.height = 1080;
            lp.topMargin = -140;
            lp.bottomMargin = -140;
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
        mWebView.setWebViewClient(new MyWebViewClient(this));
    }

    //销毁Webview
    @Override
    public void onDestroy() {
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
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {
        private WeakReference<Fragment> reference;

        public MyWebViewClient(Fragment fragment) {
            reference = new WeakReference<>(fragment);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Fragment f = reference.get();
            if (f != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
            }
            return true;
        }
    }
}
