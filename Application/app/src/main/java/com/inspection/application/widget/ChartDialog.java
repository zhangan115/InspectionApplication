package com.inspection.application.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.inspection.application.R;
import com.inspection.application.mode.bean.equipment.ChartData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/14.
 */

public class ChartDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private LineChart mLineChart;
    List<ChartData> mChartDatas = new ArrayList<>();
    private List<String> values = new ArrayList<>();

    public ChartDialog(Activity activity) {
        super(activity, R.style.mdialog);
        this.activity = activity;
    }

    public ChartDialog(Activity activity, List<ChartData> chartDatas) {
        super(activity, R.style.mdialog);
        this.activity = activity;
        mChartDatas.addAll(chartDatas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chekchart);
        mLineChart = (LineChart) findViewById(R.id.lineChart);
        mLineChart.setNoDataTextColor(R.color.colorPrimary);
        mLineChart.setNoDataText("");
        initData();
//        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    private void initData() {
        for (int i = 0; i < mChartDatas.get(0).getData().size(); i++) {
            values.add((i + 1) + "");
        }
        initLineChart(mLineChart, mChartDatas);
        int[] colors = new int[]{R.color.colorPrimary};
        mLineChart.setData(getLineData(mChartDatas, colors));
    }

    /**
     * 设置dialog位于屏幕中间
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.x = (int) (width * 0.8);
        lp.y = (int) (height * 0.8);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        //设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
    }

    private void initLineChart(LineChart lineChart, List<ChartData> dataValues) {
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
        lineChart.getXAxis().setTextSize(12f);
        lineChart.getXAxis().setTextColor(R.color.colorPrimary);
        lineChart.getXAxis().setDrawAxisLine(true);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setAxisLineColor(R.color.colorPrimary);
        lineChart.getXAxis().setLabelCount(values.size(), false);
        lineChart.getXAxis().setValueFormatter(new ChartXFormatterN(values));
        //左边Y
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawAxisLine(true);
        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisLeft().setTextSize(12f);
        lineChart.getAxisLeft().setDrawZeroLine(false);
        lineChart.getAxisLeft().setTextColor(R.color.colorPrimary);
        lineChart.getAxisLeft().setAxisLineColor(R.color.colorPrimary);
        //右边Y
        lineChart.getAxisRight().setEnabled(false);
    }

    private void initDataSet(LineDataSet dataSet, int color) {
        dataSet.setLineWidth(2.0f);
        dataSet.setColor(activity.getResources().getColor(color));
        dataSet.setCircleColor(activity.getResources().getColor(color));
        dataSet.setCircleRadius(2.5f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(true);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setHighLightColor(R.color.transparent);
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

    /**
     * x轴显示
     * Created by zhangan on 2017-05-19.
     */

    class ChartXFormatterN implements IAxisValueFormatter {

        @Nullable
        private List<String> dataValues;

        ChartXFormatterN(@Nullable List<String> dataValues) {
            this.dataValues = dataValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int position = (int) value;
            if (position < 0) {
                position = 0;
            }
            if (position < dataValues.size()) {
                return dataValues.get(position);
            } else {
                return position + "";
            }
        }
    }
}
