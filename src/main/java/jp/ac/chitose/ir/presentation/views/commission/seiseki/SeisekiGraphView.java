package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.commission.*;

import java.util.ArrayList;

public class SeisekiGraphView {
    SeisekiGraph seisekiGraph;
    Seiseki seiseki;
    private GradeService gradeService;


    public SeisekiGraphView(Seiseki seiseki, GradeService gradeService){
        this.gradeService=gradeService;
        this.seisekiGraph=new SeisekiGraph(this.gradeService);
        this.seiseki=seiseki;

    }

    public VerticalLayout view(){
        int mode = seiseki.getMode();
        VerticalLayout layout = new VerticalLayout();
        if(mode == 0){
            layout = compareBySubject();
        }
        else if(mode == 1){
            layout = compareByYear();
        }
        return layout;

    }

    private VerticalLayout compareBySubject(){
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
        FormLayout layout = getUnderLayout();
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
            chartList.get(4).setVisible(!(e.getValue().equals("共通教育")));
            chartListB.get(4).setVisible(e.getValue().equals("共通教育"));

        });

        //ここまでall

        //ここからfirst
        HorizontalLayout layout3=new HorizontalLayout();
        FormLayout layout4=getUnderLayout();
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


        //ここからsecond
        HorizontalLayout layout5=new HorizontalLayout();
        FormLayout layout6 = getUnderLayout();
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


        //ここまでsecond

        //ここからthird
        HorizontalLayout layout7=new HorizontalLayout();
        FormLayout layout8=getUnderLayout();
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


        //ここまでthird

        //ここからfourth
        FormLayout layout9=getUnderLayout();
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


        //ここまでfourth

        //ここから基本統計量
        SeisekiTable table = new SeisekiTable(gradeService);
        Grid<GradeGpaStat> gridAll = table.getTableYearFirstAll();
        Grid<GradeGpaStat> gridFirst = table.getTableFirst();
        Grid<GradeGpaStat> gridSecond = table.getTableSecond();
        Grid<GradeGpaStat> gridThird = table.getTableThird();
        Grid<GradeGpaStat> gridFourth = table.getTableFourth();

        H3 str = new H3("基本統計量");
        H3 str1 = new H3("基本統計量");
        H3 str2 = new H3("基本統計量");
        H3 str3 = new H3("基本統計量");
        H3 str4 = new H3("基本統計量");
        all.add(str,gridAll);
        first.add(str1,gridFirst);
        second.add(str2,gridSecond);
        third.add(str3,gridThird);
        fourth.add(str4,gridFourth);
        //ここまで基本統計量
        return main;
    }
    private VerticalLayout compareByYear(){
        VerticalLayout main = new VerticalLayout();
        VerticalLayout all = new VerticalLayout();
        VerticalLayout science = new VerticalLayout();
        VerticalLayout electronic = new VerticalLayout();
        VerticalLayout information = new VerticalLayout();

        all.setVisible(false);
        science.setVisible(false);
        electronic.setVisible(false);
        information.setVisible(false);

        main.add(all,science,electronic,information);

        seiseki.getR1().addValueChangeListener(e->all.setVisible(e.getValue().equals("全体")));
        seiseki.getR1().addValueChangeListener(e->science.setVisible(e.getValue().equals("応用化学生物学科")));
        seiseki.getR1().addValueChangeListener(e->electronic.setVisible(e.getValue().equals("電子光工学科")));
        seiseki.getR1().addValueChangeListener(e->information.setVisible(e.getValue().equals("情報システム工学科")));

        //ここからall
        FormLayout layout = getUnderLayout();
        HorizontalLayout layout2=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList=seisekiGraph.makeAll2();
        ArrayList<VerticalLayout> chartListB=seisekiGraph.makeBigAll2();

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
            chartList.get(1).setVisible(!(e.getValue().equals("1年")));
            chartListB.get(1).setVisible(e.getValue().equals("1年"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(2).setVisible(!(e.getValue().equals("2年")));
            chartListB.get(2).setVisible(e.getValue().equals("2年"));

        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(3).setVisible(!(e.getValue().equals("3年")));
            chartListB.get(3).setVisible(e.getValue().equals("3年"));
        });
        seiseki.getR2().addValueChangeListener(e -> {
            chartList.get(4).setVisible(!(e.getValue().equals("4年")));
            chartListB.get(4).setVisible(e.getValue().equals("4年"));

        });

        //ここまでall

        //ここからscience
        HorizontalLayout layout3=new HorizontalLayout();
        FormLayout layout4=getUnderLayout();
        ArrayList<VerticalLayout> chartList1=seisekiGraph.makeScience();
        ArrayList<VerticalLayout> chartList1B=seisekiGraph.makeBigScience();

        for(VerticalLayout chart: chartList1){
            layout4.add(chart);
            //layout2.add(chart);
        }
        for(VerticalLayout chart: chartList1B){
            //layout.add(chart);
            layout3.add(chart);
            chart.setVisible(false);
        }


        science.add(layout3);
        science.add(layout4);


        seiseki.getR3().addValueChangeListener(e -> {
                    chartList1.get(0).setVisible(!(e.getValue().equals("全体")));
                    chartList1B.get(0).setVisible(e.getValue().equals("全体"));
                }
        );
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(1).setVisible(!(e.getValue().equals("2年")));
            chartList1B.get(1).setVisible(e.getValue().equals("2年"));
        });
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(2).setVisible(!(e.getValue().equals("3年")));
            chartList1B.get(2).setVisible(e.getValue().equals("3年"));

        });
        seiseki.getR3().addValueChangeListener(e -> {
            chartList1.get(3).setVisible(!(e.getValue().equals("4年")));
            chartList1B.get(3).setVisible(e.getValue().equals("4年"));
        });


        //ここまでscience

        //ここからelectronic
        HorizontalLayout layout5=new HorizontalLayout();
        FormLayout layout6 = getUnderLayout();
        ArrayList<VerticalLayout> chartList2=seisekiGraph.makeElectronic();
        ArrayList<VerticalLayout> chartList2B=seisekiGraph.makeBigElectronic();

        for(VerticalLayout chart: chartList2){
            layout6.add(chart);
        }
        for(VerticalLayout chart: chartList2B){
            layout5.add(chart);
            chart.setVisible(false);
        }


        electronic.add(layout5);
        electronic.add(layout6);


        seiseki.getR4().addValueChangeListener(e -> {
                    chartList2.get(0).setVisible(!(e.getValue().equals("全体")));
                    chartList2B.get(0).setVisible(e.getValue().equals("全体"));
                }
        );
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(1).setVisible(!(e.getValue().equals("2年")));
            chartList2B.get(1).setVisible(e.getValue().equals("2年"));
        });
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(2).setVisible(!(e.getValue().equals("3年")));
            chartList2B.get(2).setVisible(e.getValue().equals("3年"));

        });
        seiseki.getR4().addValueChangeListener(e -> {
            chartList2.get(3).setVisible(!(e.getValue().equals("4年")));
            chartList2B.get(3).setVisible(e.getValue().equals("4年"));
        });


        //ここまでelectronic

        //ここからinformation
        HorizontalLayout layout7=new HorizontalLayout();
        FormLayout layout8=getUnderLayout();
        ArrayList<VerticalLayout> chartList3=seisekiGraph.makeInformation();
        ArrayList<VerticalLayout> chartList3B=seisekiGraph.makeBigInformation();

        for(VerticalLayout chart: chartList3){
            layout8.add(chart);
        }
        for(VerticalLayout chart: chartList3B){
            layout7.add(chart);
            chart.setVisible(false);
        }


        information.add(layout7);
        information.add(layout8);


        seiseki.getR5().addValueChangeListener(e -> {
                    chartList3.get(0).setVisible(!(e.getValue().equals("全体")));
                    chartList3B.get(0).setVisible(e.getValue().equals("全体"));
                }
        );
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(1).setVisible(!(e.getValue().equals("2年")));
            chartList3B.get(1).setVisible(e.getValue().equals("2年"));
        });
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(2).setVisible(!(e.getValue().equals("3年")));
            chartList3B.get(2).setVisible(e.getValue().equals("3年"));

        });
        seiseki.getR5().addValueChangeListener(e -> {
            chartList3.get(3).setVisible(!(e.getValue().equals("4年")));
            chartList3B.get(3).setVisible(e.getValue().equals("4年"));
        });


        //ここまでinformation

        //ここから基本統計量
        SeisekiTable table = new SeisekiTable(gradeService);
        Grid<GetTableData> gridAll = table.getTableSubjectFirst();
        Grid<GetTableData> gridScience = table.getTableSubjectFirstScience();
        Grid<GetTableData> gridElectronic = table.getTableSubjectFirstElectronic();
        Grid<GetTableData> gridInformation = table.getTableSubjectFirstInformation();

        H3 str = new H3("基本統計量");
        H3 str1 = new H3("基本統計量");
        H3 str2 = new H3("基本統計量");
        H3 str3 = new H3("基本統計量");
        all.add(str,gridAll);
        science.add(str1,gridScience);
        electronic.add(str2,gridElectronic);
        information.add(str3,gridInformation);

        return main;
    }
    private FormLayout getUnderLayout(){
        FormLayout layout=new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("600px",2),
                new FormLayout.ResponsiveStep("900px",3),
                new FormLayout.ResponsiveStep("1200px",4),
                new FormLayout.ResponsiveStep("1500px",5)
        );
        return layout;
    }

}
