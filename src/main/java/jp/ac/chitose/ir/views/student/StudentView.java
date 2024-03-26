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
import com.github.appreciated.apexcharts.helper.*;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataCommunicator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.MainLayout;

import java.util.ArrayList;
import java.util.Comparator;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {

    //private SecurityService securityService;
    private StudentService studentService;
    private ComboBox<StudentGrade> comboBox;
    private ApexCharts chart;
    final private String blue = "#0000FF";
    final private String red = "#FF0000";

    public StudentView(StudentService studentService/*, @Autowired SecurityService securityService */) {
        //this.securityService = securityService;
        this.studentService = studentService;
        add(createTextField());
        init();
        comboBoxInitialyze();
        add(comboBox);
    }

    private void init() {
        add(new H1("Student"));
        add(new Paragraph("説明"));
        add(new H3("学年"));
        RadioButtonGroup<String> gradesRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        gradesRadioButton.addValueChangeListener(event -> {
            if(event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
        add(gradesRadioButton);
        add(new H3("学科"));
        RadioButtonGroup<String> departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.addValueChangeListener(event -> {
            if(event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
        add(departmentsRadioButton);
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.addValueChangeListener(e1 -> {
            ComboBox.ItemFilter<StudentGrade> filter = (grade, filterString) ->
                    grade.科目名().toLowerCase().startsWith(filterString.toLowerCase());
            if(e1.getValue() == null) return;
            comboBox.setItems(filter, studentService.getStudentNumberGrades(e1.getValue()).data().stream().sorted(Comparator.comparing(StudentGrade::科目名)).toList());
            comboBox.setItemLabelGenerator(StudentGrade::科目名);
        });
        return textField;
    }

    private void comboBoxInitialyze() {
        comboBox = new ComboBox<>("科目名");
        comboBox.setPlaceholder("科目名を検索");
        ComboBox.ItemFilter<StudentGrade> filter = (grade, filterString) ->
                grade.科目名().toLowerCase().startsWith(filterString.toLowerCase());
        // comboBox.setItems(filter, studentService.getStudentNumberGrades(securityService.getAuthenticatedUser().getUsername()).data().stream().sorted(Comparator.comparing(StudentGrade::科目名)).toList());
        // comboBox.setItemLabelGenerator(StudentGrade::科目名);
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
            final Series<Coordinate<String, Integer>> series = new Series(e1.getValue().科目名());
            series.setData(data);
            var grade = studentService.getStudentNumberGrade(e1.getValue().学籍番号(), e1.getValue().科目名()).data();
            Annotations annotations = new Annotations();
            ArrayList<XAxisAnnotations> xAxisAnnotations = new ArrayList<>();
            /*xAxisAnnotations.add(XAxisAnnotationsBuilder.get()
                            .withX(grade.get(0).成績評価() + "(あなたの成績位置)")
                            .withLabel(LabelBuilder.get()
                                    .withStyle(AnnotationStyleBuilder.get()
                                            .withFontSize("20px")
                                            .build())
                                    .withOrientation("horizontal")
                                    .withTextAnchor("middle")
                                    .withText("あなたの成績位置")
                                    .build())
                            .build());*/
            String target = "";
            for(int i = 0; i < histData.size(); i++) {
                var e = histData.get(i);
                if(e.成績評価().equals("平均")) {
                    if(e.度数() < 1d) target = "不可";
                    else if(e.度数() < 2d) target = "可";
                    else if(e.度数() < 3d) target = "良";
                    else if(e.度数() < 4d) target = "優";
                    else target = "秀";
                }
            }
            xAxisAnnotations.add(XAxisAnnotationsBuilder.get()
                    .withX(target.equals(grade.get(0).成績評価()) ? target + "(あなたの成績位置)" : target)
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
            chart = ApexChartsBuilder.get().withChart(
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
            chart.setWidth("100%");
            add(chart);
        });
    }
}
