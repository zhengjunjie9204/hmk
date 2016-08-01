package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zajo
 * @created 2015年11月24日 16:11
 */
public class SaleHistroyAdapter extends BaseAdapter {

    private Context mContext;
    private List<BillListItemBean> mList = new ArrayList<>();
    private HoldClass hold;
    private String flag = "";

    public SaleHistroyAdapter(Context context,String flag,List<BillListItemBean> list){
        this.flag = flag;
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<BillListItemBean> list){
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
        final BillListItemBean shy = mList.get(position);
        if(shy.getBillDetails().size() == 0){
            mList.remove(mList.get(position));
            return convertView;
        }
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sale_history, null);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
           // hold.tv_identity = (TextView) convertView.findViewById(R.id.tv_identity);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }

        hold.tv_name.setText(shy.getBillDetails().get(0).getProductName());
//        if (shy.getCustomerType() == 1) {
//            hold.tv_identity.setBackgroundResource(R.mipmap.identity_p);
//        } else {
//            hold.tv_identity.setBackgroundResource(R.mipmap.identity_n);
//        }
        int account = 0 ,totalValue = 0;

        for (BillListItemBean.BillDetailsEntity g: shy.getBillDetails()){
             account +=  g.getQuantity();
             totalValue += g.getTotalValue();
        }
        hold.tv_time.setText(StrUtil.getFriendlyTime(shy.getBillDatetime(), "MM-dd HH:mm"));
        hold.tv_count.setText("共计"+account+"件商品");
        hold.tv_money.setText("￥"+totalValue);
        return convertView;
    }

    static class HoldClass{
        private TextView tv_name,tv_identity,tv_time,tv_count,tv_money;
    }
}
