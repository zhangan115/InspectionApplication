package com.inspection.application.view.defect.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.fault.FaultDetail;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.widget.FlowsItemLayout;
import com.inspection.application.widget.ShowImageLayout;

import java.util.List;

/**
 * 故障记录详情
 * Created by pingan on 2017/12/13.
 */

public class DefectRecordDetailActivity extends BaseActivity implements DefectRecordDetailContract.View {

    private ScrollView scrollView;
    private RelativeLayout noDataLayout;

    private DefectRecordDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        long id = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (id == -1 || TextUtils.isEmpty(title)) {
            finish();
        }
        setLayoutAndToolbar(R.layout.activity_defect_detail, title);
        new DefectRecordDetailPresenter(Injection.getIntent().provideFaultRepository(App.getInstance().getModule()), this);
        scrollView = findViewById(R.id.scrollView);
        noDataLayout = findViewById(R.id.layout_no_data);
        mPresenter.getFaultDetail(id);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        scrollView.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(FaultDetail faultDetail) {
        if (faultDetail.getFaultFlows().size() == 0) {
            noData();
            return;
        }
        scrollView.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        if (faultDetail.getEquipment() != null && faultDetail.getEquipment().getRoom() != null) {
            ((TextView) (findViewById(R.id.tv_room_name))).setText(faultDetail.getEquipment().getRoom().getRoomName());
        }
        ((TextView) (findViewById(R.id.tv_fault_type))).setText(String.format("缺陷等级:%s", App.getInstance().getMapOption().get("2").get(String.valueOf(faultDetail.getFaultType()))));
        ((TextView) (findViewById(R.id.tv_report_user_name))).setText(String.format("上报人:%s", faultDetail.getUser().getRealName()));
        ((TextView) (findViewById(R.id.tv_report_time))).setText(String.format("上报时间:%s", faultDetail.getCreateTime()));
        ((TextView) (findViewById(R.id.tv_fault_des))).setText(faultDetail.getFaultDescript());
        ShowImageLayout showImageLayout = findViewById(R.id.show_image_layout);
        String[] images = new String[faultDetail.getFaultPics().size()];
        for (int i = 0; i < faultDetail.getFaultPics().size(); i++) {
            images[i] = faultDetail.getFaultPics().get(i).getPicUrl();
        }
        showImageLayout.showImage(images);
        addViewToLL(faultDetail.getFaultFlows());
    }

    private void addViewToLL(List<FaultDetail.FaultFlowsBean> faultFlows) {
        LinearLayout linearLayout = findViewById(R.id.container);
        for (int i = 0; i < faultFlows.size(); i++) {
            FlowsItemLayout layout = new FlowsItemLayout(getApplicationContext());
            layout.setContent(faultFlows.get(i));
            linearLayout.addView(layout);
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(DefectRecordDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
