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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.ui.RevenuseSellFinishActivity;
import com.xgx.syzj.widget.CustomAlertDialog;
import com.xgx.syzj.widget.PhotoViewPagerDialog;

import java.util.ArrayList;
import java.util.List;


public class RevenueGoodListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    private Handler mHandler;
    private PhotoViewPagerDialog mDialog;

    public RevenueGoodListAdapter(Context context, List<Goods> list, Handler mHandler)
    {
        this.mContext = context;
        this.mList = list;
        this.mHandler = mHandler;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_goods_sell, null);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            hold.mTvUnit = (TextView) convertView.findViewById(R.id.item_tv_unit);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.et_time = (TextView) convertView.findViewById(R.id.et_time);
            hold.mImg = (ImageView) convertView.findViewById(R.id.item_img);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.mTvUnit.setText("数量");
        Goods goods = mList.get(position);
        hold.tv_name.setText(goods.getProductName());
        hold.et_time.setText(goods.getCount() + "");
        if (goods.getCount() > 0) {
            hold.tv_money.setText("" + goods.getSellingPrice() * goods.getCount());
        } else {
            hold.tv_money.setText("" + (goods.getSellingPrice() * 1));
        }
        Picasso.with(mContext).load(goods.getImage()).centerCrop().into(hold.mImg);
        if (mHandler == null) {
            hold.et_time.setOnClickListener(new MyClickListener(goods, 0));
            hold.mImg.setOnClickListener(new MyClickListener(goods, 1));
        }
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Goods goods;
        private int isDialog;//0不需要弹出view框 1是需要弹框

        MyClickListener(Goods goods, int isDialog)
        {
            this.goods = goods;
            this.isDialog = isDialog;
        }

        @Override
        public void onClick(View v)
        {
            if (isDialog == 0) {
                CustomAlertDialog.editTextDialog(mContext, String.valueOf(goods.getQuantity()), "请输入", new MyIAlertDialogListener(goods));
            } else if (isDialog == 1) {
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
            }
        }
    }

    class MyIAlertDialogListener implements CustomAlertDialog.IAlertDialogListener {
        private Goods goods;

        MyIAlertDialogListener(Goods goods)
        {
            this.goods = goods;
        }

        @Override
        public void onSure(Object obj)
        {
            int count = (int) obj;
            if (count > goods.getQuantity()) {
                Toast.makeText(mContext, "数量不能大于库存", Toast.LENGTH_SHORT).show();
            } else {
                goods.setCount((int) obj);
                if (null != mHandler) {
                    mHandler.sendEmptyMessage(RevenuseSellFinishActivity.HANDLER_MONEY);
                }
                notifyDataSetChanged();
            }
        }
    }

    class HoldClass {
        TextView tv_name, tv_money;
        TextView et_time, mTvUnit;
        ImageView mImg;
    }
}
