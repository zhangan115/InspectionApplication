package com.inspection.application.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.app.AppStatusConstant;
import com.inspection.application.app.AppStatusManager;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.mode.source.application.ApplicationRepositoryComponent;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.login.LoginActivity;
import com.inspection.application.view.main.MainActivity;
import com.inspection.application.view.welcome.WelcomeActivity;

import javax.inject.Inject;


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
        setTheme(R.style.LoginActivityStyle);
        setContentView(R.layout.activity_splash);
        transparentStatusBar();
        new SplashPresenter(Injection.provideUserRepository(App.getInstance().getModule())
                , this);
        mPresenter.subscribe();
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

    @Override
    public void showMessage(@Nullable String message) {
        mApp.showToast(message);
    }
}
