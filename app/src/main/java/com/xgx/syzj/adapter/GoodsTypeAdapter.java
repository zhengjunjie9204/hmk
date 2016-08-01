package com.xgx.syzj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.bean.GoodsCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别适配器
 *
 * @author zajo
 * @created 2015年08月20日 15:53
 */
public class GoodsTypeAdapter extends BaseAdapter {

    public static final String TYPE_BIG = "BIG";
    public static final String TYPE_SMALL = "SMALL";

    private Context mContext;
    private List<GoodsCategory> mList = new ArrayList<>();
    private HoldClass hold;
    private String type = TYPE_BIG;
    private boolean edit = false;
    private int index = 0;

    public void setOnEditClickListener(IEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    private IEditClickListener onEditClickListener;

    public GoodsTypeAdapter(Context context, String type, boolean edit) {
        this.mContext = context;
        this.type = type;
        this.edit = edit;
    }

    public void appendList(List<GoodsCategory> list) {
        if (list == null || list.size() == 0) return;
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setIndex(int index){
        this.index = index;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    public GoodsCategory getCurrentItem(){
        return mList.get(index);
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
        final GoodsCategory goodsType = mList.get(position);
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_type, null);
            hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hold.iv = (ImageView) convertView.findViewById(R.id.iv);
            hold.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEditClickListener != null)
                        onEditClickListener.onEditItemClick(index);
                }
            });
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_title.setText(goodsType.getCategoryName());
        if (TYPE_BIG.equals(type)){
            if (index == position) {
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.top_bar_color));
                hold.tv_title.setTextColor(mContext.getResources().getColor(R.color.white));
                if (edit){
                    hold.iv.setVisibility(View.VISIBLE);
                }
            } else {
                convertView.setBackgroundColor(Color.parseColor("#F7F7F7"));
                hold.tv_title.setTextColor(mContext.getResources().getColor(R.color.title_3_color));
                if (edit){
                    hold.iv.setVisibility(View.INVISIBLE);
                }
            }
        }
        return convertView;
    }

    static class HoldClass {
        private ImageView iv;
        private TextView tv_title;
    }

    public interface IEditClickListener{
        void onEditItemClick(int position);
    }
}
