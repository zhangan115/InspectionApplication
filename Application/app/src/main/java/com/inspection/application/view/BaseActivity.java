package com.inspection.application.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.AppStatusConstant;
import com.inspection.application.app.AppStatusManager;
import com.inspection.application.view.main.MainActivity;
import com.inspection.application.base.AbsBaseActivity;


/**
 * BaseActivity 页面基类
 * 类描述：页面基类
 *
 * @author zhuzhu
 * @version v0.1
 * @since 2015-04-13
 */
public abstract class BaseActivity extends AbsBaseActivity implements OnClickListener, DialogInterface.OnCancelListener {
    protected boolean isDestroy = false;
    protected MaterialDialog loadingDialog = null;
    private TextView mTitleTv;
    private OnToolbarClickListener onToolbarClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusConstant.STATUS_FORCE_KILLED) {
            restartApp();
        }
    }

    public void setOnToolbarClickListener(OnToolbarClickListener onToolbarClickListener) {
        this.onToolbarClickListener = onToolbarClickListener;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        onCancel();
    }

    interface OnToolbarClickListener {
        void onToolBarBackClick();
    }

    /**
     * 设置当前布局资源(不包含Toolbar)
     *
     * @param layoutId 布局资源
     */
    public void setLayoutAndToolbar(int layoutId) {
        this.setLayoutAndToolbar(layoutId, -1);
    }

    /**
     * 设置界面资源与toolbar配置
     *
     * @param layoutId   布局资源
     * @param titleResId 标题
     */
    public void setLayoutAndToolbar(int layoutId, int titleResId) {
        if (titleResId != -1) {
            this.setLayoutAndToolbar(layoutId, titleResId, true);
        } else {
            this.setLayoutAndToolbar(layoutId, titleResId, false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);
    }

    /**
     * 设置界面资源与toolbar配置
     *
     * @param layoutId   布局资源
     * @param titleResId 标题
     */
    public void setLayoutAndToolbar(int layoutId, String titleResId) {
        if (TextUtils.isEmpty(titleResId)) {
            this.setContentView(layoutId);
            return;
        }
        this.setLayoutAndToolbar(layoutId, titleResId, true);
    }

    /**
     * 设置界面资源与toolbar配置
     *
     * @param layoutId    布局资源
     * @param titleResId  标题
     * @param haveToolbar 是否包含toolbar 默认不包含
     */
    public void setLayoutAndToolbar(int layoutId, int titleResId, boolean haveToolbar) {
        this.setContentView(layoutId);
        if (haveToolbar) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar == null) {
                return;
            }
            if (mTitleTv == null) {
                mTitleTv = toolbar.findViewById(R.id.titleId);
            }
            mTitleTv.setText(titleResId);
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            assert ab != null;
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toolBarClick();
                }
            });
        }
    }

    /**
     * 设置界面资源与toolbar配置
     *
     * @param layoutId    布局资源
     * @param titleResId  标题
     * @param haveToolbar 是否包含toolbar 默认不包含
     */
    public void setLayoutAndToolbar(int layoutId, String titleResId, boolean haveToolbar) {
        this.setContentView(layoutId);
        if (haveToolbar) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar == null) {
                return;
            }
            mTitleTv = toolbar.findViewById(R.id.titleId);
            if (mTitleTv == null) {
                mTitleTv = toolbar.findViewById(R.id.titleId);
            }
            mTitleTv.setText(titleResId);
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowTitleEnabled(false);
            }
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toolBarClick();
                }
            });
        }
    }

    public Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            return toolbar;
        }
        return null;
    }

    public void toolBarClick() {
        if (onToolbarClickListener != null) {
            onToolbarClickListener.onToolBarBackClick();
        }
        finish();
    }

    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        onCancel();
    }


    @Override
    public void onClick(View v) {

    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    public void hideProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.hide();
        }
    }

    public int findColorById(int color) {
        return getResources().getColor(color);
    }

    public String findStrById(int str) {
        return getResources().getString(str);
    }

    public Drawable findDrawById(int draw) {
        return getResources().getDrawable(draw);
    }

    public Dialog showProgressDialog() {
        return showProgressDialog("加载中...");
    }

    public MaterialDialog showProgressDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = new MaterialDialog.Builder(this)
                    .content(message)
                    .progress(true, 0)
                    .cancelListener(this)
                    .progressIndeterminateStyle(false).build();
        } else {
            loadingDialog.setContent(message);
        }
        loadingDialog.show();
        return loadingDialog;
    }


    protected void onCancel() {

    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    protected void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_RESTART_APP);
        startActivity(intent);
    }

}