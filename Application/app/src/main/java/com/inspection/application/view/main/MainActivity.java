package com.inspection.application.view.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.app.AppStatusConstant;
import com.inspection.application.common.BroadcastAction;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.utils.DownloadAppUtils;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.main.home.HomeFragment;
import com.inspection.application.view.main.mine.MineFragment;
import com.inspection.application.view.main.mine.MinePresenter;
import com.inspection.application.view.main.news.NewsFragment;
import com.inspection.application.view.main.news.show.NewsPresenter;
import com.inspection.application.view.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, MainContract.View {

    private MainContract.Presenter mPresenter;
    private AHBottomNavigation bottomNavigation;
    private int selectPosition = 0;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainPresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule())
                , Injection.getIntent().provideNewsRepository(App.getInstance().getModule()), this);
        initView();
        checkPermission();
        mPresenter.unSubscribe();
        mPresenter.getNewVersion();
        mPresenter.getMessage();
        mPresenter.getUnReadCount();
    }

    private void initView() {
        mFragments = getFragments();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.str_first_nav_1, R.drawable.icon_home, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.str_first_nav_2, R.drawable.icon_remind_g, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.str_first_nav_3, R.drawable.icon_mine_g, R.color.colorPrimary);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setTitleTextSizeInSp(14f, 14f);
        bottomNavigation.setBackgroundColor(findColorById(R.color.colorWhite));
        bottomNavigation.setDefaultBackgroundColor(findColorById(R.color.colorWhite));
        bottomNavigation.setForceTint(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(findColorById(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(findColorById(R.color.text_gray_999));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (selectPosition != position) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.hide(mFragments.get(selectPosition));
                    if (mFragments.get(position).isAdded()) {
                        ft.show(mFragments.get(position));
                    } else {
                        ft.add(R.id.frame_container, mFragments.get(position), "tag_" + position);
                    }
                    selectPosition = position;
                    ft.commit();
                    return true;
                }
                return false;
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame_container, mFragments.get(selectPosition), "tag_" + selectPosition);
        bottomNavigation.setCurrentItem(selectPosition);
        transaction.commit();
    }

    public ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("tag_0");
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("tag_1");
        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();
        }
        MineFragment mineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("tag_3");
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
        }
        new MinePresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule()), mineFragment);
        fragments.add(homeFragment);
        fragments.add(newsFragment);
        fragments.add(mineFragment);
        return fragments;
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
        if (!EasyPermissions.hasPermissions(this.getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, getString(R.string.request_permissions),
                    REQUEST_EXTERNAL, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
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
    protected void onCancel() {
        mPresenter.unSubscribe();
    }

    @Override
    public void showNewVersion(final NewVersion newVersion) {
        if (!EasyPermissions.hasPermissions(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            new AppSettingsDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.request_permissions))
                    .setRationale(getString(R.string.need_save_setting))
                    .setPositiveButton(getString(R.string.sure))
                    .setNegativeButton(getString(R.string.cancel))
                    .setRequestCode(REQUEST_EXTERNAL)
                    .build()
                    .show();
            return;
        }
        new MaterialDialog.Builder(this)
                .content(newVersion.getNote())
                .negativeText("取消")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        DownloadAppUtils.DownLoad(getApplicationContext(), newVersion.getUrl(), "点检");
                    }
                })
                .show();

    }

    @Override
    public void showMessage(@Nullable String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showUnReadCount(long count) {
        if (bottomNavigation == null) {
            return;
        }
        if (count == 0) {
            bottomNavigation.setNotification("", 1);
        } else if (count > 99) {
            bottomNavigation.setNotificationBackgroundColor(findColorById(R.color.text_red));
            bottomNavigation.setNotification("99+" + "", 1);
        } else {
            bottomNavigation.setNotificationBackgroundColor(findColorById(R.color.text_red));
            bottomNavigation.setNotification(count + "", 1);
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    class MessageBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), BroadcastAction.CLEAN_ALL_DATA)) {
                mPresenter.getUnReadCount();
            } else if (Objects.equals(intent.getAction(), BroadcastAction.NEWS_MESSAGE)) {
                mPresenter.getUnReadCount();
            }
        }
    }
}
