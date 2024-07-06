package jp.ac.chitose.ir.presentation.views.commission.subject;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;

import java.awt.*;

public class SubjectButton {
    private RadioButtonGroup<String> year;
    private RadioButtonGroup<String> major;
    private Select<String> select;
    private Select<String> select2;

    public SubjectButton(){
        VerticalLayout main = new VerticalLayout();
        //HorizontalLayout radioAndText = new HorizontalLayout();
        this.year = new RadioButtonGroup<>();
        year.setLabel("学年選択");
        year.setItems("全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        //radioAndText.add(year);
        //main.add(radioAndText);

        //HorizontalLayout radioAndText2=new HorizontalLayout();
        this.major=new RadioButtonGroup<>();
        major.setLabel("学科選択");
        major.setItems("全体","応用化学生物学科","電子光工学科","情報システム工学科","理工学専攻");
        //radioAndText2.add(major);
        //main.add(radioAndText2);

        //ArrayList<> subject = new ArrayList<>();
        this.select = new Select<>();
        select.setLabel("科目");
        select.setItems("1","2","3","4","0");

        this.select2= new Select<>();
        select2.setLabel("科目");
        select2.setItems("0","1");
        select2.setVisible(false);

        //main.add(comboBox);

        //return main;
    }

    public void Button(){
        Select<String> year = new Select<>();
        year.setLabel("開講年度を選択");
        year.setItems("2019","2020","2021","2022","2023");

        Select<String> term = new Select<>();
        term.setLabel("開講学期を選択");
        term.setItems("1","2","3","4");

        Checkbox grade = new Checkbox();
        Checkbox major = new Checkbox();
    }

    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();
        main.add(this.year);
        main.add(this.major);
        HorizontalLayout h = new HorizontalLayout();
        h.add(select,select2);
        main.add(h);
        return main;
    }

    public RadioButtonGroup<String> getYear() {
        return year;
    }

    public RadioButtonGroup<String> getMajor() {
        return major;
    }

    public Select<String> getSelect(){
        return select;
    }
    public Select<String> getSelect2(){ return select2;}
}
