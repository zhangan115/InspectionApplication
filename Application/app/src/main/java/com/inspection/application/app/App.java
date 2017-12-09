package com.inspection.application.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspection.application.R;
import com.inspection.application.base.AbsBaseApp;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.bean.option.OptionBean;
import com.inspection.application.mode.bean.user.User;
import com.inspection.application.mode.db.DbManager;
import com.inspection.application.view.ApplicationModule;
import com.library.utils.Base64Util;
import com.library.utils.SPHelper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * application
 * Created by pingan on 2017/11/30.
 */

public class App extends AbsBaseApp {

    private static App _instance;
    private ApplicationModule applicationModule;
    private User mUser;//当前用户
    private ArrayList<OptionBean> mOptionBeen;
    private Map<String, Map<String, String>> mapOption;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        DbManager.init(this);
        Utils.init(this);
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
    public void showToast(@Nullable String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.setBgColor(getResources().getColor(R.color.colorPrimary));
            ToastUtils.setMsgColor(getResources().getColor(R.color.colorWhite));
            ToastUtils.showShort(message);
        }
    }

    public void setOptionInfo(@NonNull List<OptionBean> list) {
        mOptionBeen = new ArrayList<>();
        mOptionBeen.addAll(list);
        String jsonStr = new Gson().toJson(list);
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.OPTION, jsonStr);
    }

    /**
     * 获取当前用户
     *
     * @return 用户
     */
    public User getCurrentUser() {
        if (mUser == null) {
            String userInfo = SPHelper.readString(getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.USER_BEAN);
            if (!TextUtils.isEmpty(userInfo)) {
                mUser = new Gson().fromJson(userInfo, User.class);
            }
        }
        return mUser;
    }

    public void setMapOption(Map<String, Map<String, String>> mapOption) {
        this.mapOption = mapOption;
    }

    public Map<String, Map<String, String>> getMapOption() {
        if (mapOption != null) {
            return mapOption;
        }
        Map<String, Map<String, String>> mOption = new HashMap<>();
        for (int i = 0; i < mOptionBeen.size(); i++) {
            Map<String, String> map = new HashMap<>();
            String optionId = mOptionBeen.get(i).getOptionId() + "";
            for (int j = 0; j < mOptionBeen.get(i).getItemList().size(); j++) {
                String itemCode = mOptionBeen.get(i).getItemList().get(j).getItemCode();
                String itemName = mOptionBeen.get(i).getItemList().get(j).getItemName();
                map.put(itemCode, itemName);
            }
            mOption.put(optionId, map);
        }
        setMapOption(mOption);
        return mOption;
    }


    /**
     * @return 获取字典
     */
    public List<OptionBean> getOptionInfo() {
        if (mOptionBeen == null) {
            String optionStr = SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.OPTION);
            Type type = new TypeToken<List<OptionBean>>() {
            }.getType();
            mOptionBeen = new Gson().fromJson(optionStr, type);
        }
        return mOptionBeen;
    }


    @NonNull
    @Override
    public String httpCacheFile() {
        return null;
    }

    @NonNull
    @Override
    public String imageCacheFile() {
        return getCacheFile("inspection").getAbsolutePath();
    }

    @Override
    public Intent needLoginIntent() {
        return null;
    }

    public void setUserInfo(@NonNull String info) {
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO, Base64Util.encode(info.getBytes()));
    }

    @Nullable
    public String getUserInfo() {
        String json = SPHelper.readString(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO);
        String name = null;
        try {
            name = new String(Base64Util.decode(json), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void cleanUserInfo() {
        SPHelper.remove(this, ConstantStr.USER_INFO, ConstantStr.USER_INFO);
    }

    public void setCurrentUser(User user) {
        this.mUser = user;
        String userInfo = new Gson().toJson(user);
        SPHelper.write(this, ConstantStr.USER_INFO, ConstantStr.USER_BEAN, userInfo);
    }

    public ApplicationModule getModule() {
        if (applicationModule == null) {
            applicationModule = new ApplicationModule(this);
        }
        return applicationModule;
    }
}
