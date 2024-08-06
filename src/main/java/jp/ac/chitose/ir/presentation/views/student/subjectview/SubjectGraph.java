package jp.ac.chitose.ir.presentation.views.student.subjectview;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.component.graph.*;

import java.util.Arrays;
import java.util.List;

public class SubjectGraph extends VerticalLayout {
    private Graph mainGraph;
    private final Graph preYearGraph;
    private final HorizontalLayout mainGraphLayout;
    private final HorizontalLayout subGraphsLayout;
    private static final String[] GRADE_LABELS = {"不可", "可", "良", "優", "秀"};
    private static final double[] GRADE_THRESHOLDS = {0.5, 1.5, 2.5, 3.5, 4.5};
    private static final String RED = "#FF0000";
    private static final String BLUE = "#0000FF";
    private static final String LAYOUT_HEIGHT = "40vh";
    private static final String NO_DATA_LAYOUT_HEIGHT = "0px";
    private static final String GRAPH_HEIGHT = "100%";

    public SubjectGraph() {
        mainGraphLayout = createLayout();
        subGraphsLayout = createLayout();
        mainGraph = createInitialGraph();
        preYearGraph = createInitialPreYearGraph();
        addInitialLayout();
    }

    private HorizontalLayout createLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setHeight(SubjectGraph.LAYOUT_HEIGHT);
        return layout;
    }

    private Graph createInitialGraph() {
        return Graph.Builder.get()
                .graphType(GRAPH_TYPE.BAR)
                .width("100%")
                .height(GRAPH_HEIGHT)
                .legendShow(false)
                .dataLabelsEnabled(false)
                .YAxisForceNiceScale(true)
                .series(new GraphSeries<>())
                .distributed(true)
                .build();
    }

    private Graph createInitialPreYearGraph() {
        return Graph.Builder.get()
                .graphType(GRAPH_TYPE.BAR)
                .YAxisForceNiceScale(true)
                .distributed(true)
                .dataLabelsEnabled(false)
                .series(new GraphSeries<>(0, 0, 0, 0, 0))
                .colors()
                .height(GRAPH_HEIGHT)
                .title("昨年度", GraphAlign.CENTER)
                .legendShow(false)
                .build();
    }

    private void addInitialLayout() {
        mainGraphLayout.add(mainGraph.getGraph());
        subGraphsLayout.add(preYearGraph.getGraph());
        add(mainGraphLayout, subGraphsLayout);
    }

    public void updateGraphs(List<StudentSubjectCalc> histData, List<StudentSubjectCalc> preYearHistData, StudentGrade studentGrade) {
        // メイングラフの更新
        final String[] colors = createColors(studentGrade.grading());
        final String[] labels = createLabels(studentGrade.grading());
        final GraphSeries<Data<String, Integer>> mainGraphSeries = createSelectYearSeries(histData, studentGrade.lecture_name());
        updateMainGraph(studentGrade.grading(), findTargetGrade(histData), colors, labels, mainGraphSeries);

        // サブグラフの更新
        updateSubGraphs(preYearHistData, studentGrade);
    }

    private String findTargetGrade(List<StudentSubjectCalc> histData) {
        return histData.stream()
                .map(StudentSubjectCalc::平均)
                .map(average -> {
                    for (int i = 0; i < GRADE_THRESHOLDS.length; i++)
                        if (average < GRADE_THRESHOLDS[i]) return GRADE_LABELS[i];
                    return GRADE_LABELS[0];
                })
                .findFirst()
                .orElse("");
    }

    private void updateSubGraphs(List<StudentSubjectCalc> preYearHistData, StudentGrade studentGrade) {
        if (studentGrade.pre_year_course_id() == null) {
            hidePreYearGraph();
        } else {
            GraphSeries<Data<String, Integer>> preYearSeries = createSelectYearSeries(preYearHistData, studentGrade.lecture_name());
            updatePreYearGraph(preYearSeries);
        }
    }

    private void hidePreYearGraph() {
        subGraphsLayout.remove(preYearGraph.getGraph());
        subGraphsLayout.setHeight(NO_DATA_LAYOUT_HEIGHT);
    }

    private String[] createColors(String grade) {
        return Arrays.stream(GRADE_LABELS)
                .map(label -> label.equals(grade) ? RED : BLUE)
                .toArray(String[]::new);
    }

    private String[] createLabels(String grade) {
        return Arrays.stream(GRADE_LABELS)
                .map(label -> label.equals(grade) ? grade + "(あなたの成績位置)" : label)
                .toArray(String[]::new);
    }

    private void updateMainGraph(String grade, String target, String[] colors, String[] labels, GraphSeries<Data<String, Integer>> series) {
        final String annotationString = target.equals(grade) ? target + "(あなたの成績位置)" : target;
        mainGraphLayout.remove(mainGraph.getGraph());
        mainGraph = mainGraph.getBuilder()
                .labels(labels)
                .colors(colors)
                .series(series)
                .resetAnnotations()
                .XAxisAnnotation(annotationString, "20px", "horizontal", "middle", "平均値")
                .build();
        mainGraphLayout.add(mainGraph.getGraph());
    }

    private GraphSeries<Data<String, Integer>> createSelectYearSeries(List<StudentSubjectCalc> histData, String subject) {
        Data<String, Integer>[] selectYearData = createDataArray(histData);
        return new GraphSeries<>(subject, selectYearData);
    }

    private Data<String, Integer>[] createDataArray(List<StudentSubjectCalc> dataList) {
        Data<String, Integer>[] dataArray = new Data[5];
        dataList.forEach(data -> {
            dataArray[0] = new Data<>(GRADE_LABELS[0], data.不可());
            dataArray[1] = new Data<>(GRADE_LABELS[1], data.可());
            dataArray[2] = new Data<>(GRADE_LABELS[2], data.良());
            dataArray[3] = new Data<>(GRADE_LABELS[3], data.優());
            dataArray[4] = new Data<>(GRADE_LABELS[4], data.秀());
        });
        return dataArray;
    }

    private void updatePreYearGraph(GraphSeries<Data<String, Integer>> preYearSeries) {
        subGraphsLayout.setHeight(LAYOUT_HEIGHT);
        if (subGraphsLayout.getChildren().findAny().isEmpty()) subGraphsLayout.add(preYearGraph.getGraph());
        preYearGraph.updateSeries(preYearSeries);
    }
}