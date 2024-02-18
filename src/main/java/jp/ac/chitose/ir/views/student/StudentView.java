package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Annotations;
import com.github.appreciated.apexcharts.config.DiscretePoint;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.PointAnnotationsBuilder;
import com.github.appreciated.apexcharts.config.markers.Shape;
import com.github.appreciated.apexcharts.config.markers.builder.HoverBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.bar.Ranges;
import com.github.appreciated.apexcharts.config.plotoptions.bar.builder.ColorsBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.helper.ColorCoordinate;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentHist;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.ApexChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("Student")
@Route(value = "student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {

    private StudentService studentService;
    private ComboBox<StudentGrade> comboBox;
    private ApexCharts chart;
    final private String blue = "#0000FF";
    final private String red = "#FF0000";

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        comboBoxInitialyze();
        add(createTextField());
        add(comboBox);
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.addValueChangeListener(e1 -> {
            if(e1.getValue() == null) return;
            comboBox.setItems(studentService.getStudentNumberGrades(e1.getValue()).data());
            comboBox.setItemLabelGenerator(StudentGrade::科目名);
        });
        return textField;
    }

    private void comboBoxInitialyze() {
        comboBox = new ComboBox<>("科目名");
        comboBox.addValueChangeListener(e1 -> {
            if(e1.getValue() == null) return;
            if(chart != null) remove(chart);
            var histData = studentService.getStudentHist(e1.getValue().科目名()).data();
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
            final Series<Coordinate<String, Integer>> series = new Series<>(e1.getValue().科目名());
            series.setData(data);
            chart = ApexChartsBuilder.get().withChart(
                            ChartBuilder.get()
                                    .withType(Type.BAR)
                                    .build())
                    .withDataLabels(DataLabelsBuilder.get()
                            .withEnabled(false)
                            .build())
                    .withPlotOptions(PlotOptionsBuilder.get()
                            .withBar(BarBuilder.get().withColumnWidth("100%").build()) // BARの間隔を０に近づける（見た目を調整してヒストグラムにみえるようにする）
                            .build())
                    .withStroke(StrokeBuilder.get().withWidth(0.1).withColors("#000").build()) // 柱（棒）の外枠を黒色に設定してヒストグラムに見た目を近づける
                    .withYaxis(YAxisBuilder.get().withForceNiceScale(true).build()) // Y軸の最大値；ここでは80に設定
                    .withSeries(series)
                    .build();
            chart.setHeight("500px");
            chart.setWidthFull();
            add(chart);
        });
    }
}
