package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.BillGoodsDetailbean;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.ConsumptionItem;
import com.xgx.syzj.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员消费记录适配器
 *
 * @author zajo
 * @created 2015年09月21日 15:05
 */
public class ConsumptionAdapter extends BaseAdapter {

    private Context context;
    private List<BillGoodsDetailbean> mList;
    private HoldClass hold;

    public ConsumptionAdapter(Context context,List<BillGoodsDetailbean> mList){
        this.context = context;
        this.mList = mList;
    }

    public void appendList(List<BillGoodsDetailbean> list){
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
        final BillGoodsDetailbean data = mList.get(position);
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_consumption, null);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(hold);//getStringByFormat
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_name.setText(data.getProductName());
        hold.tv_money.setText("￥"+data.getTotalValue());
        hold.tv_num.setText(data.getQuantity()+"");
        hold.tv_time.setText(DateUtil.getStringByFormat(data.getBillDatetime(),DateUtil.dateFormatYMDHMS));
        return convertView;
    }

    class HoldClass{
        private TextView tv_name,tv_money,tv_time,tv_num;
    }
}
