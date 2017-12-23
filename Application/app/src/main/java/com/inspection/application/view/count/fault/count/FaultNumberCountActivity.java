package com.inspection.application.view.count.fault.count;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.chart.ChartData;
import com.inspection.application.mode.bean.count.FaultNumber;
import com.inspection.application.view.BaseActivity;
import com.library.widget.DateDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 故障数据统计
 * Created by pingan on 2017/12/22.
 */

public class FaultNumberCountActivity extends BaseActivity implements FaultNumberCountContact.View {

    private FaultNumberCountContact.Presenter mPresenter;
    private LineChart mLineChart;
    private TextView mTime;
    private int mYear;
    private List<String> month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_fault_number_count, "缺陷数统计");
        new FaultNumberPresenter(Injection.getIntent().provideCountRepository(), this);
        mTime = findViewById(R.id.id_fault_time);
        mLineChart = findViewById(R.id.lineChart);
        mLineChart.setNoDataTextColor(findColorById(R.color.colorPrimary));
        mLineChart.setNoDataText("");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        mYear = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR);
        mTime.setText(String.valueOf(mYear));
        mTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DateDialog dateDlg = new DateDialog(FaultNumberCountActivity.this, R.style.MyDateDialog, mYear, 1, 1);
                dateDlg.setConfirmButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar calendar = dateDlg.getDate();
                        mYear = calendar.get(Calendar.YEAR);
                        mTime.setText(String.valueOf(mYear));
                        mPresenter.getFaultNumberData(String.valueOf(mYear));
                    }
                });
                dateDlg.setBackButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dateDlg.cancel();
                    }
                });
                dateDlg.pickYear();
                dateDlg.show();
            }
        });
        month = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            month.add((i + 1) + "月");
        }
        mPresenter.getFaultNumberData(String.valueOf(mYear));
    }


    private void initLineChart(LineChart lineChart) {
        lineChart.clear();
        lineChart.setDescription(null);
        lineChart.setNoDataText("");
        lineChart.setAlpha(1f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.getLegend().setEnabled(false);
        //x
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setTextSize(10f);
        lineChart.getXAxis().setTextColor(findColorById(R.color.text_black));
        lineChart.getXAxis().setDrawAxisLine(true);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setAxisLineColor(findColorById(R.color.text_gray_999));
        lineChart.getXAxis().setLabelCount(month.size(), true);
        lineChart.getXAxis().setValueFormatter(new ChartXFormatter(month));
        lineChart.getXAxis().setAvoidFirstLastClipping(true);
        //左边Y
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawAxisLine(true);
        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisLeft().setTextSize(10f);
        lineChart.getAxisLeft().setDrawZeroLine(false);
        lineChart.getAxisLeft().setTextColor(findColorById(R.color.text_black));
        lineChart.getAxisLeft().setAxisLineColor(findColorById(R.color.text_gray_999));
        lineChart.getAxisLeft().setStartAtZero(true);
        //右边Y
        lineChart.getAxisRight().setEnabled(false);
    }

    private void initDataSet(LineDataSet dataSet, int color) {
        dataSet.setLineWidth(2.0f);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setCircleRadius(2.5f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(true);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setHighLightColor(findColorById(R.color.transparent));
    }


    private LineData getLineData(List<FaultNumber> chartDatas) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            List<Entry> entries = new ArrayList<>();
            for (int j = 0; j < chartDatas.size(); j++) {
                entries.add(new Entry(j, chartDatas.get(j).getFaultCount()));
            }
            LineDataSet dataSet = new LineDataSet(entries, "");
            initDataSet(dataSet, findColorById(R.color.colorPrimary));
            dataSets.add(dataSet);
        }

        return new LineData(dataSets);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {

    }

    @Override
    public void showFaultNumber(List<FaultNumber> faultNumbers) {
        initLineChart(mLineChart);
        mLineChart.setData(getLineData(faultNumbers));
        mLineChart.animateY(1000);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(FaultNumberCountContact.Presenter presenter) {
        mPresenter = presenter;
    }
}
