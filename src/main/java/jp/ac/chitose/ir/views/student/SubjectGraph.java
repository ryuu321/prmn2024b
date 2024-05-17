package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentSubjectCalc;
import jp.ac.chitose.ir.views.component.*;

import java.util.List;

public class SubjectGraph extends VerticalLayout {
    private Graph mainGraph;
    private final Graph preYearGraph;
    private final Graph dummy1;
    private final Graph dummy2;
    private final HorizontalLayout mainGraphLayout;
    private final HorizontalLayout underGraphsLayout;
    private final String[] strs = new String[]{"不可", "可", "良", "優", "秀"};

    // コンストラクタ　複数のグラフを横並びにするためのHorizontalLayoutの初期化
    public SubjectGraph() {
        mainGraphLayout = new HorizontalLayout();
        mainGraphLayout.setWidthFull();
        mainGraphLayout.setHeight("40vh");
        underGraphsLayout = new HorizontalLayout();
        underGraphsLayout.setWidthFull();
        underGraphsLayout.setHeight("40vh");
        mainGraph = Graph.Builder.get().graphType(GRAPH_TYPE.BAR).width("100%").height("100%").legendShow(false)
                .dataLabelsEnabled(false).YAxisForceNiceScale(true).series(new GraphSeries()).distributed(true).build();
        preYearGraph = Graph.Builder.get().graphType(GRAPH_TYPE.BAR).YAxisForceNiceScale(true).distributed(true).dataLabelsEnabled(false).series(new GraphSeries(0, 0, 0, 0, 0))
                .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF").height("100%").title("昨年度", GraphAlign.CENTER).legendShow(false).build();
        dummy1 = preYearGraph.getBuilder().build();
        dummy2 = preYearGraph.getBuilder().build();
        mainGraphLayout.add(mainGraph.getGraph());
        underGraphsLayout.add(preYearGraph.getGraph(), dummy1.getGraph(), dummy2.getGraph());
        add(mainGraphLayout, underGraphsLayout);
    }

    // 指定された科目のグラフを生成する機能　受けた年のグラフ、その前年のグラフを実装済み
    public void create(List<StudentSubjectCalc> histData, StudentGrade studentGrade) {
        String[] target = new String[1];
        final GraphSeries<Data<String, Integer>> series = createGraphSeries(histData, target, studentGrade);
        String[] colors = new String[5];
        String[] labels = new String[5];
        String grade = studentGrade.成績評価();
        createColorsAndLabels(grade, colors, labels);
        mainGraphLayout.remove(mainGraph.getGraph());
        mainGraph = mainGraph.getBuilder().labels(labels).colors(colors).series(series).resetAnnotations()
                        .XAxisAnnotation(target[0].equals(studentGrade.成績評価()) ? target[0] + "(あなたの成績位置)" : target[0], "20px", "horizontal", "middle", "平均値").build();
        mainGraphLayout.add(mainGraph.getGraph());
        GraphSeries<Data<String, Integer>> preYearSeries = createPreYearSeries(histData, studentGrade);
        if(preYearSeries == null) {
            underGraphsLayout.removeAll();;
            underGraphsLayout.setHeight("0vh");
        }
        else {
            underGraphsLayout.setHeight("40vh");
            if(underGraphsLayout.getChildren().count() == 0) underGraphsLayout.add(preYearGraph.getGraph(), dummy1.getGraph(), dummy2.getGraph());
            preYearGraph.updateSeries(preYearSeries);
            dummy1.updateSeries(preYearSeries);
            dummy2.updateSeries(preYearSeries);
        }
        long cnt = underGraphsLayout.getChildren().count();
        underGraphsLayout.getChildren().forEach(component -> {
            if(component.isVisible()) component.getStyle().setWidth((float) 100 / cnt + "%");
            else component.getStyle().setWidth("0%");
        });
        System.out.println(preYearGraph.getGraph().getWidth() + " " + dummy1.getGraph().getWidth() + " " + dummy2.getGraph().getWidth());;
    }

    // 選ばれた科目を生徒が受けた年のSeriesを作る機能
    // 平均値も同時に割り出す
    private GraphSeries<Data<String, Integer>> createGraphSeries(List<StudentSubjectCalc> histData, String[] target, StudentGrade studentGrade) {
        Data<String, Integer>[] data = new Data[5];
        histData.forEach(e2 -> {
            if(e2.開講年() == studentGrade.開講年()) {
                data[0] = new Data<>("不可", e2.不可());
                data[1] = new Data<>("可", e2.可());
                data[2] = new Data<>("良", e2.良());
                data[3] = new Data<>("優", e2.優());
                data[4] = new Data<>("秀", e2.秀());
                if(e2.平均() + 0.5 < 1d) target[0] = "不可";
                else if(e2.平均() + 0.5 < 2d) target[0] = "可";
                else if(e2.平均() + 0.5 < 3d) target[0] = "良";
                else if(e2.平均() + 0.5 < 4d) target[0] = "優";
                else target[0] = "秀";
            }
        });
        return new GraphSeries(studentGrade.科目名(), data);
    }

    // 選ばれた科目の生徒が受ける前の年のSeriesを作る機能
    // 去年のデータがない場合、nullを返す
    private GraphSeries<Data<String, Integer>> createPreYearSeries(List<StudentSubjectCalc> histData, StudentGrade studentGrade) {
        Data<String, Integer>[] preYearData = new Data[5];
        histData.forEach(e2 -> {
            if (e2.開講年() == studentGrade.開講年() - 1) {
                preYearData[0] = new Data<>("不可", e2.不可());
                preYearData[1] = new Data<>("可", e2.可());
                preYearData[2] = new Data<>("良", e2.良());
                preYearData[3] = new Data<>("優", e2.優());
                preYearData[4] = new Data<>("秀", e2.秀());
            }
        });
        if(preYearData[0] == null) return null;
        return new GraphSeries<>(studentGrade.科目名(), preYearData);
    }

    // 成績位置などを知らせるためのグラフのカラーとラベルを作るための機能
    private void createColorsAndLabels(String grade, String[] colors, String[] labels) {
        for(int i = 0; i < 5; i++) {
            if(strs[i].equals(grade)) {
                String red = "#FF0000";
                colors[i] = red;
                labels[i] = grade + "(あなたの成績位置)";
            }
            else {
                String blue = "#0000FF";
                colors[i] = blue;
                labels[i] = strs[i];
            }
        }
    }
}
