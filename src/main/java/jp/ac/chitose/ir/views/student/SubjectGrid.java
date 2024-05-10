package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentSubjectCalc;

import java.util.List;

public class SubjectGrid extends VerticalLayout {
    private Grid<StudentSubjectCalc> grid;
    private GridListDataView<StudentSubjectCalc> gridListDataView;
    private StudentGrade changeValue;

    public void create(StudentService studentService, String subject, StudentGrade changeValue) {
        removeAll();
        this.changeValue = changeValue;
        List<StudentSubjectCalc> histData = studentService.getStudentSubjectCalc(changeValue.科目名()).data();
        grid = new Grid(StudentSubjectCalc.class, false);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addColumn(StudentSubjectCalc::平均).setHeader("平均");
        grid.addColumn(StudentSubjectCalc::分散).setHeader("分散");
        gridListDataView = grid.setItems(histData);
        gridListDataView.setFilter(new Filter());
        add(grid);
    }

    private class Filter implements SerializablePredicate<StudentSubjectCalc> {
        @Override
        public boolean test(StudentSubjectCalc studentSubjectCalc) {
            return changeValue.開講年() == studentSubjectCalc.開講年();
        }
    }
}
