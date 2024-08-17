package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.commission.GradeService;
import jp.ac.chitose.ir.application.service.management.SecurityService;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.RadioButtonFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterableComboBox;
import jp.ac.chitose.ir.presentation.views.student.gpaview.GPAView;
import jp.ac.chitose.ir.presentation.views.student.subjectview.SubjectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final GradeService gradeService;
    private final StudentGradeService studentGradeService;
    private final FilterableComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private GPAView gpaView;
    private final SubjectView subjectView;
    private final static String[] FILTER_NAMES = {"学年", "学科"};
    private final static RadioButtonValues[] FILTER_VALUES = {RadioButtonValues.SCHOOL_YEARS, RadioButtonValues.DEPARTMENTS};
    private final static List<BiPredicate<StudentGrade, String>> FILTER_FUNCTIONS = List.of(
            (grade, str) -> matchesFilter(str, grade.schoolYear()),
            (grade, str) -> matchesFilter(str, grade.department()));

    public StudentView(StudentGradeService studentGradeService, GradeService gradeService, SecurityService securityService) {
        this.gradeService = gradeService;
        this.studentGradeService = studentGradeService;
        subjectComboBox = new FilterableComboBox<>("科目名", createComboBoxFilters(), FilterPosition.TOP);
        subjectView = new SubjectView(studentGradeService);
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
        subjectComboBox.setItemLabelGenerator(StudentGrade::lecture_name);
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(this::updateView);
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

        subjectComboBox.setItems(studentGradeService.getStudentNumberSubjects(studentNumber).data());
        initializeGPAView(studentNumber);
        add(gpaView);
    }

    private void initializeGPAView(String studentNumber) {
        gpaView = new GPAView(gradeService, studentGradeService, studentNumber, subjectComboBox);
    }

    private void updateView(AbstractField.ComponentValueChangeEvent<ComboBox<StudentGrade>, StudentGrade> event) {
        if (event.getValue() == null) {
            remove(subjectComboBox);
            remove(subjectView);
            add(gpaView);
        } else {
            subjectView.update(findByCourseId(event.getValue().course_id()));
            remove(gpaView);
            add(subjectComboBox, subjectView);
        }
    }

    private StudentGrade findByCourseId(String courseId) {
        Optional<StudentGrade> gradeOptional = subjectComboBox.getItems()
                .filter(item -> item.course_id().equals(courseId))
                .findFirst();
        return gradeOptional.orElse(subjectComboBox.getItems().findFirst().get());
    }

    private void addComponentsToLayout() {
        add(studentNumberField);
    }
}