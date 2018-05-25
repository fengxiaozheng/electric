package com.payexpress.electric.mvp.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.payexpress.electric.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by fengxiaozheng
 * on 2018/1/30.
 */

public class AlertDialog {
    private MaterialDialog dialog;
    private AlertDialog(){}

    public static MaterialDialog show(Context context, String userNo, String amount){
        MaterialDialog dialog = new MaterialDialog(context);
        View view = LayoutInflater.from(context)
                                .inflate(R.layout.dialog_content,
                                        null);
        dialog.setCanceledOnTouchOutside(true);
        TextView user = view.findViewById(R.id.dg_user_no);
        TextView a = view.findViewById(R.id.dg_amount);
        user.setText(String.format("电力用户号：%s", userNo));
        a.setText(String.format("购电金额：%s", amount));
        dialog.setTitle("提示")
                .setView(view);
        return dialog;
    }


}
