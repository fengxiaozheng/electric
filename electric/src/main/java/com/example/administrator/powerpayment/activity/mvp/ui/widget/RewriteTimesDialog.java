package com.example.administrator.powerpayment.activity.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;


/**
 * Created by fengxiaozheng
 * on 2018/6/8.
 */

public class RewriteTimesDialog extends Dialog {
    private ImageView iv_content;
    private TextView info;
    private TextView tv_content;
    private Handler handler;

    public RewriteTimesDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        init(context);
    }

    private void init(Context context){
        handler = new Handler(Looper.getMainLooper());
        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_rewrite_times,
                        null);
        setCanceledOnTouchOutside(false);
        ImageView close = view.findViewById(R.id.iv_close);
        iv_content = view.findViewById(R.id.iv_content);
        info = view.findViewById(R.id.tv_info);
        tv_content = view.findViewById(R.id.tv_content);
        close.setOnClickListener(v -> {
            dismiss();
        });
        setContentView(view);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = 640;
        lp.height = 480;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

    public void updateInfo(){
        handler.post(() -> {
            iv_content.setImageResource(R.mipmap.ka);
            info.setVisibility(View.GONE);
            tv_content.setText("读卡中，请勿拔卡");
        });
    }

    public void shown() {
        iv_content.setImageResource(R.mipmap.ka);
        info.setText("次数补写中...");
        tv_content.setText("请勿拔卡");
        show();
    }

    public void fail(){
        if (isShowing()) {
            handler.post(() -> {
                iv_content.setImageResource(R.mipmap.img_fail);
                info.setText("次数补写失败");
                tv_content.setText("请重试");
            });
        }
    }

    public void success() {
        if (isShowing()) {
            handler.post(() -> {
                iv_content.setImageResource(R.mipmap.img_success);
                info.setText("次数补写成功");
                tv_content.setText("请拔出卡片并收好您的卡！");
            });
        }
    }

    public void readCardError() {
        if (isShowing()) {
            handler.post(() -> {
                iv_content.setImageResource(R.mipmap.read_fail);
                info.setText("次数补写失败");
                tv_content.setText("卡片读取失败，请重试");
            });
        }
    }
}
