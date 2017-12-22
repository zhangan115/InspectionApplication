package com.inspection.application.view.main.news.show;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.inspection.application.app.App;
import com.inspection.application.common.BroadcastAction;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.db.DbManager;
import com.inspection.application.mode.source.news.NewsUtils;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.alarm.AlarmActivity;
import com.inspection.application.view.defect.detail.DefectRecordDetailActivity;
import com.inspection.application.view.task.TaskListActivity;
import com.inspection.application.view.task.data.TaskDataActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

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
    private boolean isRefresh;
    private MessageBR messageBR;

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
        messageBR = new MessageBR();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastAction.NEWS_MESSAGE);
        filter.addAction(BroadcastAction.CLEAN_ALL_DATA);
        if (getActivity() != null) {
            getActivity().registerReceiver(messageBR, filter);
        }
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
        refreshLoadLayout.setOnRefreshListener(this);
        refreshLoadLayout.setOnLoadListener(this);
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
                BGABadgeImageView icon = (BGABadgeImageView) vHolder.getView(R.id.iv_news_icon);
                title.setText(data.getTitle());
                if (data.isMe()) {
                    content.setText(data.getMeContent());
                } else {
                    content.setText(data.getNewsContent());
                }
                time.setText(DataUtil.timeFormat(data.getMessageTime(), null));
                icon.setImageDrawable(findDrawById(NewsUtils.getNewsNotifyDraw(data)));
                if (type == 0 && data.isMe() && !data.getHasRead()) {
                    icon.showCirclePointBadge();
                } else {
                    icon.hiddenBadge();
                }
            }
        };
        recycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int type = mList.get(position).getSmallType();
                if (type == 101 || type == 102 || type == 103) {
                    Intent faultInt = new Intent(getActivity(), DefectRecordDetailActivity.class);
                    faultInt.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getTitle());
                    faultInt.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getTaskId());
                    faultInt.putExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, true);
                    startActivity(faultInt);
                } else if (type == 201 || type == 202 || type == 203) {
                    Intent workInt = new Intent(getActivity(), TaskDataActivity.class);
                    workInt.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getTitle());
                    workInt.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getTaskId());
                    startActivity(workInt);
                } else if (type == 501) {
                    Intent workInt = new Intent(getActivity(), TaskListActivity.class);
                    startActivity(workInt);
                }
                if (!mList.get(position).getHasRead() && mList.get(position).getIsMe()) {
                    mList.get(position).setHasRead(true);
                    recycleView.getAdapter().notifyItemChanged(position);
                    DbManager.getDbManager().getDaoSession().getNewsBeanDao().insertOrReplace(mList.get(position));
                }
            }
        });
        isRefresh = true;
        mPresenter.getMessageList(type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (getActivity() != null) {
                getActivity().unregisterReceiver(messageBR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessageList(List<NewsBean> newsBeans) {
        noDataLayout.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(newsBeans);
        recycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessageListMore(List<NewsBean> newsBeans) {
        mList.addAll(newsBeans);
        recycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        refreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLoadLayout.setRefreshing(false);
        isRefresh = false;
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noMoreData() {
        refreshLoadLayout.setNoMoreData(true);
    }

    @Override
    public void hideLoadingMore() {
        refreshLoadLayout.loadFinish();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMore() {
        if (isRefresh) {
            return;
        }
        mPresenter.getMessageList(type, mList.get(mList.size() - 1).get_id());
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mList.clear();
        recycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getMessageList(type);
    }

    class MessageBR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), BroadcastAction.CLEAN_ALL_DATA)) {
                mList.clear();
                recycleView.getAdapter().notifyDataSetChanged();
                noData();
            } else if (Objects.equals(intent.getAction(), BroadcastAction.NEWS_MESSAGE)) {
                onRefresh();
            }
        }
    }
}
