package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.Combo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class GoodsitemChargeAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Combo> mDataList;
    private Map<Integer, Combo> comboMap;

    public GoodsitemChargeAdapter(Context context, List<Combo> mDataList)
    {
        this.mContext = context;
        this.mDataList = mDataList;
        comboMap = new HashMap<>();
    }

    public Map<Integer, Combo> getSlectMap(){
        return comboMap;
    }

    public void cleanMap(){
        comboMap.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount()
    {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int position)
    {
        if (null != mDataList.get(position).getItems()) {
            return mDataList.get(position).getItems().size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }


    @Override
    public View getGroupView(int position, boolean b, View convertView, ViewGroup viewGroup)
    {
        HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_menber_select_item, null);
            hold.tv_meal = (TextView) convertView.findViewById(R.id.tv_meal);
            hold.tv_money2 = (TextView) convertView.findViewById(R.id.tv_money2);
            hold.cb_wash2 = (CheckBox) convertView.findViewById(R.id.cb_wash2);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Combo combo = mDataList.get(position);
        hold.tv_meal.setText(combo.getName());
        hold.tv_money2.setText(combo.getPrice() + "");
        if (comboMap.containsKey(combo.getId())) {
            hold.cb_wash2.setChecked(true);
        } else {
            hold.cb_wash2.setChecked(false);
        }
        hold.cb_wash2.setOnClickListener(new MyClickListener(combo));
        return convertView;
    }

    class MyClickListener implements View.OnClickListener {
        Combo combo;

        MyClickListener(Combo combo)
        {
            this.combo = combo;
        }

        @Override
        public void onClick(View v)
        {
            if (comboMap.containsKey(combo.getId())) {
                comboMap.clear();
            }else{
                comboMap.clear();
                comboMap.put(combo.getId(),combo);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public View getChildView(int position, int position1, boolean b, View convertView, ViewGroup viewGroup)
    {
        HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_menber_select_item2, null);
            hold.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            hold.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        Combo.ItemsBean itemsBean = mDataList.get(position).getItems().get(position1);
        hold.tv_item.setText(itemsBean.getName());
        hold.tv_count.setText("X" + itemsBean.getCount());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    public class HoldClass {
        public TextView tv_meal, tv_item, tv_count, tv_money2;
        public CheckBox cb_wash2;
    }

    private SignledListener mListener;
    public interface SignledListener{
        public void onComboClick(Combo combo);
    }

    public void setSignledClick(SignledListener mListener){
        this.mListener = mListener;
    }
}
