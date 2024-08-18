package jp.ac.chitose.ir.presentation.views.commission.university.layouts.studentsupport;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class Scholarship extends VerticalLayout {
    public Scholarship(){
        add(new H1("奨学金"));
        add(new Paragraph("説明"));
        add(new H2("日本学生支援機構"));
        add(createJassoGrid());
        add(new H2("大学院研究援助金"));
        add(createResearchSupportGrid());
        add(new H2("その他奨学金"));
        add(creatOtherSupportgrid());
    }

    //日本学生支援機構の奨学金に関するGrid
    private Grid<Jasso> createJassoGrid(){
        Jasso test1 = new Jasso("学部","給付",36,8,0,44);
        Jasso test2 = new Jasso("学部","第一種",59,13,0,72);
        Jasso test3 = new Jasso("学部","第二種",33,12,0,45);
        Jasso test4 = new Jasso("大学院","第一種",3,4,0,7);
        Jasso test5 = new Jasso("大学院","第二種",1,1,0,2);

        ArrayList<Jasso> jasso =new ArrayList<>();
        jasso.add(test1);
        jasso.add(test2);
        jasso.add(test3);
        jasso.add(test4);
        jasso.add(test5);

        Grid<Jasso> grid=new Grid<>();
        grid.setItems(jasso);
        grid.addColumn(Jasso::academictype).setHeader("学種");
        grid.addColumn(Jasso::type).setHeader("貸与種別");
        grid.addColumn(Jasso::reserve).setHeader("予約");
        grid.addColumn(Jasso::enrollment).setHeader("在学");
        grid.addColumn(Jasso::emergency).setHeader("緊急・応急");
        grid.addColumn(Jasso::all).setHeader("採用計");

        grid.setHeight("240px");

        return grid;
    };

    private Grid<ResearchSupport> createResearchSupportGrid(){
      ResearchSupport test1=new ResearchSupport("国内",16);
      ResearchSupport test2=new ResearchSupport("国外",0);

      ArrayList<ResearchSupport> researchSupports=new ArrayList<>();
      researchSupports.add(test1);
      researchSupports.add(test2);

      Grid<ResearchSupport> grid = new Grid<>();
      grid.setItems(researchSupports);
      grid.addColumn(ResearchSupport::type).setHeader("種別").setWidth("80px");
      grid.addColumn(ResearchSupport::number).setHeader("採用件数").setWidth("80px");
      grid.setHeight("130px");

      return grid;
    };

    private Grid<OtherSupport> creatOtherSupportgrid(){
      OtherSupport test1=new OtherSupport("札幌市奨学金","給付","学部",7);
      OtherSupport test2=new OtherSupport("JEES・MUFG緊急支援奨学金","給付","学部・大学院",4);
      OtherSupport test3=new OtherSupport("千歳市奨学金","給付","学部",3);
      OtherSupport test4=new OtherSupport("栗林育英奨学金","給付","学部",2);
      OtherSupport test5=new OtherSupport("山口正栄記念奨学金","給付","大学院",1);
      OtherSupport test6=new OtherSupport("公益財団法人G-7奨学財団","給付","大学院",1);
      OtherSupport test7=new OtherSupport("公益財団法人戸部眞紀奨学財団","給付","大学院",1);

      ArrayList<OtherSupport> otherSupports=new ArrayList<>();
      otherSupports.add(test1);
      otherSupports.add(test2);
      otherSupports.add(test3);
      otherSupports.add(test4);
      otherSupports.add(test5);
      otherSupports.add(test6);
      otherSupports.add(test7);

      Grid<OtherSupport> grid = new Grid<>();
      grid.setItems(otherSupports);
      grid.addColumn(OtherSupport::name).setHeader("奨学金名");
      grid.addColumn(OtherSupport::type).setHeader("貸与・給付");
      grid.addColumn(OtherSupport::academictype).setHeader("学種");
      grid.addColumn(OtherSupport::number).setHeader("採用人数");


      return grid;
    };
}
