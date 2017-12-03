package com.inspection.application.view.contact;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtils;
import com.library.utils.ActivityUtilsV4;


/**
 * 联系人界面
 * Created by zhangan on 2017/9/5.
 */

public class ContactActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container_toolbar, "联系人");
        ContactFragment fragment = (ContactFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ContactFragment.newInstance();
        }
        new ContactPresenter(Injection.provideCustomerRepository(), fragment);
        ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
    }
}
