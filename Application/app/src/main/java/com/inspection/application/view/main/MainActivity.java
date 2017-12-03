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
        new MainPresenter(Injection.provideApplicationRepository(App.getInstance().getModule()), this);
        checkPermission();
        mPresenter.getNewVersion();
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
