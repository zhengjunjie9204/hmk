package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xgx.syzj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 收银记账详情页自定义列表
 *
 * @author zajo
 * @created 2015年10月28日 16:19
 */
public class RevenueDetailAutoAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private HoldClass hold;
    private RevenueAutoItemDeleteListener deleteListener;

    public RevenueDetailAutoAdapter(Context context, List<String> list, RevenueAutoItemDeleteListener listener) {
        this.mContext = context;
        this.mList = list;
        this.deleteListener = listener;
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
        final String money = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_revenue_detail_auto, null);
            hold.btn_del = (Button) convertView.findViewById(R.id.btn_del);
            hold.btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null)
                        deleteListener.onDelete(position);
                }
            });
            hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hold.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        String title = "自定义销售";
        int index = position + 1;
        if (index < 10) {
            title += "0" + index;
        } else {
            title += index;
        }
        hold.tv_title.setText(title);
        hold.tv_money.setText("¥ " + money);
        return convertView;
    }

    private static class HoldClass {
        private TextView tv_title, tv_money;
        private Button btn_del;
    }

    public interface RevenueAutoItemDeleteListener{
        void onDelete(int position);
    }
}
