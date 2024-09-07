package jp.ac.chitose.ir.presentation.views.commission.university.layouts.classwork;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.TableData;
import jp.ac.chitose.ir.application.service.commission.CommonUnits;
import jp.ac.chitose.ir.application.service.commission.MajorUnits;
import jp.ac.chitose.ir.application.service.commission.UniversityService;




public class GraduationCredits extends VerticalLayout {

    private UniversityService universityService;

    public GraduationCredits(UniversityService universityService) {
        this.universityService = universityService;

        add(new H1("卒業単位数"));
        add(new Paragraph("説明"));
        add(new H2("共通教育科目"));
        add(creatcommonGrid());
        add(new H2("専門教育科目"));
        add(createMajorgrid());

    }

    private Grid<CommonUnits> creatcommonGrid(){

        TableData<CommonUnits> commonUnitsTableData=universityService.getCommonUnits(2024);

        //CommonUnits commonUnits=new CommonUnits(
        //        commonUnitsTableData.data().get(0).department(),
        //        commonUnitsTableData.data().get(0).required(),
        //        commonUnitsTableData.data().get(0).required_elective_specialty1(),
        //        commonUnitsTableData.data().get(0).required_elective_specialty2(),
        //        commonUnitsTableData.data().get(0).required_elective_education(),
        //        commonUnitsTableData.data().get(0).elective(),
        //        commonUnitsTableData.data().get(0).foreign_language_required1(),
        //        commonUnitsTableData.data().get(0).foreign_language_required2(),
        //        commonUnitsTableData.data().get(0).physical_education_elective(),
        //        commonUnitsTableData.data().get(0).total()
        //);
        Grid<CommonUnits> grid = new Grid<>();
        grid.setItems(commonUnitsTableData.data());
        grid.addColumn(CommonUnits::required).setHeader("必修");
        grid.addColumn(CommonUnits::required_elective_specialty1).setHeader("選択必修(専門基礎1)");
        grid.addColumn(CommonUnits::required_elective_specialty2).setHeader("選択必修(専門基礎2)");
        grid.addColumn(CommonUnits::required_elective_education).setHeader("選択必修(一般教養)");
        grid.addColumn(CommonUnits::elective).setHeader("選択");
        grid.addColumn(CommonUnits::foreign_language_required1).setHeader("外国語①");
        grid.addColumn(CommonUnits::foreign_language_required2).setHeader("外国語②");
        grid.addColumn(CommonUnits::total).setHeader("共通教育科目合計");

        grid.setHeight("100px");

        return grid;
    }

    private Grid<MajorUnits> createMajorgrid(){

        TableData<MajorUnits> majorUnitsTableData=universityService.getMajorUnits(2024);
        MajorUnits majorunits=new MajorUnits(
                majorUnitsTableData.data().get(0).department(),
                majorUnitsTableData.data().get(0).required(),
                majorUnitsTableData.data().get(0).required_elective(),
                majorUnitsTableData.data().get(0).elective(),
                majorUnitsTableData.data().get(0).specialty_total(),
                majorUnitsTableData.data().get(0).others(),
                majorUnitsTableData.data().get(0).total()
        );


        Grid<MajorUnits> grid=new Grid<>();
        grid.setItems(majorUnitsTableData.data());
        grid.addColumn(MajorUnits::department).setHeader("学科");
        grid.addColumn(MajorUnits::required).setHeader("必修");
        grid.addColumn(MajorUnits::required_elective).setHeader("選択必修");
        grid.addColumn(MajorUnits::elective).setHeader("選択");
        grid.addColumn(MajorUnits::specialty_total).setHeader("専門合計");
        grid.addColumn(MajorUnits::others).setHeader("その他");
        grid.addColumn(MajorUnits::total).setHeader("卒業単位数合計");

        grid.setHeight("180px");

        return grid;
    };


}
