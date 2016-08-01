package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.bean.Goods;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情页物品列表
 *
 * @author zajo
 * @created 2015年09月24日 11:31
 */
public class RevenueDetailGoodsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    private HoldClass hold;

    public RevenueDetailGoodsAdapter(Context context){
        this.mContext = context;
    }

    public void appendList(List<Goods> list){
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
        final Goods goods = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_detail_goods, null);
            hold.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_all_money = (TextView) convertView.findViewById(R.id.tv_all_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Api.getImageLoader().get(goods.getImage(), hold.iv_goods);
        hold.tv_name.setText(goods.getProductName());
        hold.tv_money.setText("¥ " + goods.getStrSellingPrice());
        hold.tv_count.setText("*" + goods.getRevenueCount()+"个");
        if (goods.getSellingPrice() > 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            hold.tv_all_money.setText("¥ "+ df.format(goods.getSellingPrice() * goods.getRevenueCount()));
        } else {
            hold.tv_all_money.setText("¥ 0");
        }
        return convertView;
    }

    static class HoldClass{
        private ImageView iv_goods;
        private TextView tv_name,tv_money,tv_count,tv_all_money;
    }
}
