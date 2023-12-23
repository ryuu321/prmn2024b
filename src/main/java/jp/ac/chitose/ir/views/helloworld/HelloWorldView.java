package jp.ac.chitose.ir.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.DataLabels;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.animations.Easing;
import com.github.appreciated.apexcharts.config.chart.animations.builder.DynamicAnimationBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.AnimationsBuilder;
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
import jp.ac.chitose.ir.views.component.ChartJS;
import jp.ac.chitose.ir.views.component.GoogleChart;

import java.security.SecureRandom;
import java.util.*;

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

        add(new H1("addon chartjs"), chartjs());
        //add(new H1("chart js"),new ChartJS());
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
        options.put("title", "成績分布");
        return new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.BAR, options);

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
        Series<Double> series = new Series<>("2023");
        series.setData(学習量);

        ApexCharts chart = ApexChartsBuilder.get().withChart(
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
    }

    private ApexCharts chartjs() {
        final ApexCharts chart = ApexChartsBuilder.get().withChart(
                ChartBuilder.get()
                        .withType(Type.LINE)
                        .withAnimations(AnimationsBuilder.get()
                                .withEnabled(false)
                                .withEasing(Easing.LINEAR)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withSeries(new Series<>(0))
                .withXaxis(XAxisBuilder.get()
                        .withCategories()
                        .withMax(10.0)
                        .build())
                .build();


        chart.setHeight("400px");
        chart.setWidth("400px");
        final SecureRandom random = new SecureRandom();
        final Series<Double> series = new Series<>("2021", 25.2, 30.2, 25.77, 45.66, 60.00, 55.5);
        final Series<Double> serie1 = new Series<>("2022", 30.3, 34.10, 20.11, 12.15, 55.66, 82.5, 64.35, 100.4, 77.66, 14.32, 25.77);
        final Series<Double> serie2 = new Series<>("2023", 25.5, 55.3, 44.5, 99.6, 10.3, 44.6, 36.6);
        final Series[] randomSeries = new Series[]{series, serie1, serie2, series};
        //chart.updateSeries(radomSeries[SECURE_RANDOM.nextInt(randomSeries .length)]);

        chart.updateSeries(randomSeries);
        return chart;
    }
}
