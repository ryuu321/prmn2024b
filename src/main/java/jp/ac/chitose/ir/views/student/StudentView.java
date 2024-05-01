package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentTable;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
@PermitAll
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private ComboBox<StudentGrade> comboBox;
    private TextField textField;
    private ApexCharts chart;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private Grid<StudentTable> grid;
    private TreeGrid<StudentTable> treeGrid;
    private GraphPattern graphPattern;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        setup();
    }

    private void setup() {
        graphPattern = new GPAGraphPattern();
        textFieldInitialize();
        schoolYearsRadioButtonInitialize();
        departmentsRadioButtonInitialize();
        comboBoxInitialize();
        grid = new Grid<>(StudentTable.class, false);
        treeGrid = new TreeGrid<>();

        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton);
        HorizontalLayout comboBoxAndResetButton = new HorizontalLayout(comboBox, createResetButton());
        comboBoxAndResetButton.setAlignItems(Alignment.END);
        add(comboBoxAndResetButton);
        addChart(schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue());
    }

    private void textFieldInitialize() {
        textField = new TextField();
        textField.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) return;
            comboBox.setItems(studentService.getStudentNumberGrades(valueChangeEvent.getValue()).data());
            comboBox.setItemLabelGenerator(StudentGrade::科目名);
            grid.addColumn(StudentTable::科目名).setHeader("科目名");
            grid.addColumn(data -> String.valueOf(data.対象学年())).setHeader("学年");
            grid.addColumn(StudentTable::必選別).setHeader("必選別");
            grid.addColumn(StudentTable::成績評価).setHeader("成績評価");
            grid.setItems(studentService.getStudentTable(valueChangeEvent.getValue()).data());
            grid.setWidthFull();
            this.add(grid);
        });
    }

    private void schoolYearsRadioButtonInitialize() {
        schoolYearsRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> {
            if(!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue())) {
                updateChart(valueChangeEvent.getValue(), departmentsRadioButton.getValue());
            }
        });
        add(schoolYearsRadioButton);
    }

    private void departmentsRadioButtonInitialize() {
        departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> {
            if(!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue())) {
                updateChart(schoolYearsRadioButton.getValue(), valueChangeEvent.getValue());
            }
        });
    }

    private void comboBoxInitialize() {
        comboBox = new ComboBox<>("科目名");
        comboBox.setWidth("400px");
        comboBox.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null) return;
            else graphPattern = new SubjectGraphPattern(valueChangeEvent.getValue());
            updateChart(schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue());
        });
    }

    private Button createResetButton() {
        Button resetButton = new Button("reset");
        resetButton.addClickListener(click -> {
            graphPattern = new GPAGraphPattern();
            updateChart(schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue());
            if(textField.getValue() == null) return;
            comboBox.clear();
            comboBox.setItems(studentService.getStudentNumberGrades(textField.getValue()).data());
            comboBox.setItemLabelGenerator(StudentGrade::科目名);
        });
        return resetButton;
    }

    private void updateChart(String schoolYear, String department) {
        if(chart != null) {
            remove(chart);
            remove(grid);
        }
        addChart(schoolYear, department);
    }

    private void addChart(String schoolYear, String department) {
        chart = graphPattern.create(studentService, schoolYear, department);
        add(chart);
        add(grid);
    }
}