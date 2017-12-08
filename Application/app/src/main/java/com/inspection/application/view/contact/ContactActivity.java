package com.inspection.application.view.contact;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.customer.EmployeeBean;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtils;
import com.library.utils.ActivityUtilsV4;

import java.util.ArrayList;


/**
 * 联系人界面
 * Created by zhangan on 2017/9/5.
 */

public class ContactActivity extends BaseActivity {

    private boolean isChooseUser;//是否选择人员

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container_toolbar, "联系人");
        isChooseUser = getIntent().getBooleanExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, false);
        ArrayList<EmployeeBean> employeeBeans = getIntent().getParcelableArrayListExtra(ConstantStr.KEY_BUNDLE_LIST);
        ContactFragment fragment = (ContactFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ContactFragment.newInstance(isChooseUser, employeeBeans);
        }
        new ContactPresenter(Injection.getIntent().provideCustomerRepository(), fragment);
        ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
    }

}
