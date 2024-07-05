package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
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

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final FilterableComboBox<String, StudentGrade> subjectComboBox;
    private final TextField studentNumberField;
    private FilteredGrid<Number, StudentSubjectCalc> subjectGrid;
    private SubjectGraph subjectGraph;
    private final static String ALL = "全体";
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
        this.subjectComboBox = new FilterableComboBox<>("科目名");
        this.studentNumberField = new TextField();

        initializeComponents();
        addComponentsToLayout();
    }

    private void initializeComponents() {
        initializeSubjectComboBox();
        initializeStudentNumberField();
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

    private void addRadioButtonFilter(FilterableComboBox<String, StudentGrade> comboBox, RadioButtonValues values, Function<StudentGrade, String> valueProvider) {
        comboBox.addFilter(RadioButtonFilter.create(
                comboBox,
                values.getValues(),
                (grade, filterValue) -> matchesFilter(filterValue, valueProvider.apply(grade))));
    }

    private static boolean matchesFilter(String filterValue, String itemValue) {
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
        FilteredGrid<String, StudentGrade> gradeGrid = new FilteredGrid<>(StudentGrade.class, GRADE_VALUE_PROVIDERS, GRADE_HEADER_NAMES);
        gradeGrid.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        gradeGrid.addRadioButtonFilters(FILTER_VALUES, FILTER_FUNCTIONS, FILTER_NAMES);
        gradeGrid.addItemClickListener(grade -> subjectComboBox.setValue(grade.getItem()));
        return gradeGrid;
    }

    private FilteredGrid<Number, StudentSubjectCalc> createSubjectGrid() {
        return new FilteredGrid<>(StudentSubjectCalc.class, SUBJECT_VALUE_PROVIDERS, SUBJECT_HEADER_NAMES);
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
        NoneComponentFilter<Number, StudentSubjectCalc> filter = new NoneComponentFilter<>((subjectCalc, grade) -> subjectCalc.開講年() == (int)grade, studentService.getStudentNumberGrade(studentNumberField.getValue(), subject).data().get(0).開講年());
        subjectGrid.addFilter(filter);
        subjectGrid.filter();
    }

    private void addComponentsToLayout() {
        add(studentNumberField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), subjectComboBox.getFilters().get(0).getFilterComponent());
        add(new H3("学科"), subjectComboBox.getFilters().get(1).getFilterComponent());
        add(subjectComboBox);
    }
}