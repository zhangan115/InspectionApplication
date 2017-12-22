package com.inspection.application.view.count.fault.level;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.inspection.application.R;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.chart.ChartData;
import com.inspection.application.mode.bean.count.FaultLevel;
import com.inspection.application.view.BaseActivity;
import com.library.widget.DateDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 故障等级统计
 * Created by pingan on 2017/12/22.
 */

public class FaultLevelCountActivity extends BaseActivity implements FaultLevelContract.View {

    private LineChart mLineChart;
    private TextView mTime;
    private int mYear;
    private List<String> month;
    private FaultLevelContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_fault_type, "缺陷等级统计");
        new FaultLevelPresenter(Injection.getIntent().provideCountRepository(), this);
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
                final DateDialog dateDlg = new DateDialog(FaultLevelCountActivity.this, R.style.MyDateDialog, mYear, 1, 1);
                dateDlg.setConfirmButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar calendar = dateDlg.getDate();
                        mYear = calendar.get(Calendar.YEAR);
                        mTime.setText(String.valueOf(mYear));
                        mPresenter.getChartData(String.valueOf(mYear));
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
        mPresenter.getChartData(mTime.getText().toString());
    }

    @Override
    public void showData(List<ChartData> chartDatas) {

    }

    @Override
    public void showChartData(List<FaultLevel> faultLevels) {
        List<ChartData> chartDatas = new ArrayList<>();

        ChartData allFault = new ChartData();
        List<ChartData.Data> allList = new ArrayList<>();

        ChartData aFault = new ChartData();
        List<ChartData.Data> aList = new ArrayList<>();

        ChartData bFault = new ChartData();
        List<ChartData.Data> bList = new ArrayList<>();

        ChartData cFault = new ChartData();
        List<ChartData.Data> cList = new ArrayList<>();

        month = new ArrayList<>();
        for (int j = 0; j < faultLevels.size(); j++) {
            ChartData.Data allData = new ChartData.Data();
            allData.setDataValue(faultLevels.get(j).getAllFaultCount());
            allList.add(allData);
            ChartData.Data aData = new ChartData.Data();
            aData.setDataValue(faultLevels.get(j).getAFaultCount());
            aList.add(aData);
            ChartData.Data bData = new ChartData.Data();
            bData.setDataValue(faultLevels.get(j).getBFaultCount());
            bList.add(bData);
            ChartData.Data cData = new ChartData.Data();
            cData.setDataValue(faultLevels.get(j).getCFaultCount());
            cList.add(cData);
            month.add((j + 1) + "月");
        }
        allFault.setData(allList);
        aFault.setData(aList);
        bFault.setData(bList);
        cFault.setData(cList);
        chartDatas.add(allFault);
        chartDatas.add(aFault);
        chartDatas.add(bFault);
        chartDatas.add(cFault);

        initLineChart(mLineChart);
        int[] colors = new int[]{R.color.line_color_1, R.color.line_color_2
                , R.color.line_color_3, R.color.line_color_4};
        mLineChart.setData(getLineData(chartDatas, colors));
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
    public void setPresenter(FaultLevelContract.Presenter presenter) {
        mPresenter = presenter;
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
        lineChart.getXAxis().setValueFormatter(new ChartXFormatterN(month));
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

    private void initDataSet(LineDataSet dataSet, @ColorInt int color) {
        dataSet.setLineWidth(2.0f);
        dataSet.setColor(findColorById(color));
        dataSet.setCircleColor(findColorById(color));
        dataSet.setCircleRadius(2.5f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(true);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setHighLightColor(findColorById(R.color.transparent));
    }


    private LineData getLineData(List<ChartData> chartDatas, int[] color) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < chartDatas.size(); i++) {
            List<Entry> entries = new ArrayList<>();
            for (int j = 0; j < chartDatas.get(i).getData().size(); j++) {
                entries.add(new Entry(j, chartDatas.get(i).getData().get(j).getDataValue()));
            }
            LineDataSet dataSet = new LineDataSet(entries, "");
            initDataSet(dataSet, color[i]);
            dataSets.add(dataSet);
        }

        return new LineData(dataSets);
    }
}
