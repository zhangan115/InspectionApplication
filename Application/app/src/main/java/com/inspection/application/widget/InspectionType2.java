package com.inspection.application.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.inspection.application.R;
import com.inspection.application.view.photo.ViewPagePhotoActivity;
import com.library.utils.GlideUtils;

import java.text.MessageFormat;

/**
 * 类型3
 * Created by zhangan on 2017/9/27.
 */

public class InspectionType2 extends LinearLayout {

    private TextView nameTv;
    private ImageView valueTv;
    private Context context;

    public InspectionType2(Context context) {
        super(context);
        init(context);
    }

    public InspectionType2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_inspection_2, this);
        this.context = context;
        nameTv = (TextView) findViewById(R.id.name);
        valueTv = (ImageView) findViewById(R.id.value);

    }

    public InspectionType2 setValue(String name, String value) {
        nameTv.setText(MessageFormat.format("{0}:", name));
        GlideUtils.ShowImage(context, value, valueTv, R.drawable.picture_default);
        setTag(R.id.tag_position, value);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (String) v.getTag(R.id.tag_position);
                ViewPagePhotoActivity.startActivity(context, new String[]{url}, 0);
            }
        });
        return this;
    }
}
