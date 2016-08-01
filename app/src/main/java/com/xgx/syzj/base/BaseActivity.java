package com.xgx.syzj.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.xgx.syzj.R;
import com.xgx.syzj.app.AppConfig;
import com.xgx.syzj.app.AppManager;
import com.xgx.syzj.app.lifecycle.IComponentContainer;
import com.xgx.syzj.app.lifecycle.LifeCycleComponent;
import com.xgx.syzj.app.lifecycle.LifeCycleComponentManager;
import com.xgx.syzj.widget.CustomProgressDialog;

/**
 * 基础Activity
 *
 * @author zajo
 * @created 2015年08月06日 15:54
 */
public class BaseActivity extends FragmentActivity implements IComponentContainer {

    protected CustomProgressDialog dialog;

    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.layout_top_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        dialog = CustomProgressDialog.createDialog(this).setMessage("加载中...");
        AppManager.getAppManager().addActivity(this); //将activity推入管理栈
        AppConfig.init(this);
    }

    protected void showLoadingDialog(int res_id) {
        dialog.setMessage(getString(res_id));
        dialog.show();
    }

    protected void hideLoadingDialog() {
        dialog.dismiss();
    }

    protected void hideTopBar() {
        getActionBar().hide();
    }

    public void setTitleText(String title) {
        ((TextView) (getActionBar().getCustomView().findViewById(R.id.tv_title))).setText(title);
    }

    public void setSubmit(String text) {
        Button btn = (Button) (getActionBar().getCustomView().findViewById(R.id.btn_submit));
        btn.setVisibility(View.VISIBLE);
        btn.setText(text);
    }

    public void setAnimator(Techniques techniques, View view) {
        YoYo.with(techniques)
                .duration(1000)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mComponentContainer.onBecomesPartiallyInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    protected void onDestroy() { //销毁时activity出栈
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        mComponentContainer.onDestroy();
    }

    /**
     * 跳转Activity
     *
     * @param activity
     * @param bundle
     */
    protected void gotoActivity(Class<? extends Activity> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 跳转Activity
     *
     * @param activity
     */
    protected void gotoActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 跳转activity for result
     *
     * @param activity
     * @param bundle
     * @param requestCode
     */
    protected void gotoActivityForResult(Class<? extends Activity> activity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 获取当前activity实例
     *
     * @return
     */
    protected Activity getActivity() {
        return this;
    }

    public void defaultFinish() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void goBack(View view) {
        defaultFinish();
    }

    public void onSubmit(View view) {
        submit();
    }

    protected void submit() {

    }

    public void showShortToast(int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            defaultFinish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void addComponent(LifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }
}
