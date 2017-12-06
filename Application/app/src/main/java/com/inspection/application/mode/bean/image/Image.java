package com.inspection.application.mode.bean.image;

import com.inspection.application.app.App;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.lang.ref.PhantomReference;

/**
 * 拍照对象
 */
@Entity(nameInDb = "file_image")
public class Image {

    @Id(autoincrement = true)
    private Long _id;
    private Long saveTime;
    private String imageLocal;
    private String imageUrl;
    private boolean isUpload;
    private int workType;
    private String itemId;
    @Transient
    private String photoLocal;
    private long currentUserId = App.getInstance().getCurrentUser().getUserId();//使用者id

    @Generated(hash = 419458804)
    public Image(Long _id, Long saveTime, String imageLocal, String imageUrl,
                 boolean isUpload, int workType, String itemId, long currentUserId) {
        this._id = _id;
        this.saveTime = saveTime;
        this.imageLocal = imageLocal;
        this.imageUrl = imageUrl;
        this.isUpload = isUpload;
        this.workType = workType;
        this.itemId = itemId;
        this.currentUserId = currentUserId;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public String getImageLocal() {
        return this.imageLocal;
    }

    public void setImageLocal(String imageLocal) {
        this.imageLocal = imageLocal;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public int getWorkType() {
        return this.workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public long getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getPhotoLocal() {
        return photoLocal;
    }

    public void setPhotoLocal(String photoLocal) {
        this.photoLocal = photoLocal;
    }
}
