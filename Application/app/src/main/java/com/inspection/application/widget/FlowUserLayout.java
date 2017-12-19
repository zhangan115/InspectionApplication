package com.inspection.application.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.bean.user.User;
import com.library.utils.GlideUtils;

/**
 * 选择人员展示
 * Created by zhangan on 2017/8/31.
 */

public class FlowUserLayout extends LinearLayout {

    private Context context;
    private ImageView iv_user_icon;
    private TextView tv_user_name;

    public FlowUserLayout(Context context) {
        super(context);
        init(context);
    }

    public FlowUserLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_flow_user, this);
        iv_user_icon = findViewById(R.id.iv_user_icon);
        tv_user_name = findViewById(R.id.tv_user_name);
    }

    public FlowUserLayout setUser(User user) {
        GlideUtils.ShowCircleImage(context, user.getPortraitUrl(), iv_user_icon, R.drawable.icon_monitor);
        tv_user_name.setText(user.getRealName());
        return this;
    }

}
