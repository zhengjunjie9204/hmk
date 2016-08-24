package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Product;
import com.xgx.syzj.bean.ProductItems;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.List;

/**
 * Created by 32918 on 2016/8/9.
 */
public class OrderDetailItemAdapter2 extends BaseAdapter {
    private Context mContext;
    private List list;


    public OrderDetailItemAdapter2(Context mContext, List list)
    {
        this.mContext = mContext;
        this.list = list;

    }

    @Override
    public int getCount()
    {

        return 2;
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.payorder_member_money_record, null);
            new ViewHolder(v);
            convertView = v;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tv_time.setText("套餐A");
        holder.tv_add.setText("1000");
        holder.tv_count.setText(2000+"");

        return convertView;
    }

    static class ViewHolder {
        TextView tv_time,tv_add,tv_count;

        public ViewHolder(View view)
        {
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_add = (TextView) view.findViewById(R.id.tv_add);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            view.setTag(this);
        }
    }
}
