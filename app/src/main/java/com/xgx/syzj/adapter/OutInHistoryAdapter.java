package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情物品列表适配器
 *
 * @author zajo
 * @created 2015年09月23日 10:31
 */
public class OutInHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> cList = new ArrayList<>();
    private List<String> tList=new ArrayList<>();

    public OutInHistoryAdapter(Context context, List<String> clist, List<String> tList){
        this.mContext = context;
        this.cList = clist;
        this.tList=tList;
    }


    @Override
    public int getCount() {
        return cList.size();
    }

    @Override
    public Object getItem(int position) {
        return cList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldClass hold;
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_in_out, null);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_time.setText(tList.get(position));
        hold.tv_count.setText(cList.get(position));
        return convertView;
    }

    class HoldClass{
        TextView tv_time,tv_count;
    }
}
