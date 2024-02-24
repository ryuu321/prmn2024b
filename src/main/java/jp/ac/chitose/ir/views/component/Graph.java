package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Animations;
import com.github.appreciated.apexcharts.config.chart.PointAnnotations;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.DynamicAnimation;
import com.github.appreciated.apexcharts.config.chart.animations.builder.AnimateGraduallyBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.PointAnnotationsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.SelectionBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.Bar;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private ApexCharts graph = ApexChartsBuilder.get().build();;
    private Graph(Builder builder) {
        setChart(builder.chart);
        setSeries(builder.series);
        setPlotOptions(builder.options);
        setStroke(builder.stroke);
        setHeight(builder.height);
        setWidth(builder.width);
        setDataLabels(builder.dataLabels);
    }

    public void setChart(Chart chart) {
        graph.setChart(chart);
    }

    public void setSeries(Series... series) {
        graph.setSeries(series);
    }

    public void setPlotOptions(PlotOptions options) {
        graph.setPlotOptions(options);
    }

    public void setStroke(Stroke stroke) {
        graph.setStroke(stroke);
    }

    public void setHeight(String height) {
        graph.setHeight(height);
    }

    public void setWidth(String width) {
        graph.setWidth(width);
    }

    public void setDataLabels(DataLabels dataLabels) {
        graph.setDataLabels(dataLabels);
    }

    public ApexCharts getGraph() {
        return graph;
    }

    public static class Builder {
        final private Chart chart = ChartBuilder.get().build();
        private Series[] series = new Series[]{new Series(0)};
        final private PlotOptions options = PlotOptionsBuilder.get().build();
        final private Stroke stroke = StrokeBuilder.get().build();
        final private DataLabels dataLabels = DataLabelsBuilder.get().build();
        final private Annotations annotations = new Annotations();
        private String width = "400px";
        private String height = "400px";

        public Builder graphType(GRAPH_TYPE graphType) {
            this.chart.setType(graphType.type);
            return this;
        }

        public Builder histogram(boolean histogram) {
            if(histogram) {
                Bar bar = (options.getBar() == null ? BarBuilder.get().build() : options.getBar());
                bar.setColumnWidth("100%");
                options.setBar(bar);
                stroke.setWidth(0.1);
                var colors = new ArrayList<String>();
                colors.add("#000");
                stroke.setColors(colors);
            }
            return this;
        }

        public Builder dataLabelsEnabled(boolean enabled) {
            dataLabels.setEnabled(enabled);
            return this;
        }

        public Builder animationsEnabled(boolean enabled) {
            Animations animations = (chart.getAnimations() == null ? AnimationsBuilder.get().build() : chart.getAnimations());
            animations.setEnabled(enabled);
            chart.setAnimations(animations);
            return this;
        }

        public Builder height(String height) {
            this.height = height;
            return this;
        }

        public Builder width(String width) {
            this.width = width;
            return this;
        }

        public Builder series(Series... series) {
            this.series = series;
            return this;
        }

        public Builder series(List<Series> series) {
            this.series = series.toArray(Series[]::new);
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }
    }

    public enum GRAPH_TYPE {
        SCATTER(Type.SCATTER),
        BAR(Type.BAR);
        Type type;
        GRAPH_TYPE(Type type) {this.type = type;}
    }
}
