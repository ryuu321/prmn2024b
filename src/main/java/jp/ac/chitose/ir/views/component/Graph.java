package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.annotations.XAxisAnnotations;
import com.github.appreciated.apexcharts.config.annotations.YAxisAnnotations;
import com.github.appreciated.apexcharts.config.annotations.builder.*;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Animations;
import com.github.appreciated.apexcharts.config.chart.PointAnnotations;
import com.github.appreciated.apexcharts.config.chart.StackType;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.DynamicAnimation;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.chart.animations.builder.AnimateGraduallyBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.*;
import com.github.appreciated.apexcharts.config.datalables.TextAnchor;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.Bar;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.PieBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.pie.Name;
import com.github.appreciated.apexcharts.config.plotoptions.pie.Value;
import com.github.appreciated.apexcharts.config.plotoptions.pie.builder.TotalBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.*;
import com.github.appreciated.apexcharts.config.yaxis.title.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Formatter;
import com.github.appreciated.apexcharts.helper.Series;
import com.github.appreciated.apexcharts.helper.StringFormatter;
import com.github.appreciated.apexcharts.helper.SuffixFormatter;
import com.vaadin.flow.component.charts.model.Labels;

import java.util.*;

public class Graph {
    final private ApexCharts graph = new ApexCharts();
    private Graph(Builder builder) {
        setChart(builder.chart);
        if(builder.chart.getType() == Type.PIE || builder.chart.getType() == Type.DONUT) setDoubles(builder.doubles);
        else setSeries(builder.series);
        setLabels(builder.labels);
        setLegend(builder.legend);
        setResponsive(builder.responsive);
        setAnnotations(builder.annotations);
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

    public void setDoubles(Double... doubles) { graph.setSeries(doubles); }

    public void setLabels(String... labels) { graph.setLabels(labels); }

    public void setLegend(Legend legend) { graph.setLegend(legend); }

    public void setResponsive(Responsive responsive) { graph.setResponsive(responsive); }

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

    public void setAnnotations(Annotations annotations) {graph.setAnnotations(annotations); }

    public ApexCharts getGraph() {
        return graph;
    }

    private static Series[] graphSeriesToSeries(GraphSeries... graphSeries) {
        return Arrays.stream(graphSeries).map(graphSeries1 -> new Series(graphSeries1.getName(), graphSeries1.getData())).toArray(Series[]::new);
    }

    private static Series[] graphSeriesToSeries(Collection<GraphSeries> graphSeries) {
        return graphSeries.stream().map(graphSeries1 -> new Series(graphSeries1.getName(), graphSeries1.getData())).toArray(Series[]::new);
    }

    public static class Builder {
        private final Chart chart = new Chart();
        private Series[] series = new Series[]{new Series(0)};
        private Double[] doubles = new Double[]{0.0};
        private final PlotOptions options = new PlotOptions();
        private final Stroke stroke = new Stroke();
        private final DataLabels dataLabels = new DataLabels();
        private final Annotations annotations = new Annotations();
        private final Legend legend = new Legend();
        private final Responsive responsive = new Responsive();
        private String[] labels = new String[]{};
        private String width = "400px";
        private String height = "400px";

        private Builder() {}

        public static Builder get() {
            return new Builder();
        }

        public Builder graphType(GRAPH_TYPE graphType) {
            chart.setType(graphType.type);
            return this;
        }

        public Builder histogram(boolean histogram) {
            if(histogram) {
                graphType(GRAPH_TYPE.BAR);
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

        public Builder labels(String[] labels) {
            this.labels = labels;
            return this;
        }

        public Builder band(boolean band) {
            if(band) {
                graphType(GRAPH_TYPE.BAR);
                Bar bar = (options.getBar() == null ? new Bar() : options.getBar());
                bar.setHorizontal(true);
                options.setBar(bar);
                chart.setStacked(true);
                chart.setStackType(StackType.FULL);
            }
            return this;
        }

        public Builder dataLabelsEnabled(boolean enabled) {
            dataLabels.setEnabled(enabled);
            return this;
        }

        public Builder horizontal(boolean horizontal) {
            Bar bar = (options.getBar() == null ? BarBuilder.get().build() : options.getBar());
            bar.setHorizontal(horizontal);
            options.setBar(bar);
            return this;
        }

        public Builder doubles(Double... doubles) {
            this.doubles = doubles;
            return this;
        }

        public Builder stacked(boolean stacked) {
            chart.setStacked(stacked);
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

        public Builder series(GraphSeries... series) {
            this.series = graphSeriesToSeries(series);
            return this;
        }

        public Builder series(Collection<GraphSeries> series) {
            this.series = graphSeriesToSeries(series);
            return this;
        }

        public Builder easing(Easing easing) {
            chart.setAnimations(AnimationsBuilder.get().withEasing(easing).build());
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }
    }

    public enum GRAPH_TYPE {
        SCATTER(Type.SCATTER),
        BAR(Type.BAR),
        PIE(Type.PIE),
        DONUT(Type.DONUT),
        BOXPLOT(Type.BOXPLOT);

        Type type;
        GRAPH_TYPE(Type type) {this.type = type;}
    }
}
