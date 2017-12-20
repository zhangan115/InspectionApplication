package com.inspection.application.view.main.news.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.source.news.NewsUtils;
import com.inspection.application.view.MvpFragment;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pingan on 2017/12/20.
 */

public class ShowMessageFragment extends MvpFragment<NewsContract.Presenter> implements NewsContract.View, RecycleRefreshLoadLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private int type;
    private static final String NEWS_TYPE = "NEWS_TYPE";
    private ExpendRecycleView recycleView;
    private RecycleRefreshLoadLayout refreshLoadLayout;
    private List<NewsBean> mList;
    private RelativeLayout noDataLayout;

    public static ShowMessageFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(NEWS_TYPE, type);
        ShowMessageFragment fragment = new ShowMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        type = getArguments().getInt(NEWS_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_message_lsit, container, false);
        recycleView = rootView.findViewById(R.id.recycleViewId);
        noDataLayout = rootView.findViewById(R.id.layout_no_data);
        refreshLoadLayout = rootView.findViewById(R.id.refresh_layout);
        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        refreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        refreshLoadLayout.setOnLoadListener(this);
        refreshLoadLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = new ArrayList<>();
        RVAdapter<NewsBean> adapter = new RVAdapter<NewsBean>(recycleView, mList, R.layout.item_news) {
            @Override
            public void showData(ViewHolder vHolder, NewsBean data, int position) {
                TextView title = (TextView) vHolder.getView(R.id.tv_title);
                TextView content = (TextView) vHolder.getView(R.id.tv_content);
                TextView time = (TextView) vHolder.getView(R.id.tv_time);
                ImageView icon = (ImageView) vHolder.getView(R.id.iv_news_icon);
                title.setText(data.getTitle());
                if (data.isMe()) {
                    content.setText(data.getMeContent());
                } else {
                    content.setText(data.getNewsContent());
                }
                time.setText(DataUtil.timeFormat(data.getMessageTime(), null));
                icon.setImageDrawable(findDrawById(NewsUtils.getNewsNotifyDraw(data)));
            }
        };
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mPresenter.getMessageList(type);
    }

    @Override
    public void showMessageList(List<NewsBean> newsBeans) {
        mList.clear();
        mList.addAll(newsBeans);

    }

    @Override
    public void showLoading() {
        refreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
