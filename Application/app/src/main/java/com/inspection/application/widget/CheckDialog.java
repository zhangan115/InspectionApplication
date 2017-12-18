package com.inspection.application.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;


import com.inspection.application.R;
import com.inspection.application.mode.bean.equipment.CheckValue;
import com.inspection.application.view.equipment.data.CommonAdapter;
import com.inspection.application.view.equipment.data.ViewHolder;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/14.
 */

public class CheckDialog extends Dialog implements View.OnClickListener {
    private Activity activity;

    private CheckValue mCheckValue;
    private ListView mListView;

    public CheckDialog(Activity activity) {
        super(activity, R.style.mdialog);
        this.activity = activity;
    }

    public CheckDialog(Activity activity, CheckValue value) {
        super(activity, R.style.mdialog);
        this.activity = activity;
        mCheckValue = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chekdata);
        mListView = (ListView) findViewById(R.id.id_check_listview);
        initData();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    private void initData() {
        List<CheckValue.ValueListBean> listBeen = new ArrayList<>();
        listBeen.addAll(mCheckValue.getValueList());
        PopAdpter adpter = new PopAdpter(activity, listBeen, R.layout.item_dialog_check);
        mListView.setAdapter(adpter);
        int totalHeight = 0;
        int height = adpter.getCount();
        if (height > 4) {
            height = 4;
        }
        for (int i = 0; i < height; i++) {//最大显示4个的高度
            View listItem = adpter.getView(i, null, mListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        params.height = totalHeight
                + (mListView.getDividerHeight() * (mListView.getCount() - 1));
        mListView.setLayoutParams(params);
    }

    class PopAdpter extends CommonAdapter<CheckValue.ValueListBean> {

        public PopAdpter(Context context, List<CheckValue.ValueListBean> lists, int layoutId) {
            super(context, lists, layoutId);
        }

        @Override
        public void conVert(ViewHolder vHolder, CheckValue.ValueListBean data) {
            TextView name = (TextView) vHolder.getView(R.id.id_check_dialog_name);
            name.setText(mCheckValue.getInspectionName());
            TextView time = (TextView) vHolder.getView(R.id.id_check_dialog_time);
            time.setText(DataUtil.timeFormat(data.getCommitTime(), null));
            TextView result = (TextView) vHolder.getView(R.id.id_check_dialog_result);
            result.setText(data.getValue());
        }

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
    }
}
