package com.inspection.application.mode.source.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    Subscription getNewsData(String info,@Nullable RequestNewsMessageCallBack callBack);

    interface RequestNewsMessageCallBack {

        void onFinish();

    }

    void requestMessage(@Nullable RequestNewsMessageCallBack callBack);

    void startAutoGetMessage();

    void getNewsData(int type, long messageId, IListCallBack<NewsBean> callBack);

    void cleanSub();

    long getUnReadCount();
}
