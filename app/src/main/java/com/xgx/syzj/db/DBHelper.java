package com.xgx.syzj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xgx.syzj.app.Constants;

/**
 * @author zajo
 * @created 2015年11月11日 11:24
 */
public class DBHelper extends SQLiteOpenHelper {

    protected Context mContext;
    private SQLiteDatabase mDB = null;

    public DBHelper(Context context){
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        mContext = context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.mDB = sqLiteDatabase;
        createTables();
        Log.e("tag", "DBHelper onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.mDB = sqLiteDatabase;
        dropTables();
        createTables();
        Log.e("tag", "DBHelper onUpgrade");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDB = db;
    }

    private void createTables() {
        this.mDB.execSQL(UserDBHelper.getCreateTableSql());
    }

    private void dropTables() {
        execSql(getDropTableSql(UserDBHelper.TABLE_NAME));
    }

    public String getDropTableSql(String tableName) {
        String sql = "DROP TABLE IF EXISTS '" + tableName + "'";
        return sql;
    }

    public boolean execSql(String sql){
        try{
            getWritableDatabase().execSQL(sql);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(mDB != null){
            db = mDB;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }
}
