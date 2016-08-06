package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.FastOrder;

import java.util.List;

/**
 * 收银记账详情物品列表适配器
 *
 * @author zajo
 * @created 2015年09月23日 10:31
 */
public class RevenueFastAdapter extends BaseAdapter {

    private Context mContext;
    private List<FastOrder> mList;

    public RevenueFastAdapter(Context context, List<FastOrder> list){
        this.mContext = context;
        this.mList = list;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_car_history, null);
            hold.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_carNum);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        FastOrder fastOrder = mList.get(position);

        hold.tv_name.setText(fastOrder.getCarNumber());
        if (fastOrder.getPayStatus() == 0) {
            hold.tv_result.setText("未完成");
        }else if(fastOrder.getPayStatus() == 3){
            hold.tv_result.setText("已完成");
        }
        return convertView;
    }

    class HoldClass{
        TextView tv_name,tv_number,tv_jifen,tv_result;
    }
}
