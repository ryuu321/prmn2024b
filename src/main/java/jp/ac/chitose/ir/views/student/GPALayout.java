package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentService;

public class GPALayout extends VerticalLayout {
    private final GPAGraph gpaGraph;
    private final GradeGrid gradeGrid;

    public GPALayout(StudentService studentService, String schoolYear, ComboBox comboBox, String textFieldValue) {
        gpaGraph = new GPAGraph(studentService, schoolYear);
        gradeGrid = new GradeGrid(studentService, comboBox, textFieldValue);
        add(gpaGraph, gradeGrid);
    }
}
