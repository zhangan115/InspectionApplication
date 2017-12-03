package com.inspection.application.view.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.inspection.application.R;
import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.view.MvpFragment;
import com.library.widget.PinnedHeaderExpandableListView;

import java.util.List;

/**
 * 联系人
 * Created by zhangan on 2017/9/5.
 */

public class ContactFragment extends MvpFragment<ContactContract.Presenter> implements ContactContract.View {

    private PinnedHeaderExpandableListView mListView;
    private ContactListAdapter mContactAdapter;
    private RelativeLayout noDataLayout;

    public static ContactFragment newInstance() {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
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
        if (mPresenter != null) {
            mPresenter.getEmployee();
        }
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
