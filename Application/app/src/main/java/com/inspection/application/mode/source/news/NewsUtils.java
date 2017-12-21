package com.inspection.application.mode.source.news;

import android.text.TextUtils;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.bean.news.ContentBean;
import com.inspection.application.mode.bean.news.MessageContent;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.library.utils.DataUtil;


/**
 * 消息类型
 * Created by zhangan on 2017/10/16.
 */

public class NewsUtils {

    public static NewsBean getNewsBean(MessageContent message) {
        NewsBean newsBean = new NewsBean();
        newsBean.set_id(message.getLogId());
        newsBean.setContentInfo(message.getAppContent());
        newsBean.setMessageTime(message.getMessageTime());
        newsBean.setTip(message.getTip());
        newsBean.setMessageId(message.getLogId());
        newsBean.setMessageType(message.getMessageType());
        newsBean.setSmallType(message.getContentInfo().getSmallType());
        getNewsContent(newsBean, message);
        return newsBean;
    }

    public static int getNewsNotifyDraw(NewsBean newsBean) {
        switch (newsBean.getSmallType()) {
            case 101:
            case 102:
            case 103:
            case 104:
                return R.drawable.icon_audit_alm;
            case 201:
            case 202:
            case 203:
                return R.drawable.icon_system_msg;
            case 501:
                return R.drawable.icon_task_alm;
        }
        return R.drawable.icon_system_msg;
    }


    public static int getNewsType(NewsBean newsBean) {
        if (newsBean.isAlarm()) {
            return 1;
        }
        if (newsBean.isWork()) {
            return 2;
        }
        return 0;
    }
    /*
    1、缺陷消息 101缺陷上报，102缺陷关闭，103缺陷流转，104缺陷确认
2、巡检任务 201巡检任务领取，202巡检任务开始，203巡检任务结束
     */

    private static void getNewsContent(NewsBean newsBean, MessageContent message) {
        ContentBean contentBean = message.getContentInfo().getContent();
        switch (message.getContentInfo().getSmallType()) {
            case 101:
                newsBean.setAlarm(true);
                newsBean.setTaskId(contentBean.getFaultId());
                StringBuilder sb101 = new StringBuilder();
                sb101.append(contentBean.getUserRealName());
                sb101.append("上报缺陷");
                sb101.append(contentBean.getEquipmentName());
                if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                    sb101.append("(").append(contentBean.getEquipmentSn()).append(")");
                }
                sb101.append(contentBean.getFaultType());
                sb101.append("至");
                sb101.append(contentBean.getUserRealNames());
                newsBean.setNewsContent(sb101.toString());
                if (!TextUtils.isEmpty(message.getContentInfo().getUserIds())) {
                    String[] userIds = message.getContentInfo().getUserIds().split(",");
                    for (String userId : userIds) {
                        if (userId.equals(String.valueOf(App.getInstance().getCurrentUser().getUserId()))) {
                            newsBean.setMe(true);
                            StringBuilder sb = new StringBuilder();
                            sb.append(contentBean.getUserRealName())
                                    .append("指派给你:")
                                    .append(contentBean.getEquipmentName());
                            if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                                sb.append("(").append(contentBean.getEquipmentSn()).append(")");
                            }
                            sb.append(contentBean.getFaultType());
                            sb.append(",请及时处理");
                            newsBean.setMeContent(sb.toString());
                            break;
                        }
                    }
                }
                break;
            case 102:
                newsBean.setAlarm(true);
                newsBean.setTaskId(contentBean.getFaultId());
                StringBuilder sb102 = new StringBuilder();
                sb102.append(contentBean.getEquipmentName());
                sb102.append(contentBean.getFaultType());
                if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                    sb102.append("(").append(contentBean.getEquipmentSn()).append(")");
                }
                sb102.append("已关闭");
                newsBean.setNewsContent(sb102.toString());
                break;
            case 103:
                newsBean.setAlarm(true);
                newsBean.setTaskId(contentBean.getFaultId());
                StringBuilder sb103 = new StringBuilder();
                sb103.append(contentBean.getUserRealName());
                sb103.append("指派给");
                if (!TextUtils.isEmpty(contentBean.getUserRealNames())) {
                    sb103.append(contentBean.getUserRealNames());
                }
                sb103.append(":");
                sb103.append(contentBean.getEquipmentName());
                if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                    sb103.append("(").append(contentBean.getEquipmentSn()).append(")");
                }
                sb103.append(contentBean.getFaultType());
                newsBean.setNewsContent(sb103.toString());
                if (!TextUtils.isEmpty(message.getContentInfo().getUserIds())) {
                    String[] userIds = message.getContentInfo().getUserIds().split(",");
                    for (String userId : userIds) {
                        if (userId.equals(String.valueOf(App.getInstance().getCurrentUser().getUserId()))) {
                            newsBean.setMe(true);
                            StringBuilder sb = new StringBuilder();
                            sb.append(contentBean.getUserRealName());
                            sb.append("指派给你:");
                            sb.append(contentBean.getEquipmentName());
                            if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                                sb.append("(").append(contentBean.getEquipmentSn()).append(")");
                            }
                            sb.append(contentBean.getFaultType());
                            sb.append(",请及时处理");
                            newsBean.setMeContent(sb.toString());
                            break;
                        }
                    }
                }
                break;
            case 104:
                newsBean.setAlarm(true);
                newsBean.setTaskId(contentBean.getFaultId());
                StringBuilder sb104 = new StringBuilder();
                sb104.append(contentBean.getUserRealName());
                sb104.append("指派检修工作给");
                sb104.append(contentBean.getUserRealNames()).append(":");
                sb104.append(contentBean.getEquipmentName());
                if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                    sb104.append("(").append(contentBean.getEquipmentSn()).append(")");
                }
                sb104.append(contentBean.getFaultType());
                sb104.append(",将于");
                sb104.append(DataUtil.timeFormat(contentBean.getRepairTime(), "MM-dd-HH:mm"));
                sb104.append("执行");
                newsBean.setNewsContent(sb104.toString());

                if (!TextUtils.isEmpty(message.getContentInfo().getUserIds())) {
                    String[] userIds = message.getContentInfo().getUserIds().split(",");
                    for (String userId : userIds) {
                        if (userId.equals(String.valueOf(App.getInstance().getCurrentUser().getUserId()))) {
                            newsBean.setMe(true);
                            StringBuilder sb = new StringBuilder();
                            sb.append(contentBean.getUserRealName());
                            sb.append("指派检修工作给你:");
                            sb.append(contentBean.getEquipmentName());
                            if (!TextUtils.isEmpty(contentBean.getEquipmentSn())) {
                                sb.append("(").append(contentBean.getEquipmentSn()).append(")");
                            }
                            sb.append(contentBean.getFaultType())
                                    .append(",请在")
                                    .append(DataUtil.timeFormat(contentBean.getRepairTime(), "MM月dd日 HH:mm"))
                                    .append("执行");
                            newsBean.setMeContent(sb.toString());
                            break;
                        }
                    }
                }
                break;
            case 201:
                newsBean.setWork(true);
                newsBean.setTaskId(contentBean.getTaskId());
                StringBuilder sb201 = new StringBuilder();
                sb201.append(contentBean.getUserRealName());
                sb201.append("领取了");
                if (!TextUtils.isEmpty(contentBean.getTaskName())) {
                    sb201.append(contentBean.getTaskName());
                }
                newsBean.setNewsContent(sb201.toString());
                break;
            case 202:
                newsBean.setWork(true);
                newsBean.setTaskId(contentBean.getTaskId());
                StringBuilder sb202 = new StringBuilder();
                sb202.append(contentBean.getUserRealName());
                sb202.append("开始了");
                if (!TextUtils.isEmpty(contentBean.getTaskName())) {
                    sb202.append("''").append(contentBean.getTaskName()).append("''");
                }
                newsBean.setTaskId(contentBean.getTaskId());
                newsBean.setNewsContent(sb202.toString());
                break;
            case 203:
                newsBean.setWork(true);
                newsBean.setTaskId(contentBean.getTaskId());
                StringBuilder sb203 = new StringBuilder();
                sb203.append(contentBean.getUserRealName());
                sb203.append("完成了");
                if (!TextUtils.isEmpty(contentBean.getTaskName())) {
                    sb203.append("''").append(contentBean.getTaskName()).append("''");
                }
                sb203.append("巡检");
                newsBean.setNewsContent(sb203.toString());
                break;
            case 501:
                newsBean.setMe(true);
                StringBuilder sb501 = new StringBuilder();
                newsBean.setTaskId(contentBean.getTaskId());
                sb501.append("在").append(DataUtil.timeFormat(contentBean.getStartTime(), "HH:mm"))
                        .append("有你执行的巡检任务")
                        .append("''")
                        .append(contentBean.getTaskName())
                        .append("''");
                newsBean.setMeContent(sb501.toString());
                break;
        }
    }
}
