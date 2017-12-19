package com.inspection.application.view.task.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.customer.EmployeeBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.employee.EmployeeActivity;
import com.inspection.application.view.task.work.TaskWorkActivity;
import com.inspection.application.widget.FlowUserLayout;
import com.inspection.application.widget.RoomListLayout;
import com.library.utils.DisplayUtil;

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
    private LinearLayout ll_add_user;
    private TextView finishTv;
    private UploadTaskInfo uploadTaskInfo;
    private InspectionDetailBean inspectionBeen;
    private String mExecutorUserIds;
    private ArrayList<EmployeeBean> chooseEmployeeBeen;//已经添加的人员

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
        chooseEmployeeBeen = new ArrayList<>();
        noDataLayout = findViewById(R.id.layout_no_data);
        mRoomsLayout = findViewById(R.id.ll_item);
        ll_add_user = findViewById(R.id.ll_add_user);
        finishTv = findViewById(R.id.tv_finish);
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
        setEmployeeToView();
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
    public void finishAll() {
        finish();
    }

    @Override
    public void setPresenter(TaskInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void addRoomToLayout() {
        if (inspectionBeen == null) {
            return;
        }
        mRoomsLayout.removeAllViews();
        roomListLayouts.clear();
        finishTv.setVisibility(View.VISIBLE);
        finishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int finishCount = 0;
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getTaskRoomState() == ConstantInt.OPERATION_STATE_3) {
                        finishCount++;
                    }
                }
                if (finishCount != mList.size()) {
                    showMessage("还有没有完成的点检任务");
                }
                if (ll_add_user.getVisibility() == View.VISIBLE) {
                    StringBuilder sb = new StringBuilder();
                    if (chooseEmployeeBeen != null) {
                        for (int i = 0; i < chooseEmployeeBeen.size(); i++) {
                            sb.append(chooseEmployeeBeen.get(i).getUser().getUserId());
                            if (i != chooseEmployeeBeen.size() - 1) {
                                sb.append(",");
                            }
                        }
                    }
                    mPresenter.finishAllTask(taskId, sb.toString());
                } else {
                    mPresenter.finishAllTask(taskId, mExecutorUserIds);
                }
            }
        });
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

    private void setEmployeeToView() {
        if (inspectionBeen.getExecutorUserList() != null && inspectionBeen.getExecutorUserList().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < inspectionBeen.getExecutorUserList().size(); i++) {
                if (inspectionBeen.getExecutorUserList().get(i).getUser() != null) {
                    sb.append(inspectionBeen.getExecutorUserList().get(i).getUser().getUserId());
                    if (i != inspectionBeen.getExecutorUserList().size() - 1) {
                        sb.append(",");
                    }
                }
            }
            if (!TextUtils.isEmpty(sb.toString())) {
                mExecutorUserIds = sb.toString();
            }
            ll_add_user.setVisibility(View.GONE);
        } else {
            if (chooseEmployeeBeen.size() > 0) {
                EmployeeBean employeeBean = new EmployeeBean();
                employeeBean.setSelect(true);
                employeeBean.setUser(App.getInstance().getCurrentUser());
                chooseEmployeeBeen.add(employeeBean);
            }
            ll_add_user.setVisibility(View.VISIBLE);
        }
        addEmployee();
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


    private void addEmployee() {
        ll_add_user.removeAllViews();
        if (chooseEmployeeBeen == null) {
            return;
        }
        for (int i = 0; i < chooseEmployeeBeen.size(); i++) {
            FlowUserLayout layout = new FlowUserLayout(this);
            layout.setUser(chooseEmployeeBeen.get(i).getUser());
            ll_add_user.addView(layout);
        }
        ImageView addIv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(this, 50), DisplayUtil.dip2px(this, 50));
        addIv.setLayoutParams(params);
        addIv.setImageDrawable(findDrawById(R.drawable.icon_add));
        addIv.setOnClickListener(addEmployeeClickListener);
        ll_add_user.addView(addIv);
    }

    private int REQUEST_CODE = 200;

    private View.OnClickListener addEmployeeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent addEmployeeIntent = new Intent(TaskInfoActivity.this, EmployeeActivity.class);
            addEmployeeIntent.putExtra(ConstantStr.KEY_BUNDLE_LIST, chooseEmployeeBeen);
            startActivityForResult(addEmployeeIntent, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<EmployeeBean> been = data.getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST_1);
            if (been != null && been.size() != 0) {
                chooseEmployeeBeen.clear();
                chooseEmployeeBeen.addAll(been);
            }
            addEmployee();
        }
    }

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
