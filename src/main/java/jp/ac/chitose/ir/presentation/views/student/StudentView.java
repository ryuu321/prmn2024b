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
import java.util.Optional;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final FilterableComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private FilterableGrid<Number, StudentSubjectCalc> subjectGrid;
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
        subjectComboBox = new FilterableComboBox<>("科目名", createComboBoxFilters(), FilterPosition.TOP);
        studentNumberField = new TextField();

        initializeComponents();
        addComponentsToLayout();
    }

    private List<Filter<String, StudentGrade>> createComboBoxFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            filters.add(new RadioButtonFilter<>(FILTER_VALUES.get(i).getValues(), FILTER_FUNCTIONS.get(i), FILTER_NAMES.get(i)));
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
        initializeSubjectLayout();
        add(gpaLayout);
    }

    private void initializeGPALayout(String studentNumber) {
        gpaLayout = new VerticalLayout();
        String studentSchoolYear = studentService.getStudentSchoolYear(studentNumber).data().get(0).学年();
        gpaLayout.add(new GPAGraph(studentService.getStudentGPA().data(), studentSchoolYear));
        gpaLayout.add(createGradeGrid(studentNumber));
    }

    private void initializeSubjectLayout() {
        subjectLayout = new VerticalLayout();
        subjectGraph = new SubjectGraph();
        subjectLayout.add(subjectGraph);
        subjectGrid = createSubjectGrid();
        subjectLayout.add(subjectGrid);
    }

    private FilterableGrid<String, StudentGrade> createGradeGrid(String studentNumber) {
        FilterableGrid<String, StudentGrade> gradeGrid = new FilterableGrid<>(StudentGrade.class, false, createGradeGridFilters(), FilterPosition.TOP);
        for(int i = 0; i < GRADE_HEADER_NAMES.size(); i++) {
            gradeGrid.addColumn(GRADE_VALUE_PROVIDERS.get(i), GRADE_HEADER_NAMES.get(i));
        }
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

    private FilterableGrid<Number, StudentSubjectCalc> createSubjectGrid() {
        FilterableGrid<Number, StudentSubjectCalc> subjectGrid = new FilterableGrid<>(StudentSubjectCalc.class, false, new ArrayList<>(), FilterPosition.TOP);
        for(int i = 0; i < SUBJECT_HEADER_NAMES.size(); i++) {
            subjectGrid.addColumn(SUBJECT_VALUE_PROVIDERS.get(i), SUBJECT_HEADER_NAMES.get(i));
        }
        subjectGrid.setAllRowsVisible(true);
        return subjectGrid;
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
        Optional<NoneComponentFilter<Number, StudentSubjectCalc>> optionalNoneComponentFilter = subjectGrid.getTypeFilters().stream()
                .filter(filter -> filter instanceof NoneComponentFilter)
                .map(filter -> (NoneComponentFilter<Number, StudentSubjectCalc>) filter)
                .findFirst();
        if(optionalNoneComponentFilter.isPresent()) {
            NoneComponentFilter<Number, StudentSubjectCalc> noneComponentFilter = optionalNoneComponentFilter.get();
            noneComponentFilter.setValue(studentService.getStudentNumberGrade(studentNumberField.getValue(), subject).data().get(0).開講年());
        }
    }

    private void addComponentsToLayout() {
        add(studentNumberField, new H1("Student"), new Paragraph("説明"));
        add(subjectComboBox);
    }
}