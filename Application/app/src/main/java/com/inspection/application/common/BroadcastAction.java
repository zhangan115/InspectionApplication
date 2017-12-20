package com.inspection.application.common;

/**
 * 广播常量
 * Created by zhangan on 2017/11/17.
 */

public interface BroadcastAction {

    String CLEAN_ALL_DATA = "action_clean_all_message";//清除了所有消息

    String MESSAGE_UN_READ_COUNT = "action_message_un_read_count";
    String MESSAGE_UN_READ_STATE = "action_un_read_state";
    String NEWS_MESSAGE = "action_new_message";//新消息提示
}
