package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
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
import com.xgx.syzj.ui.RevenuseSellFinishActivity;
import com.xgx.syzj.utils.ArithUtils;
import com.xgx.syzj.utils.StrUtil;
import com.xgx.syzj.widget.PhotoViewPagerDialog;

import java.util.ArrayList;
import java.util.List;


public class RevenueGoodListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    private List<Goods> sellList = new ArrayList<>();
    private Handler mHandler;
    private PhotoViewPagerDialog mDialog;

    public RevenueGoodListAdapter(Context context, List<Goods> list, Handler mHandler) {
        this.mContext = context;
        this.mList = list;
        this.mHandler = mHandler;
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
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_goods, null);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.mImg = (ImageView) convertView.findViewById(R.id.item_img);
            hold.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            hold.iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Goods goods = mList.get(position);
        hold.tv_name.setText(goods.getProductName());
        hold.tv_count.setText(goods.getCount() + "");
        hold.tv_quantity.setText(goods.getQuantity()+"");

        if (goods.getCount()>0){
            hold.iv_delete.setVisibility(View.VISIBLE);
        }else{
            hold.iv_delete.setVisibility(View.GONE);
        }
        if (goods.getCount() > 0) {
            hold.tv_money.setText("￥" + ArithUtils.showString(ArithUtils.mul(goods.getSellingPrice(), goods.getCount())));
        } else {
            hold.tv_money.setText("￥" + ArithUtils.showString(ArithUtils.mul(goods.getSellingPrice() ,1)));
        }
        if (!StrUtil.isEmpty(goods.getImage())) {
            Picasso.with(mContext).load(goods.getImage()).into(hold.mImg);
        }
        hold.iv_delete.setOnClickListener(new MyClickListener(goods));
        hold.iv_add.setOnClickListener(new MyClickListener(goods));
        if (mHandler == null) {
            hold.mImg.setOnClickListener(new MyClickListener(goods));
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Goods goods;

        MyClickListener(Goods goods) {
            this.goods = goods;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_img:
                    if (mDialog == null) {
                        List<String> list = new ArrayList<>();
                        for (Goods goods1 : mList) {
                            if (!TextUtils.isEmpty(goods1.getImage())) {
                                list.add(goods1.getImage());
                            }
                        }
                        mDialog = new PhotoViewPagerDialog(mContext, list);
                    }
                    mDialog.show();
                    break;
                case R.id.iv_add:
                    goods.setCount(goods.getCount()+1);
                    if (null != mHandler) {
                        mHandler.sendEmptyMessage(RevenuseSellFinishActivity.HANDLER_MONEY);
                    }
                    if (!sellList.contains(goods)) {
                        sellList.add(goods);
                    }
                    notifyDataSetChanged();
                    break;
                case R.id.iv_delete:
                    int count=goods.getCount()-1;
                    goods.setCount(count);
                    if (null != mHandler) {
                        mHandler.sendEmptyMessage(RevenuseSellFinishActivity.HANDLER_MONEY);
                    }
                    if (count==0){
                        sellList.remove(goods);
                    }
                    notifyDataSetChanged();
                    break;
            }

        }
    }

    class HoldClass {
        TextView tv_name, tv_money,tv_quantity;
        TextView tv_count;
        ImageView mImg,iv_delete,iv_add;
    }

    public List<Goods> getSellList() {
        return sellList;
    }
}
