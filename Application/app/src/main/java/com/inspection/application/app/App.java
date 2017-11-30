package com.inspection.application.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.bean.User;
import com.library.base.AbsBaseApp;
import com.library.utils.Base64Util;
import com.library.utils.SPHelper;

import java.io.UnsupportedEncodingException;

/**
 * Created by pingan on 2017/11/30.
 */

public class App extends AbsBaseApp {

    private static App _instance;
    private static SQLiteDatabase db;
//    private static DaoMaster mDaoMaster;
//    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static App getInstance() {
        return _instance;
    }

    public void editHost(String host) {
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.APP_HOST, host);
    }

    @Override
    public String AppHost() {
        return SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.APP_HOST, Api.HOST);
    }

    @Override
    public void showToast(@NonNull String message) {

    }

    @NonNull
    @Override
    public String httpCacheFile() {
        return null;
    }

    @NonNull
    @Override
    public String imageCacheFile() {
        return null;
    }

    @Override
    public Intent needLoginIntent() {
        return null;
    }

    /**
     * @return 获取用户名称
     */
    @Nullable
    public String getUserName() {
        String name = null;
        try {
            name = new String(Base64Util.decode(SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.USER_NAME)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * @return 获取用户密码
     */
    @Nullable
    public String getUserPass() {
        String pass = null;
        try {
            pass = new String(Base64Util.decode(SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.USER_PASS)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public void setCurrentUser(User data) {

    }
}
