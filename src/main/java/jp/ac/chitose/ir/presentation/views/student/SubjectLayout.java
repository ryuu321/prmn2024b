package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;

import java.util.List;

public class SubjectLayout extends VerticalLayout {
    private final StudentService studentService;
    private final SubjectGraph subjectGraph;
    private final SubjectGrid subjectGrid;

    // コンストラクタ　科目の画面に必要なものを管理するクラス
    public SubjectLayout(StudentService studentService) {
        this.studentService = studentService;
        subjectGraph = new SubjectGraph();
        subjectGrid = new SubjectGrid();
        add(subjectGraph, subjectGrid);
    }

    // 管理しているものに指定されている科目の画面を作らせる機能
    public void update(String textFieldValue, String comboBoxValue) {
        List<StudentSubjectCalc> histData = studentService.getStudentSubjectCalc(comboBoxValue).data();
        StudentGrade studentGrade = studentService.getStudentNumberGrade(textFieldValue, comboBoxValue).data().get(0);
        subjectGraph.updateGraphs(histData, studentGrade);
        subjectGrid.updateGrid(histData, studentGrade);
    }
}
