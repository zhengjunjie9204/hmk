package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.stockRecordHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情物品列表适配器
 *
 * @author zajo
 * @created 2015年09月23日 10:31
 */
public class InHistoryAdapter extends BaseAdapter {
    private  String mflag="0";
    private Context mContext;
    private List<stockRecordHistory> mDataList = new ArrayList<>();


    public InHistoryAdapter(Context context, List<stockRecordHistory> mDataList){
        this.mContext = context;
        this.mDataList = mDataList;

    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {

        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldClass hold;
        stockRecordHistory data = mDataList.get(position);
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_in_out, null);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }

            hold.tv_time.setText(data.getCreateTime());
            hold.tv_count.setText( data.getStock_count()+ "件");

        return convertView;
    }

    class HoldClass{
        TextView tv_time,tv_count;
    }
}