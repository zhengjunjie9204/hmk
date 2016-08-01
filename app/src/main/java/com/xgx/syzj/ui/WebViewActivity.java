package com.xgx.syzj.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xgx.syzj.R;
import com.xgx.syzj.base.BaseActivity;

/**
 * 帮助中心、手机橱窗、开店论坛
 *
 * @author zajo
 * @created 2015年08月13日 14:42
 */
public class WebViewActivity extends BaseActivity {

    public static final String VIEW_TYPE_KEY = "KEY";
    public static final String VIEW_TYPE_HELP = "HELP";
    public static final String VIEW_TYPE_SHOP = "SHOP";
    public static final String VIEW_TYPE_BLOG = "BLOG";

    private WebView wv;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        flag = getIntent().getStringExtra(VIEW_TYPE_KEY);
        if (VIEW_TYPE_HELP.equals(flag)) {
            setTitleText(getString(R.string.help_center_title));
            setSubmit(getString(R.string.help_center_submit));
        }

        wv = (WebView) findViewById(R.id.wv);
        wv.requestFocusFromTouch();
        //自适应页面
        WebSettings webSettings = wv.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        wv.loadUrl("http://www.i200.cn/bbs/forum.php");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void submit() {
        super.submit();
        gotoActivity(FeedBackActivity.class);
    }
}
