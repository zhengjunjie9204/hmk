package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.CountRecords;

import java.util.ArrayList;

/**
 * Created by 32918 on 2016/8/11.
 * 冲计记录Adapter
 */
public class CountRecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CountRecords> mList;
    private String[] payType;
    public CountRecordAdapter(Context mContext, ArrayList<CountRecords> mList)
    {
        this.mContext = mContext;
        this.mList = mList;
        payType = mContext.getResources().getStringArray(R.array.pay_type);
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_count_records, null);
            new ViewHolder(view);
            convertView = view;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CountRecords records = mList.get(position);
        holder.mTvTime.setText(records.getPayTime());
        holder.mTvType.setText(""+payType[records.getPayType()]);
        holder.mTvName.setText(records.getName());
        holder.mTvMoney.setText("￥"+records.getFee());
        return convertView;
    }

    class ViewHolder {
        TextView mTvTime, mTvName, mTvType, mTvMoney;

        public ViewHolder(View view)
        {
            mTvTime = (TextView) view.findViewById(R.id.item_tv_record_time);
            mTvType = (TextView) view.findViewById(R.id.item_tv_record_type);
            mTvName = (TextView) view.findViewById(R.id.item_tv_record_name);
            mTvMoney = (TextView) view.findViewById(R.id.item_tv_record_money);
            view.setTag(this);
        }
    }
}
