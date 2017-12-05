package com.inspection.application.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.app.AppStatusConstant;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.contact.ContactActivity;
import com.inspection.application.view.equipment.EquipListActivity;
import com.inspection.application.view.fault.FaultActivity;
import com.inspection.application.view.inject.InjectActivity;
import com.inspection.application.view.setting.SettingActivity;
import com.inspection.application.view.splash.SplashActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, MainContract.View {

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainPresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule()), this);
        initView();
        checkPermission();
        mPresenter.getNewVersion();
    }

    private void initView() {
        findViewById(R.id.tv_alarm_news).setOnClickListener(this);
        findViewById(R.id.tv_inject_manager).setOnClickListener(this);
        findViewById(R.id.tv_equipment_list).setOnClickListener(this);
        findViewById(R.id.tv_task).setOnClickListener(this);
        findViewById(R.id.tv_fault_submit).setOnClickListener(this);
        findViewById(R.id.tv_count).setOnClickListener(this);
        findViewById(R.id.tv_work_manager).setOnClickListener(this);
        findViewById(R.id.tv_customer).setOnClickListener(this);
        findViewById(R.id.tv_my_setting).setOnClickListener(this);
        findViewById(R.id.tv_news).setOnClickListener(this);
    }

    public static final int REQUEST_EXTERNAL = 10;//内存卡权限

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_EXTERNAL) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setRationale(getString(R.string.need_save_setting))
                        .setTitle(getString(R.string.request_permissions))
                        .setPositiveButton(getString(R.string.sure))
                        .setNegativeButton(getString(R.string.cancel))
                        .setRequestCode(REQUEST_EXTERNAL)
                        .build()
                        .show();
            }
        }
    }

    @AfterPermissionGranted(REQUEST_EXTERNAL)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                    REQUEST_EXTERNAL, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void restartApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_BACK_TO_HOME);
        if (action == AppStatusConstant.ACTION_RESTART_APP) {
            restartApp();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_alarm_news:
                break;
            case R.id.tv_inject_manager:
                startActivity(new Intent(this, InjectActivity.class));
                break;
            case R.id.tv_equipment_list:
                startActivity(new Intent(this, EquipListActivity.class));
                break;
            case R.id.tv_task:
                break;
            case R.id.tv_fault_submit:
                startActivity(new Intent(this, FaultActivity.class));
                break;
            case R.id.tv_count:
                break;
            case R.id.tv_work_manager:
                break;
            case R.id.tv_customer:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.tv_my_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.tv_news:
                break;
        }
    }

    @Override
    protected void onCancel() {
        mPresenter.unSubscribe();
    }

    @Override
    public void showNewVersion(NewVersion newVersion) {

    }

    @Override
    public void showMessage(@Nullable String message) {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
