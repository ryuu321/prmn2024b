package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import jp.ac.chitose.ir.service.student.StudentGPA;
import jp.ac.chitose.ir.service.student.StudentService;

public class GPAGraphPattern implements GraphPattern {
    @Override
    public ApexCharts create(StudentService studentService, String schoolYear, String department) {
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
        for(;i < 5; i++) {
            coordinates[i] = new Coordinate<>(gradeChangeString(i), 0);
        }
        final Series<Coordinate<String, Integer>> series = new Series<>();
        series.setData(coordinates);
        ApexCharts chart = ApexChartsBuilder.get().withChart(
                        ChartBuilder.get()
                                .withType(Type.BAR)
                                .build())
                .withXaxis(XAxisBuilder.get()
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withColumnWidth("100%")
                                .withDistributed(true)
                                .build())
                        .build())
                .withLegend(LegendBuilder.get()
                        .withShow(false)
                        .build())
                .withStroke(StrokeBuilder.get().withWidth(0.1).withColors("#000").build())
                .withYaxis(YAxisBuilder.get().withForceNiceScale(true).build())
                .withSeries(series)
                .build();
        String blue = "#0000FF";
        chart.setColors(blue, blue, blue, blue, blue);
        chart.setHeight("400px");
        chart.setWidthFull();
        return chart;
    }

    private String gradeChangeString(int i) {
        switch(i) {
            case 0 -> {
                return "不可";
            }
            case 1 -> {
                return "可";
            }
            case 2 -> {
                return "良";
            }
            case 3 -> {
                return "優";
            }
            default -> {
                return "秀";
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
