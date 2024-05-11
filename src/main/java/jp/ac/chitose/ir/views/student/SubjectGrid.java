package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentSubjectCalc;

import java.util.List;

public class SubjectGrid extends VerticalLayout {
    private StudentGrade studentGrade;

    public void create(List<StudentSubjectCalc> histData, StudentGrade studentGrade) {
        removeAll();
        this.studentGrade = studentGrade;
        Grid<StudentSubjectCalc> grid = new Grid(StudentSubjectCalc.class, false);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addColumn(StudentSubjectCalc::平均).setHeader("平均");
        grid.addColumn(StudentSubjectCalc::分散).setHeader("分散");
        GridListDataView<StudentSubjectCalc> gridListDataView = grid.setItems(histData);
        gridListDataView.setFilter(new Filter());
        add(grid);
    }

    private class Filter implements SerializablePredicate<StudentSubjectCalc> {
        @Override
        public boolean test(StudentSubjectCalc studentSubjectCalc) {
            return studentSubjectCalc.開講年() == studentGrade.開講年();
        }
    }
}
