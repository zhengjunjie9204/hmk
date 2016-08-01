package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.GoodsCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zajo
 * @created 2015年09月23日 15:01
 */
public class RevenueGoodsTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsCategory> mList = new ArrayList<>();
    private HoldClass hold;
    private int mIndex = 0;
    //类型选中商品数量
    private Map<String, Integer> typeItems = new HashMap<>();

    public RevenueGoodsTypeAdapter(Context context,List<GoodsCategory> list, Map<String, Integer> map){
        this.mContext = context;
        this.mList = list;
        this.typeItems = map;
    }

    public void appendList(List<GoodsCategory> list){
        if(list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList(){
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void setIndex(int index){
        this.mIndex = index;
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
        final GoodsCategory type = mList.get(position);
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_type, null);
            hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_title.setText(type.getCategoryName());
        if (mIndex == position) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.top_bar_color));
        } else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
        if(typeItems.containsKey(type.getCategoryName()) && typeItems.get(type.getCategoryName()) > 0) {
            hold.tv_count.setVisibility(View.VISIBLE);
            hold.tv_count.setText(typeItems.get(type.getCategoryName())+"");
        } else {
            hold.tv_count.setVisibility(View.GONE);
        }
        return convertView;
    }

    class HoldClass {
        TextView tv_title,tv_count;
    }
}
