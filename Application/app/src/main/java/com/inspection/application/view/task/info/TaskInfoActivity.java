package com.inspection.application.view.task.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.task.work.TaskWorkActivity;
import com.inspection.application.widget.RoomListLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 巡检任务信息
 * Created by pingan on 2017/12/11.
 */

public class TaskInfoActivity extends BaseActivity implements TaskInfoContract.View {

    private long taskId;
    private TaskInfoContract.Presenter mPresenter;
    private List<RoomListLayout> roomListLayouts;
    private List<RoomListBean> mList;
    private Subscription subscription;

    private RelativeLayout noDataLayout;
    private LinearLayout mRoomsLayout;
    private UploadTaskInfo uploadTaskInfo;
    private InspectionDetailBean inspectionBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_task_info, "任务列表");
        taskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (taskId == -1) {
            finish();
            return;
        }
        new TaskInfoPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        noDataLayout = findViewById(R.id.layout_no_data);
        mRoomsLayout = findViewById(R.id.ll_item);
        mList = new ArrayList<>();
        roomListLayouts = new ArrayList<>();
        mPresenter.getInspectionDetailList(taskId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(InspectionDetailBean inspectionBeen) {
        this.inspectionBeen = inspectionBeen;
        noDataLayout.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(inspectionBeen.getRoomList());
        uploadTaskInfo = new UploadTaskInfo(inspectionBeen.getEndTime(), inspectionBeen.getIsManualCreated(), inspectionBeen.getPlanEndTime(), inspectionBeen.getPlanStartTime()
                , inspectionBeen.getStartTime(), inspectionBeen.getTaskId(), inspectionBeen.getTaskName(), inspectionBeen.getTaskState());
        addRoomToLayout();
    }

    @Override
    public void startWork(RoomListBean data) {
        Intent intent = new Intent(TaskInfoActivity.this, TaskWorkActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, taskId);
        intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, data);
        intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT_1, uploadTaskInfo);
        startActivity(intent);
    }

    @Override
    public void setPresenter(TaskInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void addRoomToLayout() {
        mRoomsLayout.removeAllViews();
        roomListLayouts.clear();
        for (int i = 0; i < mList.size(); i++) {
            RoomListLayout roomListLayout = new RoomListLayout(this);
            roomListLayout.setRoomBean(taskId, mList.get(i), i);
            roomListLayout.setListener(onStartListener, onFinishListener);
            roomListLayouts.add(roomListLayout);
            mRoomsLayout.addView(roomListLayout);
        }
        subscription = rx.Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (roomListLayouts.size() > 0) {
                            for (int i = 0; i < roomListLayouts.size(); i++) {
                                roomListLayouts.get(i).timer();
                            }
                        }
                    }
                });
    }

    private RoomListLayout.OnStartListener onStartListener = new RoomListLayout.OnStartListener() {
        @Override
        public void onStart(RoomListBean data, int position) {
            if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_1) {
                mPresenter.startTask(data, taskId);
            } else {
                startWork(data);
            }
        }
    };

    private RoomListLayout.OnFinishListener onFinishListener = new RoomListLayout.OnFinishListener() {
        @Override
        public void onFinish(RoomListBean data, int position) {
            if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_1) {
                showMessage("请开始巡检");
            } else if (data.getTaskRoomState() == ConstantInt.ROOM_STATE_2) {
                mPresenter.checkTaskFinish(data, taskId);
            } else {
                startWork(data);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addRoomToLayout();
    }
}
