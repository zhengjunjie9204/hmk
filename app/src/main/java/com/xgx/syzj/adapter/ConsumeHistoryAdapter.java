package com.xgx.syzj.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.ConsumeHistory;

import java.util.List;

/**
 * 会员消费记录适配器
 *
 * @author zajo
 * @created 2015年09月21日 15:05
 */
public class ConsumeHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<ConsumeHistory> mList;
    private HoldClass hold;

    public ConsumeHistoryAdapter(Context context, List<ConsumeHistory> mList){
        this.context = context;
        this.mList = mList;
    }

    public void appendList(List<ConsumeHistory> list){
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
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
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_consumption, null);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_num = (TextView) convertView.findViewById(R.id.tv_num1);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(hold);//getStringByFormat
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        final ConsumeHistory data = mList.get(position);
        Log.e("zjj",data.toString());
        if(data.getProductList()!=null){
            List<ConsumeHistory.ItemList> itemList = data.getItemList();
            ConsumeHistory.ItemList item = itemList.get(0);
            hold.tv_name.setText(item.name);
            hold.tv_num.setText(item.amount+"");
            hold.tv_money.setText("￥"+item.price);
        }else if(data.getItemList()!=null){
            List<ConsumeHistory.ItemList> itemList = data.getItemList();
            ConsumeHistory.ItemList item = itemList.get(0);
            hold.tv_name.setText(item.name);
            hold.tv_num.setText(item.amount);
            hold.tv_money.setText("￥"+item.price);
        }

//        hold.tv_num.setText(data.getQuantity()+"");
//        hold.tv_time.setText(DateUtil.getStringByFormat(data.getPayTime(),DateUtil.dateFormatYMDHMS));
        hold.tv_time.setText(data.getPayTime());
        return convertView;
    }

    class HoldClass{
        private TextView tv_name,tv_money,tv_time,tv_num;
    }
}
