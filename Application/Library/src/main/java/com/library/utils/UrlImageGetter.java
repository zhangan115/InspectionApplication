package com.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 图文混排
 * Created by zhangan on 2017-06-15.
 */

public class UrlImageGetter implements Html.ImageGetter {

    private Context c;
    private TextView container;
    private int width;

    public UrlImageGetter(Context c, TextView container) {
        this.c = c;
        this.container = container;
        width = c.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(c).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {
                float scaleWidth = ((float) width) / loadedImage.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), matrix,
                        true);
                urlDrawable.bitmap = loadedImage;
                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                container.invalidate();
                container.setText(container.getText());
            }
        });
        return urlDrawable;
    }
}
