package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentSubjectCalc;

import java.util.List;

public class SubjectLayout extends VerticalLayout {
    private final StudentService studentService;
    private final SubjectGraph subjectGraph;
    private final SubjectGrid subjectGrid;
    public SubjectLayout(StudentService studentService) {
        this.studentService = studentService;
        subjectGraph = new SubjectGraph();
        subjectGrid = new SubjectGrid();
        add(subjectGraph, subjectGrid);
    }

    public void create(String textFieldValue, String comboBoxValue) {
        List<StudentSubjectCalc> histData = studentService.getStudentSubjectCalc(comboBoxValue).data();
        StudentGrade studentGrade = studentService.getStudentNumberGrade(textFieldValue, comboBoxValue).data().get(0);
        subjectGraph.create(histData, studentGrade);
        subjectGrid.create(histData, studentGrade);
    }
}
