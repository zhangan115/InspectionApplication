package com.inspection.application.mode.source.application;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IObjectCallBack;

import java.io.File;

import rx.Subscription;

/**
 * 系统mode data source</br>
 * Created by pingan on 2017/11/30.
 */

public interface ApplicationDataSource {

    interface NewVersionCallBack {

        void newVersion(NewVersion result);

        void noNewVersion();
    }

    /**
     * 新版本检测
     *
     * @param callBack 回调
     */
    @NonNull
    Subscription getNewVersion(@NonNull NewVersionCallBack callBack);

    interface CleanCacheCallBack {

        void cleanFinish();

        void cleanFail();
    }

    /**
     * 清理缓存
     *
     * @param cacheCallBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription cleanCache(@NonNull CleanCacheCallBack cacheCallBack);

    @NonNull
    Subscription exitApp();

    /**
     * 上传文件
     *
     * @param file     文件
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription uploadUserPhoto(@NonNull File file, @NonNull final IObjectCallBack<String> callBack);

    @NonNull
    Subscription postQuestion(String title, String content, @NonNull final IObjectCallBack<String> callBack);

}
