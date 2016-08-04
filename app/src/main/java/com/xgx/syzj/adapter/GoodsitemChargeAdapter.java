package com.xgx.syzj.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.ReItem;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class GoodsitemChargeAdapter implements ExpandableListAdapter{
    private Context mContext;
    private List<ReItem> sList;

    public GoodsitemChargeAdapter(Context context, List sList ) {
        this.mContext = context;
        this.sList = sList;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getGroupCount() {
        if(sList.size()>0){
            return sList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
         if(sList.size()>0){
            return sList.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View convertView, ViewGroup viewGroup) {

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
        ReItem reItem = sList.get(position);
        hold.tv_meal.setText(reItem.getName());
        hold.tv_money2.setText(reItem.getMoney()+"");

        return convertView;
    }

    @Override
    public View getChildView(int position, int i1, boolean b, View convertView, ViewGroup viewGroup) {
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
        ReItem reItem = sList.get(position);
        hold.tv_item.setText(reItem.getName());
        hold.tv_count.setText("15");
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
    public class HoldClass{
        public TextView tv_meal,tv_item,tv_count,tv_money2;
        public CheckBox cb_wash2;

    }
}
