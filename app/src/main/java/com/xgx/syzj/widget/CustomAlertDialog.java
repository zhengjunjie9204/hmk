package com.xgx.syzj.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ListViewDialogAdapter;
import com.xgx.syzj.utils.Utils;

/**
 * 信息提示框
 *
 * @author zajo
 * @created 2015年08月07日 16:35
 */
public class CustomAlertDialog {

    /**
     * 注册时填写手机号码错误提示框
     *
     * @param context
     * @param msg
     */
    public static void showRegErrorDialog(Context context, String msg)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_message);
        dialog.findViewById(R.id.tv_title).setVisibility(View.GONE);
        ((TextView) dialog.findViewById(R.id.tv_message)).setText(msg);
        dialog.findViewById(R.id.ll_btn).setVisibility(View.GONE);
        dialog.findViewById(R.id.ll_bottom).setVisibility(View.VISIBLE);
        TextView tv_sure = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 设置签到地点
     *
     * @param context
     * @param listener
     */
    public static void showSetQianDaoDialog(Context context, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_category_delete);
        dialog.findViewById(R.id.tv_title).setVisibility(View.GONE);
        ((TextView) dialog.findViewById(R.id.tv_message)).setText("是否设定当前地点为店铺考勤地点？");
        Button btn_left = (Button) dialog.findViewById(R.id.btn_left);
        btn_left.setText("是");
        btn_left.setTextColor(context.getResources().getColor(R.color.title_6_color));
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    listener.onSure(null);
                dialog.dismiss();
            }
        });
        Button btn_right = (Button) dialog.findViewById(R.id.btn_right);
        btn_right.setText("否");
        btn_right.setTextColor(context.getResources().getColor(R.color.title_6_color));
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 显示签到信息
     *
     * @param context
     * @param second
     */
    public static void showQianDaoDialog(Context context, int second)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_qiandao);
        dialog.show();
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                mHandler.removeCallbacks(this);
                dialog.dismiss();
            }
        }, second * 1000);
    }

    /**
     * 销售详情退货弹出框
     *
     * @param context
     */
    public static void showSaleTuiDialog(Context context, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        final Integer[] radioButtonId = {R.id.tui_a, R.id.tui_b, R.id.tui_c, R.id.tui_d};

        dialog.setContentView(R.layout.dialog_tui);
        dialog.show();
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    for (int id : radioButtonId) {
                        RadioButton rd = (RadioButton) dialog.findViewById(id);
                        if (rd.isChecked()) {
                            listener.onSure(rd.getText().toString());
                        }
                    }
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除分类提醒框
     *
     * @param context
     */
    public static void showCategoryDeleteDialog(Context context, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_category_delete);
        dialog.show();
        dialog.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    listener.onSure(null);
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示提醒框
     *
     * @param context
     * @param title
     * @param msg
     * @param listener
     */
    public static void showRemindDialog(Context context, String title, String msg, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_message);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        ((TextView) dialog.findViewById(R.id.tv_message)).setText(msg);
        dialog.findViewById(R.id.ll_bottom).setVisibility(View.GONE);
        dialog.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    listener.onSure(null);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }
    /**
     * 单选显示提醒框
     *
     * @param context
     * @param title
     * @param msg
     * @param listener
     */
    public static void showRemindDialog2(Context context, String title, String msg, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_message_two);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        ((TextView) dialog.findViewById(R.id.tv_message)).setText(msg);
        dialog.findViewById(R.id.ll_bottom).setVisibility(View.GONE);
        dialog.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    listener.onSure(null);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    public static void showListDialog(Context context, String title, String[] items, final IAlertListDialogItemClickListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_listview);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_title);
        tv_message.setText(title);
        ListView lv = (ListView) dialog.findViewById(R.id.lv_content);
        lv.setAdapter(new ListViewDialogAdapter(context, items));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                if (listener != null)
                    listener.onItemClick(position);
                dialog.dismiss();
            }
        });
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 收银结账折扣输入框
     */
    public static void showDiscountDialog(Context context, final String title, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_discount);
        final EditText et = (EditText) dialog.findViewById(R.id.et);
        if (title != null) {
            ((TextView) dialog.findViewById(R.id.tv_title)).setText(title);
            et.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        dialog.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                    listener.onSure(et);
                dialog.dismiss();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (title != null) return;
                String temp = s.toString().trim();
                if (TextUtils.isEmpty(temp)) return;
                if (temp.length() > 4) {
                    et.setText(temp.substring(0, temp.length() - 1));
                    return;
                }
                double dis = Double.parseDouble(temp);
                if (dis < 0) {
                    et.setText(0 + "");
                } else if (dis > 10) {
                    et.setText(10 + "");
                }
            }
        });
        dialog.show();
    }

    /**
     * 选择员工权限
     */

    public static void choicePermission(Context context, final IAlertListDialogItemClickListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_choice_permission);
        dialog.findViewById(R.id.ll_dianzhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
                if (listener != null) {
                    listener.onItemClick(0);
                }
            }
        });
        dialog.findViewById(R.id.ll_yuangong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
                if (listener != null) {
                    listener.onItemClick(1);
                }
            }
        });
        dialog.show();
    }

    /**
     * 选取支付方式
     */
    public static void showPayModeDialog(Context context, boolean isSee, final IAlertListDialogItemClickListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_pay_mode);
        if (isSee) {
            dialog.findViewById(R.id.ll_petcard).setVisibility(View.VISIBLE);
        }
        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (listener != null)
                    listener.onItemClick(0);
            }
        });
        dialog.findViewById(R.id.ll_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (listener != null)
                    listener.onItemClick(1);
            }
        });
        dialog.findViewById(R.id.ll_wepay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (listener != null)
                    listener.onItemClick(2);
            }
        });
        dialog.findViewById(R.id.ll_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (listener != null)
                    listener.onItemClick(3);
            }
        });
        dialog.findViewById(R.id.ll_petcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if (listener != null)
                    listener.onItemClick(4);
            }
        });
        dialog.show();
    }

    public static void editTextDialog(final Context context,String count,String title, final IAlertDialogListener listener)
    {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_count);
        final EditText et = (EditText) dialog.findViewById(R.id.et_count);
        TextView mTvTitle = (TextView) dialog.findViewById(R.id.tv_title1);
        mTvTitle.setText(title);
        et.setHint(count + "");
        Utils.showSoftInput((Activity) context);
        dialog.findViewById(R.id.btn_cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int count = 0;
                String strNum = et.getText().toString().trim();
                if (!TextUtils.isEmpty(strNum)) {
                    count = Integer.parseInt(strNum);
                }
                if (count > 0) {
                    et.setText(count - 1 + "");
                }
            }
        });

        dialog.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String val = et.getText().toString().trim();
                int count = 0;
                if(!TextUtils.isEmpty(val)){
                    count = Integer.parseInt(val);
                }
                et.setText(count + 1 + "");
            }
        });

        dialog.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int count = 0;
                String strNum = et.getText().toString().trim();
                if (!TextUtils.isEmpty(strNum)) {
                    count = Integer.parseInt(strNum);
                    if (listener != null && count > 0)
                        listener.onSure(count);
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public interface IAlertDialogListener {
        void onSure(Object obj);
    }

    public interface IAlertListDialogItemClickListener {
        void onItemClick(int position);
    }
}
