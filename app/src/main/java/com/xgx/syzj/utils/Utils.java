package com.xgx.syzj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.xgx.syzj.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 工具类
 *
 * @author zajo
 * @created 2015年08月13日 17:44
 */
public class Utils {
    /**
     * @param imgFilePath 图片路径
     * @return String
     * @deprecated 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     */
    public static String GetImageBase64Str(String imgFilePath)
    {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context)
    {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAuth(int code)
    {
        if (0 == code)
            return "老板";
        return "员工";
    }

    public static int getDIP(Activity activity, int num)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, dm);
    }

    /**
     * 显示输入法
     *
     * @param activity
     */
    public static void showSoftInput(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value)
    {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判斷鍵盤是否已經顯示在界面上
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputIsActive(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }

    public static int dp2px(Context context, int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int getSDKVersionNumber()
    {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    //获取支付方式资源和文字
    private static int[] resIcon = new int[]{R.mipmap.new_cash, R.mipmap.new_paycard, R.mipmap.new_ailipay};
    private static String[] resName = new String[]{"现金支付", "刷卡支付", "微信支付", "支付宝支付", "储值卡支付"};

    public static int getPayResIconId(int position)
    {
        return resIcon[position];
    }

    public static String getPayResName(int position)
    {
        return resName[position];
    }

    public static int getPayIndex(String name)
    {
        for (int i = 0; i < resName.length; i++) {
            if (resName[i].equals(name))
                return i;
        }
        return 0;
    }
}
