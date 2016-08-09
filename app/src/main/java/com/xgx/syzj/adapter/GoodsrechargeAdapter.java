package com.xgx.syzj.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Storeitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class GoodsrechargeAdapter extends BaseAdapter {
    Context mContext;
    List<Storeitem> mList;
    private Map<Integer, Storeitem> selectMap;
    public GoodsrechargeAdapter(Context context, List<Storeitem> mList)
    {
        this.mContext = context;
        this.mList = mList;
        selectMap = new HashMap<>();
    }

    public Map<Integer, Storeitem> getSlectMap(){
        return selectMap;
    }

    public void cleanMap(){
        selectMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
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
        final Storeitem item = mList.get(position);
        hold.tv_name.setText(item.getName());
        hold.tv_money.setText(item.getPrice() + "");
        hold.et_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String s = hold.et_count.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    hold.cb_wash.setChecked(true);
                    int c = Integer.parseInt(s.toString());
                    hold.tv_money.setText(item.getPrice() * c + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        if (selectMap.containsKey(item.getId())) {
            hold.cb_wash.setChecked(true);
        } else {
            hold.cb_wash.setChecked(false);
        }
        hold.cb_wash.setOnClickListener(new MyClickListener(item));
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        Storeitem item;

        MyClickListener(Storeitem item)
        {
            this.item = item;
        }

        @Override
        public void onClick(View v)
        {
            if (selectMap.containsKey(item.getId())) {
                selectMap.clear();
            }else{
                selectMap.clear();
                selectMap.put(item.getId(),item);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public class HoldClass {
        TextView tv_name;
        TextView tv_money;
        EditText et_count;
        CheckBox cb_wash;
    }


    private SignledListener mListener;
    public interface SignledListener{
        public void onStoreClick(Storeitem item);
    }

    public void setSignledClick(SignledListener mListener){
        this.mListener = mListener;
    }

}