package com.inspection.application.mode.source.task;


import com.inspection.application.view.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 系统 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface TaskRepositoryComponent {

    TaskRepository getRepository();
}
