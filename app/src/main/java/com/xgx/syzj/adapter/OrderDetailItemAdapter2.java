package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.OrderDetail;
import com.xgx.syzj.bean.Product;
import com.xgx.syzj.bean.ProductItems;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.List;

/**
 * Created by 32918 on 2016/8/9.
 */
public class OrderDetailItemAdapter2 extends BaseAdapter {
    private Context mContext;
    private List<ProductItems> list;
    private List<Product> mProductList;
    private List<OrderDetail> mOrderDet;
    private int flag;//0项目信息 1商品信息

    public OrderDetailItemAdapter2(Context mContext, List<ProductItems> list, List<Product> mProductList, int flag, List<OrderDetail> mOrderDet)
    {
        this.mContext = mContext;
        this.list = list;
        this.mProductList = mProductList;
        this.flag = flag;
        this.mOrderDet=mOrderDet;
    }

    @Override
    public int getCount()
    {
        if (flag == 0) {
            return list.size();
        }else if (flag == 1) {
            return mProductList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.payorder_member_money_record1, null);
            new ViewHolder(v);
            convertView = v;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if(flag==0){
            OrderDetail orderDetail = mOrderDet.get(position);
            ProductItems project = list.get(position);
            holder.tv_time.setText(project.getName());
            holder.tv_add.setText(orderDetail.getFee()+"");
//            holder.tv_count.setText(orderDetail.getOrderAmount()+"");
        }else if(flag==1){
            OrderDetail orderDetail = mOrderDet.get(position);
            Product product = mProductList.get(position);
            holder.tv_time.setText(product.getName());
            holder.tv_add.setText(orderDetail.getFee()+"");
//            holder.tv_count.setText(orderDetail.getOrderAmout()+"");
        }


        return convertView;
    }

    static class ViewHolder {
        TextView tv_time,tv_add;

        public ViewHolder(View view)
        {
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_add = (TextView) view.findViewById(R.id.tv_add);
//            tv_count = (TextView) view.findViewById(R.id.tv_count);
            view.setTag(this);
        }
    }
}
