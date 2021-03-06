package com.xgx.syzj.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;
import com.xgx.syzj.bean.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表适配器
 *
 * @author zajo
 * @created 2015年08月20日 11:32
 */
public class GoodsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    private HoldClass hold;

    public GoodsListAdapter(Context context, List<Goods> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<Goods> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Goods goods = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, null);
            hold.iv_goods = (ImageView) convertView.findViewById(R.id.iv_good);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.iv_goods.setTag(position);
        List<Goods.ImagesBean> images = goods.getImages();
        for (Goods.ImagesBean image : images) {
            if(!TextUtils.isEmpty(image.getImage())){
                String pic1 = image.getImage();
                Picasso.with(mContext).load(pic1).fit().into(hold.iv_goods);
            }
        }

        hold.tv_name.setText(goods.getProductName());
        hold.tv_count.setText("库存："+goods.getQuantity()+"件");
        hold.tv_money.setText("¥ "+goods.getSellingPrice());
        return convertView;
    }

    static class HoldClass {
        private ImageView iv_goods;
        private TextView tv_name, tv_count, tv_money;
    }

}
