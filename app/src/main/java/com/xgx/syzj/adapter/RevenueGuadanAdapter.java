package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.bean.Cart;
import com.xgx.syzj.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂单列表适配器
 *
 * @author zajo
 * @created 2015年09月22日 16:36
 */
public class RevenueGuadanAdapter extends BaseAdapter {

    private Context mContext;
    private List<Cart> mList = new ArrayList<>();
    private HoldClass hold;

    public RevenueGuadanAdapter(Context context, List<Cart> list){
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<Cart> list){
        if (list == null || list.size() == 0) return;
        this.mList.addAll(list);
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
        final Cart gd = mList.get(position);
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_guadan, null);
            hold.iv_goods = (CircleImageView) convertView.findViewById(R.id.iv_goods);
            hold.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_goods = (TextView) convertView.findViewById(R.id.tv_goods);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Api.getImageLoader().get("http://pic31.nipic.com/20130719/3347542_103839221000_2.jpg", hold.iv_goods);
        hold.tv_name.setText(gd.getAssociatorName());
        hold.tv_num.setText(gd.getCartName());
        if (gd.getCartDetails() != null && gd.getCartDetails().size() > 0) {
            hold.tv_goods.setText(gd.getCartDetails().get(0).getProductName());
        } else {
            hold.tv_goods.setText("");
        }
        hold.tv_money.setText("¥ " + gd.getStrTotalValue());
        return convertView;
    }

    class HoldClass{
        CircleImageView iv_goods;
        TextView tv_num,tv_name,tv_goods,tv_money;
    }
}
