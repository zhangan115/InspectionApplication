package com.inspection.application.mode.source.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.NewsApi;
import com.inspection.application.mode.bean.news.ContentBean;
import com.inspection.application.mode.bean.news.FuckNews;
import com.inspection.application.mode.bean.news.MessageContent;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.bean.news.db.NewsBeanDao;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.db.DbManager;
import com.library.utils.DataUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        dataSp = context.getSharedPreferences(ConstantStr.USER_DATA, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Subscription getNewsData(String info) {
        return new ApiCallBackList<FuckNews>(Api.createRetrofit().create(NewsApi.class).getNewsContent(info)) {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onData(List<FuckNews> data) {

            }

            @Override
            public void onFail(@NonNull String message) {
                Logger.d(message);
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void noData() {

            }
        }.execute().observeOn(Schedulers.io()).flatMap(new Func1<List<FuckNews>, Observable<List<MessageContent>>>() {
            @Override
            public Observable<List<MessageContent>> call(List<FuckNews> fuckNews) {
                List<MessageContent> messageContents = new ArrayList<>();
                if (fuckNews != null && fuckNews.size() > 0) {
                    for (int i = 0; i < fuckNews.size(); i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(fuckNews.get(i).getContent());
                            MessageContent contentBean = new Gson().fromJson(jsonObject.toString(), MessageContent.class);
                            if (contentBean != null) {
                                contentBean.setLogId(fuckNews.get(i).getLogId());
                                contentBean.setMessageTime(fuckNews.get(i).getCreateTime());
                                messageContents.add(contentBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return Observable.just(messageContents);
            }
        }).flatMap(new Func1<List<MessageContent>, Observable<List<NewsBean>>>() {
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
                if (newsBeans.size() > 0) {
                    DbManager.getDbManager().getDaoSession().getNewsBeanDao().insertOrReplaceInTx(newsBeans);
                }
                return Observable.just(newsBeans);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<NewsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<NewsBean> newsBeans) {
                if (App.getInstance() != null && newsBeans != null && newsBeans.size() > 0) {
                    App.getInstance().getNewsMessage();
                }
                if (App.getInstance() != null) {
                    App.getInstance().updateMessageTime();
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
                                .queryBuilder().where(NewsBeanDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())).limit(1)
                                .orderDesc(NewsBeanDao.Properties._id)
                                .list();
                        Long messageId = null;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            if (newsBeans != null && newsBeans.size() > 0) {
                                messageId = newsBeans.get(0).get_id();
                            }
                            if (messageId == null) {
                                Calendar calendar = new GregorianCalendar();
                                calendar.setTime(new Date());
                                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
                                Logger.d(DataUtil.timeFormat(calendar.getTimeInMillis(), "yyyy-MM-dd"));
                                jsonObject.put("time", DataUtil.timeFormat(calendar.getTimeInMillis(), "yyyy-MM-dd"));
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
                .orderDesc(NewsBeanDao.Properties._id)
                .rx()
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFinish();
                        callBack.noData();
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeans) {
                        callBack.onFinish();
                        if (newsBeans != null && newsBeans.size() > 0) {
                            callBack.onData(newsBeans);
                        } else {
                            callBack.noData();
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

    @Override
    public long getUnReadCount() {
        return DbManager.getDbManager().getDaoSession().getNewsBeanDao().queryBuilder().where(NewsBeanDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())
                , NewsBeanDao.Properties.IsMe.eq(true)
                , NewsBeanDao.Properties.HasRead.eq(false)).count();
    }
}
