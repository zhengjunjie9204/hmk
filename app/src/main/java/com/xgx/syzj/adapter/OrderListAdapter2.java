package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.OrderList;

import java.util.List;

/**
 * Created by 32918 on 2016/8/8.
 */
public class OrderListAdapter2 extends BaseAdapter {
    private Context mContext;
    private List<OrderList> mDataList;
    public OrderListAdapter2(Context mContext, List<OrderList> mDataList)
    {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount()
    {
        return mDataList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_list, null);
            new ViewHolder(view);
            convertView = view;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        OrderList orderList = mDataList.get(position);
        holder.mTvName.setText(orderList.getName());
        holder.mTvTime.setText(orderList.getCreateTime());
        holder.mTvMobile.setText(orderList.getMobile());
        holder.mTvMoney.setText(""+orderList.getPayAmount());
        holder.mTvNumber.setText(orderList.getCarNumber());
        return convertView;
    }

    static class ViewHolder {
        TextView mTvNumber, mTvTime, mTvMoney, mTvName, mTvMobile;

        public ViewHolder(View v)
        {
            mTvMobile = (TextView) v.findViewById(R.id.tv_mobile);
            mTvNumber = (TextView) v.findViewById(R.id.tv_number);
            mTvName = (TextView) v.findViewById(R.id.tv_name);
            mTvTime = (TextView) v.findViewById(R.id.tv_time);
            mTvMoney = (TextView) v.findViewById(R.id.tv_money);
            v.setTag(this);
        }
    }
}
