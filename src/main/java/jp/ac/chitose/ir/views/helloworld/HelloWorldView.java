package jp.ac.chitose.ir.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Annotations;
import com.github.appreciated.apexcharts.config.annotations.Label;
import com.github.appreciated.apexcharts.config.annotations.XAxisAnnotations;
import com.github.appreciated.apexcharts.config.annotations.builder.LabelBuilder;
import com.github.appreciated.apexcharts.config.annotations.builder.XAxisAnnotationsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jp.ac.chitose.ir.service.*;
import jp.ac.chitose.ir.service.sample.SampleService;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.*;

import java.util.*;
import java.util.List;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class  HelloWorldView extends VerticalLayout {

    private TextField name;
    private Button sayHello;

    private HelloService helloService;

    private SampleService sampleService;

    public HelloWorldView(HelloService helloService, SampleService sampleService) {
        this.helloService = helloService;
        this.sampleService = sampleService;

        // タイトル表示　（最も簡単なコンポーネントの使用例）
        H1 title = new H1("IR System & Data Project にようこそ");
        add(title);

        add(new Paragraph("ここに表示されているものはサンプルです。プログラムの書き方と見え方の参考程度です。みなさんと一緒に作っていきます。"));
        add(new Paragraph("上記の「テーブル使用例」をクリックすると別のページに遷移して表データを見ることができます"));

        // グラフ表示の例；
        // 　データはPythonから取得
        //GoogleChart googleChart = googleChartの使用例();
        //add(googleChart);

        add(散布図ApechCharts版());

        // ボタン（なんらかの動作をさせるときに利用する）の表示と動作の使用例
        Button graphButton = new Button("成績分布やアンケートの散布図を表示する");
        graphButton.setWidthFull();
        // clickされたときに動作させたいプログラムをaddClickListenerメソッドに記述する；ラムダ式
        graphButton.addClickListener(event -> {
            HorizontalLayout layout = new HorizontalLayout();
            var 成績分布 = 年度別成績分布();
            var 散布図リスト = アンケート分析の散布図();
            layout.add(成績分布);
            散布図リスト.stream().forEachOrdered(chart -> layout.add(chart));
            add(layout);
            Notification.show("表示しました（いくらでも追加表示されます）");
        });
        // ボタンの文字の大きさや、表示サイズなどを設定（Vaadinのデザインシステムに則って行う。デザインはLumoという名称がついています）
        graphButton.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Padding.XLARGE);
        add(graphButton);

        add(new H1("addon ヒストグラム"), ヒストグラム());

        add(new H1("addon 箱ひげ図"), 箱ひげ図());
        //add(new H1("chart js"),new ChartJS());
        test();
        add(pie());
    }

    private void test() {
        GraphSeries[] series = new GraphSeries[]{new GraphSeries("2021", new Data<>("a",10), new Data<>("b", 50), new Data<>("c", 20)),
                                       new GraphSeries("2022", new Data<>("a",20), new Data<>("b", 30), new Data<>("c", 40))};
        add(Graph.Builder.get().band().series(series).build().getGraph());
        series = new GraphSeries[]{new GraphSeries("2021", new Data<>("a", 10), new Data<>("b", 20)), new GraphSeries("2022", new Data<>("a", 30), new Data<>("b", 40))};
        add(Graph.Builder.get().series(series).graphType(GRAPH_TYPE.BAR).horizontal(true).stacked(true).build().getGraph());
    }

    private GoogleChart googleChartの使用例() {
        // グラフの表示の仕方の参考実装。GoogleChartクラス
        var sampleOne = sampleService.getSampleOne();
        System.out.println("sampleOnes is "+ sampleOne);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Column("string", "month"),
                new GoogleChart.Column("number", "score")
        );

        // 2. 行を設定
        /*
        var rows = Arrays.asList(
                new GoogleChart.Row(new GoogleChart.RowValue( "jan") , new GoogleChart.RowValue(15)),
                new GoogleChart.Row(new GoogleChart.RowValue("oct") , new GoogleChart.RowValue(50))
        );
         */

        var rows = sampleOne.data().stream()
                .map(row ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(row.month()),
                                new GoogleChart.RowValue(row.score())
                        )
                )
                .toList();

        /* data structure
            _________________
            | month | score |
            -----------------
            |  jan  |  15   |
            |  oct  |  50   |
            -----------------

            reference :
             https://developers.google.com/chart/interactive/docs/reference?hl=en#chartwrapperobject
             https://github.com/GoogleWebComponents/google-chart/blob/main/google-chart.ts
             https://qiita.com/cy-hiroshi-chiba/items/84096f9c44dc292f6c5f
         */

        try {
            // debug print
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(cols));
            System.out.println(objectMapper.writeValueAsString(rows));
        } catch (JsonProcessingException e) { e.printStackTrace(); }

        // 3. カラムと行をGoogleChartに設定して表示
        return new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.AREA);

    }

    private GoogleChart 年度別成績分布() {
        // グラフの表示の仕方の参考実装。GoogleChartクラス
        var sampleTwo = sampleService.getSampleTwo();
        System.out.println("sampleOnes is "+ sampleTwo);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Column("string", "年度"),
                new GoogleChart.Column("number", "秀"),
                new GoogleChart.Column("number", "優"),
                new GoogleChart.Column("number", "良"),
                new GoogleChart.Column("number", "可"),
                new GoogleChart.Column("number", "不可"),
                new GoogleChart.Column("number", "欠席")
        );

        var rows = sampleTwo.data().stream()
                .map(row ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(row.年度()),
                                new GoogleChart.RowValue(row.秀()),
                                new GoogleChart.RowValue(row.優()),
                                new GoogleChart.RowValue(row.良()),
                                new GoogleChart.RowValue(row.可()),
                                new GoogleChart.RowValue(row.不可()),
                                new GoogleChart.RowValue(row.欠席())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        //var options = "{\"isStacked\": \"percent\"}";
        Map<String, Object> options = new HashMap<>();
        options.put("isStacked", "percent");
        options.put("title", "成績分布");;
        return new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.BAR, options);
        //return apexChart.bar(series);
    }

    private List<GoogleChart> アンケート分析の散布図() {
        // グラフの表示の仕方の参考実装。GoogleChartクラス
        var sampleThree = sampleService.getSampleThrees();
        System.out.println("sampleOnes is "+ sampleThree);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Column("string", "年度"),
                new GoogleChart.Column("number", "難易度")
        );

        var rows = sampleThree.data().stream()
                .map(row ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(row.年度()),
                                new GoogleChart.RowValue(row.難易度())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        var chart1 = new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER);

        cols = Arrays.asList(
                new GoogleChart.Column("string", "年度"),
                new GoogleChart.Column("number", "学習量")
        );

        rows = sampleThree.data().stream()
                .map(row ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(row.年度()),
                                new GoogleChart.RowValue(row.学習量())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        var chart2 = new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER);

        return List.of(chart1, chart2);
    }

    private ApexCharts 散布図ApechCharts版() {
        var sampleThrees = sampleService.getSampleThrees();
        var 学習量 = sampleThrees.data().stream().map(row -> row.学習量()).toList().toArray(new Double[0]);
        GraphSeries<Double> series = new GraphSeries<>("2023");
        series.setData(学習量);

        List<GraphSeries> seriesList = new ArrayList<>();
        seriesList.add(series);

        /*ApexCharts chart = ApexChartsBuilder.get().withChart(
                        ChartBuilder.get()
                                .withType(Type.SCATTER)
                                .withAnimations(AnimationsBuilder.get()
                                        .withEnabled(false)
                                        .build())
                                .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withSeries(series)
                .withYaxis(YAxisBuilder.get().withForceNiceScale(true).build())
                .build();
        return chart;
*/
        return Graph.Builder.get().series(seriesList).graphType(GRAPH_TYPE.SCATTER).build().getGraph();
    }

    private ApexCharts ヒストグラム() {

        // ヒストグラムで表示するデータを用意する
        // データはSeriesクラスを使う
        // Seriesクラスのコンストラクタには、名前、データの中身、を設定する。
        // データの中身は、Coordinateクラスを設定する。Coordinateクラスが一つで柱（棒）；階級が一つできる
        // 柱（棒）；階級には、データ数、があるので、Coordinateクラスのコンストラクタの第２引数にデータ数を設定する
        // 階級の幅の数に応じてCoordinateクラスをnewして設定する（階級数が7なら7個Cooridnateクラスを設定する）
        final GraphSeries<Data<String, Integer>> series = new GraphSeries<>("2021",
                new Data<>("10", 25), // 第一引数に階級、第二引数にデータ数
                new Data<>("20", 30), // 第一引数に階級、第二引数にデータ数
                new Data<>("30", 40), // 第一引数に階級、第二引数にデータ数
                new Data<>("40", 60), // 第一引数に階級、第二引数にデータ数
                new Data<>("50", 40), // 第一引数に階級、第二引数にデータ数
                new Data<>("60", 20), // 第一引数に階級、第二引数にデータ数
                new Data<>("70", 10)  // 第一引数に階級、第二引数にデータ数
                );

        // ヒストグラムを作成する
        // withType(Type.BAR)は棒グラフで表示する指示にあたる
        // 棒グラフをもとにヒストグラムに見た目を変更する。変更するポイントは下記にコメントで補足
        /* final ApexCharts chart = ApexChartsBuilder.get().withChart(
                ChartBuilder.get()
                        .withType(Type.BAR) // Typeにヒストグラムがない。公式サイトのissueによるBARでやるように指示がある
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get().withColumnWidth("100%").build()) // BARの間隔を０に近づける（見た目を調整してヒストグラムにみえるようにする）
                        .build())
                .withStroke(StrokeBuilder.get().withWidth(0.1).withColors("#000").build()) // 柱（棒）の外枠を黒色に設定してヒストグラムに見た目を近づける
                .withYaxis(YAxisBuilder.get().withMax(80).build()) // Y軸の最大値；ここでは80に設定
                .withSeries(series)
                .build();
         */
        return Graph.Builder.get().histogram()
                .height("400px").width("400px").series(series).animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }

    private ApexCharts pie() {
        Double[] doubles = new Double[]{22.0, 22.0, 33.0, 11.0, 32.3};
        String[] labels = new String[]{"Team A", "Team B", "Team C", "Team D", "Team E"};
        return Graph.Builder.get()
                .graphType(GRAPH_TYPE.DONUT)
                .doubles(doubles)
                .labels(labels)
                .build()
                .getGraph();
    }

    private ApexCharts 箱ひげ図() {
        // 箱ひげ図で表示するデータを用意する
        // データはSeriesクラスを使う
        // Seriesクラスのコンストラクタには、名前、データの中身、を設定する。
        // データの中身は、Coordinateクラスを設定する。Coordinateクラスが一つで箱が一つできる
        // 箱には、最小値、第一四分位数、中央値、第三四分位数、最大値、があるので、Coordinateクラスのコンストラクタの第２引数に順番に設定する
        final Series<Coordinate<String, Double>> series = new Series<>("box",
                new Coordinate<>("2021", 43.2, 65.0, 69.1, 76.8, 81.6), // １つ目の箱{ x: category/date, y: [min, q1, median, q3, max] }
                new Coordinate<>("2022", 30.8, 39.2, 45.0, 51.0, 59.3)  // ２つ目の箱{ x: category/date, y: [min, q1, median, q3, max] }
                );

        final GraphSeries<Data<String, Double>> series1 = new GraphSeries<>("box",
                new Data<>("2021", 43.2, 65.0, 69.1, 76.8, 81.6),
                new Data<>("2022", 30.8, 39.2, 45.0, 51.0, 59.3)
        );


        // 箱ひげ図を作成する
        // withType(Type.BOXPLOT)が箱ひげ図で表示する指示にあたる
        /*final ApexCharts chart = ApexChartsBuilder.get().withChart(
                        ChartBuilder.get()
                                .withType(Type.BOXPLOT)
                                .withAnimations(AnimationsBuilder.get()
                                        .withEnabled(false)
                                        .withEasing(Easing.LINEAR)
                                        .build())
                                .build())
                .withSeries(randomSeries)
                .build();

        chart.setHeight("600px");
        chart.setWidth("600px");*/

        return Graph.Builder.get().graphType(GRAPH_TYPE.BOXPLOT).animationsEnabled(false)
                .easing(Easing.LINEAR).width("600px").height("600px").series(series1).build().getGraph();
    }
}
