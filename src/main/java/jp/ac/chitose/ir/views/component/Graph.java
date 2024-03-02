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
import com.github.appreciated.apexcharts.config.chart.animations.builder.AnimateGraduallyBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.*;
import com.github.appreciated.apexcharts.config.datalables.TextAnchor;
import com.github.appreciated.apexcharts.config.plotoptions.Bar;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.yaxis.Labels;
import com.github.appreciated.apexcharts.config.yaxis.builder.*;
import com.github.appreciated.apexcharts.config.yaxis.title.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Formatter;
import com.github.appreciated.apexcharts.helper.Series;
import com.github.appreciated.apexcharts.helper.StringFormatter;
import com.github.appreciated.apexcharts.helper.SuffixFormatter;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    final private ApexCharts graph = new ApexCharts();
    private Graph(Builder builder) {
        setChart(builder.chart);
        setSeries(builder.series);
        graph.setXaxis(XAxisBuilder.get().withCategories(new String[]{"a", "b"}).build());
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

    public static class Builder {
        final private Chart chart = new Chart();
        private Series[] series = new Series[]{new Series(0)};
        final private PlotOptions options = new PlotOptions();
        final private Stroke stroke = new Stroke();
        final private DataLabels dataLabels = new DataLabels();
        final private Annotations annotations = new Annotations();
        final private YAxis yAxis = new YAxis();
        private String width = "400px";
        private String height = "400px";

        public Builder graphType(GRAPH_TYPE graphType) {
            this.chart.setType(graphType.type);
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

        public Builder stacked(boolean stacked) {
            chart.setStacked(stacked);
            return this;
        }

        public Builder XAxisAnnotations(List targets, List<String> texts) {
            XAxis xAxis = XAxisBuilder.get().build();
            List<XAxisAnnotations> xAxisAnnotations = new ArrayList<>();
            for(int i = 0; i < targets.size(); i++) {
                xAxisAnnotations.add(XAxisAnnotationsBuilder.get().withX(targets.get(i)).withLabel(LabelBuilder.get().withText(texts.get(i)).withOrientation("horizontal").withPosition("top").build()).build());
            }
            System.out.println(xAxisAnnotations);
            annotations.setXaxis(xAxisAnnotations);
            return this;
        }

        public Builder YAxisAnnotations(List<Double> targets, List<String> texts, String position) {
            List<YAxisAnnotations> yAxisAnnotations = new ArrayList<>();
            for(int i = 0; i < targets.size(); i++) {
                yAxisAnnotations.add(YAxisAnnotationsBuilder.get().withLabel(AnnotationLabelBuilder.get().withText(texts.get(i)).withPosition(position).build()).withYAxisIndex(targets.get(i)).build());
            }
            annotations.setYaxis(yAxisAnnotations);
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
        BAR(Type.BAR),
        PIE(Type.PIE);

        Type type;
        GRAPH_TYPE(Type type) {this.type = type;}
    }
}
