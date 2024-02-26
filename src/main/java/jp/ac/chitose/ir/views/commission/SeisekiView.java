package jp.ac.chitose.ir.views.commission;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jp.ac.chitose.ir.service.sample.SampleGpa2;
import jp.ac.chitose.ir.service.sample.SampleService;
import jp.ac.chitose.ir.service.sample.SapmleGpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SeisekiView implements View{
    private SampleService sampleService;

    public SeisekiView(SampleService sampleService){
        this.sampleService = sampleService;
    }
    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();
        H2 a = new H2("成績に関する統計データ");
        main.add(a);

        main.add(new Paragraph("成績画面に関する説明文"));

        HorizontalLayout radioAndText = new HorizontalLayout();

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("学年選択");
        radioGroup.setItems("全体", "1年", "2年","3年","4年","M1","M2");
        radioAndText.add(radioGroup);

        main.add(radioAndText);


        RadioButtonGroup<String> radioGroup2 = new RadioButtonGroup<>();
        radioGroup2.setLabel("学科を選択");
        radioGroup2.setItems("全体","応用科学生物学科", "電子光工学科", "情報システム工学科","一年生","全て");
        main.add(radioGroup2);

        radioGroup2.setVisible(false);
        radioGroup.addValueChangeListener(e -> radioGroup2.setVisible(e.getValue().equals("全体")));

        HorizontalLayout layout1 = new HorizontalLayout();
        HorizontalLayout layout2 = new HorizontalLayout();
        VerticalLayout layout3 = new VerticalLayout();

        VerticalLayout chartOfZentai = bar(sampleService.getSampleGpa().data().get(0).getData(),sampleService.getSampleGpa().data().get(0));
        VerticalLayout chartOfOuyou = bar(sampleService.getSampleGpa().data().get(1).getData(),sampleService.getSampleGpa().data().get(1));
        VerticalLayout chartOfDensi = bar(sampleService.getSampleGpa().data().get(2).getData(),sampleService.getSampleGpa().data().get(2));
        VerticalLayout chartOfZyouhou = bar(sampleService.getSampleGpa().data().get(3).getData(),sampleService.getSampleGpa().data().get(3));
        VerticalLayout chartOfItinen = bar(sampleService.getSampleGpa().data().get(4).getData(),sampleService.getSampleGpa().data().get(4));

        ArrayList<VerticalLayout> chartList = new ArrayList<>(Arrays.asList(chartOfZentai,chartOfOuyou,chartOfDensi,chartOfZyouhou,chartOfItinen));

        layout1.add(chartOfZentai,chartOfOuyou,chartOfDensi);
        layout2.add(chartOfZyouhou,chartOfItinen);
        layout3.add(layout1,layout2);
        main.add(layout3);
        layout3.setVisible(false);

        Grid<SampleGpa2> grid = hyou(sampleService.getSampleGpa2().data());
        grid.setWidth("1050px");
        grid.setHeight("300px");
        H2 text = new H2("統計データ詳細");
        text.setVisible(false);
        main.add(text);
        main.add(grid);
        grid.setVisible(false);

        radioGroup.addValueChangeListener(e -> layout3.setVisible(e.getValue().equals("全体")));
        radioGroup.addValueChangeListener(e -> grid.setVisible(e.getValue().equals("全体")));
        radioGroup.addValueChangeListener(e -> text.setVisible(e.getValue().equals("全体")));

        radioGroup2.addValueChangeListener(e -> chartOfZentai.setVisible(e.getValue().equals("全体")));
        radioGroup2.addValueChangeListener(e -> chartOfOuyou.setVisible(e.getValue().equals("応用科学生物学科")));
        radioGroup2.addValueChangeListener(e -> chartOfDensi.setVisible(e.getValue().equals("電子光工学科")));
        radioGroup2.addValueChangeListener(e -> chartOfZyouhou.setVisible(e.getValue().equals("情報システム工学科")));
        radioGroup2.addValueChangeListener(e -> chartOfItinen.setVisible(e.getValue().equals("一年生")));
        radioGroup2.addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for(VerticalLayout chart : chartList){
                    chart.setVisible(true);
                }
            }
        });
        //成績画面終わり
        return main;
    }
    private Grid<SampleGpa2> hyou(List<SampleGpa2> sample){
        Grid<SampleGpa2> grid = new Grid<>();
        grid.addColumn(SampleGpa2::subject).setHeader("学科");
        grid.addColumn(SampleGpa2::human).setHeader("人数");
        grid.addColumn(SampleGpa2::average).setHeader("平均値");
        grid.addColumn(SampleGpa2::min).setHeader("最小値");
        grid.addColumn(SampleGpa2::max).setHeader("最大値");
        grid.addColumn(SampleGpa2::std).setHeader("標準偏差");
        grid.setItems(sample);
        return grid;
    }
    private VerticalLayout bar(ArrayList<Integer> a, SapmleGpa b) {
        ArrayList<Coordinate<String,Integer>> data = new ArrayList<>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        int max = Collections.max(a);

        for(int i = 0;i < a.size();i++){
            data.add(new Coordinate<>(String.valueOf(alphabet[i]),a.get(i)));
        }
        Series<Coordinate<String, Integer>> series = new Series<>(b.getName(),
                data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),data.get(5),data.get(6),data.get(7),
                data.get(8),data.get(9),data.get(10),data.get(11),data.get(12),data.get(13),data.get(14),data.get(15)

        );

        final ApexCharts chart = ApexChartsBuilder.get().withChart(
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
                .withYaxis(YAxisBuilder.get().withMax(max).build()) // Y軸の最大値；ここでは80に設定
                .withSeries(series)
                .build();

        chart.setHeight("300px");
        chart.setWidth("400px");
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2(b.getName()));
        layout.add(chart);
        return layout;
    }
}
