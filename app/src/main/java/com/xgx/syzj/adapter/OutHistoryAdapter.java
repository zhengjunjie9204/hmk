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
 * Created by Administrator on 2016/8/17 0017.
 */
public class OutHistoryAdapter extends BaseAdapter {
    private  String mflag="0";
    private Context mContext;
    private List<stockRecordHistory> mDataList = new ArrayList<>();

    public OutHistoryAdapter(Context context, List<stockRecordHistory> mDataList){
        this.mContext = context;
        this.mDataList = mDataList;

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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
        hold.tv_count.setText( data.getStock_count()+ "个");

        return convertView;
    }
    class HoldClass{
        TextView tv_time,tv_count;
    }
}
