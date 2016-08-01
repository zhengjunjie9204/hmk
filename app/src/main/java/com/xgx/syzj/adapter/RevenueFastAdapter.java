package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情物品列表适配器
 *
 * @author zajo
 * @created 2015年09月23日 10:31
 */
public class RevenueFastAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private List<String> nameList=new ArrayList<>();

    public RevenueFastAdapter(Context context, List<String> list,List<String> nameList){
        this.mContext = context;
        this.mList = list;
        this.nameList=nameList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_history, null);
            hold.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_name.setText(nameList.get(position));
        hold.tv_number.setText(mList.get(position));
        return convertView;
    }

    class HoldClass{
        TextView tv_name,tv_number,tv_jifen,tv_money;
    }
}
