package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.OrderForm;
import com.xgx.syzj.bean.OrderFormList;
import com.xgx.syzj.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * @author zajo
 * @created 2015年11月20日 11:20
 */
public class OrderFormAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {

    private Context mContext;
    private List<OrderForm> mList = new ArrayList<>();
    private int[] mSectionIndices;
    private Long[] mSectionLetters;

    public OrderFormAdapter(Context context) {
        this.mContext = context;
    }

    public void appendList(List<OrderFormList> list) {
        if (list == null || list.size() == 0) return;
        mSectionIndices = new int[list.size()];
        mSectionLetters = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mList.addAll(list.get(i).getList());
            mSectionIndices[i] = list.get(i).getList().size();
            mSectionLetters[i] = list.get(i).getData();
        }
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        // set header text as first char in name
        holder.text.setText(StrUtil.getFriendlyTime(mList.get(position).getOrderTime(), "yyyy-MM-dd"));

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mList.get(position).getOrderTime();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_order_form, parent, false);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_reach = (TextView) convertView.findViewById(R.id.tv_reach);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderForm of = mList.get(position);
        holder.tv_name.setText(of.getUserName()+"("+of.getUserPhone()+")");
        if (of.getStatus() == OrderForm.STATUS.REACH) {
            holder.tv_reach.setText("已到达");
            holder.tv_reach.setVisibility(View.VISIBLE);
        } else if(of.getStatus() == OrderForm.STATUS.INVALID) {
            holder.tv_reach.setText("已作废");
            holder.tv_reach.setVisibility(View.VISIBLE);
        } else {
            holder.tv_reach.setVisibility(View.GONE);
        }
        holder.tv_num.setText("订单商品:" + of.getOrderId());
        holder.tv_time.setText("预约时间:"+StrUtil.getFriendlyTime(of.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (mSectionIndices.length == 0) {
            return 0;
        }

        if (sectionIndex >= mSectionIndices.length) {
            sectionIndex = mSectionIndices.length - 1;
        } else if (sectionIndex < 0) {
            sectionIndex = 0;
        }
        return mSectionIndices[sectionIndex];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        private TextView tv_name, tv_reach, tv_num, tv_time;
    }
}
