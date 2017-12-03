package com.inspection.application.mode.source.equipment;


import javax.inject.Singleton;

import dagger.Component;

/**
 * 设备 component
 * <p>
 * Created by zhangan on 2017-06-22.
 */
@Singleton
@Component()
public interface EquipmentRepositoryComponent {

    EquipmentRepository getRepository();
}
