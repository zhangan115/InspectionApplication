package com.inspection.application.mode.bean.news.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.app.App;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Yangzb on 2017/7/31 15:09
 * E-mail：yangzongbin@si-top.com
 * 推送消息
 */
@Entity(nameInDb = "file_news")
public class NewsBean implements Parcelable {

    @Id(autoincrement = true)
    private Long _id;
    private long messageId;
    private String tip;
    private String contentInfo;
    private String fromUser;
    private String newsContent;
    private String meContent;
    private String title;
    private Long taskId;
    private long messageTime;
    private int messageType;
    private int toUserId;
    private int newsType;
    private int smallType;
    private boolean hasRead;
    private boolean isMe;
    private boolean isAlarm;
    private boolean isWork;
    private long currentUserId = App.getInstance().getCurrentUser().getUserId();//使用者id

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getMeContent() {
        return meContent;
    }

    public void setMeContent(String meContent) {
        this.meContent = meContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public int getSmallType() {
        return smallType;
    }

    public void setSmallType(int smallType) {
        this.smallType = smallType;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }

    public long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeLong(this.messageId);
        dest.writeString(this.tip);
        dest.writeString(this.contentInfo);
        dest.writeString(this.fromUser);
        dest.writeString(this.newsContent);
        dest.writeString(this.meContent);
        dest.writeString(this.title);
        dest.writeValue(this.taskId);
        dest.writeLong(this.messageTime);
        dest.writeInt(this.messageType);
        dest.writeInt(this.toUserId);
        dest.writeInt(this.newsType);
        dest.writeInt(this.smallType);
        dest.writeByte(this.hasRead ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMe ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAlarm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isWork ? (byte) 1 : (byte) 0);
        dest.writeLong(this.currentUserId);
    }

    public boolean getHasRead() {
        return this.hasRead;
    }

    public boolean getIsMe() {
        return this.isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public boolean getIsAlarm() {
        return this.isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public boolean getIsWork() {
        return this.isWork;
    }

    public void setIsWork(boolean isWork) {
        this.isWork = isWork;
    }

    public NewsBean() {
    }

    protected NewsBean(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.messageId = in.readLong();
        this.tip = in.readString();
        this.contentInfo = in.readString();
        this.fromUser = in.readString();
        this.newsContent = in.readString();
        this.meContent = in.readString();
        this.title = in.readString();
        this.taskId = (Long) in.readValue(Long.class.getClassLoader());
        this.messageTime = in.readLong();
        this.messageType = in.readInt();
        this.toUserId = in.readInt();
        this.newsType = in.readInt();
        this.smallType = in.readInt();
        this.hasRead = in.readByte() != 0;
        this.isMe = in.readByte() != 0;
        this.isAlarm = in.readByte() != 0;
        this.isWork = in.readByte() != 0;
        this.currentUserId = in.readLong();
    }

    @Generated(hash = 2079109429)
    public NewsBean(Long _id, long messageId, String tip, String contentInfo, String fromUser,
            String newsContent, String meContent, String title, Long taskId, long messageTime,
            int messageType, int toUserId, int newsType, int smallType, boolean hasRead,
            boolean isMe, boolean isAlarm, boolean isWork, long currentUserId) {
        this._id = _id;
        this.messageId = messageId;
        this.tip = tip;
        this.contentInfo = contentInfo;
        this.fromUser = fromUser;
        this.newsContent = newsContent;
        this.meContent = meContent;
        this.title = title;
        this.taskId = taskId;
        this.messageTime = messageTime;
        this.messageType = messageType;
        this.toUserId = toUserId;
        this.newsType = newsType;
        this.smallType = smallType;
        this.hasRead = hasRead;
        this.isMe = isMe;
        this.isAlarm = isAlarm;
        this.isWork = isWork;
        this.currentUserId = currentUserId;
    }

    public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}
