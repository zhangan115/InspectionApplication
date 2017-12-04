package com.inspection.application.mode.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.inspection.application.app.App;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.user.User;
import com.inspection.application.mode.callback.IObjectCallBack;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ApiCallBackObject<T> {

    private int mTryCount;
    private Observable<Bean<T>> mObservable;

    public ApiCallBackObject() {

    }

    public ApiCallBackObject(@NonNull Observable<Bean<T>> observable) {
        this.mObservable = observable;
    }

    public Observable<?> execute(final @Nullable IObjectCallBack<T> callBack) {
        return execute(false, callBack);
    }

    public Observable<?> execute(final boolean isEmpty, final @Nullable IObjectCallBack<T> callBack) {
        mTryCount = 1;
        return mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable t) {
                        if (callBack != null) {
                            callBack.onFinish();
                            callBack.onError(t.getMessage());
                            callBack.noData();
                        }
                    }
                })
                .doOnNext(new Action1<Bean<T>>() {
                    @Override
                    public void call(Bean<T> t) {
                        if (t.getErrorCode() == ApiErrorCode.SUCCEED) {
                            if (callBack != null) {
                                callBack.onFinish();
                                if (t.getData() == null && !isEmpty) {
                                    callBack.noData();
                                } else {
                                    callBack.onSuccess(t.getData());
                                }
                            }
                        } else if (t.getErrorCode() == ApiErrorCode.NOT_LOGGED && mTryCount == 0) {
                            App.getInstance().needLogin();
                        } else if (t.getErrorCode() != ApiErrorCode.NOT_LOGGED) {
                            if (callBack != null) {
                                callBack.onFinish();
                                callBack.onError(t.getMessage());
                                callBack.noData();
                            }
                        }
                    }
                })
                .flatMap(new Func1<Bean<T>, Observable<NotLoggedThrowable>>() {
                    @Override
                    public Observable<NotLoggedThrowable> call(Bean<T> customerBean) {
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
                                    String name = App.getInstance().getUserName();
                                    String pass = App.getInstance().getUserPass();
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("userName", name);
                                        jsonObject.put("userPwd", pass);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    return Api.createRetrofit().create(UserApi.class)
                                            .userLogin(jsonObject.toString())
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
                });
    }
}
