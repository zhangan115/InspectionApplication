package com.inspection.application.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.inspection.application.R;
import com.inspection.application.mode.bean.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * 拍照
 * Created by zhangan on 2017/9/7.
 */

public class TakePhotoView extends LinearLayout implements View.OnClickListener, PhotoView.UploadListener {

    private Context context;
    private List<Image> images;
    private List<PhotoView> photoViews;
    private LinearLayout imgListLayout;
    private ImageView takePhoto;
    private TakePhotoListener takePhotoListener;
    private String[] urls;
    private static final int PHOTO_SIZE = 5;

    public TakePhotoView(Context context) {
        super(context);
        init(context);
    }

    public TakePhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_take_photo, this);
        imgListLayout = findViewById(R.id.id_fault_img_list);
        takePhoto = findViewById(R.id.id_fault_take_photo);
        takePhoto.setOnClickListener(this);
        photoViews = new ArrayList<>();
    }

    public List<Image> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }


    public void setImages(List<Image> imageList) {
        int imageSize = imageList.size();
        if (imageSize > PHOTO_SIZE) {
            this.images = imageList.subList(imageSize - PHOTO_SIZE, imageSize);
        } else {
            this.images = imageList;
        }
        photoViews.clear();
        imgListLayout.removeAllViews();
        if (images.size() >= PHOTO_SIZE) {
            takePhoto.setVisibility(View.GONE);
        } else {
            takePhoto.setVisibility(View.VISIBLE);
        }
        urls = new String[images.size()];
        for (int i = 0; i < images.size(); i++) {
            urls[i] = images.get(i).getImageLocal();
        }
        for (int i = 0; i < images.size(); i++) {
            imgListLayout.addView(createImageView(context, images.get(i), i));
        }
    }

    public void setTakePhotoListener(TakePhotoListener listener) {
        this.takePhotoListener = listener;
    }

    private PhotoView createImageView(Context context, Image images, int position) {
        PhotoView photoView = new PhotoView(context);
        photoView.showImage(images, urls, position, this);
        photoViews.add(photoView);
        return photoView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_fault_take_photo:
                if (takePhotoListener == null) {
                    return;
                }
                takePhotoListener.onTakePhoto();
                break;
        }
    }

    @Override
    public void onDelete(int position, Image image) {
        if (takePhotoListener != null) {
            takePhotoListener.onDelete(position, image);
        }

    }

    @Override
    public void onTakePhoto(int position, Image image) {
        if (takePhotoListener != null) {
            takePhotoListener.onTakePhoto(position, image);
        }
    }

    public interface TakePhotoListener {

        void onTakePhoto();

        void onDelete(int position, Image image);

        void onTakePhoto(int position, Image image);

    }
}
