package com.xgx.syzj.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Product;
import com.xgx.syzj.bean.ProductItems;
import com.xgx.syzj.datamodel.BillGoodsReturnModel;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CustomAlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by 32918 on 2016/8/9.
 */
public class OrderDetailItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductItems> list;
    private List<Product> mProductList;
    private int flag;//0项目信息 1商品信息
    private Product product;

    public OrderDetailItemAdapter(Context mContext, List<ProductItems> list, List<Product> mProductList, int flag)
    {
        this.mContext = mContext;
        this.list = list;
        this.mProductList = mProductList;
        this.flag = flag;
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_list_product, null);
            new ViewHolder(v);
            convertView = v;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (flag == 0) {
            ProductItems project = list.get(position);
            if(project.getPayType()==8)
            {
                holder.mTvMoney.setText("计次卡");
            }else{
                holder.mTvMoney.setText("￥" + project.getTotalPrice());
            }
            holder.mTvName.setText(project.getName());

            holder.mTvTime.setText("工时：" + project.getLaborTime());
        } else if (flag == 1) {
             product = mProductList.get(position);
            holder.mTvName.setText(product.getName());
            holder.mTvMoney.setText("￥" + product.getTotalPrice());
            holder.mTvTime.setText("数量：" + product.getCount());
            holder.mTvBack.setVisibility(View.VISIBLE);
            holder.mTvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    CustomAlertDialog.showSaleTuiDialog(mContext, new CustomAlertDialog.IAlertDialogListener() {
                        @Override
                        public void onSure(Object obj)
                        {
                            Message msg = new Message();
                            msg.what = 0x03;
                            msg.obj = obj;
                            mHandler.sendMessage(msg);
                        }
                    });
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        TextView mTvName, mTvMoney, mTvTime, mTvBack;

        public ViewHolder(View view)
        {
            mTvName = (TextView) view.findViewById(R.id.item_tv_name);
            mTvMoney = (TextView) view.findViewById(R.id.item_tv_money);
            mTvTime = (TextView) view.findViewById(R.id.item_tv_time);
            mTvBack = (TextView) view.findViewById(R.id.item_tv_back);
            view.setTag(this);
        }
    }
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x03:
                    String getStr =(String) msg.obj;
                    if(!TextUtils.isEmpty(getStr)){
//                        int employeeId = CacheUtil.getmInstance().getUser().getEmployeeId();
//                        BillGoodsReturnModel.doRequest(product.getProductId(),employeeId,);
//                        mList.remove(mList.get(removeItemId));
//                        Map<String,Integer> map = new HashMap<String,Integer>();
//                        map.put("returnId",returnId);
//                        EventBus.getDefault().post(map);
////                        saleHistoryInterfaces.notifyChangeData(removeItemId);
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
