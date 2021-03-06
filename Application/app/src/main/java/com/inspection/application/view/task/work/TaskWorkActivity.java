package com.inspection.application.view.task.work;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.client.android.CaptureActivity;
import com.inspection.application.BuildConfig;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.fault.FaultActivity;
import com.inspection.application.view.task.work.show.ShowTaskWorkFragment;
import com.inspection.application.view.task.work.show.ShowTaskWorkPresenter;
import com.library.adapter.RVAdapter;
import com.library.utils.ActivityUtilsV4;
import com.orhanobut.logger.Logger;

/**
 * 执行task
 * Created by pingan on 2017/12/17.
 */

public class TaskWorkActivity extends BaseActivity implements IViewCreateListener, TaskWorkContract.View {

    private RoomListBean mRoomListBean;
    private RecyclerView mRecyclerView;
    private boolean mTwoPane;
    private TaskEquipmentBean mTaskEquipmentBean;
    private IEquipmentChangeListener equipmentChangeListener;
    private TaskWorkContract.Presenter mPresenter;
    private int mCurrentPosition = 0;
    private UploadTaskInfo uploadTaskInfo;
    private boolean isUploadingData;
    private long taskId;
    private boolean isScan = TextUtils.equals("T", BuildConfig.TEST);
    private ViewCreateBr viewCreateBr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TaskWorkPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        mRoomListBean = mPresenter.getRoomDataFromCache();
        taskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        uploadTaskInfo = getIntent().getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT_1);
        setLayoutAndToolbar(R.layout.activity_task_work, mRoomListBean.getRoom().getRoomName());
        ImageView floatingActionButton = findViewById(R.id.float_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskWorkActivity.this, FaultActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, taskId);
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG_1, mRoomListBean.getRoom().getRoomId());
                if (mTaskEquipmentBean == null) {
                    showMessage("没有设备需要巡检");
                    return;
                }
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG_2, mTaskEquipmentBean.getEquipment().getEquipmentId());
                intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, mTaskEquipmentBean.getEquipment());
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        TextView tv_no_equipment;
        if (drawer != null) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View view = navigationView.getHeaderView(0);
            drawer.isDrawerOpen(GravityCompat.START);
            mRecyclerView = view.findViewById(R.id.recycleViewId);
            tv_no_equipment = view.findViewById(R.id.tv_no_equipment);
        } else {
            tv_no_equipment = findViewById(R.id.tv_no_equipment);
            mRecyclerView = findViewById(R.id.recycleViewId);
            mTwoPane = true;
        }
        if (mRoomListBean.getTaskEquipment() == null || mRoomListBean.getTaskEquipment().size() == 0) {
            tv_no_equipment.setVisibility(View.VISIBLE);
        } else {
            tv_no_equipment.setVisibility(View.GONE);
            mRoomListBean.getTaskEquipment().get(mCurrentPosition).setChoose(true);
        }
        findViewById(R.id.tv_up).setOnClickListener(this);
        findViewById(R.id.tv_down).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        RVAdapter<TaskEquipmentBean> adapter = new RVAdapter<TaskEquipmentBean>(mRecyclerView, mRoomListBean.getTaskEquipment(), R.layout.item_task_equipment) {
            @Override
            public void showData(ViewHolder vHolder, TaskEquipmentBean data, int position) {
                RadioButton rb = (RadioButton) vHolder.getView(R.id.rb);
                TextView equipmentName = (TextView) vHolder.getView(R.id.tv_equipment_name);
                TextView equipmentState = (TextView) vHolder.getView(R.id.tv_equipment_state);
                equipmentName.setText(data.getEquipment().getEquipmentName());
                boolean isMissing = false;
                if (data.getEquipment().getEquipmentDb() != null && data.getEquipment().getEquipmentDb().getUploadState()) {
                    for (int i = 0; i < data.getDataList().get(0).getDataItemValueList().size(); i++) {
                        if (TextUtils.isEmpty(data.getDataList().get(0).getDataItemValueList().get(i).getDataItem().getValue())) {
                            isMissing = true;
                            break;
                        }
                    }
                    if (isMissing) {
                        equipmentState.setTextColor(findColorById(R.color.color_un_start));
                        equipmentState.setBackground(findDrawById(R.drawable.bg_inspection_month));
                        equipmentState.setText("漏检");
                    } else {
                        equipmentState.setTextColor(findColorById(R.color.color_finish));
                        equipmentState.setBackground(findDrawById(R.drawable.bg_inspection_day));
                        equipmentState.setText("已检");
                    }
                } else {
                    equipmentState.setTextColor(findColorById(R.color.color_working));
                    equipmentState.setBackground(findDrawById(R.drawable.bg_inspection_spect));
                    equipmentState.setText("未检");
                }
                rb.setChecked(data.isChoose());
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mRoomListBean.getTaskEquipment().get(position).isChoose()) {
                    return;
                } else {
                    for (int i = 0; i < mRoomListBean.getTaskEquipment().size(); i++) {
                        if (i != position) {
                            mRoomListBean.getTaskEquipment().get(i).setChoose(false);
                        } else {
                            mRoomListBean.getTaskEquipment().get(i).setChoose(true);
                        }
                    }
                }
                mCurrentPosition = position;
                showTaskEquipData();
            }
        });
        viewCreateBr = new ViewCreateBr();
        IntentFilter filter = new IntentFilter();
        filter.addAction("view_create");
        filter.addAction("equipment_state");
        registerReceiver(viewCreateBr, filter);
        mPresenter.loadEquipmentDb(taskId, mRoomListBean);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_up:
                if (mCurrentPosition == 0) {
                    return;
                } else {
                    --mCurrentPosition;
                }
                showTaskEquipData();
                break;
            case R.id.tv_down:
                if (mCurrentPosition == mRoomListBean.getTaskEquipment().size() - 1) {
                    App.getInstance().showToast("没有了!");
                    return;
                }
                if (isAllDataAdd()) {
                    //取消点击上传功能
//                    uploadData();
                    ++mCurrentPosition;
                    showTaskEquipData();
                } else {
                    new MaterialDialog.Builder(this)
                            .content("你有项目没有填写!是否继续?")
                            .positiveText(R.string.button_ok)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    uploadData();
                                    ++mCurrentPosition;
                                    showTaskEquipData();
                                }
                            })
                            .negativeText(R.string.cancel)
                            .show();
                }
                break;
            case R.id.tv_finish:
                boolean isCanFinish = true;
                for (int i = 0; i < mRoomListBean.getTaskEquipment().size(); i++) {
                    TaskEquipmentBean taskEquipmentBean = mRoomListBean.getTaskEquipment().get(i);
                    for (int j = 0; j < taskEquipmentBean.getDataList().get(0).getDataItemValueList().size(); j++) {
                        if (TextUtils.isEmpty(taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(j).getDataItem().getValue())) {
                            isCanFinish = false;
                            break;
                        }
                    }
                }
                if (isCanFinish) {
                    mPresenter.finishTask(uploadTaskInfo, mRoomListBean);
                } else {
                    App.getInstance().showToast("有遗漏设备");
                }
                break;
        }
    }

    private void uploadData() {
        mTaskEquipmentBean = mRoomListBean.getTaskEquipment().get(mCurrentPosition);
        if (!mTaskEquipmentBean.getEquipment().getEquipmentDb().getUploadState()) {
            isUploadingData = true;
            mPresenter.uploadTaskData(uploadTaskInfo, mRoomListBean, mCurrentPosition);
        }
    }

    private boolean isAllDataAdd() {
        for (int i = 0; i < mTaskEquipmentBean.getDataList().get(0).getDataItemValueList().size(); i++) {
            if (TextUtils.isEmpty(mTaskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItem().getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    private static int SCAN_CODE = 200;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_scan) {
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.putExtra(CaptureActivity.SHOW_LIGHT, false);
            intent.putExtra(CaptureActivity.PLAY_SOUND, true);
            startActivityForResult(intent, SCAN_CODE);
            return true;
        }
        return isScan;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == SCAN_CODE) {
            String result = data.getStringExtra(CaptureActivity.RESULT);
            if (!TextUtils.isEmpty(result)) {
                Logger.d(result);
                try {
                    long scanEquipId = Long.valueOf(result);
                    scanResult(scanEquipId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void scanResult(long scanId) {
        boolean findEquipment = false;
        if (mTaskEquipmentBean == null) return;
        if (scanId != mTaskEquipmentBean.getEquipment().getEquipmentId()) {
            for (int i = 0; i < mRoomListBean.getTaskEquipment().size(); i++) {
                if (scanId == mRoomListBean.getTaskEquipment().get(i).getEquipment().getEquipmentId()) {
                    mCurrentPosition = i;
                    findEquipment = true;
                    break;
                }
            }
            if (findEquipment) {
                mRecyclerView.getAdapter().notifyDataSetChanged();
                showTaskEquipData();
            } else {
                App.getInstance().showToast("没有找到设备");
            }
        }
    }

    private void showTaskEquipData() {
        if (mRoomListBean.getTaskEquipment() == null || mRoomListBean.getTaskEquipment().size() == 0) {
            showMessage("该区域下无点检的设备");
            return;
        }
        for (int i = 0; i < mRoomListBean.getTaskEquipment().size(); i++) {
            if (i != mCurrentPosition) {
                mRoomListBean.getTaskEquipment().get(i).setChoose(false);
            } else {
                mRoomListBean.getTaskEquipment().get(i).setChoose(true);
            }
        }
        mTaskEquipmentBean = mRoomListBean.getTaskEquipment().get(mCurrentPosition);
        getTitleTv().setText(mTaskEquipmentBean.getEquipment().getEquipmentName());
        if (!mTwoPane) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
        if (equipmentChangeListener != null) {
            equipmentChangeListener.equipmentChange(mTaskEquipmentBean, mRoomListBean.getTaskRoomState() != ConstantInt.OPERATION_STATE_3);
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void viewCreate() {
        showTaskEquipData();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer!=null){
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (viewCreateBr != null)
                unregisterReceiver(viewCreateBr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ViewCreateBr extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), "view_create")) {
                viewCreate();
            } else if (TextUtils.equals(intent.getAction(), "equipment_state")) {
                equipmentStateChange();
            }
        }
    }

    @Override
    public void equipmentStateChange() {
        mRecyclerView.getAdapter().notifyItemChanged(mCurrentPosition);
    }

    @Override
    public void uploadTaskDataSuccess() {
        isUploadingData = false;
        if (mRecyclerView.getAdapter() != null) {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void uploadTaskDataFail() {
        isUploadingData = false;
        App.getInstance().showToast("上传失败");
    }

    @Override
    public void finishSuccess() {
        Intent intent = new Intent();
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mRoomListBean.getTaskRoomId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void loadEquipFail() {
        App.getInstance().showToast("读取数据失败");
    }

    @Override
    public void loadEquipSuccess() {
        ShowTaskWorkFragment fragment = (ShowTaskWorkFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ShowTaskWorkFragment.newInstance(taskId, mRoomListBean.getRoom().getRoomId());
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        }
        new ShowTaskWorkPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), fragment);
        fragment.setCreateListener(this);
        equipmentChangeListener = fragment;
        if (mRecyclerView.getAdapter() != null) {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(TaskWorkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onBackPressed() {
        if (isUploadingData) {
            new MaterialDialog.Builder(this)
                    .content("正在上传数据!请稍等")
                    .positiveText(R.string.button_ok)
                    .negativeText(R.string.cancel)
                    .show();
        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if(drawer!=null){
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    super.onBackPressed();
                }
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public void toolBarClick() {
        if (isUploadingData) {
            new MaterialDialog.Builder(this)
                    .content("正在上传数据!请稍等")
                    .positiveText(R.string.button_ok)
                    .negativeText(R.string.cancel)
                    .show();
        } else {
            super.toolBarClick();
        }

    }
}
