package com.inspection.application.view.task.work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.task.work.show.ShowTaskWorkFragment;
import com.inspection.application.view.task.work.show.ShowTaskWorkPresenter;
import com.library.adapter.RVAdapter;
import com.library.utils.ActivityUtilsV4;
import com.orhanobut.logger.Logger;

/**
 * 执行task
 * Created by pingan on 2017/12/17.
 */

public class TaskWorkActivity extends BaseActivity implements IViewCreateListener {

    private RoomListBean mRoomListBean;
    private RecyclerView mRecyclerView;
    private boolean mTwoPane;
    private TaskEquipmentBean mTaskEquipmentBean;
    private IEquipmentChangeListener equipmentChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomListBean = getIntent().getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
        long taskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        mRoomListBean.getTaskEquipment().get(0).setChoose(true);
        mTaskEquipmentBean = mRoomListBean.getTaskEquipment().get(0);
        setLayoutAndToolbar(R.layout.activity_task_work, mRoomListBean.getRoom().getRoomName());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View view = navigationView.getHeaderView(0);
            mRecyclerView = view.findViewById(R.id.recycleViewId);
        } else {
            mRecyclerView = findViewById(R.id.recycleViewId);
            mTwoPane = true;
        }
        ShowTaskWorkFragment fragment = (ShowTaskWorkFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ShowTaskWorkFragment.newInstance(taskId, mRoomListBean.getRoom().getRoomId());
        }
        new ShowTaskWorkPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), fragment);
        fragment.setCreateListener(this);
        equipmentChangeListener = fragment;
        ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        findViewById(R.id.tv_up).setOnClickListener(this);
        findViewById(R.id.tv_down).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        RVAdapter<TaskEquipmentBean> adapter = new RVAdapter<TaskEquipmentBean>(mRecyclerView, mRoomListBean.getTaskEquipment(), R.layout.item_task_equipment) {
            @Override
            public void showData(ViewHolder vHolder, TaskEquipmentBean data, int position) {
                LinearLayout ll_layout = (LinearLayout) vHolder.getView(R.id.ll_layout);
                TextView equipmentName = (TextView) vHolder.getView(R.id.tv_equipment_name);
                TextView equipmentState = (TextView) vHolder.getView(R.id.tv_equipment_state);
                equipmentName.setText(data.getEquipment().getEquipmentName());
                if (data.getEquipment().getEquipmentDb() != null && data.getEquipment().getEquipmentDb().getUploadState()) {
                    equipmentState.setText("已上传");
                } else {
                    equipmentState.setText("");
                }
                if (data.isChoose()) {
                    equipmentName.setTextColor(findColorById(R.color.colorWhite));
                    equipmentState.setTextColor(findColorById(R.color.colorWhite));
                    ll_layout.setBackgroundColor(findColorById(R.color.green_btn_normal));
                } else {
                    equipmentName.setTextColor(findColorById(R.color.text_black));
                    equipmentState.setTextColor(findColorById(R.color.text_black));
                    ll_layout.setBackgroundColor(findColorById(R.color.colorWhite));
                }
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
                mTaskEquipmentBean = mRoomListBean.getTaskEquipment().get(position);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                showTaskEquipData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_up:

                break;
            case R.id.tv_down:

                break;
            case R.id.tv_finish:

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private static int SCAN_CODE = 200;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_scan) {
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.putExtra(CaptureActivity.SHOW_LIGHT, true);
            intent.putExtra(CaptureActivity.PLAY_SOUND, true);
            startActivityForResult(intent, SCAN_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (scanId != mTaskEquipmentBean.getEquipment().getEquipmentId()) {
            for (int i = 0; i < mRoomListBean.getTaskEquipment().size(); i++) {
                if (scanId == mRoomListBean.getTaskEquipment().get(i).getEquipment().getEquipmentId()) {
                    mRoomListBean.getTaskEquipment().get(i).setChoose(true);
                    mTaskEquipmentBean = mRoomListBean.getTaskEquipment().get(i);
                } else {
                    mRoomListBean.getTaskEquipment().get(i).setChoose(false);
                }
            }
            mRecyclerView.getAdapter().notifyDataSetChanged();
            showTaskEquipData();
        }
    }

    private void showTaskEquipData() {
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
    }

    @Override
    public void viewCreate() {
        showTaskEquipData();
    }
}
