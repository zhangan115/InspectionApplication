package com.inspection.application.mode.source.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApplicationApi;
import com.inspection.application.mode.api.UserApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.option.OptionBean;
import com.inspection.application.mode.bean.user.User;
import com.inspection.application.mode.callback.IListCallBack;
import com.library.utils.Base64Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

/**
 * user repository
 * Created by pingan on 2017/12/2.
 */
@Singleton
public class UserRepository implements UserDataSource {

    private Context mContext;
    private SharedPreferences userSp;//用户信息sp(清除缓存不清除该sp 信息)
    private long WELCOME_TIME = 1500;//欢迎页面展示时长

    @Inject
    public UserRepository(@NonNull Context context) {
        userSp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
        mContext = context;
    }

    @NonNull
    @Override
    public Subscription login(@NonNull final String name, @NonNull final String pass, @NonNull final LoadUserCallBack callBack) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", name);
            jsonObject.put("userPwd", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Bean<User>> observable = Api.createRetrofit().create(UserApi.class)
                .userLogin(jsonObject.toString());
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callBack.onFinish();
                        callBack.onLoginFail(throwable.getMessage());
                        throwable.printStackTrace();
                    }
                })
                .doOnNext(new Action1<Bean<User>>() {

                    @Override
                    public void call(Bean<User> userBean) {
                        callBack.onFinish();
                        if (userBean.getErrorCode() == 0) {
                            App.getInstance().setUserInfo(jsonObject.toString());
                            userSp.edit().putBoolean(ConstantStr.USE_APP, true).apply();
                            App.getInstance().setCurrentUser(userBean.getData());
                            try {
                                String encryptionStr = Base64Util.encode(jsonObject.toString().getBytes("UTF-8"));
                                userSp.edit().putString(ConstantStr.USER_INFO, encryptionStr).apply();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            callBack.onLoginFail(userBean.getMessage());
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Bean<User>, Observable<Bean<List<OptionBean>>>>() {
                    @Override
                    public Observable<Bean<List<OptionBean>>> call(Bean<User> userBean) {
                        if (userBean.getErrorCode() == 0) {
                            return Api.createRetrofit().create(ApplicationApi.class).getOptionInfo();
                        }
                        return Observable.just(null);
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bean<List<OptionBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.onFinish();
                        callBack.onLoginFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Bean<List<OptionBean>> listBean) {
                        callBack.onFinish();
                        if (listBean == null) {
                            callBack.onLoginFail(null);
                            return;
                        }
                        if (listBean.getErrorCode() == 0) {
                            App.getInstance().setOptionInfo(listBean.getData());
                            callBack.onLoginSuccess(App.getInstance().getCurrentUser());
                            Map<String, Map<String, String>> mOption = new HashMap<String, Map<String, String>>();
                            for (int i = 0; i < listBean.getData().size(); i++) {
                                Map<String, String> map = new HashMap<String, String>();
                                String optionId = listBean.getData().get(i).getOptionId() + "";
                                for (int j = 0; j < listBean.getData().get(i).getItemList().size(); j++) {
                                    String itemCode = listBean.getData().get(i).getItemList().get(j).getItemCode();
                                    String itemName = listBean.getData().get(i).getItemList().get(j).getItemName();
                                    map.put(itemCode, itemName);
                                }
                                mOption.put(optionId, map);
                            }
                            App.getInstance().setMapOption(mOption);
                        } else {
                            callBack.onLoginFail(listBean.getMessage());
                        }
                    }
                });
    }

    @NonNull
    @Override
    public Subscription autoLogin(@NonNull final AutoLoginCallBack callBack) {
        boolean showWelcome = userSp.getBoolean(ConstantStr.USE_APP, false);
        if (!showWelcome) {
            callBack.showWelcome();
            return Observable.just(false).subscribe();
        }
        String userInfo = userSp.getString(ConstantStr.USER_INFO, null);
        if (TextUtils.isEmpty(userInfo)) {
            return Observable.just(null).delaySubscription(WELCOME_TIME, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            callBack.onNeedLogin();
                        }
                    });
        } else {
            String decryptStr = "";
            try {
                decryptStr = new String(Base64Util.decode(userInfo), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Observable<Bean<User>> observable = Api.createRetrofit().create(UserApi.class)
                    .userLogin(decryptStr);
            final long startTime = System.currentTimeMillis();
            return observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            callBack.showMessage(throwable.getMessage());
                        }
                    })
                    .doOnNext(new Action1<Bean<User>>() {
                        @Override
                        public void call(Bean<User> userBean) {
                            if (userBean.getErrorCode() == 0) {
                                App.getInstance().setCurrentUser(userBean.getData());
                            } else {
                                App.getInstance().showToast(userBean.getMessage());
                                callBack.onNeedLogin();
                            }
                        }
                    })
                    .observeOn(Schedulers.io())
                    .flatMap(new Func1<Bean<User>, Observable<Bean<List<OptionBean>>>>() {
                        @Override
                        public Observable<Bean<List<OptionBean>>> call(Bean<User> userBean) {
                            if (userBean.getErrorCode() == 0) {
                                return Api.createRetrofit().create(ApplicationApi.class).getOptionInfo();
                            }
                            return Observable.just(null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Bean<List<OptionBean>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            callBack.onNeedLogin();
                        }

                        @Override
                        public void onNext(Bean<List<OptionBean>> listBean) {
                            if (listBean != null && listBean.getErrorCode() == 0) {
                                App.getInstance().setOptionInfo(listBean.getData());
                                Map<String, Map<String, String>> mOption = new HashMap<>();
                                for (int i = 0; i < listBean.getData().size(); i++) {
                                    Map<String, String> map = new HashMap<>();
                                    String optionId = listBean.getData().get(i).getOptionId() + "";
                                    for (int j = 0; j < listBean.getData().get(i).getItemList().size(); j++) {
                                        String itemCode = listBean.getData().get(i).getItemList().get(j).getItemCode();
                                        String itemName = listBean.getData().get(i).getItemList().get(j).getItemName();
                                        map.put(itemCode, itemName);
                                    }
                                    mOption.put(optionId, map);
                                }
                                App.getInstance().setMapOption(mOption);
                                autoFinish(startTime, callBack);
                            } else {
                                callBack.onNeedLogin();
                            }
                        }
                    });
        }
    }

    private void autoFinish(long startTime, @NonNull final AutoLoginCallBack callBack) {
        long finishTime = System.currentTimeMillis();
        if (finishTime - startTime < WELCOME_TIME) {
            long waiteTime = WELCOME_TIME - (finishTime - startTime);
            Observable.just(null).delaySubscription(waiteTime, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            callBack.onAutoFinish();
                        }
                    });
        } else {
            callBack.onAutoFinish();
        }
    }
}
