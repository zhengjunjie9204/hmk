package com.xgx.syzj.ui;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.AppConfig;
import com.xgx.syzj.app.Constants;
import com.xgx.syzj.crashhandler.CrashHandler;
import com.xgx.syzj.utils.SharedPreferencesUtil;

/**
 * @author zajo
 * @created 2015年08月10日 14:38
 */
public class SYZJApplication extends Application {

    private static SYZJApplication mInstance = null;

    private SharedPreferencesUtil spUtil;

    public static SYZJApplication getInstance() {
        if (mInstance == null) {
            mInstance = new SYZJApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        CrashHandler.active(this);
        AppConfig.initImageDirPath(this);
        Api.init(this);

        Logger.init()                 // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(Constants.LOGLEVEL)        // default LogLevel.FULL.Use LogLevel.NONE for the release versions
//                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
    }

    public SharedPreferencesUtil getSpUtil() {
        if (spUtil == null)
            spUtil = new SharedPreferencesUtil(this);
        return spUtil;
    }

}
