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
 * on 2018/1/30.
 */

public class AlertDialog extends Dialog {
    private ImageView iv_content;
    private ImageView info;
    private TextView tv_content;
    private Handler handler;
    private Context context;

    public AlertDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        init(context);
    }

    private void init(Context context){
        handler = new Handler(Looper.getMainLooper());
        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_card,
                        null);
        setCanceledOnTouchOutside(false);
        ImageView close = view.findViewById(R.id.iv_close);
        iv_content = view.findViewById(R.id.iv_content);
        info = view.findViewById(R.id.iv_info);
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
        iv_content.setImageResource(R.mipmap.img_chaka);
        info.setVisibility(View.VISIBLE);
        tv_content.setText(context.getString(R.string.str_055));
        show();
    }

    public void fail(){
        if (isShowing()) {
            handler.post(() -> {
                iv_content.setImageResource(R.mipmap.read_fail);
                info.setVisibility(View.GONE);
                tv_content.setText("读卡失败，请重新插卡");
            });
        }
    }

    public void startRewrite() {
        handler.post(() -> {
            iv_content.setImageResource(R.mipmap.ka);
            info.setVisibility(View.GONE);
            tv_content.setText("正在补写，请勿拔卡");
        });
    }
}
