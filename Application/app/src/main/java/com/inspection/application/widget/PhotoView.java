package com.inspection.application.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.view.photo.ViewPagePhotoActivity;
import com.library.utils.GlideUtils;

/**
 * 照片展示
 * Created by zhangan on 2017/9/7.
 */

public class PhotoView extends RelativeLayout implements View.OnLongClickListener, View.OnClickListener {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Context context;
    private String[] urls;
    private int photoPosition = -1;
    private UploadListener listener;
    private Image image;

    public PhotoView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_image, this);
        imageView = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public void showImage(@NonNull Image image, String[] url, int position, @Nullable UploadListener listener) {
        this.image = image;
        this.listener = listener;
        this.photoPosition = position;
        this.urls = url;
        if (!TextUtils.isEmpty(image.getImageUrl())) {
            setTag(R.id.tag_object, image);
            progressBar.setVisibility(View.GONE);
            GlideUtils.ShowImage(context, image.getImageLocal(), imageView, R.drawable.picture_default);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.picture_default));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        new MaterialDialog.Builder(context)
                .items(R.array.choose_condition_2)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (listener == null) {
                            return;
                        }
                        if (!image.getIsUpload()) {
                            return;
                        }
                        switch (position) {
                            case 0://删除照片
                                listener.onDelete(photoPosition, image);
                                break;
                            default://重新拍照
                                listener.onTakePhoto(photoPosition, image);
                                break;
                        }
                    }
                })
                .show();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (photoPosition != -1 && urls != null && urls.length > 0)
            ViewPagePhotoActivity.startActivity(context, urls, photoPosition);
    }

    interface UploadListener {

        void onDelete(int position, Image image);

        void onTakePhoto(int position, Image image);
    }

}
