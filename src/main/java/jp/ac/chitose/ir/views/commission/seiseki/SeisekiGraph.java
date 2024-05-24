package jp.ac.chitose.ir.views.commission.seiseki;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.TableData;
import jp.ac.chitose.ir.service.commission.*;
import jp.ac.chitose.ir.views.component.Data;
import jp.ac.chitose.ir.views.component.GRAPH_TYPE;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphSeries;

import java.util.ArrayList;
import java.util.List;

public class SeisekiGraph {
    private TableData<CommissionGpa> tableAll;
    private TableData<CommissionGpaFirst> tableFirst;
    private TableData<CommissionGpaSecond> tableSecond;
    private TableData<CommissionGpaThird> tableThird;
    private TableData<CommissionGpaFourth> tableFourth;


    private CommissionService commissionService;
    public SeisekiGraph(CommissionService com){
        this.commissionService=com;
        tableAll = commissionService.getCommissionGpa();
        tableFirst = commissionService.getCommissionGpaFirst();
        tableSecond = commissionService.getCommissionGpaSecond();
        tableThird = commissionService.getCommissionGpaThird();
        tableFourth = commissionService.getCommissionGpaFourth();
    }


    public ArrayList<VerticalLayout> makeAll(){
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
//        TableData<CommissionGpa> table = commissionService.getCommissionGpa();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=tableAll.data().get(i).getName();
            chartList.add(graph(tableAll.data().get(i).getData(),b));
        }
      return chartList;
    }
    public ArrayList<VerticalLayout> makeBigAll(){
        ArrayList<VerticalLayout> chartListAll = new ArrayList<>();
//        TableData<CommissionGpa> table = commissionService.getCommissionGpa();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=tableAll.data().get(i).getName();
            chartListAll.add(bigGraph(tableAll.data().get(i).getData(),b));
        }
        return chartListAll;
    }

    public ArrayList<VerticalLayout> makeFirst(){
        ArrayList<VerticalLayout> chartList1 = new ArrayList<>();
//        TableData<CommissionGpaFirst> table = commissionService.getCommissionGpaFirst();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=tableFirst.data().get(i).getName();
            chartList1.add(graph(tableFirst.data().get(i).getData(),b));
        }
        return chartList1;
    }

    public ArrayList<VerticalLayout> makeBigFirst(){
        ArrayList<VerticalLayout> chartList1B = new ArrayList<>();
//        TableData<CommissionGpaFirst> table = commissionService.getCommissionGpaFirst();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=tableFirst.data().get(i).getName();
            chartList1B.add(bigGraph(tableFirst.data().get(i).getData(),b));
        }
        return chartList1B;
    }

    public ArrayList<VerticalLayout> makeSecond(){
        ArrayList<VerticalLayout> chartList2 = new ArrayList<>();
//        TableData<CommissionGpaSecond> table = commissionService.getCommissionGpaSecond();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=tableSecond.data().get(i).getName();
            chartList2.add(graph(tableSecond.data().get(i).getData(),b));
        }
        return chartList2;
    }

    public ArrayList<VerticalLayout> makeBigSecond(){
        ArrayList<VerticalLayout> chartList2B = new ArrayList<>();
//        TableData<CommissionGpaSecond> table = commissionService.getCommissionGpaSecond();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=tableSecond.data().get(i).getName();
            chartList2B.add(bigGraph(tableSecond.data().get(i).getData(),b));
        }
        return chartList2B;
    }

    public ArrayList<VerticalLayout> makeThird(){
        ArrayList<VerticalLayout> chartList3 = new ArrayList<>();
//        TableData<CommissionGpaThird> table = commissionService.getCommissionGpaThird();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=tableThird.data().get(i).getName();
            chartList3.add(graph(tableThird.data().get(i).getData(),b));
        }
        return chartList3;
    }

    public ArrayList<VerticalLayout> makeBigThird(){
        ArrayList<VerticalLayout> chartList3B = new ArrayList<>();
//        TableData<CommissionGpaThird> table = commissionService.getCommissionGpaThird();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=tableThird.data().get(i).getName();
            chartList3B.add(bigGraph(tableThird.data().get(i).getData(),b));
        }
        return chartList3B;
    }

    public ArrayList<VerticalLayout> makeFourth(){
        ArrayList<VerticalLayout> chartList4 = new ArrayList<>();
//        TableData<CommissionGpaFourth> table = commissionService.getCommissionGpaFourth();//こうしたらロード時間短くなった。
        for(int i=0;i<4;i++){
            String b=tableFourth.data().get(i).getName();
            chartList4.add(graph(tableFourth.data().get(i).getData(),b));
        }
        return chartList4;
    }

    public ArrayList<VerticalLayout> makeBigFourth(){
        ArrayList<VerticalLayout> chartList4B = new ArrayList<>();
//        TableData<CommissionGpaFourth> table = commissionService.getCommissionGpaFourth();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=tableFourth.data().get(i).getName();
            chartList4B.add(bigGraph(tableFourth.data().get(i).getData(),b));
        }
        return chartList4B;
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


    private VerticalLayout graph(ArrayList<Integer> a, String b){
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2(b));
        HorizontalLayout graphLayout = new HorizontalLayout();
        graphLayout.add(histgram(a,b));
        layout.add(graphLayout);
        return layout;
    }
    private VerticalLayout bigGraph(ArrayList<Integer> a, String b){
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2(b));
        FormLayout graphLayout = new FormLayout();
        graphLayout.add(bigHistgram(a,b));
        graphLayout.add(pie(a,b));
        graphLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("600px",2)
        );
        layout.add(graphLayout);
        return layout;
    }

    private ApexCharts histgram(ArrayList<Integer> a, String b){
        String[] name = {"0.25","0.75","1.25","1.75","2.25","2.75","3.25","3.75"};
        ArrayList<Data<String,Integer>> dataList = new ArrayList<>();
        for(int i = 0;i < a.size();i++){
            dataList.add(new Data<>((name[i]),a.get(i)));
        }
        GraphSeries<Data<String, Integer>> series = new GraphSeries<>(b,
                dataList.get(0),
                dataList.get(1),
                dataList.get(2),
                dataList.get(3),
                dataList.get(4),
                dataList.get(5),
                dataList.get(6),
                dataList.get(7));

        return Graph.Builder.get().histogram()
                .height("250px").width("300px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").animationsEnabled(false).build().getGraph();
    }
    private ApexCharts bigHistgram(ArrayList<Integer> a,String b){
        String[] name = {"0.25","0.75","1.25","1.75","2.25","2.75","3.25","3.75"};
        ArrayList<Data<String,Integer>> dataList = new ArrayList<>();
        for(int i = 0;i < a.size();i++){
            dataList.add(new Data<>((name[i]),a.get(i)));
        }
        GraphSeries<Data<String, Integer>> series = new GraphSeries<>(b,
                dataList.get(0),
                dataList.get(1),
                dataList.get(2),
                dataList.get(3),
                dataList.get(4),
                dataList.get(5),
                dataList.get(6),
                dataList.get(7));
//width:もともと700px
        return Graph.Builder.get().histogram()
                .height("250px").width("300px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").animationsEnabled(false).build().getGraph();
    }
    private ApexCharts pie(ArrayList<Integer> a, String b){
        String[] name = {"0以上0.5未満","0.5以上1.0未満","1.0以上1.5未満","1.5以上2.0未満","2.0以上2.5未満","2.5以上3.0未満","3.0以上3.5未満","3.5以上"};

        ArrayList<Double> dataList = new ArrayList<>();
        for(int data : a){
            dataList.add((double)data);
        }
        Double[] datalist = new Double[8];
        for(int i = 0; i < datalist.length;i++){
            datalist[i] = dataList.get(i);
        }

        return Graph.Builder.get()
                .graphType(GRAPH_TYPE.PIE)
                .doubles(datalist)
                .labels(name)
                .height("250px")
                .animationsEnabled(false)
                .colors("#0000FF","#000080","#008080","#008000","#00FF00","#00FFFF","#FFFF00","#FF0000")
                .build()
                .getGraph();
    }

}
