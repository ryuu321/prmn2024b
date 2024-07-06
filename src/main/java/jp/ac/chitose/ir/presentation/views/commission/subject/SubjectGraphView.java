package jp.ac.chitose.ir.presentation.views.commission.subject;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class SubjectGraphView {


    private SubjectButton button;
    public SubjectGraphView(SubjectButton button){
        this.button=button;

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
        main.add(all);
        main.add(first);
        main.add(second);
        main.add(third);
        main.add(fourth);
        button.getSelect().addValueChangeListener(e->all.setVisible(e.getValue().equals("1")));
        button.getSelect().addValueChangeListener(e->first.setVisible(e.getValue().equals("2")));
        button.getSelect().addValueChangeListener(e->second.setVisible(e.getValue().equals("3")));
        button.getSelect().addValueChangeListener(e->third.setVisible(e.getValue().equals("4")));
        button.getSelect().addValueChangeListener(e->fourth.setVisible(e.getValue().equals("0")));


        all.add(new H1("test1"));
        first.add(new H1("test2"));
        second.add(new H1("test3"));
        third.add(new H1("test4"));
        fourth.add(new H1("test0"));

        return main;
    }
}
