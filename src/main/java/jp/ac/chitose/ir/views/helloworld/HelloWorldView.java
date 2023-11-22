package jp.ac.chitose.ir.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
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

import java.util.Arrays;
import java.util.List;

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
        var sampleOnes = sampleService.getSampleOne();
        System.out.println("sampleOnes is "+ sampleOnes);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Col("string", "month"),
                new GoogleChart.Col("number", "score")
        );

        // 2. 行を設定
        /*
        var rows = Arrays.asList(
                new GoogleChart.Row(new GoogleChart.RowValue( "jan") , new GoogleChart.RowValue(15)),
                new GoogleChart.Row(new GoogleChart.RowValue("oct") , new GoogleChart.RowValue(50))
        );
         */

        var rows = sampleOnes.data().stream()
                .map(sampleOne ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(sampleOne.month()),
                                new GoogleChart.RowValue(sampleOne.score())
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
        var sampleTwos = sampleService.getSampleTwo();
        System.out.println("sampleOnes is "+ sampleTwos);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Col("string", "年度"),
                new GoogleChart.Col("number", "秀"),
                new GoogleChart.Col("number", "優"),
                new GoogleChart.Col("number", "良"),
                new GoogleChart.Col("number", "可"),
                new GoogleChart.Col("number", "不可"),
                new GoogleChart.Col("number", "欠席")
        );

        var rows = sampleTwos.data().stream()
                .map(sampleTwo ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(sampleTwo.年度()),
                                new GoogleChart.RowValue(sampleTwo.秀()),
                                new GoogleChart.RowValue(sampleTwo.優()),
                                new GoogleChart.RowValue(sampleTwo.良()),
                                new GoogleChart.RowValue(sampleTwo.可()),
                                new GoogleChart.RowValue(sampleTwo.不可()),
                                new GoogleChart.RowValue(sampleTwo.欠席())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        var options = "{\"isStacked\": \"percent\"}";
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.BAR, options));

    }

    void アンケート分析の散布図() {
        // グラフの表示の仕方の参考実装。GoogleChartクラス
        var sampleThrees = sampleService.getSampleThrees();
        System.out.println("sampleOnes is "+ sampleThrees);

        // 1. カラムを設定
        var cols = Arrays.asList(
                new GoogleChart.Col("string", "年度"),
                new GoogleChart.Col("number", "難易度")
        );

        var rows = sampleThrees.data().stream()
                .map(sampleThree ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(sampleThree.年度()),
                                new GoogleChart.RowValue(sampleThree.難易度())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER));

        cols = Arrays.asList(
                new GoogleChart.Col("string", "年度"),
                new GoogleChart.Col("number", "学習量")
        );

        rows = sampleThrees.data().stream()
                .map(sampleThree ->
                        new GoogleChart.Row(
                                new GoogleChart.RowValue(sampleThree.年度()),
                                new GoogleChart.RowValue(sampleThree.学習量())
                        )
                )
                .toList();

        // 3. カラムと行をGoogleChartに設定して表示
        add(new GoogleChart(cols, rows, GoogleChart.CHART_TYPE.SCATTER));

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        // javascriptを直書きして動作の確認をとった。この方法は非推奨。推奨はGoogleChartクラスを利用すること。
        UI ui = getUI().get();
        ui.getPage().executeJs(
                """                                        
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawChart1);
                                        
                        function drawChart1() {
                                
                            // Create the data table.
                            var data = new google.visualization.DataTable();
                            data.addColumn('string', '月');
                            data.addColumn('number', 'PV');
                            data.addRows([
                              ['9月', 10],
                              ['10月', 30],
                              ['11月', 100],
                              ['12月', 200],
                              ['1月', 300]
                            ]);
                                    
                            // Set chart options
                            var options = {
                              'title':'ブログのPV推移',
                              'height':300
                            };
                                                    
                            // Instantiate and draw our chart, passing in some options.
                            var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                            chart.draw(data, options);
                        }
                        """);
    }
}
