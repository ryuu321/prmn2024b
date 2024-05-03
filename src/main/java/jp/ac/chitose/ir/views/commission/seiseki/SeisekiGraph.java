package jp.ac.chitose.ir.views.commission.seiseki;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jp.ac.chitose.ir.service.TableData;
import jp.ac.chitose.ir.service.commission.*;
import jp.ac.chitose.ir.views.component.Data;
import jp.ac.chitose.ir.views.component.GRAPH_TYPE;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphSeries;

import java.util.ArrayList;
import java.util.List;

public class SeisekiGraph {
    private RadioButtonGroup<String> r;
    private ArrayList<VerticalLayout> chartList;
    private ArrayList<VerticalLayout> chartList1;
    private ArrayList<VerticalLayout> chartList2;
    private ArrayList<VerticalLayout> chartList3;
    private ArrayList<VerticalLayout> chartList4;

    private CommissionService commissionService;
    public SeisekiGraph(CommissionService com){
        this.commissionService=com;
        this.chartList=new ArrayList<>();
        this.chartList1=new ArrayList<>();
        this.chartList2=new ArrayList<>();
        this.chartList3=new ArrayList<>();
        this.chartList4=new ArrayList<>();

    }


    public ArrayList<VerticalLayout> makeAll(){
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
        TableData<CommissionGpa> table = commissionService.getCommissionGpa();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=table.data().get(i).getName();
            chartList.add(graph(table.data().get(i).getData(),b));
        }
      return chartList;
    }
    public ArrayList<VerticalLayout> makeBigAll(){
        ArrayList<VerticalLayout> chartListAll = new ArrayList<>();
        TableData<CommissionGpa> table = commissionService.getCommissionGpa();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=table.data().get(i).getName();
            chartListAll.add(bigGraph(table.data().get(i).getData(),b));
        }
        return chartListAll;
    }

    public ArrayList<VerticalLayout> makeFirst(){
        ArrayList<VerticalLayout> chartList1 = new ArrayList<>();
        TableData<CommissionGpaFirst> table = commissionService.getCommissionGpaFirst();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=table.data().get(i).getName();
            chartList1.add(graph(table.data().get(i).getData(),b));
        }
        return chartList1;
    }

    public ArrayList<VerticalLayout> makeBigFirst(){
        ArrayList<VerticalLayout> chartList1B = new ArrayList<>();
        TableData<CommissionGpaFirst> table = commissionService.getCommissionGpaFirst();//こうしたらロード時間短くなった
        for(int i=0;i<5;i++){
            String b=table.data().get(i).getName();
            chartList1B.add(bigGraph(table.data().get(i).getData(),b));
        }
        return chartList1B;
    }

    public ArrayList<VerticalLayout> makeSecond(){
        ArrayList<VerticalLayout> chartList2 = new ArrayList<>();
        TableData<CommissionGpaSecond> table = commissionService.getCommissionGpaSecond();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList2.add(graph(table.data().get(i).getData(),b));
        }
        return chartList2;
    }

    public ArrayList<VerticalLayout> makeBigSecond(){
        ArrayList<VerticalLayout> chartList2B = new ArrayList<>();
        TableData<CommissionGpaSecond> table = commissionService.getCommissionGpaSecond();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList2B.add(bigGraph(table.data().get(i).getData(),b));
        }
        return chartList2B;
    }

    public ArrayList<VerticalLayout> makeThird(){
        ArrayList<VerticalLayout> chartList3 = new ArrayList<>();
        TableData<CommissionGpaThird> table = commissionService.getCommissionGpaThird();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList3.add(graph(table.data().get(i).getData(),b));
        }
        return chartList3;
    }

    public ArrayList<VerticalLayout> makeBigThird(){
        ArrayList<VerticalLayout> chartList3B = new ArrayList<>();
        TableData<CommissionGpaThird> table = commissionService.getCommissionGpaThird();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList3B.add(bigGraph(table.data().get(i).getData(),b));
        }
        return chartList3B;
    }

    public ArrayList<VerticalLayout> makeFourth(){
        ArrayList<VerticalLayout> chartList4 = new ArrayList<>();
        TableData<CommissionGpaFourth> table = commissionService.getCommissionGpaFourth();//こうしたらロード時間短くなった。
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList4.add(graph(table.data().get(i).getData(),b));
        }
        return chartList4;
    }

    public ArrayList<VerticalLayout> makeBigFourth(){
        ArrayList<VerticalLayout> chartList4B = new ArrayList<>();
        TableData<CommissionGpaFourth> table = commissionService.getCommissionGpaFourth();//こうしたらロード時間短くなった
        for(int i=0;i<4;i++){
            String b=table.data().get(i).getName();
            chartList4B.add(bigGraph(table.data().get(i).getData(),b));
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
        HorizontalLayout graphLayout = new HorizontalLayout();
        graphLayout.add(bigHistgram(a,b));
        graphLayout.add(band(a,b));
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
                .height("250px").width("300px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").build().getGraph();
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

        return Graph.Builder.get().histogram()
                .height("250px").width("700px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").build().getGraph();
    }
    private ApexCharts band(ArrayList<Integer> a, String b){
        String[] name = {"0.25","0.75","1.25","1.75","2.25","2.75","3.25","3.75"};

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
                .build()
                .getGraph();


    }


}
