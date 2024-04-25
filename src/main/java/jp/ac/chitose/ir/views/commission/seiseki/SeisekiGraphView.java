package jp.ac.chitose.ir.views.commission.seiseki;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.commission.CommissionService;

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
        VerticalLayout main=new VerticalLayout();
        HorizontalLayout layout=new HorizontalLayout();
        HorizontalLayout layout2=new HorizontalLayout();
        ArrayList<VerticalLayout> chartList=seisekiGraph.makeAll();
        for(VerticalLayout chart: chartList){
            layout.add(chart);
            layout2.add(chart);
        }
        main.add(layout,layout2);
        main.setVisible(false);
        //seiseki.getR1().addValueChangeListener(e->main.setVisible(e.getValue().equals("全体")));
        seiseki.getR2().addValueChangeListener(e -> chartList.get(0).setVisible(e.getValue().equals("全体")));
        seiseki.getR2().addValueChangeListener(e -> chartList.get(1).setVisible(e.getValue().equals("応用化学生物学科")));
        seiseki.getR2().addValueChangeListener(e -> chartList.get(2).setVisible(e.getValue().equals("電子光工学科")));
        seiseki.getR2().addValueChangeListener(e -> chartList.get(3).setVisible(e.getValue().equals("情報システム工学科")));
        seiseki.getR2().addValueChangeListener(e -> chartList.get(4).setVisible(e.getValue().equals("一年生")));
        seiseki.getR2().addValueChangeListener(e -> {
            if (e.getValue().equals("全て")) {
                for (VerticalLayout chart : chartList) {
                    chart.setVisible(true);
                }
            }
        });


        return main;
    }
}
