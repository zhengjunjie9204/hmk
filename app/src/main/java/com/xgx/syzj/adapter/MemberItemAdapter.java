package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.CountItemsBean;

import java.util.List;

/**
 * Created by 32918 on 2016/8/11.
 */
public class MemberItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<CountItemsBean> items;

    public MemberItemAdapter(Context mContext, List<CountItemsBean> items)
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_member_item,null);
            new ViewHolder(v);
            convertView = v;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CountItemsBean bean = items.get(position);
        holder.mTvName.setText(bean.getName());
        holder.mTvCount.setText(bean.getCount()+"");
        return convertView;
    }

    class ViewHolder {
        TextView mTvName;
        TextView mTvCount;

        public ViewHolder(View v)
        {
            mTvName = (TextView) v.findViewById(R.id.item_count_name);
            mTvCount = (TextView) v.findViewById(R.id.item_count_num);
            v.setTag(this);
        }
    }
}
