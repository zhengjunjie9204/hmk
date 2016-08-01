package com.xgx.syzj.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.xgx.syzj.R;
import com.xgx.syzj.app.Constants;

/**
 * 闪屏页
 *
 * @author zajo
 * @created 2015年08月06日 16:46
 */
public class SplashActivity extends Activity {

    private int first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        first = SYZJApplication.getInstance().getSpUtil().getInt(Constants.SharedPreferencesClass.SP_FIRST_START);
        // 渐变展示启动屏
        AlphaAnimation alpha = new AlphaAnimation(0.5f, 1.0f);
        alpha.setDuration(1500);
        view.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (first == 0){
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    SYZJApplication.getInstance().getSpUtil().addString(Constants.SharedPreferencesClass.SP_MAIN_SHORTCUT,"3#6");
                    SYZJApplication.getInstance().getSpUtil().addInt(Constants.SharedPreferencesClass.SP_FIRST_START, 1);
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

}
