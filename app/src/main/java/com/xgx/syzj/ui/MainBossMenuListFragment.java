package com.xgx.syzj.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xgx.syzj.R;
import com.xgx.syzj.adapter.MainMenuListAdapter;
import com.xgx.syzj.bean.User;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CircleImageView;

/**
 * 主页面侧滑栏Fragment
 *
 * @author zajo
 * @created 2015年08月11日 11:25
 */
public class MainBossMenuListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ListView mMenuList;
    private RelativeLayout rl_header;
    private CircleImageView iv_logo;
    private Context mContext;
    private int[] MenuImagesN;
    private int[] MenuImagesP;
    private String[] MenuTitles;
    private MainMenuListAdapter mAdapter;
    private IBossMainMenuListItemClick IBossMainMenuListItemClick;
    private User user;
    private TextView tv_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        MenuImagesN = new int[]{R.mipmap.menu_message_cent_n, R.mipmap.menu_app_share_n,
                R.mipmap.menu_helping_n, R.mipmap.menu_outlogiin_n,R.mipmap.menu_ssetting_n};
        MenuImagesP = new int[]{R.mipmap.menu_message_cent_p, R.mipmap.menu_app_share_p,
                R.mipmap.menu_helping_p, R.mipmap.menu_outlogiin_p,R.mipmap.menu_ssetting_p};
        MenuTitles = new String[]{mContext.getString(R.string.menu_account_text),
                mContext.getString(R.string.menu_store_manage),
                mContext.getString(R.string.menu_insert_data),
                mContext.getString(R.string.menu_apk_update),
                mContext.getString(R.string.menu_apk_exit)};
    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof IBossMainMenuListItemClick)){
            throw new ClassCastException();
        }
        IBossMainMenuListItemClick = (IBossMainMenuListItemClick) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu_boss_list, container, false);
        user = CacheUtil.getmInstance().getUser();
        initListView(view);
        initData();

        return view;
    }

    private void initData() {
        String storeLogo = user.getStoreLogo();
        Picasso.with(getContext()).load(user.getStoreLogo()).into(iv_logo);
        switch (user.getRoles()) {
            case  1:
                tv_name.setText(user.getUserName()+"(老板)");
                break;
            case  2:
                tv_name.setText(user.getUserName()+"(店长)");
                break;
            case  3:
                tv_name.setText(user.getUserName()+"(员工)");
                break;

        }
    }

    // 初始化listview
    public void initListView(View view) {
        tv_name =(TextView)view.findViewById(R.id.tv_name);
        rl_header = (RelativeLayout) view.findViewById(R.id.rl_header);
        iv_logo = (CircleImageView) view.findViewById(R.id.iv_logo);
        mMenuList = (ListView) view.findViewById(R.id.lv_menu);
        mAdapter = new MainMenuListAdapter(mContext, MenuImagesN, MenuImagesP, MenuTitles);
        mMenuList.setAdapter(mAdapter);
        mMenuList.setOnItemClickListener(this);
        rl_header.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_header:
                IBossMainMenuListItemClick.onItemClick(101);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.setIndex(position);
        IBossMainMenuListItemClick.onItemClick(position);
    }

}
//定义Fragment点击传递事件到MainActivity
interface IBossMainMenuListItemClick{
    void onItemClick(int position);
}

