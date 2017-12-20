package com.inspection.application.view.main.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.main.news.show.NewsContract;
import com.inspection.application.view.main.news.show.NewsPresenter;
import com.inspection.application.view.main.news.show.ShowMessageFragment;
import com.library.utils.DataUtil;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * 代办消息
 * Created by pingan on 2017/12/8.
 */

public class NewsFragment extends MvpFragment<NewsContract.Presenter> implements View.OnClickListener {

    private BGABadgeImageView mFaultBGView;
    private BGABadgeImageView mWorkBGView;
    private TextView newsTimeTv;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        rootView.findViewById(R.id.ll_fault).setOnClickListener(this);
        rootView.findViewById(R.id.ll_work).setOnClickListener(this);
        mFaultBGView = rootView.findViewById(R.id.bg_view_fault);
        mWorkBGView = rootView.findViewById(R.id.bg_view_work);
        newsTimeTv = rootView.findViewById(R.id.tv_news_time);
        TextView titleTv = rootView.findViewById(R.id.titleId);
        titleTv.setText(findStrById(R.string.str_first_nav_2));
        ShowMessageFragment fragment = (ShowMessageFragment) getChildFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ShowMessageFragment.newInstance(0);
        }
        new NewsPresenter(Injection.getIntent().provideNewsRepository(App.getInstance().getModule()), fragment);
        getChildFragmentManager().beginTransaction().add(R.id.frame_container, fragment).commit();
        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fault:

                break;
            case R.id.ll_work:

                break;
        }
    }

    private void setTimeToTv(long time) {
        newsTimeTv.setText(String.format(getString(R.string.get_last_news_time), DataUtil.timeFormat(time, "HH:mm")));
    }
}
