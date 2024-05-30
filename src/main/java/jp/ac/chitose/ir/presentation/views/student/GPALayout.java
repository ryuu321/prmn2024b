package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;

public class GPALayout extends VerticalLayout {

    // コンストラクタ　GPAの画面に必要なものを管理するクラス 自分にそれらを追加する
    public GPALayout(StudentService studentService, String schoolYear, ComboBox<StudentGrade> subjectComboBox, String textFieldValue) {
        GPAGraph gpaGraph = new GPAGraph(studentService, schoolYear);
        GradeGrid gradeGrid = new GradeGrid(studentService, subjectComboBox, textFieldValue);
        add(gpaGraph, gradeGrid);
    }
}
