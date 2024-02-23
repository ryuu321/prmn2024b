package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Series;

import java.util.List;

public class ApexChart {

    private Scatter scatter;
    private Histogram histogram;
    private Bar bar;

    public ApexChart() {
        scatter = new Scatter();
        histogram = new Histogram();
        bar = new Bar();
    }

    public ApexCharts scatter(List<Series> series) {
        return scatter.scatter(series.toArray(Series[]::new));
    }
    public ApexCharts scatter(Series... series) {
        return scatter.scatter(series);
    }

    public ApexCharts histogram(List<Series> series) {
        return histogram.histogram(series.toArray(Series[]::new));
    }

    public ApexCharts histogram(Series... series) {
        return histogram.histogram(series);
    }

    public ApexCharts bar(List<Series> series) {
        return bar.bar(series.toArray(Series[]::new));
    }

    public ApexCharts bar(Series... series) {
        return bar.bar(series);
    }
}