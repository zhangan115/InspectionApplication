package com.inspection.application.view.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.contact.ContactActivity;
import com.inspection.application.view.defect.DefectRecordActivity;
import com.inspection.application.view.equipment.EquipListActivity;
import com.inspection.application.view.fault.FaultActivity;
import com.inspection.application.view.inject.InjectActivity;
import com.inspection.application.view.login.LoginActivity;
import com.inspection.application.view.mytask.MyTaskActivity;
import com.inspection.application.view.rule.RuleActivity;
import com.inspection.application.view.task.TaskListActivity;
import com.library.utils.GlideUtils;

/**
 * 首页
 * Created by pingan on 2017/12/8.
 */

public class HomeFragment extends MvpFragment implements View.OnClickListener {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.findViewById(R.id.tv_alarm_news).setOnClickListener(this);
        rootView.findViewById(R.id.tv_inject_manager).setOnClickListener(this);
        rootView.findViewById(R.id.tv_equipment_list).setOnClickListener(this);
        rootView.findViewById(R.id.tv_task).setOnClickListener(this);
        rootView.findViewById(R.id.tv_my_task).setOnClickListener(this);
        rootView.findViewById(R.id.tv_fault_submit).setOnClickListener(this);
        rootView.findViewById(R.id.tv_count).setOnClickListener(this);
        rootView.findViewById(R.id.tv_work_manager).setOnClickListener(this);
        rootView.findViewById(R.id.tv_customer).setOnClickListener(this);
        rootView.findViewById(R.id.tv_fault_record).setOnClickListener(this);
        if (App.getInstance().getCurrentUser() != null) {
            ImageView userPhoto = rootView.findViewById(R.id.iv_user_photo);
            GlideUtils.ShowCircleImage(getActivity(), App.getInstance().getCurrentUser().getPortraitUrl(), userPhoto, R.drawable.icon_monitor);
            TextView userName = rootView.findViewById(R.id.tv_user_name);
            TextView userDept = rootView.findViewById(R.id.tv_user_dept);
            userName.setText(String.format("欢迎,%s", App.getInstance().getCurrentUser().getRealName()));
            userDept.setText(App.getInstance().getCurrentUser().getUserRoleNames());
        }
        rootView.findViewById(R.id.tv_quit).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (getActivity() == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_alarm_news:
                break;
            case R.id.tv_inject_manager:
                startActivity(new Intent(getActivity(), InjectActivity.class));
                break;
            case R.id.tv_equipment_list:
                startActivity(new Intent(getActivity(), EquipListActivity.class));
                break;
            case R.id.tv_task:
                startActivity(new Intent(getActivity(), TaskListActivity.class));
                break;
            case R.id.tv_fault_submit:
                startActivity(new Intent(getActivity(), FaultActivity.class));
                break;
            case R.id.tv_count:
                break;
            case R.id.tv_work_manager:
                startActivity(new Intent(getActivity(), RuleActivity.class));
                break;
            case R.id.tv_customer:
                startActivity(new Intent(getActivity(), ContactActivity.class));
                break;
            case R.id.tv_fault_record:
                startActivity(new Intent(getActivity(), DefectRecordActivity.class));
                break;
            case R.id.tv_quit:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.tv_my_task:
                startActivity(new Intent(getActivity(), MyTaskActivity.class));
                break;
        }
    }
}
