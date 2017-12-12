package com.inspection.application.view.rule;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inspection.application.R;
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

public class RuleActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe_layout;
    private List<StandBean.ListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_rule, R.string.main_work_manager);
        recyclerView = findViewById(R.id.recycleViewId);
        swipe_layout = findViewById(R.id.swipe_layout);
        swipe_layout.setColorSchemeColors(findColorById(R.color.colorPrimary));
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

            }
        });
    }


}
