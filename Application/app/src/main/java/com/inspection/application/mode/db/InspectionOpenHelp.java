package com.inspection.application.mode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.inspection.application.mode.bean.equipment.db.DaoMaster;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDbDao;
import com.inspection.application.mode.bean.equipment.db.EquipmentDbDao;
import com.inspection.application.mode.bean.equipment.db.RoomDbDao;
import com.inspection.application.mode.bean.image.ImageDao;


import org.greenrobot.greendao.database.Database;

/**
 * 数据库升级类
 */


public class InspectionOpenHelp extends DaoMaster.OpenHelper {

    public InspectionOpenHelp(Context context, String name) {
        super(context, name);
    }

    public InspectionOpenHelp(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, ImageDao.class, EquipmentDbDao.class, EquipmentDataDbDao.class, RoomDbDao.class);
    }
}
