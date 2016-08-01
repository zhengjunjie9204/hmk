package com.xgx.syzj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.AppConfig;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zajo
 * @created 2015年09月23日 15:48
 */
public class RevenueGoodsDataAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    private HoldClass hold;
    //选中商品数量
    private Map<String, Integer> goodsItems = new HashMap<>();
    private OnItemDeleteClick onItemDeleteClick;

    public RevenueGoodsDataAdapter(Context context, Map<String, Integer> map, OnItemDeleteClick onItemDeleteClick) {
        this.mContext = context;
        this.goodsItems = map;
        this.onItemDeleteClick = onItemDeleteClick;
    }

    public void appendList(List<Goods> list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanData(){
        mList.clear();
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
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_goods, null);
            hold.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            hold.tv_select_count = (TextView) convertView.findViewById(R.id.tv_select_count);
            int padding = Utils.getDIP((Activity)mContext, 5);
            int size = AppConfig.SCREEN_WIDTH/3 - padding;
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(size,size);
            convertView.setLayoutParams(param);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Api.getImageLoader().get(goods.getImage(), hold.iv_goods);
        hold.tv_name.setText(goods.getProductName());
        hold.tv_count.setText("库存："+goods.getQuantity());
        hold.tv_money.setText("¥ "+goods.getStrSellingPrice());
        if (goodsItems.get(goods.getProductName()+goods.getProductId()) != null &&
                goodsItems.get(goods.getProductName()+goods.getProductId()) > 0){
            hold.iv_delete.setVisibility(View.VISIBLE);
            hold.tv_select_count.setVisibility(View.VISIBLE);
            hold.tv_select_count.setText(goodsItems.get(goods.getProductName()+goods.getProductId())+"");
        } else {
            hold.iv_delete.setVisibility(View.GONE);
            hold.tv_select_count.setVisibility(View.GONE);
        }
        hold.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemDeleteClick != null)
                    onItemDeleteClick.onClick(goods);
            }
        });
        return convertView;
    }

    static class HoldClass{
        private ImageView iv_goods,iv_delete;
        private TextView tv_name,tv_count,tv_money,tv_select_count;
    }

    public interface OnItemDeleteClick{
        void onClick(Goods goods);
    }
}
