package com.inspection.application.mode.source.news;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 消息data source
 * Created by pingan on 2017/12/9.
 */

public interface NewsDataSource {

    /**
     * 获取消息数据
     *
     * @param info 参数
     */
    @NonNull
    Subscription getNewsData(String info);

    interface IAutoGetMessage {

        void onStart();

        void onFail();
    }

    @NonNull
    Subscription startAutoGetMessage();

    @NonNull
    Subscription getNewsData(int type, long messageId, IListCallBack<NewsBean> callBack);

    void cleanSub();

    long getUnReadCount();
}
