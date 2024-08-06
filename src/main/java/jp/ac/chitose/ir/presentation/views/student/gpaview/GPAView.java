package jp.ac.chitose.ir.presentation.views.student.gpaview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import jp.ac.chitose.ir.application.service.student.StudentGPA;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.views.student.RadioButtonValues;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.RadioButtonFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterableComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class GPAView extends VerticalLayout {
    private final StudentService studentService;
    //private final GPAGraph graph;
    private final GradeGrid grid;
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

    public GPAView(StudentService studentService, String studentNumber, FilterableComboBox<String, StudentGrade> subjectComboBox) {
        this.studentService = studentService;
        /*String schoolYear = studentService.getStudentSchoolYear(studentNumber).data().get(0).学年();
        List<StudentGPA> gpaData = studentService.getStudentGPA().data();
        graph = new GPAGraph(gpaData, schoolYear);*/
        grid = createGradeGrid(studentNumber, subjectComboBox);
        addComponentToLayout();
    }

    private GradeGrid createGradeGrid(String studentNumber, FilterableComboBox<String, StudentGrade> subjectComboBox) {
        GradeGrid grid = new GradeGrid(createGradeGridFilters(), FilterPosition.TOP);
        for(int i = 0; i < GRADE_HEADER_NAMES.size(); i++) {
            grid.addColumn(GRADE_VALUE_PROVIDERS.get(i), GRADE_HEADER_NAMES.get(i));
        }
        grid.setAllRowsVisible(true);
        grid.setItems(studentService.getStudentNumberGrades(studentNumber).data());
        grid.addItemClickListener(grade -> subjectComboBox.setValue(grade.getItem()));
        return grid;
    }

    private List<Filter<String, StudentGrade>> createGradeGridFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        for(int i = 0; i < Math.min(FILTER_VALUES.size(), FILTER_FUNCTIONS.size()); i++) {
            filters.add(new RadioButtonFilter<>(FILTER_VALUES.get(i).getValues(), FILTER_FUNCTIONS.get(i), FILTER_NAMES.get(i)));
        }
        return filters;
    }

    private void addComponentToLayout() {
        //add(graph);
        add(grid);
    }

    private static boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals("全体") || filterValue.equals(itemValue);
    }
}
