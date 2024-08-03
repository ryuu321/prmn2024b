package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jp.ac.chitose.ir.application.service.commission.GradeService;

public class SeisekiView implements View {

    private VerticalLayout yearFirstLayout;
    private VerticalLayout subjectFirstLayout;
    private GradeService gradeService;


    public SeisekiView(GradeService gradeService){
        this.gradeService = gradeService;
    }
    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();
        H1 a = new H1("成績に関する統計データ");
        main.add(a);

        main.add(new Paragraph("成績画面に関する説明文"));

        RadioButtonGroup<String> yearOrSubject = new RadioButtonGroup<>();
        yearOrSubject.setItems("学年","学科");
        yearOrSubject.setValue("学年");
        main.add(yearOrSubject);
        yearFirstLayout = getSeisekiYearFirstLayout();
        main.add(yearFirstLayout);

        yearOrSubject.addValueChangeListener(e -> {
            if(e.getValue().equals("学年")){
                yearFirstLayout = getSeisekiYearFirstLayout();
                main.add(yearFirstLayout);
                main.remove(subjectFirstLayout);
            }
            else if(e.getValue().equals("学科")){
                subjectFirstLayout = getSeisekiSubjectFirstLayout();
                main.remove(yearFirstLayout);
                main.add(subjectFirstLayout);
            }
        });
        return main;
    }
    private VerticalLayout getSeisekiYearFirstLayout(){
        VerticalLayout seisekiYearFirstLayout = new VerticalLayout();
        Seiseki seiseki = new Seiseki(0);
        seisekiYearFirstLayout.add(seiseki.view());
        SeisekiGraphView seisekiGraphView=new SeisekiGraphView(seiseki,gradeService);
        seisekiYearFirstLayout.add(seisekiGraphView.view());
        return seisekiYearFirstLayout;

    }

    private VerticalLayout getSeisekiSubjectFirstLayout(){
        VerticalLayout seisekiSubjectFirstLayout = new VerticalLayout();
        Seiseki seiseki = new Seiseki(1);
        seisekiSubjectFirstLayout.add(seiseki.view());
        SeisekiGraphView seisekiGraphView =  new SeisekiGraphView(seiseki,gradeService);
        seisekiSubjectFirstLayout.add(seisekiGraphView.view());
        return seisekiSubjectFirstLayout;
    }


}
