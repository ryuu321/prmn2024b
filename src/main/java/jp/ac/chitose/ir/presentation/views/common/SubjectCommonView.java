package jp.ac.chitose.ir.presentation.views.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jp.ac.chitose.ir.application.service.student.GradeCount;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;

import java.util.List;
import java.util.Map;

public class SubjectCommonView extends VerticalLayout {
    private final StudentGradeService studentGradeService;
    private final CommonView commonView;
    private final H1 lectureName;
    private final Button backButton;
    private final RadioButtonGroup<String> years;
    private final CommonGraph commonGraph;

    public SubjectCommonView(StudentGradeService studentGradeService, CommonView commonView) {
        this.studentGradeService = studentGradeService;
        this.commonView = commonView;
        this.lectureName = new H1();
        this.backButton = createBackButton(commonView);
        years = new RadioButtonGroup<>();
        commonGraph = new CommonGraph();
        add(lectureName, backButton, years, commonGraph);
    }

    public void updateView() {
        commonView.updateView(null);
    }

    public void updateData(StudentGrade grade) {
        lectureName.setText(grade.lecture_name());
        Map<String, Map<String, String>> dict = commonView.getCourseIdDict().courseIdDict();
        List<String> keys = dict.get(grade.lecture_name()).keySet().stream().sorted().toList();
        years.setItems(keys);
        years.setValue(keys.get(0));
        years.addValueChangeListener(value -> updateGraph(value.getValue()));
        GradeCount histData = studentGradeService.getGradeGraph(dict.get(grade.lecture_name()).get(years.getValue())).data().get(0);
        commonGraph.updateGraphs(histData, grade.lecture_name());
    }

    private void updateGraph(String year) {
        Map<String, Map<String, String>> dict = commonView.getCourseIdDict().courseIdDict();
        GradeCount histData = studentGradeService.getGradeGraph(dict.get(lectureName.getText()).get(years.getValue())).data().get(0);
        commonGraph.updateGraphs(histData, lectureName.getText());
    }

    private Button createBackButton(CommonView commonView) {
        Button button = new Button("戻る");
        button.addClickListener(click -> commonView.updateView(null));
        return button;
    }
}
