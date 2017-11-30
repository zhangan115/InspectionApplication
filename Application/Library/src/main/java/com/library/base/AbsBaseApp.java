package com.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * app 类基类
 * Created by zhangan on 2017-06-21.
 */

public abstract class AbsBaseApp extends Application {

    protected List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
    }

    /**
     * 打开一个页面
     *
     * @param activity activity
     */
    public void openActivity(@NonNull Activity activity) {
        activityList.add(0, activity);
    }

    /**
     * 关闭一个页面
     *
     * @param activity activity
     */
    public void closeActivity(@NonNull Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 退出App
     */
    public void exitApp() {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param activity 页面
     */
    public void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示键盘
     */
    public void showSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }

    /**
     * app host
     *
     * @return 地址
     */
    public abstract String AppHost();

    /**
     * 显示toast
     *
     * @param message 信息
     */
    public abstract void showToast(@NonNull String message);

    @NonNull
    public abstract String httpCacheFile();

    @NonNull
    public abstract String imageCacheFile();

    public void needLogin() {
        exitApp();
        if (activityList.size() > 0) {
            activityList.get(0).startActivity(needLoginIntent());
        }
    }

    @Nullable
    protected File getCacheFile(String name) {
        File file;
        if (getExternalCacheDir() == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().getAbsoluteFile() + File.separator + name);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        file = null;
                    }
                }
            } else {
                file = null;
            }
        } else {
            file = getExternalCacheDir();
        }
        return file;
    }

    public abstract Intent needLoginIntent();
}
