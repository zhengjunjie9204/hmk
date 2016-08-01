package com.xgx.syzj.db;

import android.content.Context;

/**
 * 用户数据表
 *
 * @author zajo
 * @created 2015年11月11日 11:28
 */
public class UserDBHelper extends DBHelper {

    public static final String TABLE_NAME = "user";

    public UserDBHelper(Context context) {
        super(context);
    }

    public static String getCreateTableSql() {
        String sql = "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'accountId' TEXT," +
                "'mobilePhone' TEXT," +
                "'password' TEXT," +
                "'userName' TEXT," +
                "'authority' INT," +
                "'storeId' INT," +
                "'accessToken' TEXT," +
                "'createTime' INTEGER," +
                "'userKey' TEXT," +
                "'grantType' TEXT," +
                "'userId' TEXT," +
                "'refreshToken' TEXT" +
                ")";
        return sql;
    }

//    public boolean insert(Shop shop, String psw) {
//        ContentValues cv = new ContentValues();
//        User user = shop.getUserInfo();
//        Token token = shop.getTokenInfo();
//        cv.put("path", star.getPath());
//        cv.put("name", star.getName());
//        cv.put("tip", CHexConver.byte2HexStr(data, data.length));
//        cv.put("img", data);
//        getWritableDatabase().insert(TABLE_NAME, null, cv);
//        return false;
//    }

}