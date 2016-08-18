package com.xgx.syzj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.base.BaseActivity;
import com.xgx.syzj.bean.Member;

/**
 * 收银记账结果页
 *
 * @author zajo
 * @created 2015年09月23日 10:33
 */
public class RevenueResultActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_money;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revenue_result);
        setTitleText("");
        setSubmit(getString(R.string.revenue_result_button_continue));
        tv_money = (TextView) findViewById(R.id.tv_money);
        findViewById(R.id.btn_continue).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String money = bundle.getString("money");
        if (!TextUtils.isEmpty(money)){
            tv_money.setText("¥ "+money);
        }
        member = bundle.getParcelable("member");
    }

    @Override
    protected void submit() {
        super.submit();
        gotoActivity(RevenueFastActivity.class);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                gotoActivity(RevenueActivity.class);
//                AppManager.getAppManager().returnToActivity(RevenueActivity.class);
//                ((RevenueActivity)(AppManager.getAppManager().currentActivity())).clean();
                break;
            case R.id.btn_send:
                Intent intent = new Intent(this, SaleHistoryActivity.class);
//                gotoActivity(SaleHistoryActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                defaultFinish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().returnToActivity(MainActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void goBack(View view) {
        super.goBack(view);
        AppManager.getAppManager().returnToActivity(MainActivity.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}