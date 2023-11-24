package jp.ac.chitose.ir.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jp.ac.chitose.ir.service.*;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.GoogleChart;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    private HelloService helloService;

    private SampleService sampleService;

    public HelloWorldView(HelloService helloService, SampleService sampleService) {
        this.helloService = helloService;
        this.sampleService = sampleService;

        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Hello reply = helloService.sayHello(new HelloService.SayHelloRequestBody(name.getValue()));
            reply.message();
            add(new H6(reply.message()));
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);


        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        //add(name, sayHello);


        //add(new Hr());
        //HelloWorld helloWorld = new HelloWorld();
        //add(helloWorld);

        viewGoogleChart();
        年度別成績分布();
        アンケート分析の散布図();

        var div = new Div();
        div.setId("chart_div");
        add(div);
    }

    void viewGoogleChart() {
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
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.AREA));

    }

    void 年度別成績分布() {
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
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.BAR, options));

    }

    void アンケート分析の散布図() {
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
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER));

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
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER));

    }

}
