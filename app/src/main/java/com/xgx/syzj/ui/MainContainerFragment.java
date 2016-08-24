package com.xgx.syzj.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xgx.syzj.R;
import com.xgx.syzj.utils.CacheUtil;
import com.xgx.syzj.widget.CustomAlertDialog;

/**
 * 主页面Fragment
 *
 * @author zajo
 * @created 2015年08月11日 11:26
 */
public class MainContainerFragment extends Fragment implements View.OnClickListener, IMainActivityClick {

    private ImageView iv_menu;
    private TextView tv_name,tv_location;
    private Button btn_qiandao, btn_add_goods,
            btn_add_member, btn_add_revenue, btn_add_pay, btn_man_goods,
            btn_man_member, btn_check_sell, btn_analysis;

    private IMainMenuListItemClick iMainMenuListItemClick;

    private int[] resIDs = new int[]{R.mipmap.man_addsales_bit, R.mipmap.man_goodsmanger_big,
            R.mipmap.man_add_user_bit, R.mipmap.man_usermanger_big, R.mipmap.man_salesfor_big,
            R.mipmap.man_scan_bit, R.mipmap.man_payfor_big, R.mipmap.man_analigs_big};
    private String[] resStr = null;

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof IMainMenuListItemClick)) {
            throw new ClassCastException();
        }
        iMainMenuListItemClick = (IMainMenuListItemClick) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resStr = new String[]{getString(R.string.main_add_goods), getString(R.string.main_manage_goods), getString(R.string.main_add_menber),
                getString(R.string.main_manage_menber), getString(R.string.main_add_revenue), getString(R.string.main_check_sell),
                getString(R.string.main_manage_pay), getString(R.string.main_analysis),};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_container, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tv_name=(TextView) view.findViewById(R.id.tv_name);
        String storeName = CacheUtil.getmInstance().getUser().getStoreName();
        tv_name.setText(storeName);
        onDataChange();

        iv_menu.setOnClickListener(this);

//        v1 = view.findViewById(R.id.sv1);
//        v2 = view.findViewById(R.id.sv2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                if (iMainMenuListItemClick != null)
                    iMainMenuListItemClick.onItemClick(100);
                break;

        }
    }

    @Override
    public void onDataChange() {
        //设置两个快捷菜单
//        String[] temp = SYZJApplication.getInstance().getSpUtil().get(Constants.SharedPreferencesClass.SP_MAIN_SHORTCUT).split("#");
//        if (temp.length != 2)
//            return;
//        Resources res = getResources();
//        Drawable drawable = res.getDrawable(resIDs[Integer.parseInt(temp[0])]);
//        btn_man_a.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
//        btn_man_a.setText(resStr[Integer.parseInt(temp[0])]);
//        btn_man_a.setTag(resIDs[Integer.parseInt(temp[0])]);
//        drawable = res.getDrawable(resIDs[Integer.parseInt(temp[1])]);
//        btn_man_b.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
//        btn_man_b.setText(resStr[Integer.parseInt(temp[1])]);
//        btn_man_b.setTag(resIDs[Integer.parseInt(temp[1])]);
    }

    @Override
    public void onLocation(String msg) {
//        tv_location.setVisibility(View.VISIBLE);
//        tv_location.setText(msg);
    }

//    View v1,v2;

//    @Override
//    public void onChangeView() {
//        v1.setVisibility(v2.isShown() ? View.VISIBLE : View.GONE);
//        v2.setVisibility(v2.isShown() ? View.GONE : View.VISIBLE);
//    }
}
