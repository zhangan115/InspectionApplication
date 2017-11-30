package com.inspection.application.view.login;

import dagger.Module;
import dagger.Provides;

/**
 * login dagger module
 * Created by zhangan on 2017-02-27.
 */
@Module
class LoginModule {

    private final LoginContract.View mView;

    LoginModule(LoginContract.View view) {
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return mView;
    }
}
