package jp.ac.chitose.ir.presentation.views.student.subjectview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;

import java.util.Collections;
import java.util.List;

public class SubjectView extends VerticalLayout {
    private final StudentGradeService studentGradeService;
    private final SubjectGraph graph;
    private final  SubjectGrid grid;
    private final static List<String> SUBJECT_HEADER_NAMES = List.of("受講人数", "不可", "可", "良", "優", "秀", "平均", "分散");
    private final static List<ValueProvider<StudentSubjectCalc, Number>> SUBJECT_VALUE_PROVIDERS = List.of(
            StudentSubjectCalc::合計の人数,
            StudentSubjectCalc::不可,
            StudentSubjectCalc::可,
            StudentSubjectCalc::良,
            StudentSubjectCalc::優,
            StudentSubjectCalc::秀,
            StudentSubjectCalc::平均,
            StudentSubjectCalc::分散);


    public SubjectView(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
        this.graph = new SubjectGraph();
        this.grid = createSubjectGrid();
        addComponentToLayout();
    }

    private SubjectGrid createSubjectGrid() {
        SubjectGrid grid = new SubjectGrid(Collections.emptyList(), FilterPosition.TOP);
        for(int i = 0; i < SUBJECT_HEADER_NAMES.size(); i++) {
            grid.addColumn(SUBJECT_VALUE_PROVIDERS.get(i), SUBJECT_HEADER_NAMES.get(i));
        }
        grid.setAllRowsVisible(true);
        return grid;
    }

    private void addComponentToLayout() {
        add(graph);
        add(grid);
    }

    public void update(StudentGrade grade) {
        List<StudentSubjectCalc> data = studentGradeService.getGradeGraph(grade.course_id()).data();
        List<StudentSubjectCalc> preYearData = null;
        if(grade.pre_year_course_id() != null) preYearData = studentGradeService.getGradeGraph(grade.pre_year_course_id()).data();
        updateSubjectGraph(data, preYearData, grade);
        updateSubjectGrid(data);
    }

    private void updateSubjectGraph(List<StudentSubjectCalc> data, List<StudentSubjectCalc> preYearData, StudentGrade studentGrade) {
        graph.updateGraphs(data, preYearData, studentGrade);
    }

    private void updateSubjectGrid(List<StudentSubjectCalc> data) {
        grid.setItems(data);
    }
}
