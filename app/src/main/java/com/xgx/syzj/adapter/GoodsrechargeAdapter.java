package com.xgx.syzj.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.ReItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class GoodsrechargeAdapter extends BaseAdapter implements View.OnClickListener {
    Context mContext;
    List<ReItem> mList;
    GoodCallBack mCallback;

    public GoodsrechargeAdapter(Context context, List mList ,GoodCallBack callback) {
        this.mContext = context;
        this.mList = mList;
        this.mCallback=callback;
    }

    @Override
    public int getCount() {
        if (mList.size() > 0) {
            return mList.size();

        }
        return 3;
    }

    @Override
    public void onClick(View view) {

        mCallback.click(view);
    }



    public interface GoodCallBack {
        public void click(View v);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_count_menber, null);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.et_count = (EditText) convertView.findViewById(R.id.et_count);
            hold.cb_wash = (CheckBox) convertView.findViewById(R.id.cb_wash);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        final ReItem item = mList.get(position);
        hold.tv_money.setText(item.getMoney()+"");
        hold.et_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = hold.et_count.getText().toString();
                if(!TextUtils.isEmpty(s))
                {
                    hold.cb_wash .setChecked(true);
                    int c=Integer.parseInt(s.toString());
                    hold.tv_money.setText(item.getMoney()*c+"");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hold.cb_wash.setOnClickListener(this);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class HoldClass {
        TextView tv_name;
        TextView tv_money;
        EditText et_count;
        CheckBox cb_wash;
    }



}