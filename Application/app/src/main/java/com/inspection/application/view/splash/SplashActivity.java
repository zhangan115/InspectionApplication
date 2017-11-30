package com.inspection.application.view.splash;

import android.content.Intent;
import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.app.AppStatusConstant;
import com.inspection.application.app.AppStatusManager;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.login.LoginActivity;
import com.inspection.application.view.main.MainActivity;
import com.inspection.application.view.welcome.WelcomeActivity;


/**
 * 启动屏幕
 * Created by zhangan on 2017-07-24.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL);//进入应用初始化设置成未登录状态
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        transparentStatusBar();
        if (mPresenter != null) {
            mPresenter.subscribe();
        } else {
            needLogin();
        }
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void needLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showWelcome() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void openHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
