package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.TitleSubtitle;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentGPA;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.component.GRAPH_TYPE;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphAlign;
import jp.ac.chitose.ir.views.component.GraphSeries;

public class GPAGraphPattern implements GraphPattern {
    @Override
    public VerticalLayout create(StudentService studentService, String schoolYear, String department) {
        schoolYear = schoolYearChange(schoolYear);
        final var GPAData = studentService.getStudentGPA().data();
        Coordinate<String, Integer>[] coordinates = new Coordinate[5];
        int i = 0;
        for(StudentGPA GPA : GPAData) {
            if(GPA.学年().equals(schoolYear)) {
                coordinates[i] = new Coordinate<>(gradeChangeString(i), GPA.度数());
                i++;
            }
        }
        for(;i < 5; i++) coordinates[i] = new Coordinate<>(gradeChangeString(i), 0);
        final GraphSeries<Coordinate<String, Integer>> series = new GraphSeries<>(coordinates);
        Graph graph = Graph.Builder.get().histogram().series(series).width("100%").height("250px").YAxisForceNiceScale(true)
                .title("GPA", GraphAlign.CENTER).dataLabelsEnabled(false).legendShow(false)
                .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF").build();
        return new VerticalLayout(graph.getGraph());
    }

    private String gradeChangeString(int i) {
        switch(i) {
            case 0 -> {
                return "0";
            }
            case 1 -> {
                return "1";
            }
            case 2 -> {
                return "2";
            }
            case 3 -> {
                return "3";
            }
            default -> {
                return "4";
            }
        }
    }

    private String schoolYearChange(String schoolYear) {
        switch(schoolYear) {
            case "1年生" -> {
                return "B1";
            }
            case "2年生" -> {
                return "B2";
            }
            case "3年生" -> {
                return "B3";
            }
            case "4年生" -> {
                return "B4";
            }
            case "修士1年生" -> {
                return "M1";
            }
            default -> {
                return "M2";
            }
        }
    }
}
