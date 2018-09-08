package com.inspection.application.view.main.news;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.main.news.show.NewsPresenter;
import com.inspection.application.view.main.news.show.ShowMessageFragment;
import com.library.utils.ActivityUtilsV4;

/**
 * 缺陷消息
 * Created by pingan on 2017/12/21.
 */

public class WorkNewsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container_toolbar, getString(R.string.str_work_news));
        ShowMessageFragment fragment = (ShowMessageFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ShowMessageFragment.newInstance(2);
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        }
        new NewsPresenter(Injection.getIntent().provideNewsRepository(App.getInstance().getModule()), fragment);
    }
}
