package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xgx.syzj.R;

public class ListViewDialogAdapter extends BaseAdapter {

    Context mContext;
    String[] mList = new String[]{};

    public ListViewDialogAdapter(Context context, String[] mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.item_listview_dialog, null);
        ((TextView) convertView.findViewById(R.id.tv_item)).setText(mList[position]);
        return convertView;
    }
}
