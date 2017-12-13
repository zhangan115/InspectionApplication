package com.inspection.application.view.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inspection.application.R;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.contact.ContactActivity;
import com.inspection.application.view.equipment.EquipListActivity;
import com.inspection.application.view.fault.FaultActivity;
import com.inspection.application.view.inject.InjectActivity;
import com.inspection.application.view.main.mine.MineFragment;
import com.inspection.application.view.rule.RuleActivity;
import com.inspection.application.view.setting.SettingActivity;
import com.inspection.application.view.task.TaskListActivity;

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
        rootView.findViewById(R.id.tv_fault_submit).setOnClickListener(this);
        rootView.findViewById(R.id.tv_count).setOnClickListener(this);
        rootView.findViewById(R.id.tv_work_manager).setOnClickListener(this);
        rootView.findViewById(R.id.tv_customer).setOnClickListener(this);
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
        }
    }
}
