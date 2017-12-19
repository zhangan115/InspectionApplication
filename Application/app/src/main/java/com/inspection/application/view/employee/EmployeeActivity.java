package com.inspection.application.view.employee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.bean.customer.EmployeeBean;
import com.inspection.application.view.BaseActivity;
import com.library.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择人员
 * Created by pingan on 2017/12/19.
 */

public class EmployeeActivity extends BaseActivity implements EmployeeContract.View {

    private PinnedHeaderExpandableListView mListView;
    private EmployeeAdapter mEmployeeAdapter;
    private RelativeLayout noDataLayout;
    private ArrayList<EmployeeBean> chooseEmployeeBeans;//选中的人员
    private EmployeeContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_employee, getString(R.string.str_choose_employee));
        new EmployeePresenter(Injection.getIntent().provideCustomerRepository(), this);
        mListView = findViewById(R.id.expandableListView);
        noDataLayout = findViewById(R.id.layout_no_data);
        chooseEmployeeBeans = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST);
        mEmployeeAdapter = new EmployeeAdapter(this, R.layout.item_employee_group, R.layout.item_employee_choose);
        mPresenter.getEmployee();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sure, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sure) {
            Intent intent = new Intent();
            if (mEmployeeAdapter != null && mEmployeeAdapter.getSelectEmployee() != null) {
                ArrayList<EmployeeBean> bean = mEmployeeAdapter.getSelectEmployee();
                intent.putExtra(ConstantStr.KEY_BUNDLE_LIST_1, bean);
                setResult(Activity.RESULT_OK, intent);
            } else {
                setResult(Activity.RESULT_CANCELED);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showData(List<DepartmentBean> departmentBeans) {
        for (int i = 0; i < departmentBeans.size(); i++) {
            if (departmentBeans.get(i).getUserList() != null) {
                for (int j = 0; j < departmentBeans.get(i).getUserList().size(); j++) {
                    for (int k = 0; k < chooseEmployeeBeans.size(); k++) {
                        if (departmentBeans.get(i).getUserList().get(j).getUser().getUserId() == chooseEmployeeBeans.get(k).getUser().getUserId()) {
                            departmentBeans.get(i).getUserList().get(j).setSelect(true);
                            break;
                        }
                    }
                }
            }
        }
        noDataLayout.setVisibility(View.GONE);
        mEmployeeAdapter.setData(departmentBeans);
        mListView.setAdapter(mEmployeeAdapter);
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
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(EmployeeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
