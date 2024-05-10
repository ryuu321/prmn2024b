package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentTable;
import jp.ac.chitose.ir.views.MainLayout;

import java.text.Normalizer;
import java.util.function.Consumer;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private ComboBox<String> comboBox;
    private TextField textField;;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private GradeGrid gradeGrid;
    private SubjectGrid subjectGrid;
    private GPAGraph gpaGraph = new GPAGraph();
    private SubjectGraph subjectGraph = new SubjectGraph();

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        setup();
        System.out.println(textField.getParent());
    }

    private void setup() {
        gpaGraph = new GPAGraph();
        subjectGraph = new SubjectGraph();
        subjectGraph.setVisible(false);
        gradeGrid = new GradeGrid(studentService);
        subjectGrid = new SubjectGrid();
        subjectGrid.setVisible(false);
        textFieldInitialize();
        schoolYearsRadioButtonInitialize();
        departmentsRadioButtonInitialize();
        comboBoxInitialize();
        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton, comboBox);
        add(gpaGraph, subjectGraph);
        add(gradeGrid, subjectGrid);
    }

    private void textFieldInitialize() {
        textField = new TextField();
        textField.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) return;
            comboBox.setItems(studentService.getStudentNumberGrades(valueChangeEvent.getValue()).data().stream()
                    .map(StudentGrade::科目名).toList());
            gpaGraph.create(studentService, studentService.getStudentSchoolYear(textField.getValue()).data().get(0).学年());
            gradeGrid.create(valueChangeEvent.getValue(), comboBox);
        });
    }

    private void schoolYearsRadioButtonInitialize() {
        schoolYearsRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> {
        });
        add(schoolYearsRadioButton);
    }

    private void departmentsRadioButtonInitialize() {
        departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> {
        });
    }

    private void comboBoxInitialize() {
        comboBox = new ComboBox<>("科目名");
        comboBox.setWidth("600px");
        comboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        comboBox.setClearButtonVisible(true);
        comboBox.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) {
                if(textField.getValue() != null) {
                    comboBox.clear();
                    comboBox.setItems(studentService.getStudentNumberGrades(textField.getValue()).data().stream()
                            .map(StudentGrade::科目名).toList());
                }
                gradeGrid.create(textField.getValue(), comboBox);
                gpaGraph.setVisible(true);
                gradeGrid.setVisible(true);
                subjectGraph.setVisible(false);
                subjectGrid.setVisible(false);
            } else {
                subjectGraph.create(studentService, schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue(), studentService.getStudentNumberGrade(textField.getValue(), valueChangeEvent.getValue()).data().get(0));
                subjectGrid.create(studentService, valueChangeEvent.getValue(), studentService.getStudentNumberGrade(textField.getValue(), valueChangeEvent.getValue()).data().get(0));
                gpaGraph.setVisible(false);
                gradeGrid.setVisible(false);
                subjectGraph.setVisible(true);
                subjectGrid.setVisible(true);
            }
        });
    }
}