package com.inspection.application.mode.source.user;


import com.inspection.application.view.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 用户 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface UserRepositoryComponent {

    UserRepository getRepository();
}
