package com.xgx.syzj.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.widget.TextView;

import com.xgx.syzj.R;

/**
 * 加载框
 *
 * @author zajo
 * @created 2015年08月07日 15:03
 */
public class CustomProgressDialog extends Dialog {

    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Activity context) {
        super(context);
    }

    public CustomProgressDialog(Activity context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Activity context) {
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.custom_progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCancelable(false);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }
    }

    /**
     *
     * [Summary] setTitile 标题
     *
     * @param strTitle
     * @return
     *
     */
    public CustomProgressDialog setTitile(String strTitle) {
        return customProgressDialog;
    }

    /**
     *
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) customProgressDialog
                .findViewById(R.id.load_info_text);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }
}
