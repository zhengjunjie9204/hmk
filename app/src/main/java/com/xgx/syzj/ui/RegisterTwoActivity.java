package com.xgx.syzj.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xgx.syzj.R;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.base.BaseActivity;

import org.w3c.dom.Text;

/**
 * 注册第二步
 *
 * @author zajo
 * @created 2015年08月07日 19:32
 */
public class RegisterTwoActivity extends BaseActivity implements View.OnClickListener{

    private Button btn_send,btn_next;
    private TextView tv_phone;

    private Handler mHandler = new Handler();
    private int count = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_two);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setText(String.format(getString(R.string.reg_two_time), count));
        btn_send.setEnabled(false);
        btn_next = (Button) findViewById(R.id.btn_next);
        findViewById(R.id.tv_one).setBackgroundColor(getResources().getColor(R.color.light_color));
        findViewById(R.id.iv_two).setBackgroundResource(R.mipmap.registered_two_two);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        Bundle bundle = getIntent().getExtras();
        tv_phone.setText(bundle.getString("phone"));

        btn_send.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        mHandler.removeCallbacks(timer);
        mHandler.postDelayed(timer, 1000);
    }

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            count--;
            if (count < 0){
                mHandler.removeCallbacks(this);
                count = 30;
                btn_send.setEnabled(true);
                btn_send.setText(getString(R.string.reg_two_resend));
            } else {
                mHandler.postDelayed(timer, 1000);
                btn_send.setText(String.format(getString(R.string.reg_two_time), count));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                btn_send.setText(String.format(getString(R.string.reg_two_time), count));
                mHandler.postDelayed(timer, 1000);
                btn_send.setEnabled(false);
                break;
            case R.id.btn_next:
                Bundle bundle = new Bundle();
                bundle.putString("phone", tv_phone.getText().toString());
                gotoActivity(RegisterThreeActivity.class, bundle);
                finish();
                break;
        }
    }
}
