package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.StoreItem;
import com.xgx.syzj.ui.MemberSelectProjectActivity;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class GoodsrechargeAdapter extends BaseAdapter {
    Context mContext;
    List<StoreItem> mList;
    private Map<Integer, StoreItem> selectMap;
    private Handler mHandler;

    public GoodsrechargeAdapter(Context context, List<StoreItem> mList, Handler mHandler)
    {
        this.mContext = context;
        this.mList = mList;
        this.mHandler = mHandler;
        selectMap = new HashMap<>();
    }

    public Map<Integer, StoreItem> getSlectMap()
    {
        return selectMap;
    }

    public void cleanMap()
    {
        selectMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup)
    {
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_count_menber, null);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.et_count = (TextView) convertView.findViewById(R.id.et_count);
            hold.cb_wash = (CheckBox) convertView.findViewById(R.id.cb_wash);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        final StoreItem item = mList.get(position);
        hold.tv_name.setText(item.getName());
        if (item.getLaborTime() == 0) {
            item.setLaborTime(1);
        }
        hold.tv_money.setText(String.valueOf(item.getPrice() * item.getLaborTime()));
        hold.et_count.setText(String.valueOf(item.getLaborTime()));
        hold.et_count.setOnClickListener(new MyClickListener(item, true));
        if (selectMap.containsKey(item.getId())) {
            hold.cb_wash.setChecked(true);
        } else {
            hold.cb_wash.setChecked(false);
        }
        hold.cb_wash.setOnClickListener(new MyClickListener(item, false));
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private StoreItem item;
        private boolean isAlert;

        MyClickListener(StoreItem item, boolean isAlert)
        {
            this.item = item;
            this.isAlert = isAlert;
        }

        @Override
        public void onClick(View v)
        {
            if(isAlert){
                CustomAlertDialog.editTextDialog(mContext, String.valueOf(item.getLaborTime()),"请输入充值次数", new MyIAlertDialogListener(item));
            }else{
                if (selectMap.containsKey(item.getId())) {
                    selectMap.clear();
                } else {
                    selectMap.clear();
                    selectMap.put(item.getId(), item);
                }
                mHandler.sendEmptyMessage(MemberSelectProjectActivity.STATUS_RECHARGE);
                notifyDataSetChanged();
            }
        }
    }

    class MyIAlertDialogListener implements CustomAlertDialog.IAlertDialogListener{
        private StoreItem item;
        MyIAlertDialogListener(StoreItem item){
            this.item = item;
        }
        @Override
        public void onSure(Object obj)
        {
            item.setLaborTime((Integer) obj);
            selectMap.clear();
            selectMap.put(item.getId(), item);
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
        TextView et_count;
        CheckBox cb_wash;
    }
}