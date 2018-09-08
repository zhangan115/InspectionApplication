package com.inspection.application.view.main.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.BroadcastAction;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.main.news.show.NewsContract;
import com.inspection.application.view.main.news.show.NewsPresenter;
import com.inspection.application.view.main.news.show.ShowMessageFragment;
import com.library.utils.DataUtil;
import com.library.utils.SPHelper;

import java.util.List;
import java.util.Objects;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * 代办消息
 * Created by pingan on 2017/12/8.
 */

public class NewsFragment extends MvpFragment<NewsContract.Presenter> implements View.OnClickListener {

    private BGABadgeImageView mFaultBGView;
    private BGABadgeImageView mWorkBGView;
    private TextView newsTimeTv;
    private MessageBR messageBR;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageBR = new MessageBR();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastAction.NEWS_MESSAGE);
        filter.addAction(BroadcastAction.NEWS_MESSAGE_TIME);
        filter.addAction(BroadcastAction.CLEAN_ALL_DATA);
        if (getActivity() == null) {
            return;
        }
        getActivity().registerReceiver(messageBR, filter);
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
        setTimeToTv(System.currentTimeMillis());
        TextView titleTv = rootView.findViewById(R.id.titleId);
        titleTv.setText(findStrById(R.string.str_first_nav_2));
        updateUi();
        ShowMessageFragment fragment = (ShowMessageFragment) getChildFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = ShowMessageFragment.newInstance(0);
            getChildFragmentManager().beginTransaction().add(R.id.frame_container, fragment).commit();
        }
        new NewsPresenter(Injection.getIntent().provideNewsRepository(App.getInstance().getModule()), fragment);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() == null) {
            return;
        }
        try {
            getActivity().unregisterReceiver(messageBR);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fault:
                SPHelper.write(getActivity(), ConstantStr.USER_DATA, ConstantStr.NEWS_TYPE_ALARM, false);
                startActivity(new Intent(getActivity(), FaultNewsActivity.class));
                mFaultBGView.hiddenBadge();
                break;
            case R.id.ll_work:
                SPHelper.write(getActivity(), ConstantStr.USER_DATA, ConstantStr.NEWS_TYPE_WORK, false);
                startActivity(new Intent(getActivity(), WorkNewsActivity.class));
                mWorkBGView.hiddenBadge();
                break;
        }
    }

    class MessageBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), BroadcastAction.NEWS_MESSAGE) || Objects.equals(intent.getAction(), BroadcastAction.CLEAN_ALL_DATA)) {
                updateUi();
            } else if (Objects.equals(intent.getAction(), BroadcastAction.NEWS_MESSAGE_TIME)) {
                setTimeToTv(System.currentTimeMillis());
            }
        }
    }

    private void updateUi() {
        if (SPHelper.readBoolean(getActivity(), ConstantStr.USER_DATA, ConstantStr.NEWS_TYPE_ALARM, false)) {
            mFaultBGView.showCirclePointBadge();
        } else {
            mFaultBGView.hiddenBadge();
        }
        if (SPHelper.readBoolean(getActivity(), ConstantStr.USER_DATA, ConstantStr.NEWS_TYPE_WORK, false)) {
            mWorkBGView.showCirclePointBadge();
        } else {
            mWorkBGView.hiddenBadge();
        }
    }

    private void setTimeToTv(long time) {
        if (newsTimeTv != null)
            newsTimeTv.setText(String.format(getString(R.string.get_last_news_time), DataUtil.timeFormat(time, "HH:mm")));
    }
}
