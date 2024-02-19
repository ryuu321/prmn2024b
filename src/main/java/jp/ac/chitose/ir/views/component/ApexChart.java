package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

public class ApexChart {

    private Scatter scatter;

    public ApexChart() {
        scatter = new Scatter();
    }

    public ApexCharts scatter(Series... series) {
        return scatter.scatter(series);
    }
}