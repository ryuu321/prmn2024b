package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.commission.*;

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
        ArrayList<VerticalLayout> chartListB=seisekiGraph.makeBigAll();

        for(VerticalLayout chart: chartList){
            layout.add(chart);
            //layout2.add(chart);
        }
        for(VerticalLayout chart: chartListB){
            //layout.add(chart);
            layout2.add(chart);
            chart.setVisible(false);
        }


        all.add(layout2);
        all.add(layout);


        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(0).setVisible(!(e.getValue().equals("全体")));
            chartListB.get(0).setVisible(e.getValue().equals("全体"));
                }
            );
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(1).setVisible(!(e.getValue().equals("応用化学生物学科")));
            chartListB.get(1).setVisible(e.getValue().equals("応用化学生物学科"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(2).setVisible(!(e.getValue().equals("電子光工学科")));
            chartListB.get(2).setVisible(e.getValue().equals("電子光工学科"));

        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(3).setVisible(!(e.getValue().equals("情報システム工学科")));
            chartListB.get(3).setVisible(e.getValue().equals("情報システム工学科"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(4).setVisible(!(e.getValue().equals("一年生")));
            chartListB.get(4).setVisible(e.getValue().equals("一年生"));

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
        HorizontalLayout layout3=new HorizontalLayout();
        HorizontalLayout layout4=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList1=seisekiGraph.makeFirst();
        ArrayList<VerticalLayout> chartList1B=seisekiGraph.makeBigFirst();

        for(VerticalLayout chart: chartList1){
            layout4.add(chart);
            //layout2.add(chart);
        }
        for(VerticalLayout chart: chartList1B){
            //layout.add(chart);
            layout3.add(chart);
            chart.setVisible(false);
        }


        first.add(layout3);
        first.add(layout4);


        seiseki.getR3().addValueChangeListener(e -> {
                    chartList1.get(0).setVisible(!(e.getValue().equals("学年全体")));
                    chartList1B.get(0).setVisible(e.getValue().equals("学年全体"));
                }
        );
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(1).setVisible(!(e.getValue().equals("Aクラス")));
            chartList1B.get(1).setVisible(e.getValue().equals("Aクラス"));
        });
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(2).setVisible(!(e.getValue().equals("Bクラス")));
            chartList1B.get(2).setVisible(e.getValue().equals("Bクラス"));

        });
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(3).setVisible(!(e.getValue().equals("Cクラス")));
            chartList1B.get(3).setVisible(e.getValue().equals("Cクラス"));
        });
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(4).setVisible(!(e.getValue().equals("Dクラス")));
            chartList1B.get(4).setVisible(e.getValue().equals("Dクラス"));

        });
        seiseki.getR3().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList1) {
                    chart.setVisible(true);
                }
            }
        });
        //ここまでfirst

        //ここからsecond
        HorizontalLayout layout5=new HorizontalLayout();
        HorizontalLayout layout6=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList2=seisekiGraph.makeSecond();
        ArrayList<VerticalLayout> chartList2B=seisekiGraph.makeBigSecond();

        for(VerticalLayout chart: chartList2){
            layout6.add(chart);
        }
        for(VerticalLayout chart: chartList2B){
            layout5.add(chart);
            chart.setVisible(false);
        }


        second.add(layout5);
        second.add(layout6);


        seiseki.getR4().addValueChangeListener(e -> {
                    chartList2.get(0).setVisible(!(e.getValue().equals("学年全体")));
                    chartList2B.get(0).setVisible(e.getValue().equals("学年全体"));
                }
        );
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(1).setVisible(!(e.getValue().equals("応用化学生物学科")));
            chartList2B.get(1).setVisible(e.getValue().equals("応用化学生物学科"));
        });
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(2).setVisible(!(e.getValue().equals("電子光工学科")));
            chartList2B.get(2).setVisible(e.getValue().equals("電子光工学科"));

        });
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(3).setVisible(!(e.getValue().equals("情報システム工学科")));
            chartList2B.get(3).setVisible(e.getValue().equals("情報システム工学科"));
        });

        seiseki.getR2().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList2) {
                    chart.setVisible(true);
                }
            }
        });
        //ここまでsecond

        //ここからthird
        HorizontalLayout layout7=new HorizontalLayout();
        HorizontalLayout layout8=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList3=seisekiGraph.makeThird();
        ArrayList<VerticalLayout> chartList3B=seisekiGraph.makeBigThird();

        for(VerticalLayout chart: chartList3){
            layout8.add(chart);
        }
        for(VerticalLayout chart: chartList3B){
            layout7.add(chart);
            chart.setVisible(false);
        }


        third.add(layout7);
        third.add(layout8);


        seiseki.getR5().addValueChangeListener(e -> {
                    chartList3.get(0).setVisible(!(e.getValue().equals("学年全体")));
                    chartList3B.get(0).setVisible(e.getValue().equals("学年全体"));
                }
        );
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(1).setVisible(!(e.getValue().equals("応用化学生物学科")));
            chartList3B.get(1).setVisible(e.getValue().equals("応用化学生物学科"));
        });
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(2).setVisible(!(e.getValue().equals("電子光工学科")));
            chartList3B.get(2).setVisible(e.getValue().equals("電子光工学科"));

        });
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(3).setVisible(!(e.getValue().equals("情報システム工学科")));
            chartList3B.get(3).setVisible(e.getValue().equals("情報システム工学科"));
        });

        seiseki.getR5().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList3) {
                    chart.setVisible(true);
                }
            }
        });
        //ここまでthird

        //ここからfourth
        HorizontalLayout layout9=new HorizontalLayout();
        HorizontalLayout layout0=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList4=seisekiGraph.makeFourth();
        ArrayList<VerticalLayout> chartList4B=seisekiGraph.makeBigFourth();

        for(VerticalLayout chart: chartList4){
            layout9.add(chart);
        }
        for(VerticalLayout chart: chartList4B){
            layout0.add(chart);
            chart.setVisible(false);
        }


        fourth.add(layout0);
        fourth.add(layout9);


        seiseki.getR6().addValueChangeListener(e -> {
                    chartList4.get(0).setVisible(!(e.getValue().equals("学年全体")));
                    chartList4B.get(0).setVisible(e.getValue().equals("学年全体"));
                }
        );
        seiseki.getR6().addValueChangeListener(e -> {
            chartList4.get(1).setVisible(!(e.getValue().equals("応用化学生物学科")));
            chartList4B.get(1).setVisible(e.getValue().equals("応用化学生物学科"));
        });
        seiseki.getR6().addValueChangeListener(e -> {
            chartList4.get(2).setVisible(!(e.getValue().equals("電子光工学科")));
            chartList4B.get(2).setVisible(e.getValue().equals("電子光工学科"));

        });
        seiseki.getR6().addValueChangeListener(e -> {
            chartList4.get(3).setVisible(!(e.getValue().equals("情報システム工学科")));
            chartList4B.get(3).setVisible(e.getValue().equals("情報システム工学科"));
        });

        seiseki.getR6().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList4) {
                    chart.setVisible(true);
                }
            }
        });
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
