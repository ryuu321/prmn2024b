package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGPA;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.component.graph.Data;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphAlign;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

import java.util.List;

public class GPAGraph extends VerticalLayout {
    private static final String HEIGHT = "60vh";
    private static final int GPA_CATEGORY_NUM = 9;
    private static final int DEFAULT_DATA = 0;
    private static final String[] GPA_CATEGORY = {"0.0 ~ 0.5", "0.5 ~ 1.0", "1.0 ~ 1.5", "1.5 ~ 2.0", "2.0 ~ 2.5", "2.5 ~ 3.0", "3.0 ~ 3.5", "3.5 ~ 4.0", "4.0"};

    public GPAGraph(StudentService studentService, String schoolYear) {
        this.setHeight(HEIGHT);
        GraphSeries<Data<String, Integer>> series = createSeries(studentService, schoolYear);
        Graph graph = createGraph(series);
        add(graph.getGraph());
    }

    private GraphSeries<Data<String, Integer>> createSeries(StudentService studentService, String schoolYear) {
        List<StudentGPA> GPAData = studentService.getStudentGPA().data();
        Data<String, Integer>[] data = createData(GPAData, schoolYear);
        fillNullData(data);
        return new GraphSeries<>(data);
    }

    private Data<String, Integer>[] createData(List<StudentGPA> GPAData, String schoolYear) {
        Data<String, Integer>[] data = new Data[GPA_CATEGORY_NUM];
        for (StudentGPA gpa : GPAData) {
            String gpaString = String.valueOf(gpa.gpa());
            for (int i = 0; i < GPA_CATEGORY_NUM; i++) {
                if(!gpaString.startsWith(GPA_CATEGORY[i].substring(0, 3)) || !gpa.学年().equals(schoolYear)) continue;
                data[i] = new Data<>(GPA_CATEGORY[i], gpa.度数());
                break;
            }
        }
        return data;
    }

    private void fillNullData(Data<String, Integer>[] data) {
        for (int i = 0; i < GPA_CATEGORY_NUM; i++) {
            if (data[i] == null) {
                data[i] = new Data<>(GPA_CATEGORY[i], DEFAULT_DATA);
            }
        }
    }

    private Graph createGraph(GraphSeries<Data<String, Integer>> series) {
        return Graph.Builder.get()
                .histogram()
                .series(series)
                .width("100%")
                .height("100%")
                .YAxisForceNiceScale(true)
                .title("GPA", GraphAlign.CENTER)
                .dataLabelsEnabled(false)
                .legendShow(false)
                .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF")
                .build();
    }
}
