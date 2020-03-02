package com.inspection.application.view.defect.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.library.utils.DataUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void showActionLoading() {
        showProgressDialog("提交中...");
    }

    @Override
    public void hideActionLoading() {
        hideProgressDialog();
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
        ((TextView) (findViewById(R.id.tv_report_time))).setText(String.format("上报时间:%s", DataUtil.timeFormat(faultDetail.getCreateTime(), null)));
        if (faultDetail.getCloseTime() != 0) {
            ((TextView) (findViewById(R.id.tv_remove_time))).setText(String.format("消除时间:%s", DataUtil.timeFormat(faultDetail.getCloseTime(), null)));
        } else {
            ((TextView) (findViewById(R.id.tv_remove_time))).setText(String.format("消除时间:%s", "-"));
        }
        ((TextView) (findViewById(R.id.tv_fault_des))).setText(faultDetail.getFaultDescript());
        ((TextView) (findViewById(R.id.tv_fault_state))).setText(App.getInstance().getMapOption().get("9").get(String.valueOf(faultDetail.getFaultState())));
        ShowImageLayout showImageLayout = findViewById(R.id.show_image_layout);
        String[] images = new String[faultDetail.getFaultPics().size()];
        for (int i = 0; i < faultDetail.getFaultPics().size(); i++) {
            images[i] = faultDetail.getFaultPics().get(i).getPicUrl();
        }
        showImageLayout.showImage(images);
        if (getIntent().getBooleanExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, false)) {
            showFaultAction(faultDetail);
        }
        addViewToLL(faultDetail.getFaultFlows());
    }

    /**
     * 缺陷处理
     *
     * @param faultDetail 缺陷详情
     */
    private void showFaultAction(final FaultDetail faultDetail) {
        if (faultDetail.getFaultFlows().size() > faultDetail.getCurrentFlowIndex()
                && faultDetail.getFaultFlows().get(faultDetail.getCurrentFlowIndex()).getUsersN() != null
                && faultDetail.getFaultFlows().get(faultDetail.getCurrentFlowIndex()).getUsersN().size() > 0
                && faultDetail.getFaultState() < 3) {
            if (!TextUtils.isEmpty(faultDetail.getFaultFlows().get(faultDetail.getCurrentFlowIndex()).getUsersNext())) {
                String[] ids = faultDetail.getFaultFlows().get(faultDetail.getCurrentFlowIndex()).getUsersNext().split(",");
                for (String userId : ids) {
                    if (userId.equals(String.valueOf(App.getInstance().getCurrentUser().getUserId()))) {
//                        findViewById(R.id.ll_fault_action).setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
        if (findViewById(R.id.ll_fault_action).getVisibility() == View.VISIBLE) {
            boolean isToRepair = faultDetail.getDefaultFlowId() != null && faultDetail.getDefaultFlow() != null && faultDetail.getUsersN() != null;
            final RadioButton pointRb = findViewById(R.id.rb_point_fault);
            final RadioButton sureRb = findViewById(R.id.rb_sure_fault);
            final RadioButton closeRb = findViewById(R.id.rb_close_fault);
            if (App.getInstance().getCurrentUser().getCustomer().getIsOpen() == 1) {
                if (isToRepair) {
                    pointRb.setVisibility(View.VISIBLE);
                    sureRb.setVisibility(View.GONE);
                    pointRb.setChecked(true);
                } else {
                    pointRb.setVisibility(View.GONE);
                    sureRb.setVisibility(View.VISIBLE);
                    sureRb.setChecked(true);
                }
            }
            final EditText contentEt = findViewById(R.id.edit_content);
            findViewById(R.id.btn_sub).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String contentStr = contentEt.getText().toString().trim();
                    if (sureRb.isChecked()) {
                        mPresenter.sureFault(faultDetail.getFaultId(), contentStr);
                    } else if (pointRb.isChecked()) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("faultId", faultDetail.getFaultId());
                            jsonObject.put("flowRemark", contentStr);
                            jsonObject.put("usersNext", "-");
                            jsonObject.put("defaultFlowId", faultDetail.getDefaultFlowId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mPresenter.pointFault(jsonObject.toString());
                    } else if (closeRb.isChecked()) {
                        mPresenter.closeFault(faultDetail.getFaultId(), contentStr);
                    }
                }
            });
        }
    }

    private void addViewToLL(List<FaultDetail.FaultFlowsBean> faultFlows) {
        LinearLayout linearLayout = findViewById(R.id.container);
        if (faultFlows != null && faultFlows.size() > 0) {
//            findViewById(R.id.ll_record).setVisibility(View.VISIBLE);
            for (int i = 0; i < faultFlows.size(); i++) {
                FlowsItemLayout layout = new FlowsItemLayout(getApplicationContext());
                layout.setContent(faultFlows.get(i));
                linearLayout.addView(layout);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void success() {
        App.getInstance().showToast("操作成功!");
        finish();
    }

    @Override
    public void setPresenter(DefectRecordDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
