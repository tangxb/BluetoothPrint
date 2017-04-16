package com.yq.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smtlibrary.utils.LogUtils;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sddata.db";
    private static final int DATABASE_VERSION = 3;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        //组
        db.execSQL("CREATE TABLE IF NOT EXISTS gpinfo" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "GROUPID VARCHAR(16), " +
                "GROUPNUM INTEGER, " +
                "GROUPNAME VARCHAR(32))");


        db.execSQL("CREATE TABLE IF NOT EXISTS sbinfo" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dzbq VARCHAR(16), " +
                "hmph VARCHAR(8), " +
                "cmds0 VARCHAR(32), " +
                "hm VARCHAR(32)," +
                "dz VARCHAR(32)," +
                "dh VARCHAR(32)," +
                "sysl0 VARCHAR(32)," +
                "sysl1 VARCHAR(32)," +
                "rq VARCHAR(32)," +
                "cbye VARCHAR(16)," +
                "qsf VARCHAR(16)," +
                "ysxz VARCHAR(16)," +
                "scjy VARCHAR(16)," +
                "dds VARCHAR(6)," +
                "btbh VARCHAR(32)," +
                "dk VARCHAR(6)," +
                "sfy VARCHAR(16)," +
                "fbh VARCHAR(8)," +
                "Jd VARCHAR(8)," +
                "Wd VARCHAR(8)," +
                "yjMoney INTEGER," +//预交金额
                "isChaoBiao INTEGER default 0," +//是否抄表
                "isUpload INTEGER default 0," +
                "cmds1 VARCHAR(32)," +
                "yfs INTEGER)");


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion == 2) {
            String sql = " ALTER TABLE sbinfo ADD COLUMN fbh VARCHAR(8)";
            String sql1 = " ALTER TABLE sbinfo ADD COLUMN Jd VARCHAR(8)";
            String sql2 = " ALTER TABLE sbinfo ADD COLUMN Wd VARCHAR(8)";
            db.execSQL(sql);
            db.execSQL(sql1);
            db.execSQL(sql2);
            LogUtils.sysout("==========升级数据库", "");
        }else  if (newVersion == 3) {
            String sql = " ALTER TABLE sbinfo ADD COLUMN dh VARCHAR(32)";
            db.execSQL(sql);
            LogUtils.sysout("==========升级数据库", "");
        }
    }
}