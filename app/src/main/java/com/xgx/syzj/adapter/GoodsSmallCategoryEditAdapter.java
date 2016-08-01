package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xgx.syzj.bean.GoodsCategory;
import com.xgx.syzj.widget.EditCategoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑分类小分类列表
 *
 * @author zajo
 * @created 2015年10月20日 16:21
 */
public class GoodsSmallCategoryEditAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GoodsCategory> mList = new ArrayList<>();
    private EditCategoryView.IEditDeleteListener mListener;

    public GoodsSmallCategoryEditAdapter(Context context, EditCategoryView.IEditDeleteListener listener){
        this.mContext = context;
        this.mListener = listener;
    }

    public void appendList(List<GoodsCategory> list){
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanList(){
        mList.clear();
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
        GoodsCategory type = mList.get(position);
        EditCategoryView view = new EditCategoryView(mContext);
        view.setTag(type);
        view.setListener(mListener);
        view.setEditText(type.getCategoryName());
        convertView = view;
        return convertView;
    }
}
