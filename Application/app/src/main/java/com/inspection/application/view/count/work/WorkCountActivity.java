package com.inspection.application.view.count.work;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.WorkCountBean;
import com.inspection.application.view.BaseActivity;
import com.library.widget.DateDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 漏检统计
 * Created by pingan on 2017/12/21.
 */

public class WorkCountActivity extends BaseActivity implements WorkContract.View {

    private TextView mChooseTime;
    private TextView mChooseDept;
    private BarChart mBarChart;
    private String deptType = "1,2";//部门ID
    private Calendar mCurrentCalendar;
    private int mDeptId;
    private List<String> listStr;
    private List<DeptType> mDeptTypes;
    private WorkContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_missing_count, getString(R.string.str_missing_count));
        new WorkPresenter(Injection.getIntent().provideCountRepository(), this);
        mChooseTime = findViewById(R.id.choose_time);
        mChooseDept = findViewById(R.id.choose_dept);
        mBarChart = findViewById(R.id.bar_chart);
        mChooseTime.setOnClickListener(this);
        mChooseDept.setOnClickListener(this);
        mCurrentCalendar = Calendar.getInstance(Locale.CHINA);
        initBarChart();
        mPresenter.getDeptId(deptType);
    }

    private void initBarChart() {
        mBarChart.clear();
        mBarChart.setDescription(null);
        mBarChart.setNoDataText("没有数据!");
        mBarChart.setAlpha(1f);
        mBarChart.setTouchEnabled(true);
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);
        mBarChart.setPinchZoom(true);
        mBarChart.getLegend().setEnabled(true);

        mBarChart.getXAxis().setEnabled(true);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setTextSize(14f);
//        mBarChart.getXAxis().setSpaceMin(0f);
        mBarChart.getXAxis().setTextColor(findColorById(R.color.text_gray_999));
        mBarChart.getXAxis().setAxisLineColor(findColorById(R.color.text_black));
        mBarChart.getXAxis().setDrawAxisLine(true);
//        mBarChart.getXAxis().setLabelCount(4, true);
        mBarChart.getXAxis().setDrawGridLines(false);
//        mBarChart.getXAxis().setSpaceMax(0);
        //左边Y
        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.getAxisLeft().setDrawAxisLine(true);
        mBarChart.getAxisLeft().setDrawLabels(true);
//        mBarChart.getAxisLeft().
//        mBarChart.getAxisLeft().setLabelCount(15);
        mBarChart.getAxisLeft().setTextSize(14f);
        mBarChart.getAxisLeft().setDrawZeroLine(false);
        mBarChart.getAxisLeft().setTextColor(findColorById(R.color.text_gray_999));
        mBarChart.getAxisLeft().setAxisLineColor(findColorById(R.color.text_black));
        mBarChart.getAxisLeft().setAxisMinimum(0f);
//        mBarChart.getAxisLeft().setAxisMaximum(13);
        //右边Y
        mBarChart.getAxisRight().setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.choose_dept:
                new MaterialDialog.Builder(this)
                        .items(listStr)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mDeptId = mDeptTypes.get(position).getDeptId();
                                mChooseDept.setText(mDeptTypes.get(position).getDeptName());
                                mPresenter.getChartData(mDeptId, getDate(mCurrentCalendar));
                            }
                        })
                        .show();
                break;
            case R.id.choose_time:
                final DateDialog dateDlg = new DateDialog(this, R.style.MyDateDialog, mCurrentCalendar.get(Calendar.YEAR)
                        , mCurrentCalendar.get(Calendar.MONTH) + 1
                        , mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
                dateDlg.setConfirmButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCurrentCalendar = dateDlg.getDate();
                        mChooseTime.setText(getDate(mCurrentCalendar));
                        mPresenter.getChartData(mDeptId, getDate(mCurrentCalendar));
                    }
                });
                dateDlg.setBackButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dateDlg.cancel();
                    }
                });
                dateDlg.pickMonth();
                dateDlg.show();
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showDeptList(List<DeptType> deptTypes) {
        mDeptTypes = deptTypes;
        listStr = new ArrayList<>();
        for (int i = 0; i < deptTypes.size(); i++) {
            listStr.add(deptTypes.get(i).getDeptName());
        }
        if (deptTypes.size() > 0) {
            mDeptId = deptTypes.get(0).getDeptId();
            mChooseDept.setText(deptTypes.get(0).getDeptName());
            mPresenter.getChartData(mDeptId, getDate(mCurrentCalendar));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showChartData(List<WorkCountBean> countBeans) {
        mBarChart.getXAxis().setValueFormatter(new ChartXFormatter(countBeans));
        BarData barData = getBarData(countBeans);
        barData.setBarWidth(0.5f);
        barData.setDrawValues(true);
        barData.setValueTextSize(14f);

        mBarChart.setData(barData);
//        Matrix mMatrix = new Matrix();
//        mMatrix.postScale(1.5f, 1f);
//        mBarChart.getViewPortHandler().refresh(mMatrix, mBarChart, false);
        mBarChart.animateY(1000);
    }

    private BarData getBarData(List<WorkCountBean> countBeans) {
        List<IBarDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            List<BarEntry> barEntries = new ArrayList<>();
            for (int j = 0; j < countBeans.size(); j++) {
                barEntries.add(new BarEntry(j, countBeans.get(j).getTaskCount()));
            }
            BarDataSet dataSet = new BarDataSet(barEntries, "BarChart");
            dataSets.add(dataSet);
        }
        return new BarData(dataSets);
    }

    @Override
    public void noData() {

    }

    @Override
    public void setPresenter(WorkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private String getDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(year)).append("-");
        if (monthOfYear < 10) {
            sb.append("0").append(String.valueOf(monthOfYear));
        } else {
            sb.append(String.valueOf(monthOfYear));
        }
        return sb.toString();
    }
}
