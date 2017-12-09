package com.inspection.application.mode.source.task;


import javax.inject.Singleton;

import dagger.Component;

/**
 * 系统 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component()
public interface TaskRepositoryComponent {

    TaskRepository getRepository();
}
