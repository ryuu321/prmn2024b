package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Chart;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.SelectionBuilder;
import com.github.appreciated.apexcharts.helper.Series;

public class Graph {
    private ApexCharts graph;
    private Graph(GraphBuilder builder) {
        setChart(builder.chart);
        setSeries(builder.series);
    }

    public void setChart(Chart chart) {
        graph.setChart(chart);
    }

    public void setSeries(Series... series) {
        graph.setSeries(series);
    }

    public ApexCharts getGraph() {
        return graph;
    }

    public static class GraphBuilder {
        private Chart chart;
        private Series[] series;

        public GraphBuilder() {
            chart = ChartBuilder.get().build();
            series = new Series[]{new Series(0)};
        }

        public GraphBuilder chartType(Type chartType) {
            this.chart.setType(Type.SCATTER);
            return this;
        }

        public GraphBuilder height(String height) {
            this.chart.setHeight(height);
            return this;
        }

        public GraphBuilder width(String width) {
            this.chart.setWidth(width);;
            return this;
        }

        public GraphBuilder series(Series... series) {
            this.series = series;
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }
    }

    public enum TYPE {
        SCATTER("scatter"),
        BAR("bar");
        String type;
        TYPE(String type) {this.type = type;}
    }
}
