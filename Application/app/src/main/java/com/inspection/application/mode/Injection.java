package com.inspection.application.mode;

import android.support.annotation.NonNull;

import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.mode.source.application.DaggerApplicationRepositoryComponent;
import com.inspection.application.mode.source.customer.CustomerRepository;
import com.inspection.application.mode.source.customer.DaggerCustomerRepositoryComponent;
import com.inspection.application.mode.source.equipment.DaggerEquipmentRepositoryComponent;
import com.inspection.application.mode.source.equipment.EquipmentRepository;
import com.inspection.application.mode.source.inject.DaggerInjectRepositoryComponent;
import com.inspection.application.mode.source.inject.InjectRepository;
import com.inspection.application.mode.source.user.DaggerUserRepositoryComponent;
import com.inspection.application.mode.source.user.UserRepository;
import com.inspection.application.view.ApplicationModule;

/**
 * 提供 repository
 * Created by pingan on 2017/12/2.
 */

public class Injection {
    /**
     * @param module ApplicationModule
     * @return ApplicationRepository
     */
    public static ApplicationRepository provideApplicationRepository(@NonNull ApplicationModule module) {
        return DaggerApplicationRepositoryComponent.builder().applicationModule(module).build().getRepository();
    }

    /**
     * @param module ApplicationModule
     * @return UserRepository
     */
    public static UserRepository provideUserRepository(@NonNull ApplicationModule module) {
        return DaggerUserRepositoryComponent.builder().applicationModule(module).build().getRepository();
    }

    /**
     * @return InjectRepository
     */
    public static InjectRepository provideInjectRepository() {
        return DaggerInjectRepositoryComponent.builder().build().getRepository();
    }

    /**
     * @return CustomerRepository
     */
    public static CustomerRepository provideCustomerRepository() {
        return DaggerCustomerRepositoryComponent.builder().build().getRepository();
    }

    /**
     * @return EquipmentRepository
     */
    public static EquipmentRepository provideEquipmentRepository() {
        return DaggerEquipmentRepositoryComponent.builder().build().getRepository();
    }


}
