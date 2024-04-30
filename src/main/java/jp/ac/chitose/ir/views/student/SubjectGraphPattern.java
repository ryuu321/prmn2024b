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
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.component.Graph;

import java.util.ArrayList;

public class SubjectGraphPattern implements GraphPattern {
    final private String blue = "#0000FF";
    final private String red = "#FF0000";
    private StudentGrade changeValue;

    public SubjectGraphPattern(StudentGrade changeValue) {
        this.changeValue = changeValue;
    }

    @Override
    public ApexCharts create(StudentService studentService) {
        var histData = studentService.getStudentHist(changeValue.科目名()).data();
        Coordinate<String, Integer>[] data = new Coordinate[5];
        histData.forEach(e2 -> {
            if(e2.成績評価().equals("秀")) data[4] = new Coordinate<>(e2.成績評価(), e2.度数());
            else if(e2.成績評価().equals("優")) data[3] = new Coordinate<>(e2.成績評価(), e2.度数());
            else if(e2.成績評価().equals("良")) data[2] = new Coordinate<>(e2.成績評価(), e2.度数());
            else if(e2.成績評価().equals("可")) data[1] = new Coordinate<>(e2.成績評価(), e2.度数());
            else data[0] = new Coordinate<>(e2.成績評価(), e2.度数());
        });
        String[] strs = new String[]{"不可", "可", "良", "優", "秀"};
        for(int i = 0; i < 5; i++) if(data[i] == null) data[i] = new Coordinate<>(strs[i], 0);
        final Series<Coordinate<String, Integer>> series = new Series(changeValue.科目名());
        series.setData(data);
        var grade = studentService.getStudentNumberGrade(changeValue.学籍番号(), changeValue.科目名()).data();
        Annotations annotations = new Annotations();
        ArrayList<XAxisAnnotations> xAxisAnnotations = new ArrayList<>();
        String[] target = new String[]{""};
        for(int i = 0; i < histData.size(); i++) {
            var e = histData.get(i);
            if(e.成績評価().equals("平均")) {
                if(e.度数() < 1d) target[0] = "不可";
                else if(e.度数() < 2d) target[0] = "可";
                else if(e.度数() < 3d) target[0] = "良";
                else if(e.度数() < 4d) target[0] = "優";
                else target[0] = "秀";
            }
        }
        xAxisAnnotations.add(XAxisAnnotationsBuilder.get()
                .withX(target[0].equals(grade.get(0).成績評価()) ? target[0] + "(あなたの成績位置)" : target[0])
                .withLabel(LabelBuilder.get()
                        .withStyle(AnnotationStyleBuilder.get()
                                .withFontSize("20px")
                                .build())
                        .withOrientation("horizontal")
                        .withTextAnchor("middle")
                        .withText("平均値")
                        .build())
                .build());
        annotations.setXaxis(xAxisAnnotations);
        ApexCharts chart = ApexChartsBuilder.get().withChart(
                        ChartBuilder.get()
                                .withType(Type.BAR)
                                .build())
                .withXaxis(XAxisBuilder.get()
                        .build())
                .withAnnotations(annotations)
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
        String[] colors = new String[5];
        String[] labels = new String[5];
        for(int i = 0; i < 5; i++) {
            if(strs[i].equals(grade.get(0).成績評価())) {
                colors[i] = red;
                labels[i] = grade.get(0).成績評価() + "(あなたの成績位置)";
            }
            else {
                colors[i] = blue;
                labels[i] = strs[i];
            }
        }
        chart.setLabels(labels);
        chart.setColors(colors);
        chart.setHeight("400px");
        chart.setWidthFull();
        return chart;
    }
}
