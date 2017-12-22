package com.inspection.application.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.inspection.application.R;


/**
 * Created by Administrator on 2017/6/14.
 */

public class StaffDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private TextView mStaffName;
    private TextView mPlanTime;
    private TextView mRepTime;
    private TextView mRepPlace;
    private TextView mStaffGrpup;
    private Button mButton;

    private String mPlan;
    private String mRep;
    private String mPlace;
    private String mGroup;
    private String mName;


    public StaffDialog(Activity activity) {
        super(activity, R.style.dialog);
        this.activity = activity;
    }

    public StaffDialog(Activity activity, String name, String planTime, String repTime, String place, String group) {
        super(activity, R.style.dialog);
        this.activity = activity;
        this.mName = name;
        this.mPlan = planTime;
        this.mRep = repTime;
        this.mPlace = place;
        this.mGroup = group;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_dialog);

        mStaffName = (TextView) findViewById(R.id.id_staff_name);
        mPlanTime = (TextView) findViewById(R.id.id_staff_plantime);
        mRepTime = (TextView) findViewById(R.id.id_staff_reptime);
        mRepPlace = (TextView) findViewById(R.id.id_staff_repplace);
        mStaffGrpup = (TextView) findViewById(R.id.id_staff_group);
        mButton = (Button) findViewById(R.id.id_staff_ok);
        mButton.setOnClickListener(this);

        mStaffName.setText(mName);
        mPlanTime.setText(mPlan);
        mRepTime.setText(mRep);
        mRepPlace.setText(mPlace);
        mStaffGrpup.setText(mGroup);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕中间
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.x = (int) (width * 0.8);
        lp.y = (int) (height * 0.8);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        //设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_staff_ok:
                this.cancel();
                break;
        }
    }
}
