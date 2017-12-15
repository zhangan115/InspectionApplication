package com.inspection.application.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;


/**
 * 类型1 and 2
 * Created by zhangan on 2017/9/27.
 */

public class InspectionType1 extends LinearLayout {

    private TextView nameTv, valueTv;

    public InspectionType1(Context context) {
        super(context);
        init(context);
    }

    public InspectionType1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_inspection_1, this);
        nameTv = (TextView) findViewById(R.id.name);
        valueTv = (TextView) findViewById(R.id.value);
    }

    public InspectionType1 setValue(String name, String value) {
        nameTv.setText(name + "：" + (TextUtils.isEmpty(value) ? "" : value));
        return this;
    }
}
