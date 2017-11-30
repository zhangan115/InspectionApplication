package com.inspection.application.view.login;



import com.sito.customer.mode.customer.CustomerRepositoryComponent;
import com.sito.customer.view.FragmentScoped;

import dagger.Component;

/**
 * login dagger component
 * Created by zhangan on 2017-02-27.
 */
@FragmentScoped
@Component(dependencies = CustomerRepositoryComponent.class, modules = LoginModule.class)
interface LoginComponent {

    void inject(LoginActivity activity);
}
