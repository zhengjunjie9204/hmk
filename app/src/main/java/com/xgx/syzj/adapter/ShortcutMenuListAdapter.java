package com.xgx.syzj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xgx.syzj.R;

import java.util.ArrayList;

/**
 * 快捷菜单设置适配器
 *
 * @author zajo
 * @created 2015年08月14日 15:30
 */
public class ShortcutMenuListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] MenuTitles;
    private ArrayList<String> selected;
    private HoldClass hold;

    public ShortcutMenuListAdapter(Context mContext, String[] menuTitles, String[] str) {
        MenuTitles = menuTitles;
        this.mContext = mContext;
        selected = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            selected.add(str[i]);
        }
    }

    public ArrayList<String> getSelected() {
        return selected;
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
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shortcut_menu, null);
            hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            hold.cb = (CheckBox) convertView.findViewById(R.id.cb);
            hold.cb.setOnCheckedChangeListener(cbListener);
            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.cb.setTag(position + "");
        hold.tv_title.setText(MenuTitles[position]);
        if (!selected.contains(position + "")) {
            hold.cb.setChecked(false);
        }
        return convertView;
    }

    private CompoundButton.OnCheckedChangeListener cbListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String index = (String) buttonView.getTag();
            if (!isChecked) {
                //取消选择
                selected.remove(index);
            } else {
                //选中
                if (selected.size() >= 2) {
                    buttonView.setChecked(!isChecked);
                    Toast.makeText(mContext, mContext.getString(R.string.shortcut_menu_error_toast_text), Toast.LENGTH_SHORT).show();
                } else {
                    selected.add(index);
                }
            }
        }
    };

    static class HoldClass {
        private TextView tv_title;
        private CheckBox cb;
    }
}
