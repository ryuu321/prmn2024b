package jp.ac.chitose.ir.presentation.views.student.subjectview;

import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterableGrid;

import java.util.List;

public class SubjectGrid extends FilterableGrid<Number, StudentSubjectCalc> {
    public SubjectGrid(List<Filter<Number, StudentSubjectCalc>> list, FilterPosition position) {
        super(StudentSubjectCalc.class, false, list, position);
    }
}
