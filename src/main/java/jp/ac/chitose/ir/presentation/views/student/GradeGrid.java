package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;

import java.util.function.Function;

public class GradeGrid extends VerticalLayout {
    private final FilterGrid<String, StudentGrade> gradeGrid;
    private static final String ALL = "全体";

    public GradeGrid(StudentService studentService, ComboBox<StudentGrade> subjectComboBox, String studentNumber) {
        this.gradeGrid = initializeGrid(studentService, studentNumber, subjectComboBox);
        configureLayout();
    }

    private FilterGrid<String, StudentGrade> initializeGrid(StudentService studentService, String studentNumber, ComboBox<StudentGrade> subjectComboBox) {
        FilterGrid<String, StudentGrade> grid = new FilterGrid<>(StudentGrade.class, false);
        configureGrid(grid, subjectComboBox);
        grid.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        return grid;
    }

    private void configureGrid(FilterGrid<String, StudentGrade> grid, ComboBox<StudentGrade> subjectComboBox) {
        addColumns(grid);
        addFilters(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addItemClickListener(event -> subjectComboBox.setValue(event.getItem()));
    }

    private void addColumns(Grid<StudentGrade> grid) {
        grid.addColumn(StudentGrade::科目名).setHeader("科目名").setWidth("30%");
        grid.addColumn(StudentGrade::schoolYear).setHeader("対象学年");
        grid.addColumn(StudentGrade::department).setHeader("対象学科");
        grid.addColumn(StudentGrade::必選別).setHeader("必選別");
        grid.addColumn(StudentGrade::科目の単位数).setHeader("単位数");
        grid.addColumn(StudentGrade::成績評価).setHeader("成績評価");
    }

    private void addFilters(FilterGrid<String, StudentGrade> grid) {
        addRadioButtonFilter(grid, RadioButtonValues.SCHOOL_YEARS, StudentGrade::schoolYear);
        addRadioButtonFilter(grid, RadioButtonValues.DEPARTMENTS, StudentGrade::department);
        addRadioButtonFilter(grid, RadioButtonValues.SUBJECT_TYPES, StudentGrade::必選別);
        addRadioButtonFilter(grid, RadioButtonValues.GRADES, StudentGrade::成績評価);
    }

    private void addRadioButtonFilter(FilterGrid<String, StudentGrade> grid, RadioButtonValues values, Function<StudentGrade, String> valueProvider) {
        grid.addFilter(RadioButtonFilter.createRadioButtonFilter(
                grid,
                values.getValues(),
                (grade, filterValue) -> matchesFilter(filterValue, valueProvider.apply(grade))));
    }

    private boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals(ALL) || filterValue.equals(itemValue);
    }

    private void configureLayout() {
        add(new H3("学年"), gradeGrid.getFilters().get(0).getFilterComponent());
        add(new H3("学部・学科"), gradeGrid.getFilters().get(1).getFilterComponent());
        add(new H3("必選別"), gradeGrid.getFilters().get(2).getFilterComponent());
        add(new H3("成績評価"), gradeGrid.getFilters().get(3).getFilterComponent());
        add(gradeGrid);
    }
}