package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.ConsumeInfo;

import java.util.List;

/**
 * Created by 32918 on 2016/8/11.
 */
public class MemberConsumeAdapter extends BaseAdapter {
    private Context mContext;
    private List<ConsumeInfo> items;

    public MemberConsumeAdapter(Context mContext, List<ConsumeInfo> items)
    {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount()
    {
        return items.size();
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_consume_info,null);
            new ViewHolder(v);
            convertView = v;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ConsumeInfo info = items.get(position);
        holder.mTvTime.setText(info.getTime());
        holder.mTvMoney.setText(info.getPrice());
        return convertView;
    }

    class ViewHolder {
        TextView mTvTime;
        TextView mTvMoney;

        public ViewHolder(View v)
        {
            mTvTime = (TextView) v.findViewById(R.id.item_consume_time);
            mTvMoney = (TextView) v.findViewById(R.id.item_consume_money);
            v.setTag(this);
        }
    }
}
