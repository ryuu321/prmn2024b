package jp.ac.chitose.ir.presentation.views.commission.university.layouts.classwork.GraduationCredits;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;


public class GraduationCredits extends VerticalLayout {
    public GraduationCredits() {
        add(new H1("卒業単位数"));
        add(new Paragraph("説明"));
        add(new H2("共通教育科目"));
        add(creatcommonGrid());
        add(new H2("専門教育科目"));
        add(createMajorgrid());

    }

    private Grid<NumberOfUnits> creatcommonGrid(){

        NumberOfUnits numbers=new NumberOfUnits(37,4,6,4,0,4,2,59);

        Grid<NumberOfUnits> grid = new Grid<>();
        grid.setItems(numbers);
        grid.addColumn(NumberOfUnits::compulsoryC).setHeader("必修");
        grid.addColumn(NumberOfUnits::re1C).setHeader("選択必修(専門基礎1)");
        grid.addColumn(NumberOfUnits::re2C).setHeader("選択必修(専門基礎2)");
        grid.addColumn(NumberOfUnits::reEC).setHeader("選択必修(一般教養)");
        grid.addColumn(NumberOfUnits::electiveC).setHeader("選択");
        grid.addColumn(NumberOfUnits::foreigin1).setHeader("外国語①");
        grid.addColumn(NumberOfUnits::foreigin2).setHeader("外国語②");
        grid.addColumn(NumberOfUnits::allC).setHeader("共通教育科目合計");

        grid.setHeight("100px");

        return grid;
    }

    private Grid<Majorunits> createMajorgrid(){
        ArrayList<Majorunits> numbers = new ArrayList<>();

        Majorunits science=new Majorunits("応用化学生物学科",33,0,30,63,3,125);
        Majorunits electro=new Majorunits("電子光工学科",31,0,32,63,3,125);
        Majorunits infomation=new Majorunits("情報システム工学科",30,2,31,63,3,125);
        numbers.add(science);
        numbers.add(electro);
        numbers.add(infomation);

        Grid<Majorunits> grid=new Grid<>();
        grid.setItems(numbers);
        grid.addColumn(Majorunits::major).setHeader("学科");
        grid.addColumn(Majorunits::compulsoryM).setHeader("必修");
        grid.addColumn(Majorunits::reM).setHeader("選択必修");
        grid.addColumn(Majorunits::electiveM).setHeader("選択");
        grid.addColumn(Majorunits::allM).setHeader("専門合計");
        grid.addColumn(Majorunits::others).setHeader("その他");
        grid.addColumn(Majorunits::all).setHeader("卒業単位数合計");

        grid.setHeight("180px");

        return grid;
    };


}
