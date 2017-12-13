package com.inspection.application.view.rule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.customer.StandBean;
import com.inspection.application.view.BaseActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准规范
 * Created by pingan on 2017/12/12.
 */

public class RuleActivity extends BaseActivity implements RuleContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_layout;
    private RelativeLayout noDataLayout;
    private List<StandBean.ListBean> mList;
    private RuleContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_rule, R.string.main_work_manager);
        new RulePresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule()), this);
        recyclerView = findViewById(R.id.recycleViewId);
        swipe_layout = findViewById(R.id.swipe_layout);
        noDataLayout = findViewById(R.id.layout_no_data);

        swipe_layout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        swipe_layout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        RVAdapter<StandBean.ListBean> adapter = new RVAdapter<StandBean.ListBean>(recyclerView, mList, R.layout.item_rule) {
            @Override
            public void showData(ViewHolder vHolder, StandBean.ListBean data, int position) {
                ImageView icon = (ImageView) vHolder.getView(R.id.iv_icon);
                TextView name = (TextView) vHolder.getView(R.id.tv_rule_name);
                GlideUtils.ShowImage(getApplicationContext(), data.getIconUrl(), icon, R.drawable.picture_default);
                name.setText(data.getRegulationName());
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(RuleActivity.this, StandInfoActivity.class);
                intent.putExtra(ConstantStr.KEY_TITLE, mList.get(position).getRegulationName());
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getRegulationContent());
                startActivity(intent);
            }
        });
        mPresenter.subscribe();
    }


    @Override
    public void showData(@NonNull StandBean standBean) {
        noDataLayout.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(standBean.getList());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        swipe_layout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipe_layout.setRefreshing(false);
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
    public void setPresenter(RuleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mList.clear();
        recyclerView.getAdapter().notifyDataSetChanged();
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
