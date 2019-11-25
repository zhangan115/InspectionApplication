package com.inspection.application.mode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import com.inspection.application.mode.bean.equipment.db.EquipmentDataDbDao;
import com.inspection.application.mode.bean.equipment.db.EquipmentDbDao;
import com.inspection.application.mode.bean.equipment.db.RoomDbDao;
import com.inspection.application.mode.bean.image.DaoMaster;
import com.inspection.application.mode.bean.image.ImageDao;
import com.inspection.application.mode.bean.task.data.InspectionDataDbDao;


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
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, ImageDao.class, EquipmentDbDao.class, EquipmentDataDbDao.class, RoomDbDao.class, InspectionDataDbDao.class);
    }

}
