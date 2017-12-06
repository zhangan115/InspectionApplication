package com.inspection.application.mode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.inspection.application.app.App;
import com.inspection.application.mode.bean.equipment.db.DaoMaster;
import com.inspection.application.mode.bean.equipment.db.DaoSession;

/**
 * 数据库管理器
 * Created by pingan on 2017/12/5.
 */

public class DbManager {

    private static DaoSession mDaoSession;

    private DbManager() {

    }

    public static void init(Context context) {
        InspectionOpenHelp mHelper = new InspectionOpenHelp(context, "inspection_db");
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private static DbManager dbManager;

    public static DbManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
