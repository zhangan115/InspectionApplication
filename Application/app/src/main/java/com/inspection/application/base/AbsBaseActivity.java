package com.inspection.application.base;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



/**
 * AbsBaseActivity 页面基类
 * 类描述：页面基类
 *
 * @author zhuzhu
 * @version v0.1
 * @since 2015-04-13
 */
public abstract class AbsBaseActivity extends AppCompatActivity  {

    public final String TAG = this.getClass().getSimpleName();//TAG
    protected boolean showTag = true;//显示TAG
    protected AbsBaseApp mApp = null;//当前APP

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (showTag) {
            Log.d(TAG, "onCreate");
        }
        if (getApplication() instanceof AbsBaseApp) {
            mApp = (AbsBaseApp) getApplication();
        }
        if (mApp != null) {
            mApp.openActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (showTag) {
            Log.d(TAG, "onDestroy");
        }
        if (mApp != null) {
            mApp.closeActivity(this);
        }
    }

    /**
     * 状态栏完全透明
     */
    public void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 修改状态栏icon 颜色
     *
     * @param bDark 是否将icon 颜色变为灰色
     */
    public void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
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

}