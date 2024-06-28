package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.commission.CommissionGpa;
import jp.ac.chitose.ir.application.service.commission.CommissionGpa2;
import jp.ac.chitose.ir.application.service.commission.CommissionService;
import jp.ac.chitose.ir.presentation.component.graph.Data;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

import java.util.ArrayList;
import java.util.List;

@PermitAll
public class SeisekiView implements View {
    private CommissionService commissionService;

    public SeisekiView(CommissionService commissionService){
        this.commissionService = commissionService;
    }
    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();
        H1 a = new H1("成績に関する統計データ");
        main.add(a);

        main.add(new Paragraph("成績画面に関する説明文"));

        Seiseki seiseki=new Seiseki();
        //VerticalLayout seisekiLayout=seiseki.view();
        //main.add(seisekiLayout);
        main.add(seiseki.view());


        //HorizontalLayout radioAndText = new HorizontalLayout();

        //RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        //radioGroup.setLabel("学年選択");
        //radioGroup.setItems("全体", "1年", "2年","3年","4年","M1","M2");
        //radioAndText.add(radioGroup);

        //main.add(radioAndText);

        //RadioButtonGroup<String> radioGroup2 = new RadioButtonGroup<>();
        //radioGroup2.setLabel("学科を選択");
        //radioGroup2.setItems("全体","応用科学生物学科", "電子光工学科", "情報システム工学科","一年生","全て");
        //main.add(radioGroup2);

        //radioGroup2.setVisible(false);
        //radioGroup.addValueChangeListener(e -> radioGroup2.setVisible(e.getValue().equals("全体")));

        //RadioButtonGroup<String> radioGroup3 = new RadioButtonGroup<>();
        //radioGroup3.setLabel("クラスを選択");
        //radioGroup3.setItems("学年全体","Aクラス", "Bクラス", "Cクラス","Dクラス","全て");
        //main.add(radioGroup3);

        //radioGroup3.setVisible(false);
        //radioGroup.addValueChangeListener(e -> radioGroup3.setVisible(e.getValue().equals("1年")));

        //RadioButtonGroup<String> radioGroup4 = new RadioButtonGroup<>();
        //radioGroup4.setLabel("学科を選択");
        //radioGroup4.setItems("学年全体","応用科学生物学科", "電子光工学科", "情報システム工学科","全て");
        //main.add(radioGroup4);

        //radioGroup4.setVisible(false);
        //radioGroup.addValueChangeListener(e -> radioGroup4.setVisible(e.getValue().equals("2年")));

        //RadioButtonGroup<String> radioGroup5 = new RadioButtonGroup<>();
        //radioGroup5.setLabel("学科を選択");
        //radioGroup5.setItems("学年全体","応用科学生物学科", "電子光工学科", "情報システム工学科","全て");
        //main.add(radioGroup5);

        //radioGroup5.setVisible(false);
        //radioGroup.addValueChangeListener(e -> radioGroup5.setVisible(e.getValue().equals("3年")));

        //RadioButtonGroup<String> radioGroup6 = new RadioButtonGroup<>();
        //radioGroup6.setLabel("学科を選択");
        //radioGroup6.setItems("学年全体","応用科学生物学科", "電子光工学科", "情報システム工学科","全て");
        //main.add(radioGroup6);

        //radioGroup6.setVisible(false);
        //radioGroup.addValueChangeListener(e -> radioGroup6.setVisible(e.getValue().equals("4年")));

        //ここまではSeisekiクラスで実装済み

        //HorizontalLayout layout1 = new HorizontalLayout();
        //HorizontalLayout layout2 = new HorizontalLayout();
        //VerticalLayout layout3 = new VerticalLayout();

        //VerticalLayout chartOfZentai = graph(commissionService.getCommissionGpa().data().get(0).getData(),commissionService.getCommissionGpa().data().get(0));
        //VerticalLayout chartOfOuyou = graph(commissionService.getCommissionGpa().data().get(1).getData(),commissionService.getCommissionGpa().data().get(1));
        //VerticalLayout chartOfDensi = graph(commissionService.getCommissionGpa().data().get(2).getData(),commissionService.getCommissionGpa().data().get(2));
        //VerticalLayout chartOfZyouhou = graph(commissionService.getCommissionGpa().data().get(3).getData(),commissionService.getCommissionGpa().data().get(3));
        //VerticalLayout chartOfItinen = graph(commissionService.getCommissionGpa().data().get(4).getData(),commissionService.getCommissionGpa().data().get(4));

        //ArrayList<VerticalLayout> chartList = new ArrayList<>(Arrays.asList(chartOfZentai,chartOfOuyou,chartOfDensi,chartOfZyouhou,chartOfItinen));

        //layout1.add(chartOfZentai,chartOfOuyou,chartOfDensi);
        //layout2.add(chartOfZyouhou,chartOfItinen);
        //layout3.add(layout1,layout2);
        //main.add(layout3);
        //layout3.setVisible(false);

        SeisekiGraphView seisekiGraphView=new SeisekiGraphView(commissionService,seiseki);
        //SeisekiGraph seisekiGraph=new SeisekiGraph(commissionService);
        //main.add(seisekiGraph.makeAll());
        main.add(seisekiGraphView.view());
        //Grid<CommissionGpa2> grid = hyou(commissionService.getCommissionGpa2().data());
        //grid.setWidth("1050px");
        //grid.setHeight("300px");
        //H2 text = new H2("統計データ詳細");
        //text.setVisible(false);
        //main.add(text);
        //main.add(grid);
        //grid.setVisible(false);

        //seiseki.getR1().addValueChangeListener(e -> layout3.setVisible(e.getValue().equals("全体")));
        //seiseki.getR1().addValueChangeListener(e -> grid.setVisible(e.getValue().equals("全体")));
        //seiseki.getR1().addValueChangeListener(e -> text.setVisible(e.getValue().equals("全体")));

        //seiseki.getR2().addValueChangeListener(e -> chartOfZentai.setVisible(e.getValue().equals("全体")));
        //seiseki.getR2().addValueChangeListener(e -> chartOfOuyou.setVisible(e.getValue().equals("応用化学生物学科")));
        //seiseki.getR2().addValueChangeListener(e -> chartOfDensi.setVisible(e.getValue().equals("電子光工学科")));
        //seiseki.getR2().addValueChangeListener(e -> chartOfZyouhou.setVisible(e.getValue().equals("情報システム工学科")));
        //seiseki.getR2().addValueChangeListener(e -> chartOfItinen.setVisible(e.getValue().equals("一年生")));
        //seiseki.getR2().addValueChangeListener(e -> {
        //    if (e.getValue().equals("全て")) {
        //        for(VerticalLayout chart : chartList){
        //            chart.setVisible(true);
        //        }
        //    }
        //});
        //成績画面終わり
        return main;
    }
    private Grid<CommissionGpa2> hyou(List<CommissionGpa2> sample){
        Grid<CommissionGpa2> grid = new Grid<>();
        grid.addColumn(CommissionGpa2::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2::human).setHeader("人数");
        grid.addColumn(CommissionGpa2::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2::std).setHeader("標準偏差");
        grid.setItems(sample);
        return grid;
    }


    private VerticalLayout graph(ArrayList<Integer> a, CommissionGpa b){
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2(b.getName()));
        HorizontalLayout graphLayout = new HorizontalLayout();
        graphLayout.add(histgram(a,b));
        layout.add(graphLayout);
        return layout;
    }

    private ApexCharts histgram(ArrayList<Integer> a, CommissionGpa b) {
        String[] name = {"0.25", "0.75", "1.25", "1.75", "2.25", "2.75", "3.25", "3.75"};
        ArrayList<Data<String, Integer>> dataList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            dataList.add(new Data<>((name[i]), a.get(i)));
        }
        GraphSeries<Data<String, Integer>> series = new GraphSeries<>(b.getName(),
                dataList.get(0),
                dataList.get(1),
                dataList.get(2),
                dataList.get(3),
                dataList.get(4),
                dataList.get(5),
                dataList.get(6),
                dataList.get(7));

        return Graph.Builder.get().histogram()
                .height("300px").width("400px").series(series).dataLabelsEnabled(false).build().getGraph();
    }

}
