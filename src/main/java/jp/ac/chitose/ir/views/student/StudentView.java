package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {

    //private SecurityService securityService;
    private StudentService studentService;
    private ComboBox<StudentGrade> comboBox;
    private TextField textField;
    private ApexCharts chart;
    private GraphPattern graphPattern;

    public StudentView(StudentService studentService/*, @Autowired SecurityService securityService */) {
        //this.securityService = securityService;
        this.studentService = studentService;
        graphPattern = new GPAGraphPattern();
        chart = graphPattern.create(studentService);
        add(createTextField());
        init();
        comboBoxInitialyze();
        HorizontalLayout comboBoxAndButton = new HorizontalLayout(comboBox, createResetButton());
        comboBoxAndButton.setAlignItems(Alignment.END);
        add(comboBoxAndButton);
        add(chart);
    }

    private void init() {
        add(new H1("Student"));
        add(new Paragraph("説明"));
        add(new H3("学年"));
        RadioButtonGroup<String> gradesRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        gradesRadioButton.addValueChangeListener(event -> {
            if(event.getValue().equals(event.getOldValue())) return;
            else {
                String gradeStr = event.getValue();
            }
        });
        add(gradesRadioButton);
        add(new H3("学科"));
        RadioButtonGroup<String> departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.addValueChangeListener(event -> {
            if(event.getValue().equals(event.getOldValue())) return;
            else {
                String departmentStr = event.getValue();
            }
        });
        add(departmentsRadioButton);
    }

    private TextField createTextField() {
        textField = new TextField();
        textField.addValueChangeListener(e1 -> {
            if(e1.getValue() == null) return;
            comboBox.setItems(studentService.getStudentNumberGrades(e1.getValue()).data());
            comboBox.setItemLabelGenerator(StudentGrade::科目名);
        });
        return textField;
    }

    private Button createResetButton() {
        Button resetButton = new Button("reset");
        resetButton.addClickListener(click -> {
           graphPattern = new GPAGraphPattern();
           if(chart != null) remove(chart);
           chart = graphPattern.create(studentService);
           add(chart);
           if(textField.getValue() == null) return;
           comboBox.clear();
           comboBox.setItems(studentService.getStudentNumberGrades(textField.getValue()).data());
           comboBox.setItemLabelGenerator(StudentGrade::科目名);
        });
        return resetButton;
    }

    private void comboBoxInitialyze() {
        comboBox = new ComboBox<>("科目名");
        comboBox.setWidth("400px");
        comboBox.addValueChangeListener(e1 -> {
            if(e1.getValue() == null) return;
            else graphPattern = new SubjectGraphPattern(e1.getValue());
            if(chart != null) remove(chart);
            chart = graphPattern.create(studentService);
            add(chart);
        });
    }
}