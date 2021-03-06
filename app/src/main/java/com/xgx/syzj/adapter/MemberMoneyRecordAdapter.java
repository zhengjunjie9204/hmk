package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Recharge;
import com.xgx.syzj.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员储值记录适配器
 *
 * @author zajo
 * @created 2015年08月31日 16:37
 */
public class MemberMoneyRecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recharge> mList = new ArrayList<>();
    private HoldClass hold;

    public MemberMoneyRecordAdapter(Context context, List<Recharge> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<Recharge> list) {
        if (list == null || list.size() == 0)
            return;
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
        final Recharge record = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_member_money_record, null);
            hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hold.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_time.setText(StrUtil.getFriendlyTime(record.getRechargeDatetime(),"yyyy-MM-dd \n HH:mm:ss"));
        hold.tv_count.setText("¥ " + record.getAfterRechargeValue());
        hold.tv_add.setText("¥ " + record.getRechargeAmount());
        return convertView;
    }

    static class HoldClass {
        TextView tv_time, tv_add, tv_count;
    }
}
