package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Annotations;
import com.github.appreciated.apexcharts.config.annotations.XAxisAnnotations;
import com.github.appreciated.apexcharts.config.annotations.builder.AnnotationStyleBuilder;
import com.github.appreciated.apexcharts.config.annotations.builder.LabelBuilder;
import com.github.appreciated.apexcharts.config.annotations.builder.XAxisAnnotationsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.component.*;

import java.util.ArrayList;
import java.util.Arrays;

public class SubjectGraphPattern implements GraphPattern {
    private final StudentGrade changeValue;
    private final HorizontalLayout underCharts;

    public SubjectGraphPattern(StudentGrade changeValue) {
        this.changeValue = changeValue;
        underCharts = new HorizontalLayout();
        underCharts.setAlignItems(FlexComponent.Alignment.STRETCH);
    }

    @Override
    public VerticalLayout create(StudentService studentService, String schoolYear, String department) {
        underCharts.removeAll();
        var histData = studentService.getStudentSubjectCalc(changeValue.科目名()).data();
        Data<String, Integer>[] data = new Data[5];
        Data<String, Integer>[] preYearData = new Data[5];
        String[] target = new String[1];
        histData.forEach(e2 -> {
            if(e2.開講年() == changeValue.開講年()) {
                data[0] = new Data<>("不可", e2.不可());
                data[1] = new Data<>("可", e2.可());
                data[2] = new Data<>("良", e2.良());
                data[3] = new Data<>("優", e2.優());
                data[4] = new Data<>("秀", e2.秀());
                if(e2.平均() < 1d) target[0] = "不可";
                else if(e2.平均() < 2d) target[0] = "可";
                else if(e2.平均() < 3d) target[0] = "良";
                else if(e2.平均() < 4d) target[0] = "優";
                else target[0] = "秀";
            } else if(e2.開講年() == changeValue.開講年() - 1) {
                preYearData[0] = new Data<>("不可", e2.不可());
                preYearData[1] = new Data<>("可", e2.可());
                preYearData[2] = new Data<>("良", e2.良());
                preYearData[3] = new Data<>("優", e2.優());
                preYearData[4] = new Data<>("秀", e2.秀());
            }
        });
        final GraphSeries<Data<String, Integer>> series = new GraphSeries(changeValue.科目名(), data);
        String[] strs = new String[]{"不可", "可", "良", "優", "秀"};
        String[] colors = new String[5];
        String[] labels = new String[5];
        var grade = changeValue.成績評価();
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
        Graph graph = Graph.Builder.get().graphType(GRAPH_TYPE.BAR).labels(labels).colors(colors).height("250px").width("100%")
                .distributed(true).YAxisForceNiceScale(true).series(series).dataLabelsEnabled(false).legendShow(false)
                .XAxisAnnotation(target[0].equals(changeValue.成績評価()) ? target[0] + "(あなたの成績位置)" : target[0], "20px", "horizontal", "middle", "平均値").build();
        if(preYearData[0] == null) return new VerticalLayout(graph.getGraph());
        final GraphSeries<Data<String, Integer>> preYearSeries = new GraphSeries(changeValue.科目名(), preYearData);
        Graph preYearGraph = Graph.Builder.get().graphType(GRAPH_TYPE.BAR).YAxisForceNiceScale(true).distributed(true).dataLabelsEnabled(false).series(preYearSeries).height("100%")
                        .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF").height("100%").title("昨年度", GraphAlign.CENTER).legendShow(false).build();
        Graph dummy = Graph.Builder.get().height("100%").series(preYearSeries).build();
        underCharts.add(preYearGraph.getGraph(), dummy.getGraph());
        underCharts.setWidthFull();
        underCharts.setHeight("250px");
        return new VerticalLayout(graph.getGraph(), underCharts);
    }
}
