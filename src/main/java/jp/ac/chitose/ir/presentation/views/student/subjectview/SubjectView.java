package jp.ac.chitose.ir.presentation.views.student.subjectview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.NoneComponentFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;

import java.util.List;
import java.util.Optional;

public class SubjectView extends VerticalLayout {
    private final StudentService studentService;
    private final SubjectGraph graph;
    private final  SubjectGrid grid;
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


    public SubjectView(StudentService studentService) {
        this.studentService = studentService;
        this.graph = new SubjectGraph();
        this.grid = createSubjectGrid();
        addComponentToLayout();
    }

    private SubjectGrid createSubjectGrid() {
        List<Filter<Number, StudentSubjectCalc>> filters = List.of(new NoneComponentFilter<>((calc, number) -> calc.開講年() == number.intValue()));
        SubjectGrid grid = new SubjectGrid(filters, FilterPosition.TOP);
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

    public void update(String subject, String studentNumber) {
        List<StudentSubjectCalc> data = studentService.getStudentSubjectCalc(subject).data();
        StudentGrade studentGrade = studentService.getStudentNumberGrade(studentNumber, subject).data().get(0);
        updateSubjectGraph(data, studentGrade);
        updateSubjectGrid(data, studentGrade);
    }

    private void updateSubjectGraph(List<StudentSubjectCalc> data, StudentGrade studentGrade) {
        graph.updateGraphs(data, studentGrade);
    }

    private void updateSubjectGrid(List<StudentSubjectCalc> data, StudentGrade studentGrade) {
        grid.setItems(data);
        Optional<NoneComponentFilter<Number, StudentSubjectCalc>> optionalNoneComponentFilter = grid.getTypeFilters().stream()
                .filter(filter -> !filter.hasComponent())
                .map(filter -> (NoneComponentFilter<Number, StudentSubjectCalc>) filter)
                .findFirst();
        if(optionalNoneComponentFilter.isPresent()) {
            NoneComponentFilter<Number, StudentSubjectCalc> noneComponentFilter = optionalNoneComponentFilter.get();
            noneComponentFilter.setValue(studentGrade.開講年());
        }
    }
}
