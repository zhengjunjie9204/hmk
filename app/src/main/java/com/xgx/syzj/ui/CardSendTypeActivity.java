package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.View;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 派发卡券
 *
 * @author zajo
 * @created 2015年08月27日 17:33
 */
public class CardSendTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_send_type);
        setTitleText(getString(R.string.card_send_title));
    }

    public void onCardClick(View view){
        String tag = (String)view.getTag();
        if ("member".equals(tag)){

        } else if ("voucher".equals(tag)){

        }else if("coupon".equals(tag)){

        }
    }

}
