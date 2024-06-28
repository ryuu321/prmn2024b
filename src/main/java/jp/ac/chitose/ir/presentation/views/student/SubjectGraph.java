package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.presentation.component.graph.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SubjectGraph extends VerticalLayout {
    private Graph mainGraph;
    private final Graph preYearGraph;
    private final HorizontalLayout mainGraphLayout;
    private final HorizontalLayout underGraphsLayout;
    private final static String[] GRADE_LABELS = new String[]{"不可", "可", "良", "優", "秀"};
    private static final double[] GRADE_THRESHOLDS = {0.5, 1.5, 2.5, 3.5, 4.5};
    private final static String RED = "#FF0000";
    private final static String BLUE = "#0000FF";

    public SubjectGraph() {
        mainGraphLayout = createLayout();
        underGraphsLayout = createLayout();
        mainGraph = createInitialMainGraph();
        preYearGraph = createInitialPreYearGraph();
        addInitialLayout();
    }

    private HorizontalLayout createLayout() {
        final HorizontalLayout Layout = new HorizontalLayout();
        Layout.setWidthFull();
        Layout.setHeight("40vh");
        return Layout;
    }

    private Graph createInitialMainGraph() {
        return Graph.Builder.get().graphType(GRAPH_TYPE.BAR).width("100%").height("100%").legendShow(false)
                .dataLabelsEnabled(false).YAxisForceNiceScale(true).series(new GraphSeries<>())
                .distributed(true).build();
    }

    private Graph createInitialPreYearGraph() {
        return Graph.Builder.get().graphType(GRAPH_TYPE.BAR).YAxisForceNiceScale(true).distributed(true)
                .dataLabelsEnabled(false).series(new GraphSeries<>(0, 0, 0, 0, 0)).colors().height("100%")
                .title("昨年度", GraphAlign.CENTER).legendShow(false).build();
    }

    private void addInitialLayout() {
        mainGraphLayout.add(mainGraph.getGraph());
        underGraphsLayout.add(preYearGraph.getGraph());
        add(mainGraphLayout, underGraphsLayout);
    }

    // 指定された科目のグラフを生成する機能　受けた年のグラフ、その前年のグラフを実装済み
    public void updateGraphs(List<StudentSubjectCalc> histData, StudentGrade studentGrade) {
        final String target = findTargetGrade(histData, studentGrade);
        final String[] colors = createColors(studentGrade.成績評価());
        final String[] labels = createLabels(studentGrade.成績評価());
        final GraphSeries<Data<String, Integer>> mainGraphSeries = createSelectYearSeries(histData, studentGrade.開講年(), studentGrade.科目名());
        updateMainGraph(studentGrade.成績評価(), target, colors, labels, mainGraphSeries);

        if(!hasYearData(histData, studentGrade.開講年() - 1)) {
            underGraphsLayout.setHeight("0px");
            return;
        }
        GraphSeries<Data<String, Integer>> preYearSeries = createSelectYearSeries(histData, studentGrade.開講年() - 1, studentGrade.科目名());
        updatePreYearGraph(preYearSeries);
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

    private String findTargetGrade(List<StudentSubjectCalc> histData, StudentGrade studentGrade) {
        double average = histData.stream()
                .filter(data -> data.開講年() == studentGrade.開講年())
                .findFirst()
                .get()
                .平均();
        for (int i = 0; i < GRADE_THRESHOLDS.length; i++) {
            if (average + 0.5 < GRADE_THRESHOLDS[i]) {
                return GRADE_LABELS[i];
            }
        }
        return "";
    }

    private GraphSeries<Data<String, Integer>> createSelectYearSeries(List<StudentSubjectCalc> histData, int year, String subject) {
        final Data<String, Integer>[] selectYearData = new Data[5];
        for(StudentSubjectCalc data : histData) {
            if(data.開講年() != year) continue;
            selectYearData[0] = new Data<>(GRADE_LABELS[0], data.不可() + data.欠席());
            selectYearData[1] = new Data<>(GRADE_LABELS[1], data.可());
            selectYearData[2] = new Data<>(GRADE_LABELS[2], data.良());
            selectYearData[3] = new Data<>(GRADE_LABELS[3], data.優());
            selectYearData[4] = new Data<>(GRADE_LABELS[4], data.秀());
        }
        return new GraphSeries<>(subject, selectYearData);
    }

    private boolean hasYearData(List<StudentSubjectCalc> histData, int year) {
        Optional<StudentSubjectCalc> opt = histData.stream()
                .filter(data -> data.開講年() == year)
                .findFirst();
        return opt.isPresent();
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

    private void updatePreYearGraph(GraphSeries<Data<String, Integer>> preYearSeries) {
        underGraphsLayout.setHeight("40vh");
        if (underGraphsLayout.getChildren().findAny().isEmpty()) underGraphsLayout.add(preYearGraph.getGraph());
        preYearGraph.updateSeries(preYearSeries);
    }
}
