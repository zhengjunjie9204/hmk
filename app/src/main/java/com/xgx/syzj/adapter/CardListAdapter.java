package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.CardType;
import com.xgx.syzj.utils.StrUtil;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月22日 14:54
 */
public class CardListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardType> mList;
    private HoldClass hold;

    public CardListAdapter(Context context, List<CardType> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void appendList(List<CardType> list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void insertHead(CardType type) {
        if (type instanceof CardType) {
            mList.add(0, type);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CardType card = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card, null);
            hold.rl_contain = (RelativeLayout) convertView.findViewById(R.id.rl_contain);
            hold.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            hold.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        if (card.isSupportIntegral() && card.isSupportValue() && card.isSupportCount()) {
            //金卡
            hold.rl_contain.setBackgroundResource(R.mipmap.card_gold_bg);
        } else if (card.isSupportCount()) {
            //计次
            hold.rl_contain.setBackgroundResource(R.mipmap.card_count_bg);
        } else {
            //储值
            hold.rl_contain.setBackgroundResource(R.mipmap.card_normal_bg);
        }
        hold.tv_type.setText(card.getCardTypeName());
        hold.tv_content.setText(StrUtil.getCardContent(card));

        return convertView;
    }

    static class HoldClass {
        private RelativeLayout rl_contain;
        private TextView tv_type;
        private TextView tv_content;
    }
}
