package com.inspection.application.view.task.work;

import com.inspection.application.mode.bean.task.TaskEquipmentBean;

/**
 * 设备改变
 * Created by pingan on 2017/12/17.
 */

public interface IEquipmentChangeListener {

    void equipmentChange(TaskEquipmentBean taskEquipmentBean, boolean canEdit);

}
