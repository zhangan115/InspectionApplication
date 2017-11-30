package com.inspection.application.view.login;

import android.os.Bundle;


import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtils;

import javax.inject.Inject;

/**
 * 登陆Act
 * Created by zhangan on 2017-06-22.
 */

public class LoginActivity extends BaseActivity {

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container);
        transparentStatusBar();
        LoginFragment fragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getFragmentManager(), fragment, R.id.frame_container);
//        DaggerLoginComponent.builder().loginModule(new LoginModule(fragment))
//                .customerRepositoryComponent(App.getInstance().getRepositoryComponent())
//                .build().inject(this);
    }
}
