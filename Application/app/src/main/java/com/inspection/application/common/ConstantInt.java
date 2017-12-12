package com.inspection.application.common;

/**
 * int 型常量
 * Created by zhangan on 2017-06-21.
 */

public interface ConstantInt {

    int PAGE_SIZE = 20;
    int MAX_PAGE_SIZE = 10000;
    int OPERATION_STATE_1 = 1;//领取
    int OPERATION_STATE_2 = 2;//执行
    int OPERATION_STATE_3 = 3;//完成

    int TASK_STATE_1 = 1;//待领取
    int TASK_STATE_2 = 2;//已领取
    int TASK_STATE_3 = 3;//进行中
    int TASK_STATE_4 = 4;//已完成

    int FAULT = 1;//故障上报
    int INSPECTION = 2;//巡检

    int INSPECTION_TYPE = 0;

    int DATA_VALUE_TYPE_1 = 1;
    int DATA_VALUE_TYPE_2 = 2;//数字输入
    int DATA_VALUE_TYPE_3 = 3;//拍照
    int DATA_VALUE_TYPE_4 = 4;//文本输入

    int ROOM_STATE_1 = 1;//未开始
    int ROOM_STATE_2 = 2;//进行中
    int ROOM_STATE_3 = 3;//已完成
}
