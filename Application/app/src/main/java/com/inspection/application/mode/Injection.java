package com.inspection.application.mode;

import android.support.annotation.NonNull;

import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.mode.source.application.DaggerApplicationRepositoryComponent;
import com.inspection.application.mode.source.customer.CustomerRepository;
import com.inspection.application.mode.source.customer.DaggerCustomerRepositoryComponent;
import com.inspection.application.mode.source.equipment.DaggerEquipmentRepositoryComponent;
import com.inspection.application.mode.source.equipment.EquipmentRepository;
import com.inspection.application.mode.source.fault.DaggerFaultRepositoryComponent;
import com.inspection.application.mode.source.fault.FaultRepository;
import com.inspection.application.mode.source.inject.DaggerInjectRepositoryComponent;
import com.inspection.application.mode.source.inject.InjectRepository;
import com.inspection.application.mode.source.news.DaggerNewsRepositoryComponent;
import com.inspection.application.mode.source.news.NewsRepository;
import com.inspection.application.mode.source.task.DaggerTaskRepositoryComponent;
import com.inspection.application.mode.source.task.TaskRepository;
import com.inspection.application.mode.source.user.DaggerUserRepositoryComponent;
import com.inspection.application.mode.source.user.UserRepository;
import com.inspection.application.view.ApplicationModule;

/**
 * 提供 repository
 * Created by pingan on 2017/12/2.
 */

public class Injection {

    private static Injection injection;

    private Injection() {

    }

    public static Injection getIntent() {
        if (injection == null) {
            injection = new Injection();
        }
        return injection;
    }

    private ApplicationRepository applicationRepository;

    /**
     * @param module ApplicationModule
     * @return ApplicationRepository
     */
    public ApplicationRepository provideApplicationRepository(@NonNull ApplicationModule module) {
        if (applicationRepository == null) {
            applicationRepository = DaggerApplicationRepositoryComponent.builder().applicationModule(module).build().getRepository();
        }
        return applicationRepository;
    }

    private UserRepository userRepository;

    /**
     * @param module ApplicationModule
     * @return UserRepository
     */
    public UserRepository provideUserRepository(@NonNull ApplicationModule module) {
        if (userRepository == null) {
            userRepository = DaggerUserRepositoryComponent.builder().applicationModule(module).build().getRepository();
        }
        return userRepository;
    }

    private InjectRepository injectRepository;

    /**
     * @return InjectRepository
     */
    public InjectRepository provideInjectRepository() {
        if (injectRepository == null) {
            injectRepository = DaggerInjectRepositoryComponent.builder().build().getRepository();
        }
        return injectRepository;
    }

    private CustomerRepository customerRepository;

    /**
     * @return CustomerRepository
     */
    public CustomerRepository provideCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = DaggerCustomerRepositoryComponent.builder().build().getRepository();
        }
        return customerRepository;
    }

    private EquipmentRepository equipmentRepository;

    /**
     * @return EquipmentRepository
     */
    public EquipmentRepository provideEquipmentRepository() {
        if (equipmentRepository == null) {
            equipmentRepository = DaggerEquipmentRepositoryComponent.builder().build().getRepository();
        }
        return equipmentRepository;
    }

    private FaultRepository faultRepository;

    /**
     * @param module ApplicationModule
     * @return FaultRepository
     */
    public FaultRepository provideFaultRepository(@NonNull ApplicationModule module) {
        if (faultRepository == null) {
            faultRepository = DaggerFaultRepositoryComponent.builder().applicationModule(module).build().getRepository();
        }
        return faultRepository;
    }

    private NewsRepository newsRepository;

    /**
     * @param module ApplicationModule
     * @return NewsRepository
     */
    public NewsRepository provideNewsRepository(@NonNull ApplicationModule module) {
        if (newsRepository == null) {
            newsRepository = DaggerNewsRepositoryComponent.builder().applicationModule(module).build().getRepository();
        }
        return newsRepository;
    }

    private TaskRepository taskRepository;

    /**
     * @return TaskRepository
     */
    public TaskRepository provideTaskRepository() {
        if (taskRepository == null) {
            taskRepository = DaggerTaskRepositoryComponent.builder().build().getRepository();
        }
        return taskRepository;
    }
}
