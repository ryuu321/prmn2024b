package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final FilteredComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private FilteredGrid<Number, StudentSubjectCalc> subjectGrid;
    private SubjectGraph subjectGraph;
    private VerticalLayout gpaLayout;
    private VerticalLayout subjectLayout;
    private final static List<String> FILTER_NAMES = List.of("学年", "学科", "必選別", "成績評価");
    private final static List<RadioButtonValues> FILTER_VALUES = List.of(
            RadioButtonValues.SCHOOL_YEARS,
            RadioButtonValues.DEPARTMENTS,
            RadioButtonValues.SUBJECT_TYPES,
            RadioButtonValues.GRADES);
    private final static List<BiPredicate<StudentGrade, String>> FILTER_FUNCTIONS = List.of(
            (grade, str) -> matchesFilter(str, grade.schoolYear()),
            (grade, str) -> matchesFilter(str, grade.department()),
            (grade, str) -> matchesFilter(str, grade.必選別()),
            (grade, str) -> matchesFilter(str, grade.成績評価()));
    private final static List<String> GRADE_HEADER_NAMES = List.of("科目名", "対象学年", "対象学科", "必選別", "単位数", "成績評価");
    private final static List<ValueProvider<StudentGrade, String>> GRADE_VALUE_PROVIDERS = List.of(
            StudentGrade::科目名,
            StudentGrade::schoolYear,
            StudentGrade::department,
            StudentGrade::必選別,
            grade -> String.valueOf(grade.科目の単位数()),
            StudentGrade::成績評価);
    private final static List<String> SUBJECT_HEADER_NAMES = List.of("受講人数", "欠席", "不可", "可", "良", "優", "秀", "平均", "分散");
    private final static List<ValueProvider<StudentSubjectCalc, Number>> SUBJECT_VALUE_PROVIDERS = List.of(
            StudentSubjectCalc::合計の人数,
            StudentSubjectCalc::欠席,
            StudentSubjectCalc::不可,
            StudentSubjectCalc::可,
            StudentSubjectCalc::良,
            StudentSubjectCalc::優,
            StudentSubjectCalc::秀,
            StudentSubjectCalc::平均,
            StudentSubjectCalc::分散);

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        subjectComboBox = new FilteredComboBox<>("科目名", createComboBoxFilters(), FilterPosition.TOP);
        studentNumberField = new TextField();

        initializeComponents();
        addComponentsToLayout();
    }

    private List<Filter<String, StudentGrade>> createComboBoxFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        filters.add(new RadioButtonFilter<>(RadioButtonValues.SCHOOL_YEARS.getValues(), FILTER_FUNCTIONS.get(0), FILTER_NAMES.get(0)));
        filters.add(new RadioButtonFilter<>(RadioButtonValues.DEPARTMENTS.getValues(), FILTER_FUNCTIONS.get(1), FILTER_NAMES.get(1)));
        return filters;
    }

    private void initializeComponents() {
        initializeSubjectComboBox();
        initializeStudentNumberField();
    }

    private void initializeSubjectComboBox() {
        subjectComboBox.setItemLabelGenerator(StudentGrade::科目名);
        subjectComboBox.setComboBoxWidth("40%");
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(this::updateLayout);
    }

    private static boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals("全体") || filterValue.equals(itemValue);
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

        if(gpaLayout != null) remove(gpaLayout);
        if(subjectLayout != null) remove(subjectLayout);

        subjectComboBox.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        initializeGPALayout(studentNumber);
        initializeSubjectLayout(studentNumber);
        add(gpaLayout);
    }

    private void initializeGPALayout(String studentNumber) {
        gpaLayout = new VerticalLayout();
        String studentSchoolYear = studentService.getStudentSchoolYear(studentNumber).data().get(0).学年();
        gpaLayout.add(new GPAGraph(studentService.getStudentGPA().data(), studentSchoolYear));
        gpaLayout.add(createGradeGrid(studentNumber));
    }

    private void initializeSubjectLayout(String studentNumber) {
        subjectLayout = new VerticalLayout();
        subjectGraph = new SubjectGraph();
        subjectLayout.add(subjectGraph);
        subjectGrid = createSubjectGrid();
        subjectLayout.add(subjectGrid);
    }

    private FilteredGrid<String, StudentGrade> createGradeGrid(String studentNumber) {
        FilteredGrid<String, StudentGrade> gradeGrid = new FilteredGrid<>(StudentGrade.class, GRADE_VALUE_PROVIDERS, GRADE_HEADER_NAMES,
                createGradeGridFilters(), FilterPosition.TOP);
        gradeGrid.setAllRowsVisible(true);
        gradeGrid.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        gradeGrid.addItemClickListener(grade -> subjectComboBox.setValue(grade.getItem()));
        return gradeGrid;
    }

    private List<Filter<String, StudentGrade>> createGradeGridFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        for(int i = 0; i < Math.min(StudentView.FILTER_VALUES.size(), StudentView.FILTER_FUNCTIONS.size()); i++) {
            filters.add(new RadioButtonFilter<>(StudentView.FILTER_VALUES.get(i).getValues(), StudentView.FILTER_FUNCTIONS.get(i), FILTER_NAMES.get(i)));
        }
        return filters;
    }

    private FilteredGrid<Number, StudentSubjectCalc> createSubjectGrid() {
        FilteredGrid<Number, StudentSubjectCalc> grid = new FilteredGrid<>(StudentSubjectCalc.class, SUBJECT_VALUE_PROVIDERS, SUBJECT_HEADER_NAMES, new ArrayList<>(), FilterPosition.TOP);
        grid.setAllRowsVisible(true);
        return grid;
    }

    private void updateLayout(AbstractField.ComponentValueChangeEvent<ComboBox<StudentGrade>, StudentGrade> event) {
        if (event.getValue() == null) {
            remove(subjectLayout);
            add(gpaLayout);
        } else {
            updateSubjectLayout(event.getValue().科目名());
            remove(gpaLayout);
            add(subjectLayout);
        }
    }

    private void updateSubjectLayout(String subject) {
        updateSubjectGraph(subject);
        updateSubjectGrid(subject);
    }

    private void updateSubjectGraph(String subject) {
        List<StudentSubjectCalc> histData = studentService.getStudentSubjectCalc(subject).data();
        StudentGrade studentGrade = studentService.getStudentNumberGrade(studentNumberField.getValue(), subject).data().get(0);
        subjectGraph.updateGraphs(histData, studentGrade);
    }

    private void updateSubjectGrid(String subject) {
        subjectGrid.setItems(studentService.getStudentSubjectCalc(subject).data());
        subjectGrid.clearFilters();
        NoneComponentFilter<Number, StudentSubjectCalc> filter = new NoneComponentFilter<>((subjectCalc, grade) -> subjectCalc.開講年() == (int)grade,
                studentService.getStudentNumberGrade(studentNumberField.getValue(), subject).data().get(0).開講年());
        subjectGrid.addFilter(filter);
        subjectGrid.filter();
    }

    private void addComponentsToLayout() {
        add(studentNumberField, new H1("Student"), new Paragraph("説明"));
        add(subjectComboBox);
    }
}