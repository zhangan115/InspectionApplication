package com.inspection.application.mode.source.count;


import javax.inject.Singleton;

import dagger.Component;

/**
 * 统计 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component()
public interface CountRepositoryComponent {

    CountRepository getRepository();
}
