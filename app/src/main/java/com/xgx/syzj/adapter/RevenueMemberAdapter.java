package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情物品列表适配器
 *
 * @author zajo
 * @created 2015年09月23日 10:31
 */
public class RevenueMemberAdapter extends BaseAdapter {

    private Context mContext;
    private List<Member> mList = new ArrayList<>();
    private HoldClass hold;

    public RevenueMemberAdapter(Context context, List<Member> list){
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<Member> list){
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
        final Member member = mList.get(position);
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_member, null);
            hold.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            hold.tv_jifen = (TextView) convertView.findViewById(R.id.tv_jifen);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_name.setText(member.getName());
        hold.tv_phone.setText(member.getPhone());
        hold.tv_jifen.setText("积分："+member.getConsumeRecord());
        hold.tv_money.setText("储值："+member.getStoredMoney());
        return convertView;
    }

    class HoldClass{
        TextView tv_name,tv_phone,tv_jifen,tv_money;
    }
}
