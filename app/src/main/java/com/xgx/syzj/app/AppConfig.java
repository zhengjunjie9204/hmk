package com.xgx.syzj.app;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.volley.misc.Utils;

import java.io.File;

/**
 * 初始化app
 *
 * @author zajo
 * @created 2015年08月06日 15:21
 */
public class AppConfig {

    /**
     * 屏幕的宽度
     */
    public static int SCREEN_WIDTH = 0;

    /**
     * 屏幕的高度
     */
    public static int SCREEN_HEIGHT = 0;

    /**
     * 屏幕的密度
     */
    public static float SCREEN_DENSITY = 0.0f;

    /**
     * 图片文件夹路径
     */
    public static String IMAGE_PATH = "";

    /**
     * 图片文件夹名
     */
    public static String IMAGE_DIR_NAME = "images";

    public static void init(Activity activity) {
        if (SCREEN_DENSITY == 0 || SCREEN_WIDTH == 0 || SCREEN_HEIGHT == 0){
            //屏幕参数初始化
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            AppConfig.SCREEN_DENSITY = dm.density;
            AppConfig.SCREEN_HEIGHT = dm.heightPixels;
            AppConfig.SCREEN_WIDTH = dm.widthPixels;
            initImageDirPath(activity);
        }
    }

    public static void initImageDirPath(Context context){
        if (TextUtils.isEmpty(IMAGE_PATH)){
            //图片缓存文件夹
            File file = Utils.getDiskCacheDir(context, IMAGE_DIR_NAME);
            IMAGE_PATH = file.getAbsolutePath();
        }
    }
}
