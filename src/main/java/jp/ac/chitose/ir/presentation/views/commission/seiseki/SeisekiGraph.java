package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.TableData;
import jp.ac.chitose.ir.application.service.commission.*;
import jp.ac.chitose.ir.presentation.component.graph.Data;
import jp.ac.chitose.ir.presentation.component.graph.GRAPH_TYPE;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

import java.util.ArrayList;

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

    //mode = 0
    public ArrayList<VerticalLayout> makeAll(){
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
        for(int i=0;i<5;i++){
            String b=tableAll.data().get(i).getName();
            chartList.add(graph(tableAll.data().get(i).getData(),b));
        }
      return chartList;
    }
    public ArrayList<VerticalLayout> makeBigAll(){
        ArrayList<VerticalLayout> chartListAll = new ArrayList<>();
        for(int i=0;i<5;i++){
            String b=tableAll.data().get(i).getName();
            chartListAll.add(bigGraph(tableAll.data().get(i).getData(),b));
        }
        return chartListAll;
    }

    public ArrayList<VerticalLayout> makeFirst(){
        ArrayList<VerticalLayout> chartList1 = new ArrayList<>();
        for(int i=0;i<5;i++){
            String b=tableFirst.data().get(i).getName();
            chartList1.add(graph(tableFirst.data().get(i).getData(),b));
        }
        return chartList1;
    }

    public ArrayList<VerticalLayout> makeBigFirst(){
        ArrayList<VerticalLayout> chartList1B = new ArrayList<>();
        for(int i=0;i<5;i++){
            String b=tableFirst.data().get(i).getName();
            chartList1B.add(bigGraph(tableFirst.data().get(i).getData(),b));
        }
        return chartList1B;
    }

    public ArrayList<VerticalLayout> makeSecond(){
        ArrayList<VerticalLayout> chartList2 = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableSecond.data().get(i).getName();
            chartList2.add(graph(tableSecond.data().get(i).getData(),b));
        }
        return chartList2;
    }

    public ArrayList<VerticalLayout> makeBigSecond(){
        ArrayList<VerticalLayout> chartList2B = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableSecond.data().get(i).getName();
            chartList2B.add(bigGraph(tableSecond.data().get(i).getData(),b));
        }
        return chartList2B;
    }

    public ArrayList<VerticalLayout> makeThird(){
        ArrayList<VerticalLayout> chartList3 = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableThird.data().get(i).getName();
            chartList3.add(graph(tableThird.data().get(i).getData(),b));
        }
        return chartList3;
    }

    public ArrayList<VerticalLayout> makeBigThird(){
        ArrayList<VerticalLayout> chartList3B = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableThird.data().get(i).getName();
            chartList3B.add(bigGraph(tableThird.data().get(i).getData(),b));
        }
        return chartList3B;
    }

    public ArrayList<VerticalLayout> makeFourth(){
        ArrayList<VerticalLayout> chartList4 = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableFourth.data().get(i).getName();
            chartList4.add(graph(tableFourth.data().get(i).getData(),b));
        }
        return chartList4;
    }

    public ArrayList<VerticalLayout> makeBigFourth(){
        ArrayList<VerticalLayout> chartList4B = new ArrayList<>();
        for(int i=0;i<4;i++){
            String b=tableFourth.data().get(i).getName();
            chartList4B.add(bigGraph(tableFourth.data().get(i).getData(),b));
        }
        return chartList4B;
    }
//mode = 1
    public ArrayList<VerticalLayout> makeAll2(){
        ArrayList<VerticalLayout> chartList= new ArrayList<>();
        chartList.add(graph(tableAll.data().get(0).getData(),"全体"));
        chartList.add(graph(tableFirst.data().get(0).getData(),"1年"));
        chartList.add(graph(tableSecond.data().get(0).getData(),"2年"));
        chartList.add(graph(tableThird.data().get(0).getData(),"3年"));
        chartList.add(graph(tableFourth.data().get(0).getData(),"4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeBigAll2(){
        ArrayList<VerticalLayout> chartList= new ArrayList<>();
        chartList.add(bigGraph(tableAll.data().get(0).getData(),"全体"));
        chartList.add(bigGraph(tableFirst.data().get(0).getData(),"1年"));
        chartList.add(bigGraph(tableSecond.data().get(0).getData(),"2年"));
        chartList.add(bigGraph(tableThird.data().get(0).getData(),"3年"));
        chartList.add(bigGraph(tableFourth.data().get(0).getData(),"4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeScience(){
        ArrayList<VerticalLayout> chartList= new ArrayList<>();
        chartList.add(graph(tableAll.data().get(1).getData(),"全体"));
        chartList.add(graph(tableSecond.data().get(1).getData(),"2年"));
        chartList.add(graph(tableThird.data().get(1).getData(),"3年"));
        chartList.add(graph(tableFourth.data().get(1).getData(),"4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeBigScience() {
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
        chartList.add(bigGraph(tableAll.data().get(1).getData(), "全体"));
        chartList.add(bigGraph(tableSecond.data().get(1).getData(), "2年"));
        chartList.add(bigGraph(tableThird.data().get(1).getData(), "3年"));
        chartList.add(bigGraph(tableFourth.data().get(1).getData(), "4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeElectronic(){
        ArrayList<VerticalLayout> chartList= new ArrayList<>();
        chartList.add(graph(tableAll.data().get(2).getData(),"全体"));
        chartList.add(graph(tableSecond.data().get(2).getData(),"2年"));
        chartList.add(graph(tableThird.data().get(2).getData(),"3年"));
        chartList.add(graph(tableFourth.data().get(2).getData(),"4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeBigElectronic() {
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
        chartList.add(bigGraph(tableAll.data().get(2).getData(), "全体"));
        chartList.add(bigGraph(tableSecond.data().get(2).getData(), "2年"));
        chartList.add(bigGraph(tableThird.data().get(2).getData(), "3年"));
        chartList.add(bigGraph(tableFourth.data().get(2).getData(), "4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeInformation(){
        ArrayList<VerticalLayout> chartList= new ArrayList<>();
        chartList.add(graph(tableAll.data().get(3).getData(),"全体"));
        chartList.add(graph(tableSecond.data().get(3).getData(),"2年"));
        chartList.add(graph(tableThird.data().get(3).getData(),"3年"));
        chartList.add(graph(tableFourth.data().get(3).getData(),"4年"));
        return chartList;
    }
    public ArrayList<VerticalLayout> makeBigInformation() {
        ArrayList<VerticalLayout> chartList = new ArrayList<>();
        chartList.add(bigGraph(tableAll.data().get(3).getData(), "全体"));
        chartList.add(bigGraph(tableSecond.data().get(3).getData(), "2年"));
        chartList.add(bigGraph(tableThird.data().get(3).getData(), "3年"));
        chartList.add(bigGraph(tableFourth.data().get(3).getData(), "4年"));
        return chartList;
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
//        FormLayout graphLayout = new FormLayout();
//        graphLayout.add(bigHistgram(a,b));
//        graphLayout.add(pie(a,b));
//        graphLayout.setResponsiveSteps(
//                new FormLayout.ResponsiveStep("0",1),
//                new FormLayout.ResponsiveStep("1050px",2)
//        );
//        layout.add(graphLayout);
        HorizontalLayout graphLayout = new HorizontalLayout();
        graphLayout.add(bigHistgram(a,b));
        graphLayout.add(pie(a,b));
        layout.add(graphLayout);
        return layout;
    }

    private ApexCharts histgram(ArrayList<Integer> a, String b){
        String[] name = {"0.0~0.5","0.5~1.0","1.0~1.5","1.5~2.0","2.0~2.5","2.5~3.0","3.0~3.5","3.5~4.0","4.0"};
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
                dataList.get(7),
                dataList.get(8));

        return Graph.Builder.get().histogram()
                .height("250px").width("300px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").animationsEnabled(false).build().getGraph();
    }
    private ApexCharts bigHistgram(ArrayList<Integer> a, String b){
        String[] name = {"0.0~0.5","0.5~1.0","1.0~1.5","1.5~2.0","2.0~2.5","2.5~3.0","3.0~3.5","3.5~4.0","4.0"};
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
                dataList.get(7),
                dataList.get(8));

        return Graph.Builder.get().histogram()
                .height("300px").width("700px").series(series).XAxisLabel("GPA").YAxisLabel("人数(人)").animationsEnabled(false).build().getGraph();
    }
    private ApexCharts pie(ArrayList<Integer> a, String b){
        String[] name = {"0以上0.5未満","0.5以上1.0未満","1.0以上1.5未満","1.5以上2.0未満","2.0以上2.5未満","2.5以上3.0未満","3.0以上3.5未満","3.5以上4.0未満","4.0"};

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
                .height("450px")
                .width("450px")
                .animationsEnabled(false)
                .colors("#1676F3","#4795F5","#71B0F7","#A0CEF9","#BCE0FA","#A8D8ED","#7FC3DD","#54ADCC")
                .build()
                .getGraph();
    }

}
