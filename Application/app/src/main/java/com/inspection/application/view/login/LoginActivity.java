package com.inspection.application.view.login;

import android.os.Bundle;


import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtils;
import com.library.utils.ActivityUtilsV4;

import javax.inject.Inject;

/**
 * 登陆Act
 * Created by zhangan on 2017-06-22.
 */

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginActivityStyle);
        setLayoutAndToolbar(R.layout.activity_container);
        transparentStatusBar();
        LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        }
        new LoginPresenter(Injection.getIntent().provideUserRepository(App.getInstance().getModule()), fragment);
    }
}
