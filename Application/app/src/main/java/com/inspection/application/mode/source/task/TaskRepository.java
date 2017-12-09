package com.inspection.application.mode.source.task;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by pingan on 2017/12/9.
 */
@Singleton
public class TaskRepository implements TaskDataSource {

    @Inject
    TaskRepository() {

    }
}
