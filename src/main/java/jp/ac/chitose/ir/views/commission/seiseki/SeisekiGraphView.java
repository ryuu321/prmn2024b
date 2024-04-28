package jp.ac.chitose.ir.views.commission.seiseki;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.commission.*;

import java.util.ArrayList;

public class SeisekiGraphView {
    SeisekiGraph seisekiGraph;
    Seiseki seiseki;
    CommissionService commissionService;


    public SeisekiGraphView(CommissionService com,Seiseki seiseki){
        this.commissionService=com;
        this.seisekiGraph=new SeisekiGraph(this.commissionService);
        this.seiseki=seiseki;
    }

    public VerticalLayout view(){
        VerticalLayout main=new VerticalLayout();//グラフ全体のレイアウトを返す
        VerticalLayout all = new VerticalLayout();//全体を表示
        VerticalLayout first = new VerticalLayout();//1年生
        VerticalLayout second = new VerticalLayout();//2年生
        VerticalLayout third = new VerticalLayout();//３年生
        VerticalLayout fourth = new VerticalLayout();//４年生

        all.setVisible(false);
        first.setVisible(false);
        second.setVisible(false);
        third.setVisible(false);
        fourth.setVisible(false);

        seiseki.getR1().addValueChangeListener(e->all.setVisible(e.getValue().equals("全体")));
        seiseki.getR1().addValueChangeListener(e->first.setVisible(e.getValue().equals("1年")));
        seiseki.getR1().addValueChangeListener(e->second.setVisible(e.getValue().equals("2年")));
        seiseki.getR1().addValueChangeListener(e->third.setVisible(e.getValue().equals("3年")));
        seiseki.getR1().addValueChangeListener(e->fourth.setVisible(e.getValue().equals("4年")));

        main.add(all,first,second,third,fourth);
        //ここからall
        HorizontalLayout layout=new HorizontalLayout();
        HorizontalLayout layout2=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList=seisekiGraph.makeAll();
        ArrayList<VerticalLayout> chartList1=seisekiGraph.makeBigAll();

        for(VerticalLayout chart: chartList){
            layout.add(chart);
            //layout2.add(chart);
        }
        for(VerticalLayout chart: chartList1){
            //layout.add(chart);
            layout2.add(chart);
            chart.setVisible(false);
        }


        all.add(layout2);
        all.add(layout);

//        seiseki.getR1().addValueChangeListener(e->all.setVisible(e.getValue().equals("全体")));
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(0).setVisible(!(e.getValue().equals("全体")));
            chartList1.get(0).setVisible(e.getValue().equals("全体"));
                }
            );
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(1).setVisible(!(e.getValue().equals("応用化学生物学科")));
            chartList1.get(1).setVisible(e.getValue().equals("応用化学生物学科"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(2).setVisible(!(e.getValue().equals("電子光工学科")));
            chartList1.get(2).setVisible(e.getValue().equals("電子光工学科"));

        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(3).setVisible(!(e.getValue().equals("情報システム工学科")));
            chartList1.get(3).setVisible(e.getValue().equals("情報システム工学科"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(4).setVisible(!(e.getValue().equals("一年生")));
            chartList1.get(4).setVisible(e.getValue().equals("一年生"));

        });
        seiseki.getR2().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList) {
                    chart.setVisible(true);
                }
            }
        });
        //ここまでall

        //ここからfirst

        //ここまでfirst

        //ここからsecond

        //ここまでsecond

        //ここからthird

        //ここまでthird

        //ここからfourth

        //ここまでfourth

        //ここから基本統計量
        Grid<CommissionGpa2> gridAll = SeisekiTable.getTable(commissionService.getCommissionGpa2().data());
        Grid<CommissionGpa2First> gridFirst = SeisekiTable.getTableFirst(commissionService.getCommissionGpa2First().data());
        Grid<CommissionGpa2Second> gridSecond = SeisekiTable.getTableSecond(commissionService.getCommissionGpa2Second().data());
        Grid<CommissionGpa2Third> gridThird = SeisekiTable.getTableThird(commissionService.getCommissionGpa2Third().data());
        Grid<CommissionGpa2Fourth> gridFourth = SeisekiTable.getTableFourth(commissionService.getCommissionGpa2Fourth().data());

        all.add(gridAll);
        first.add(gridFirst);
        second.add(gridSecond);
        third.add(gridThird);
        fourth.add(gridFourth);
        //ここまで基本統計量

        return main;
    }
}
