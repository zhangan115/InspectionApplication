package com.inspection.application.widget;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.bean.fault.FaultDetail;
import com.library.utils.DataUtil;
import com.library.utils.DisplayUtil;
import com.orhanobut.logger.Logger;

/**
 * item
 * Created by pingan on 2017/12/13.
 */

public class FlowsItemLayout extends LinearLayout {

    private TextView tv_des;
    private Context mContext;

    public FlowsItemLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.layout_flows_item, this);
        tv_des = findViewById(R.id.tv_des);
    }

    public void setContent(FaultDetail.FaultFlowsBean faultFlowsBean) {
        ((TextView) findViewById(R.id.tv_time)).setText(DataUtil.timeFormat(faultFlowsBean.getFlowTime(), "yyyy.MM.dd HH:mm:ss"));
        ((TextView) findViewById(R.id.tv_user_name)).setText(String.format("审批人员:%s", faultFlowsBean.getUser().getRealName()));
        if (!TextUtils.isEmpty(faultFlowsBean.getFlowRemark())) {
            tv_des.setText(faultFlowsBean.getFlowRemark());
        }
        tv_des.post(new Runnable() {
            @Override
            public void run() {
                setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , DisplayUtil.dip2px(mContext, 110 + (50 * (tv_des.getLineCount() - 1)))));
            }
        });
    }
}
