package com.inspection.application.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 判断前台是否有activity运行
 * Created by zhangan on 2017/9/13.
 */

class AppStatusTracker implements Application.ActivityLifecycleCallbacks {

    private Application application;
    private boolean isForGround;
    private int activeCount;


    AppStatusTracker(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    public boolean isForGround() {
        return isForGround;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activeCount++;
        isForGround = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            isForGround = false;
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
