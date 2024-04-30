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
    final private String blue = "#0000FF";
    final private String red = "#FF0000";

    @Override
    public ApexCharts create(StudentService studentService) {
        String grade = "B1";
        var GPAData = studentService.getStudentGPA().data();
        Coordinate<String, Integer>[] coordinates = new Coordinate[5];
        int i = 0;
        for(StudentGPA GPA : GPAData) {
            if(GPA.学年().equals(grade)) {
                coordinates[i] = new Coordinate<>(changeString(i), GPA.度数());
                i++;
            }
        }
        for(;i < 5; i++) {
            coordinates[i] = new Coordinate<>(changeString(i), 0);
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
                                .build()) // BARの間隔を０に近づける（見た目を調整してヒストグラムにみえるようにする）
                        .build())
                .withLegend(LegendBuilder.get()
                        .withShow(false)
                        .build())
                .withStroke(StrokeBuilder.get().withWidth(0.1).withColors("#000").build()) // 柱（棒）の外枠を黒色に設定してヒストグラムに見た目を近づける
                .withYaxis(YAxisBuilder.get().withForceNiceScale(true).build())
                .withSeries(series)
                .build();
        chart.setColors(blue, blue, blue, blue, blue);
        chart.setHeight("400px");
        chart.setWidthFull();
        return chart;
    }

    private String changeString(int i) {
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
}
