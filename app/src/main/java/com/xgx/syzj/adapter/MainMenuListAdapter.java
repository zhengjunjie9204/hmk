package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgx.syzj.R;

/**
 * 首页侧滑栏列表适配器
 *
 * @author zajo
 * @created 2015年08月11日 14:43
 */
public class MainMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] MenuTitles;
    private int[] MenuImagesN;
    private int[] MenuImagesP;
    private HoldClass hold;
    private int index = 0;

    public MainMenuListAdapter(Context mContext, int[] menuImagesN, int[] menuImagesP, String[] menuTitles) {
        MenuTitles = menuTitles;
        MenuImagesN = menuImagesN;
        MenuImagesP = menuImagesP;
        this.mContext = mContext;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return MenuTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return MenuTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_main_menu, null);
            hold.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        if (index == position){
            hold.iv_icon.setBackgroundResource(MenuImagesP[position]);
            hold.tv_title.setTextColor(mContext.getResources().getColor(R.color.top_bar_color));
        } else {
            hold.iv_icon.setBackgroundResource(MenuImagesN[position]);
            hold.tv_title.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        hold.tv_title.setText(MenuTitles[position]);
        return convertView;
    }

    static class HoldClass{
        private TextView tv_title;
        private ImageView iv_icon;
    }
}
