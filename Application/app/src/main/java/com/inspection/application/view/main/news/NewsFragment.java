package com.inspection.application.view.main.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.view.MvpFragment;
import com.library.widget.ExpendRecycleView;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * 代办消息
 * Created by pingan on 2017/12/8.
 */

public class NewsFragment extends MvpFragment<NewsContract.Presenter> implements NewsContract.View, View.OnClickListener {

    private BGABadgeImageView mFaultBGView;
    private BGABadgeImageView mWorkBGView;
    private RelativeLayout noDataLayout;
    private ExpendRecycleView mExpendRecycleView;

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
        mExpendRecycleView = rootView.findViewById(R.id.recycleViewId);
        mFaultBGView = rootView.findViewById(R.id.bg_view_fault);
        mWorkBGView = rootView.findViewById(R.id.bg_view_work);
        noDataLayout = rootView.findViewById(R.id.layout_no_data);
        mExpendRecycleView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        return rootView;
    }

    @Override
    public void showMessageList() {
        noDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

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
}
