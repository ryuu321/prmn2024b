package jp.ac.chitose.ir.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
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
import jp.ac.chitose.ir.views.component.GoogleChart;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        GoogleChart googleChart = googleChartの使用例();
        add(googleChart);

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

}
