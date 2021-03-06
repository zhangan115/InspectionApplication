package com.inspection.application.mode.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.app.App;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.user.User;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 请求回调
 * Created by zhangan on 2017-05-16.
 */

public abstract class ApiCallBackList<T> {

    private int mTryCount;
    private Observable<Bean<List<T>>> mObservable;
    private List<T> data;

    public ApiCallBackList() {

    }

    public ApiCallBackList(@Nullable Observable<Bean<List<T>>> observable) {
        this.mObservable = observable;
    }

    public Observable<List<T>> execute() {
        mTryCount = 1;
        return mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable t) {
                        onFinish();
                        onFail(t.getMessage());
                        noData();
                    }
                })
                .doOnNext(new Action1<Bean<List<T>>>() {
                    @Override
                    public void call(Bean<List<T>> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            onFinish();
                            onSuccess();
                            if (t.getData() == null || t.getData().size() == 0) {
                                noData();
                            } else {
                                data = t.getData();
                                onData(data);
                            }
                        } else if (t.getErrorCode() == ApiErrorCode.NOT_LOGGED && mTryCount == 0) {
                            App.getInstance().needLogin();
                        } else if (t.getErrorCode() != ApiErrorCode.NOT_LOGGED) {
                            onFinish();
                            onFail(t.getMessage());
                            noData();
                        }
                    }
                })
                .flatMap(new Func1<Bean<List<T>>, Observable<NotLoggedThrowable>>() {
                    @Override
                    public Observable<NotLoggedThrowable> call(Bean<List<T>> customerBean) {
                        if (mTryCount > 0 && customerBean.getErrorCode() == ApiErrorCode.NOT_LOGGED) {
                            return Observable.error(new NotLoggedThrowable());
                        }
                        return Observable.just(null);
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (mTryCount > 0 && throwable != null && throwable instanceof NotLoggedThrowable) {
                                    --mTryCount;
                                    return Api.createRetrofit().create(UserApi.class)
                                            .userLogin(App.getInstance().getUserInfo())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .flatMap(new Func1<Bean<User>, Observable<NotLoggedThrowable>>() {
                                                @Override
                                                public Observable<NotLoggedThrowable> call(Bean<User> userBean) {
                                                    if (userBean.getErrorCode() == 0) {
                                                        App.getInstance().setCurrentUser(userBean.getData());
                                                    } else {
                                                        App.getInstance().needLogin();
                                                    }
                                                    return Observable.just(new NotLoggedThrowable());
                                                }
                                            }, new Func1<Throwable, Observable<? extends NotLoggedThrowable>>() {
                                                @Override
                                                public Observable<? extends NotLoggedThrowable> call(Throwable throwable) {
                                                    throwable.printStackTrace();
                                                    return Observable.just(new NotLoggedThrowable());
                                                }
                                            }, new Func0<Observable<? extends NotLoggedThrowable>>() {
                                                @Override
                                                public Observable<? extends NotLoggedThrowable> call() {
                                                    return Observable.empty();
                                                }
                                            });
                                } else {
                                    return Observable.empty();
                                }
                            }
                        });
                    }
                }).flatMap(new Func1<NotLoggedThrowable, Observable<List<T>>>() {
                    @Override
                    public Observable<List<T>> call(NotLoggedThrowable notLoggedThrowable) {
                        return Observable.just(data);
                    }
                });
    }

    public abstract void onSuccess();

    public abstract void onData(List<T> data);

    public abstract void onFail(@NonNull String message);

    public abstract void onFinish();

    public abstract void noData();
}
