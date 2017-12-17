package com.inspection.application.common;

import com.inspection.application.app.App;
import com.inspection.application.mode.bean.task.RoomListBean;

/**
 * 任务帮助类
 * Created by pingan on 2017/12/17.
 */

public class TaskStateUtils {

    public static String getTag(RoomListBean data) {
        return App.getInstance().getCurrentUser().getUserId() +
                "_" + data.getRoomDb().getTaskId() + "_" + data.getRoomDb().getRoomId();
    }
}
