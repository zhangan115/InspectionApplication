package com.inspection.application.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.common.TaskStateUtils;
import com.inspection.application.mode.bean.equipment.db.EquipmentDbDao;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.db.DbManager;
import com.library.utils.DataUtil;
import com.library.utils.SPHelper;
import com.orhanobut.logger.Logger;


/**
 * Created by zhangan on 2017/8/14.
 */

public class RoomListLayout extends LinearLayout implements View.OnClickListener {

    private ImageView iv_state, iv_end;
    private TextView tv_equip_count, tv_equip_time, tv_equip_name, tv_state;
    private LinearLayout startTaskLayout, finishTaskLayout;
    private Context context;
    private OnStartListener onStartListener;
    private OnFinishListener onFinishListener;
    private int mPosition;
    private RoomListBean roomListBean;
    private long startTime, finishTime;

    public RoomListLayout(Context context) {
        super(context);
        init(context);
    }

    public RoomListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.item_inspection_detail, this);
        startTaskLayout = findViewById(R.id.ll_start_task);
        startTaskLayout.setOnClickListener(this);
        finishTaskLayout = findViewById(R.id.ll_finish_task);
        tv_state = findViewById(R.id.tv_state);
        finishTaskLayout.setOnClickListener(this);
        iv_state = findViewById(R.id.iv_start);
        iv_end = findViewById(R.id.iv_end);
        tv_equip_count = findViewById(R.id.tv_equip_count);
        tv_equip_time = findViewById(R.id.tv_equip_time);
        tv_equip_name = findViewById(R.id.tv_equip_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_start_task:
                mPosition = (int) v.getTag(R.id.tag_position);
                roomListBean = (RoomListBean) v.getTag(R.id.tag_object);
                if (onStartListener != null) {
                    onStartListener.onStart(roomListBean, mPosition);
                }
                break;
            case R.id.ll_finish_task:
                mPosition = (int) v.getTag(R.id.tag_position);
                roomListBean = (RoomListBean) v.getTag(R.id.tag_object);
                if (onFinishListener != null) {
                    onFinishListener.onFinish(roomListBean, mPosition);
                }
                break;
        }
    }

    public void setListener(OnStartListener onStartListener, OnFinishListener onFinishListener) {
        this.onStartListener = onStartListener;
        this.onFinishListener = onFinishListener;
    }

    public void setRoomBean(long taskId, RoomListBean data, int position) {
        roomListBean = data;
        if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_1) {
            tv_state.setText("任务状态:未开始");
        } else if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_2) {
            tv_state.setText("任务状态:进行中");
        } else {
            tv_state.setText("任务状态:已完成");
        }
        tv_equip_name.setText("点检区域:" + data.getRoom().getRoomName());
        startTaskLayout.setTag(R.id.tag_position, position);
        startTaskLayout.setTag(R.id.tag_object, data);
        finishTaskLayout.setTag(R.id.tag_position, position);
        finishTaskLayout.setTag(R.id.tag_object, data);
        startTime = data.getStartTime();
        finishTime = data.getEndTime();
        if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_1) {
            tv_equip_time.setText(R.string.zero_time);
        } else if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_2) {
            if (startTime != 0) {
                tv_equip_time.setText("用时:" + DataUtil.timeFormat((System.currentTimeMillis() - startTime - 28800 * 1000), "HH:mm:ss"));
            } else {
                tv_equip_time.setText("用时:" + context.getResources().getString(R.string.zero_time));
            }
        } else {
            if (startTime == 0 || finishTime == 0) {
                tv_equip_time.setText(R.string.zero_time);
            } else {
                tv_equip_time.setText(("用时:" + DataUtil.timeFormat((finishTime - startTime - 28800 * 1000), "HH:mm:ss")));
            }
        }
        long count = DbManager.getDbManager().getDaoSession().getEquipmentDbDao().queryBuilder()
                .where(EquipmentDbDao.Properties.UserId.eq(App.getInstance().getCurrentUser().getUserId())
                        , EquipmentDbDao.Properties.TaskId.eq(taskId)
                        , EquipmentDbDao.Properties.UploadState.eq(true)
                        , EquipmentDbDao.Properties.RoomId.eq(data.getRoom().getRoomId())).count();
        tv_equip_count.setText("进度:" + count + "/" + data.getTaskEquipment().size());
    }

    public void timer() {
        if (startTime != 0 && roomListBean != null && roomListBean.getTaskRoomState() == ConstantInt.ROOM_STATE_2) {
            tv_equip_time.setText(("用时:" + DataUtil.timeFormat((System.currentTimeMillis() - startTime - 28800 * 1000), "HH:mm:ss")));
        }
    }

    public interface OnStartListener {
        void onStart(RoomListBean data, int position);
    }

    public interface OnFinishListener {
        void onFinish(RoomListBean data, int position);
    }
}
