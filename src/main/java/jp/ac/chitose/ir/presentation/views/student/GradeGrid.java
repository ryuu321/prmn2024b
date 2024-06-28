package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;

public class GradeGrid extends VerticalLayout {
    private final RadioButtonGroup<String> schoolYearsRadioButton;
    private final RadioButtonGroup<String> departmentsRadioButton;
    private final RadioButtonGroup<String> subjectTypesRadioButton;
    private final RadioButtonGroup<String> gradesRadioButton;
    private final GridListDataView<StudentGrade> gradeGridListDataView;
    private static final String ALL = "全体";
    private static final String[] SCHOOL_YEARS = {ALL, "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生"};
    private static final String[] DEPARTMENTS = {ALL, "理工学部", "応用化学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科"};
    private static final String[] SUBJECT_TYPES = {ALL, "必修", "選択必修", "選択"};
    private static final String[] GRADES = {ALL, "不可", "可", "良", "優", "秀"};

    public GradeGrid(StudentService studentService, ComboBox<StudentGrade> subjectComboBox, String textFieldValue) {
        schoolYearsRadioButton = createRadioButton(SCHOOL_YEARS);
        departmentsRadioButton = createRadioButton(DEPARTMENTS);
        subjectTypesRadioButton = createRadioButton(SUBJECT_TYPES);
        gradesRadioButton = createRadioButton(GRADES);

        Grid<StudentGrade> gradeGrid = createGrid(studentService, textFieldValue, subjectComboBox);
        gradeGridListDataView = gradeGrid.getListDataView();
        addComponentsToLayout(gradeGrid);
    }

    @SafeVarargs
    private <T> RadioButtonGroup<T> createRadioButton(T... options) {
        RadioButtonGroup<T> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(options);
        radioButtonGroup.setValue(options[0]);
        radioButtonGroup.addValueChangeListener(valueChangeEvent -> gradeGridListDataView.setFilter(this::isMatchRadioButtons));
        return radioButtonGroup;
    }

    private boolean isMatchRadioButtons(StudentGrade studentGrade) {
        boolean schoolYearMatches = matchesFilter(schoolYearsRadioButton.getValue(), studentGrade.schoolYear());
        boolean departmentMatches = matchesFilter(departmentsRadioButton.getValue(), studentGrade.department());
        boolean subjectTypeMatches = matchesFilter(subjectTypesRadioButton.getValue(), studentGrade.必選別());
        boolean gradeMatches = matchesFilter(gradesRadioButton.getValue(), studentGrade.成績評価());
        return schoolYearMatches && departmentMatches && subjectTypeMatches && gradeMatches;
    }

    private boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals(ALL) || filterValue.equals(itemValue);
    }

    private Grid<StudentGrade> createGrid(StudentService studentService, String textFieldValue, ComboBox<StudentGrade> subjectComboBox) {
        Grid<StudentGrade> grid = new Grid<>(StudentGrade.class, false);
        addColumnsToGrid(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addItemClickListener(event -> subjectComboBox.setValue(event.getItem()));
        grid.setItems(studentService.getStudentNumberGrades(textFieldValue).data());
        return grid;
    }

    private void addColumnsToGrid(Grid<StudentGrade> grid) {
        grid.addColumn(StudentGrade::科目名).setHeader("科目名").setWidth("30%");
        grid.addColumn(StudentGrade::schoolYear).setHeader("対象学年");
        grid.addColumn(StudentGrade::department).setHeader("対象学科");
        grid.addColumn(StudentGrade::必選別).setHeader("必選別");
        grid.addColumn(StudentGrade::科目の単位数).setHeader("単位数");
        grid.addColumn(StudentGrade::成績評価).setHeader("成績評価");
    }

    private void addComponentsToLayout(Grid<StudentGrade> grid) {
        add(new H3("学年"), schoolYearsRadioButton);
        add(new H3("学部・学科"), departmentsRadioButton);
        add(new H3("必選別"), subjectTypesRadioButton);
        add(new H3("成績評価"), gradesRadioButton);
        add(grid);
    }
}
