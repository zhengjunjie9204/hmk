package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;

import java.util.ArrayList;

/**
 * 收银销售
 *
 * @author zajo
 * @created 2015年09月21日 16:23
 */
public class RevenueActivity extends BaseActivity {

    private TextView tv_name, tv_money, tv_num, tv_remark, tv_count, tv_hint;
    private LinearLayout ll_count;
    private ArrayList<String> mList = new ArrayList<>();
    private Member member;
    private Button btnJS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);
        setTitleText(getString(R.string.main_add_revenue));
        setSubmit(getString(R.string.revenue_guadan_list));
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        ll_count = (LinearLayout) findViewById(R.id.ll_count);
        btnJS = (Button) findViewById(R.id.jiesuan);
    }

    public void onMemberGoods(View view) {
        String tag = (String) view.getTag();
        if ("member".equals(tag)) {
            gotoActivityForResult(RevenueMemberActivity.class, null, 1000);
        } else if ("goods".equals(tag)) {
            if (member != null){
                Bundle bundlee = new Bundle();
                bundlee.putParcelable("member", member);
                gotoActivity(RevenueGoodsActivity.class,bundlee);
            }else {
                gotoActivity(RevenueGoodsActivity.class);
            }
        }
    }

    public void onCalculate(View view) {
        String tag = (String) view.getTag();
        if ("C".equals(tag)) {
            //清除
            clean();
        } else if ("D".equals(tag)) {
            //删除
            delete();
        } else if ("A".equals(tag)) {
            //+
            addCount();
        } else if ("J".equals(tag)) {
            //结账
            addCount();
            if (mList.size() == 0)
                showShortToast("亲，销售金额不能为零哦!");
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("list", mList);
            if (member != null)
                bundle.putParcelable("member", member);
            gotoActivity(RevenueDetailActivity.class, bundle);
        } else if ("dot".equals(tag)) {
            //小数点
            addDot();
        } else {
            //数值
            addNum(tag);
        }
    }

    private void addNum(String num) {
        tv_hint.setVisibility(View.GONE);
        tv_money.setVisibility(View.VISIBLE);
        String sourceNum = tv_count.getText().toString();
        if ("¥ 0.00".equals(sourceNum))
            sourceNum = "";
        tv_count.setText(sourceNum + num);
        if (mList.size() <= 0) {
            tv_money.setText("¥ " + sourceNum + num);
        }
        transformBtnState(true);
    }

    private void addCount() {
        String sourceNum = tv_count.getText().toString();
        if ("¥ 0.00".equals(sourceNum)) return;
        tv_count.setText("¥ 0.00");
        mList.add(sourceNum);
        ll_count.setVisibility(View.VISIBLE);
        tv_num.setText(mList.size() + "");
        if (mList.size() == 1) return;
        String sourceMoney = tv_money.getText().toString();
        sourceMoney = sourceMoney.substring(1, sourceMoney.length());
        double s = Double.parseDouble(sourceMoney);
        double i = Double.parseDouble(sourceNum);
        tv_money.setText("¥ " + (s + i));
    }

    private void addDot() {
        String sourceStr = tv_count.getText().toString();
        if ("¥ 0.00".equals(sourceStr)) return;
        if (sourceStr.contains(".")) return;
        sourceStr += ".";
        tv_count.setText(sourceStr);
    }

    private void delete() {
        String sourceStr = tv_count.getText().toString();
        if ("¥ 0.00".equals(sourceStr)) return;
        sourceStr = sourceStr.substring(0, sourceStr.length() - 1);
        if (sourceStr.length() == 0) {
            tv_count.setText("¥ 0.00");
        } else {
            tv_count.setText(sourceStr);
        }
        if (mList.size() == 0) {
            transformBtnState(false);
            if (sourceStr.length() == 0) {
                tv_money.setText("¥ 0.00");
            } else {
                tv_money.setText("¥ "+sourceStr);
            }
        }
    }

    public void clean() {
        tv_count.setText("¥ 0.00");
        tv_money.setText("¥ 0.00");
        mList.clear();
        ll_count.setVisibility(View.INVISIBLE);
        transformBtnState(false);
    }

    private void transformBtnState(boolean focus){
        if (focus) {
            btnJS.setBackgroundResource(R.drawable.button_jiesuan_selector_bg);
            btnJS.setTextColor(getResources().getColor(R.color.white));
        } else {
            btnJS.setBackgroundColor(getResources().getColor(R.color.gray_bg));
            btnJS.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    protected void submit() {
        super.submit();
        gotoActivity(RevenueGuadanActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode){
            case 1000:
                Member m = data.getParcelableExtra("member");
                if (m != null){
                    member = m;
                    tv_name.setText(member.getName());
                }
                break;
        }
    }
}
