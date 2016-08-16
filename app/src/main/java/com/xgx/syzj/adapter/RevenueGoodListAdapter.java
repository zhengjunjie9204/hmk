package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Goods;
import com.xgx.syzj.ui.RevenuseSellFinishActivity;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;


public class RevenueGoodListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Goods> mList = new ArrayList<>();
    Handler mHandler;

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
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.et_time = (TextView) convertView.findViewById(R.id.et_time);
//            hold.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Goods goods = mList.get(position);
        hold.tv_name.setText(goods.getProductName());
        hold.et_time.setText(goods.getQuantity() + "");
        if (mHandler == null) {
            hold.et_time.setOnClickListener(new MyClickListener(goods));
        }
        hold.tv_money.setText("" + goods.getSellingPrice() * goods.getQuantity());
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        private Goods goods;

        MyClickListener(Goods goods)
        {
            this.goods = goods;
        }

        @Override
        public void onClick(View v)
        {
            CustomAlertDialog.editTextDialog(mContext,String.valueOf(goods.getQuantity()),"请输入", new MyIAlertDialogListener(goods));
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
            goods.setQuantity((int) obj);
            if (null != mHandler) {
                mHandler.sendEmptyMessage(RevenuseSellFinishActivity.HANDLER_MONEY);
            }
            notifyDataSetChanged();
        }
    }

    class HoldClass {
        TextView tv_name, tv_money;
        TextView et_time;
    }
}
