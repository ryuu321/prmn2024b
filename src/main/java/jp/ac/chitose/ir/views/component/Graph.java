package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.*;
import com.github.appreciated.apexcharts.config.chart.Animations;
import com.github.appreciated.apexcharts.config.chart.StackType;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.chart.builder.*;
import com.github.appreciated.apexcharts.config.plotoptions.Bar;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * ApexChartsをコンポーネント化したクラスです。<br>
 * ApexChartsをそのまま使わずにこのクラスを使って、ApexChartsのインスタンスを生成してください。<br>
 * また、Builderパターンを採用しているため、new演算子でGraphクラスのインスタンスを作成することはできません。<br>
 * Graph.Builder.get()のようにBuilderを使って、作成してください。
 */
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

    /**
     * 生成したApexChartsを返します。
     * <br>add(Graph.getGraph())といった感じで使ってください。
     * <br>add()以外のところで使ってしまうと、ApexChartsのインスタンスをこのクラス外でいじることができてしまうので、コンポーネント化した意味がなくなってしまいます。
     * @return ApexCharts
     */
    public ApexCharts getGraph() {
        return graph;
    }

    private static Series[] toSeries(GraphSeries... graphSeries) {
        return Arrays.stream(graphSeries).map(graphSeries1 -> new Series(graphSeries1.getName(), graphSeries1.getData())).toArray(Series[]::new);
    }

    private static Series[] toSeries(Collection<GraphSeries> graphSeries) {
        return graphSeries.stream().map(graphSeries1 -> new Series(graphSeries1.getName(), graphSeries1.getData())).toArray(Series[]::new);
    }

    /**
     * Graphのインスタンスを作成するためのBuilderクラスです。<br>
     * Graph.Builder.get().メソッド1().メソッド2()...のように.でメソッドをつなげて、Graphの設定をしてください。
     */
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

        /**
         * 制限するためにプライベートになっています。<br>
         * Builderのインスタンスを取得したいときは,Graph.Builder.get()という風にget()メソッドを使ってください。
         */
        private Builder() {}

        /**
         * ビルダーを返します。<br>
         * new Graph.Builder()はプライベートになっているため、new演算子ではBuilderを取得することができないことに注意してください。
         * @return Builder
         */
        public static Builder get() {
            return new Builder();
        }

        /**
         * 作るグラフのタイプをenumのGRAPH_TYPEを使って指定します。<br>
         * ヒストグラムや帯グラフなどグラフのタイプの中にないグラフを作りたいときは、<p style="font:bold">histogram()</p><p style="font:bold">band()</p>などの補助用のメソッドを用意してあるので、それを呼び出してください。
         * @param graphType 指定したいグラフタイプ
         * @return Builder
         */
        public Builder graphType(GRAPH_TYPE graphType) {
            chart.setType(graphType.type);
            return this;
        }

        /**
         * グラフのタイプの中にヒストグラムがないので、ヒストグラムを作るための補助メソッドです。
         * このメソッドを呼び出してもらえれば、ヒストグラムの設定になります。
         * @return Builder
         */
        public Builder histogram() {
            graphType(GRAPH_TYPE.BAR);
            Bar bar = (options.getBar() == null ? BarBuilder.get().build() : options.getBar());
            bar.setColumnWidth("100%");
            options.setBar(bar);
            stroke.setWidth(0.1);
            var colors = new ArrayList<String>();
            colors.add("#000");
            stroke.setColors(colors);
            return this;
        }

        /**
         * データのラベルを指定します。<br>
         * 基本的にはGraphSeriesを使えば不要ですが、円グラフの時はGraphSeriesを使えないため、データラベルの指定に使います。
         * @param labels データのラベル
         * @return Builder
         */
        public Builder labels(String[] labels) {
            this.labels = labels;
            return this;
        }

        /**
         * グラフのタイプの中に帯グラフがないので、帯グラフを作るための補助メソッドです。
         * このメソッドを呼び出してもらえれば、帯グラフの設定になります。
         * @return Builder
         */
        public Builder band() {
            graphType(GRAPH_TYPE.BAR);
            Bar bar = (options.getBar() == null ? new Bar() : options.getBar());
            bar.setHorizontal(true);
            options.setBar(bar);
            chart.setStacked(true);
            chart.setStackType(StackType.FULL);
            return this;
        }

        /**
         * データラベルの表示・非表示を設定します。
         * @param enabled データラベルの表示・非表示(true:表示, false:非表示)
         * @return Builder
         */
        public Builder dataLabelsEnabled(boolean enabled) {
            dataLabels.setEnabled(enabled);
            return this;
        }

        /**
         * 棒グラフの追加オプションです。
         * 棒グラフを縦から横にすることができます。
         * @param horizontal true:横, false:縦
         * @return Builder
         */
        public Builder horizontal(boolean horizontal) {
            Bar bar = (options.getBar() == null ? BarBuilder.get().build() : options.getBar());
            bar.setHorizontal(horizontal);
            options.setBar(bar);
            return this;
        }

        /**
         * GraphSeriesではなくても、データを指定できます。
         * また、円グラフの時はGraphSeriesを使うと、データの指定ができないため、これを使ってもらいます。
         * @param doubles データの値
         * @return Builder
         */
        public Builder doubles(Double... doubles) {
            this.doubles = doubles;
            return this;
        }

        /**
         * 積み上げ棒グラフなどを作りたいときに使うメソッドです。
         * @param stacked true:積み上げ, false:積み上げない
         * @return Builder
         */
        public Builder stacked(boolean stacked) {
            chart.setStacked(stacked);
            return this;
        }

        /**
         * アニメーションをつけたいときに使うメソッドです。
         * @param enabled true:アニメーションあり<br>false:アニメーションなし
         * @return Builder
         */
        public Builder animationsEnabled(boolean enabled) {
            Animations animations = (chart.getAnimations() == null ? AnimationsBuilder.get().build() : chart.getAnimations());
            animations.setEnabled(enabled);
            chart.setAnimations(animations);
            return this;
        }

        /**
         * グラフの高さを決めるメソッドです。
         * 400pxや50%などhtmlやcssなどの大きさの指定方法と同じように指定できます。
         * @param height グラフの高さ
         * @return Builder
         */
        public Builder height(String height) {
            this.height = height;
            return this;
        }

        /**
         * グラフの幅を決めるメソッドです。
         * 400pxや50%などhtmlやcssなどの大きさの指定方法と同じように指定できます。
         * @param width グラフの幅
         * @return Builder
         */
        public Builder width(String width) {
            this.width = width;
            return this;
        }

        /**
         * GraphSeriesの配列を引数として渡すことで、データを指定できます。
         * GraphSeriesのListやArrayListなどでもデータを指定することができます。円グラフの時はこのメソッドを使わず、doubles()メソッドを使って、データを指定してください。
         * @param series データ
         * @return Builder
         */
        public Builder series(GraphSeries... series) {
            this.series = toSeries(series);
            return this;
        }

        /**
         * GraphSeriesのListやArrayListなどを引数として渡すことで、データを指定できます。
         * GraphSeriesの配列でもデータを指定することができます。円グラフの時はこのメソッドを使わず、doubles()メソッドを使って、データを指定してください。
         * @param series データ
         * @return Builder
         */
        public Builder series(Collection<GraphSeries> series) {
            this.series = toSeries(series);
            return this;
        }

        /**
         * アニメーションのeasingを指定することができます。
         * @param easing easingの種類
         * @return Builder
         */
        public Builder easing(Easing easing) {
            chart.setAnimations(AnimationsBuilder.get().withEasing(easing).build());
            return this;
        }

        /**
         * 今まで指定してきたものが反映されたGraphクラスが返されます。
         * Graphクラスの指定がすべて終わった後にこのメソッドを呼び出してください。
         * @return Graph
         */
        public Graph build() {
            return new Graph(this);
        }
    }
}