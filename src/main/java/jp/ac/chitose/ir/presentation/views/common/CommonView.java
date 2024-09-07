package jp.ac.chitose.ir.presentation.views.common;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.student.RadioButtonValues;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.RadioButtonFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("common")
@Route(value = "common", layout = MainLayout.class)
public class CommonView extends VerticalLayout {
    private final StudentGradeService studentGradeService;
    private final CommonGrid grid;
    private final static List<String> FILTER_NAMES = List.of("学年", "学科", "必選別");
    private final static List<RadioButtonValues> FILTER_VALUES = List.of(
            RadioButtonValues.SCHOOL_YEARS,
            RadioButtonValues.DEPARTMENTS,
            RadioButtonValues.SUBJECT_TYPES);
    private final static List<BiPredicate<StudentGrade, String>> FILTER_FUNCTIONS = List.of(
            (grade, str) -> matchesFilter(str, grade.schoolYear()),
            (grade, str) -> matchesFilter(str, grade.department()),
            (grade, str) -> matchesFilter(str, grade.compulsory_subjects()));
    private final static List<String> GRADE_HEADER_NAMES = List.of("科目名", "対象学年", "対象学科", "必選別", "単位数");
    private final static List<ValueProvider<StudentGrade, String>> GRADE_VALUE_PROVIDERS = List.of(
            StudentGrade::lecture_name,
            StudentGrade::schoolYear,
            StudentGrade::department,
            StudentGrade::compulsory_subjects,
            grade -> String.valueOf(grade.number_credits_course()));

    public CommonView(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
        grid = new CommonGrid(createCommonGridFilters(), FilterPosition.TOP);
        for(int i = 0; i < GRADE_HEADER_NAMES.size(); i++) {
            grid.addColumn(GRADE_VALUE_PROVIDERS.get(i), GRADE_HEADER_NAMES.get(i));
        }
        grid.setItems(studentGradeService.getSubjectStudents().data());
        grid.setAllRowsVisible(true);
        add(grid);
    }

    private List<Filter<String, StudentGrade>> createCommonGridFilters() {
        List<Filter<String, StudentGrade>> filters = new ArrayList<>();
        for(int i = 0; i < Math.min(FILTER_VALUES.size(), FILTER_FUNCTIONS.size()); i++) {
            filters.add(new RadioButtonFilter<>(FILTER_VALUES.get(i).getValues(), FILTER_FUNCTIONS.get(i), FILTER_NAMES.get(i)));
        }
        return filters;
    }

    private static boolean matchesFilter(String filterValue, String itemValue) {
        return filterValue.equals("全体") || filterValue.equals(itemValue);
    }
}
