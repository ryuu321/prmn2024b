package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.RadioButtonFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterableComboBox;
import jp.ac.chitose.ir.presentation.views.student.gpaview.GPAView;
import jp.ac.chitose.ir.presentation.views.student.subjectview.SubjectView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final FilterableComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private GPAView gpaView;
    private final SubjectView subjectView;
    private final static String[] FILTER_NAMES = {"学年", "学科"};
    private final static RadioButtonValues[] FILTER_VALUES = {RadioButtonValues.SCHOOL_YEARS, RadioButtonValues.DEPARTMENTS};
    private final static List<BiPredicate<StudentGrade, String>> FILTER_FUNCTIONS = List.of(
            (grade, str) -> matchesFilter(str, grade.schoolYear()),
            (grade, str) -> matchesFilter(str, grade.department()));

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        subjectComboBox = new FilterableComboBox<>("科目名", createComboBoxFilters(), FilterPosition.TOP);
        subjectView = new SubjectView(studentService);
        studentNumberField = new TextField();

        initializeComponents();
        addComponentsToLayout();
    }

    private static boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals("全体") || filterValue.equals(itemValue);
    }

    private List<Filter<String, StudentGrade>> createComboBoxFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        for(int i = 0; i < FILTER_NAMES.length; i++) {
            filters.add(new RadioButtonFilter<>(FILTER_VALUES[i].getValues(), FILTER_FUNCTIONS.get(i), FILTER_NAMES[i]));
        }
        return filters;
    }

    private void initializeComponents() {
        initializeSubjectComboBox();
        initializeStudentNumberField();
    }

    private void initializeSubjectComboBox() {
        subjectComboBox.setComboBoxWidth("40%");
        subjectComboBox.setItemLabelGenerator(StudentGrade::科目名);
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(this::updateLayout);
    }

    private void initializeStudentNumberField() {
        studentNumberField.setPlaceholder("学籍番号を入力してください");
        studentNumberField.addValueChangeListener(this::handleStudentNumberChange);
    }

    private void handleStudentNumberChange(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        String studentNumber = event.getValue();
        if(studentNumber == null || studentNumber.isEmpty()) return;
        if(gpaView != null) remove(gpaView);
        remove(subjectView);

        subjectComboBox.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        initializeGPAView(studentNumber);
        add(gpaView);
    }

    private void initializeGPAView(String studentNumber) {
        gpaView = new GPAView(studentService, studentNumber, subjectComboBox);
    }

    private void updateLayout(AbstractField.ComponentValueChangeEvent<ComboBox<StudentGrade>, StudentGrade> event) {
        if (event.getValue() == null) {
            remove(subjectView);
            add(gpaView);
        } else {
            subjectView.update(event.getValue().科目名(), studentNumberField.getValue());
            remove(gpaView);
            add(subjectView);
        }
    }

    private void addComponentsToLayout() {
        add(studentNumberField);
        add(new H1("Student"));
        add(new Paragraph("説明"));
        add(subjectComboBox);
    }
}