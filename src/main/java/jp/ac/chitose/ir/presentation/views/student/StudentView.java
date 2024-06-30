package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.util.function.Function;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final FilterComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private final static String ALL = "全体";
    private GPALayout gpaLayout;
    private SubjectLayout subjectLayout;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        this.subjectComboBox = new FilterComboBox<>("科目名");
        this.studentNumberField = new TextField();

        initializeSubjectComboBox();
        initializeStudentNumberField();
        addComponentsToLayout();
    }

    private void initializeSubjectComboBox() {
        subjectComboBox.setItemLabelGenerator(StudentGrade::科目名);
        subjectComboBox.setWidth("40%");
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(this::updateLayout);
        addFiltersToComboBox();
    }

    private void addFiltersToComboBox() {
        addRadioButtonFilter(subjectComboBox, RadioButtonValues.SCHOOL_YEARS, StudentGrade::schoolYear);
        addRadioButtonFilter(subjectComboBox, RadioButtonValues.DEPARTMENTS, StudentGrade::department);
    }

    private void addRadioButtonFilter(FilterComboBox<String, StudentGrade> comboBox, RadioButtonValues values, Function<StudentGrade, String> valueProvider) {
        comboBox.addFilter(RadioButtonFilter.createRadioButtonFilter(
                comboBox,
                values.getValues(),
                (grade, filterValue) -> matchesFilter(filterValue, valueProvider.apply(grade))));
    }

    private boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals(ALL) || filterValue.equals(itemValue);
    }

    private void initializeStudentNumberField() {
        studentNumberField.setPlaceholder("学籍番号を入力してください");
        studentNumberField.addValueChangeListener(this::handleStudentNumberChange);
    }

    private void handleStudentNumberChange(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        String studentNumber = event.getValue();
        if (studentNumber == null || studentNumber.isEmpty()) {
            return;
        }

        String studentSchoolYear = studentService.getStudentSchoolYear(studentNumber).data().get(0).学年();
        subjectComboBox.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        gpaLayout = new GPALayout(studentService, studentSchoolYear, subjectComboBox, studentNumber);
        subjectLayout = new SubjectLayout(studentService);
        add(gpaLayout);
    }

    private void updateLayout(AbstractField.ComponentValueChangeEvent<ComboBox<StudentGrade>, StudentGrade> event) {
        if (event.getValue() == null) {
            remove(subjectLayout);
            add(gpaLayout);
        } else {
            subjectLayout.update(studentNumberField.getValue(), event.getValue().科目名());
            remove(gpaLayout);
            add(subjectLayout);
        }
    }

    private void addComponentsToLayout() {
        add(studentNumberField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), subjectComboBox.getFilters().get(0).getFilterComponent());
        add(new H3("学科"), subjectComboBox.getFilters().get(1).getFilterComponent());
        add(subjectComboBox);
    }
}