package com.inspection.application.mode.source.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.NewsApi;
import com.inspection.application.mode.bean.news.MessageContent;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.bean.news.db.NewsBeanDao;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.db.DbManager;
import com.library.utils.DataUtil;

import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 消息 repository
 * Created by pingan on 2017/12/9.
 */
@Singleton
public class NewsRepository implements NewsDataSource {

    private SharedPreferences dataSp;
    private CompositeSubscription mSubscription;

    @Inject
    public NewsRepository(Context context) {
        dataSp = context.getSharedPreferences(ConstantStr.SECURE_INFO, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Subscription getNewsData(String info) {
        return new ApiCallBackList<MessageContent>(Api.createRetrofit().create(NewsApi.class).getNewsContent(info)) {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onData(List<MessageContent> data) {

            }

            @Override
            public void onFail(@NonNull String message) {

            }

            @Override
            public void onFinish() {
            }

            @Override
            public void noData() {

            }
        }.execute().observeOn(Schedulers.io()).flatMap(new Func1<List<MessageContent>, Observable<List<NewsBean>>>() {
            @Override
            public Observable<List<NewsBean>> call(List<MessageContent> messageContents) {
                if (messageContents == null || messageContents.size() == 0) {
                    return Observable.just(null);
                }
                List<NewsBean> newsBeans = new ArrayList<>();
                for (int i = 0; i < messageContents.size(); i++) {
                    NewsBean bean = NewsUtils.getNewsBean(messageContents.get(i));
                    if (NewsUtils.getNewsType(bean) == 1) {
                        dataSp.edit().putBoolean(ConstantStr.NEWS_TYPE_ALARM, true).apply();
                    } else if (NewsUtils.getNewsType(bean) == 2) {
                        dataSp.edit().putBoolean(ConstantStr.NEWS_TYPE_WORK, true).apply();
                    }
                    newsBeans.add(bean);
                }
                DbManager.getDbManager().getDaoSession().getNewsBeanDao().insertOrReplaceInTx(newsBeans);
                return Observable.just(newsBeans);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<NewsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NewsBean> newsBeans) {
                if (App.getInstance() != null && newsBeans != null && newsBeans.size() > 0) {
                    App.getInstance().getNewsMessage();
                }
            }
        });
    }

    @NonNull
    @Override
    public Subscription startAutoGetMessage() {
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }
        Subscription subscription = Observable.interval(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        List<NewsBean> newsBeans = DbManager.getDbManager().getDaoSession().getNewsBeanDao()
                                .queryBuilder().where(NewsBeanDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())).limit(1).list();
                        Long messageId = null;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            if (newsBeans != null && newsBeans.size() > 0) {
                                messageId = newsBeans.get(0).getMessageId();

                            }
                            if (messageId == null) {
                                String time = DataUtil.timeFormat(System.currentTimeMillis(), "yyyy-MM-dd");
                                jsonObject.put("time", "2017-12-20");
                            } else {
                                jsonObject.put("lastId", messageId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mSubscription.add(getNewsData(jsonObject.toString()));
                    }
                });
        mSubscription.add(subscription);
        return subscription;
    }

    @NonNull
    @Override
    public Subscription getNewsData(int type, long messageId, final IListCallBack<NewsBean> callBack) {
        WhereCondition whereCondition;
        WhereCondition whereCondition1;
        if (messageId != -1) {
            whereCondition1 = NewsBeanDao.Properties.MessageId.lt(messageId);
        } else {
            whereCondition1 = NewsBeanDao.Properties.MessageId.ge(messageId);
        }
        if (type == 0) {
            whereCondition = NewsBeanDao.Properties.IsMe.eq(true);
        } else if (type == 1) {
            whereCondition = NewsBeanDao.Properties.IsAlarm.eq(true);
        } else {
            whereCondition = NewsBeanDao.Properties.IsWork.eq(true);
        }
        return DbManager.getDbManager().getDaoSession().getNewsBeanDao().queryBuilder()
                .where(NewsBeanDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId()), whereCondition, whereCondition1)
                .limit(50)
                .rx()
                .list()
                .subscribe(new Subscriber<List<NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        if (newsBeans != null) {
                            callBack.onData(newsBeans);
                        }
                    }
                });
    }

    @Override
    public void cleanSub() {
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }
}
