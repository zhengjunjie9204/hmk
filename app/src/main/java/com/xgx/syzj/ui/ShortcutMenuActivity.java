package com.xgx.syzj.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xgx.syzj.R;
import com.xgx.syzj.adapter.ShortcutMenuListAdapter;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 快捷菜单
 *
 * @author zajo
 * @created 2015年08月14日 14:43
 */
public class ShortcutMenuActivity extends BaseActivity {

    private ListView listView;
    private ShortcutMenuListAdapter mAdapter;
    private String menuTitles[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shortcut_menu);
        setTitleText(getString(R.string.shortcut_menu_setting_title));
        listView = (ListView) findViewById(R.id.lv_menu);

        menuTitles = new String[]{getString(R.string.main_add_goods), getString(R.string.main_manage_goods), getString(R.string.main_add_menber),
                getString(R.string.main_manage_menber), getString(R.string.main_add_revenue), getString(R.string.main_check_sell),
                getString(R.string.main_manage_pay), getString(R.string.main_analysis),};
        String[] temp = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_MAIN_SHORTCUT).split("#");
        mAdapter = new ShortcutMenuListAdapter(this, menuTitles, temp);
        listView.setAdapter(mAdapter);
    }

    public void onSure(View view) {
        ArrayList<String> list = mAdapter.getSelected();
        Collections.sort(list);
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i);
            if (i != list.size() - 1) {
                temp += "#";
            }
        }
        SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_MAIN_SHORTCUT, temp);
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void onCancel(View view) {
        finish();
    }
}
