package com.inspection.application.view.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.bean.customer.EmployeeBean;
import com.inspection.application.view.MvpFragment;
import com.library.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 * Created by zhangan on 2017/9/5.
 */

public class ContactFragment extends MvpFragment<ContactContract.Presenter> implements ContactContract.View {

    private PinnedHeaderExpandableListView mListView;
    private ContactListAdapter mContactAdapter;
    private RelativeLayout noDataLayout;
    private boolean isChooseUser;//是否选择人员
    private ArrayList<EmployeeBean> chooseEmployeeBeans;

    public static ContactFragment newInstance(boolean isChooseUser, ArrayList<EmployeeBean> employeeBeans) {
        Bundle args = new Bundle();
        args.putBoolean(ConstantStr.KEY_BUNDLE_BOOLEAN, isChooseUser);
        args.putParcelableArrayList(ConstantStr.KEY_BUNDLE_LIST, employeeBeans);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChooseUser = getArguments().getBoolean(ConstantStr.KEY_BUNDLE_BOOLEAN);
            chooseEmployeeBeans = getArguments().getParcelableArrayList(ConstantStr.KEY_BUNDLE_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fmg_employee, container, false);
        mListView = rootView.findViewById(R.id.expandableListView);
        mContactAdapter = new ContactListAdapter(getActivity(), R.layout.item_employee_group, R.layout.item_employee_child_call);
        noDataLayout = rootView.findViewById(R.id.layout_no_data);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getEmployee();
    }

    @Override
    public void showData(List<DepartmentBean> list) {
        mContactAdapter.setData(list);
        mListView.setAdapter(mContactAdapter);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@Nullable String message) {
        getApp().showToast(message);
    }

    @Override
    public void setPresenter(ContactContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
