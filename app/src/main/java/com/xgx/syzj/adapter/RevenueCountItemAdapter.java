package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.CountItemsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 32918 on 2016/8/10.
 */
public class RevenueCountItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<CountItemsBean> countItemsList;
    private Map<Integer,CountItemsBean> data=new HashMap<>();
    private CountItems countItems;
    public RevenueCountItemAdapter(Context mContext,List<CountItemsBean> countItemsList,CountItems countItems){
        this.mContext = mContext;
        this.countItemsList= countItemsList;
        this.countItems=countItems;
    }
    @Override
    public int getCount()
    {
        return countItemsList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView ==null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_count_item,null);
            new ViewHolder(view);
            convertView = view;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final CountItemsBean bean = countItemsList.get(position);
        holder.mTvName.setText(bean.getName());
        holder.mTvNum.setText(""+bean.getCount());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.put(position,bean);
                    countItems.getCountItems(data,position);
                }else{
                    data.remove(position);
                    countItems.getCountItems(data,position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView mTvName,mTvNum;
        CheckBox mCheckBox;
        public ViewHolder(View v){
            mTvName = (TextView)v.findViewById(R.id.item_count_name);
            mTvNum = (TextView)v.findViewById(R.id.item_count_num);
            mCheckBox = (CheckBox)v.findViewById(R.id.item_count_check);
            v.setTag(this);
        }
    }
    public interface  CountItems{
        void getCountItems(Map<Integer,CountItemsBean> data,int position);
    }
}
