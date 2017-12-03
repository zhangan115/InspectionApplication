package com.inspection.application.mode.source.customer;


import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.view.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 系统 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component()
public interface CustomerRepositoryComponent {

    CustomerRepository getRepository();
}
