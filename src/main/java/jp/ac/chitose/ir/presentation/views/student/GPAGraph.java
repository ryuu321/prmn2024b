package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGPA;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.component.graph.Data;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphAlign;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

public class GPAGraph extends VerticalLayout {

    // コンストラクタ　グラフを作成し、自分に追加する
    public GPAGraph(StudentService studentService, String schoolYear) {
        this.setHeight("60vh");
        final GraphSeries<Data<String, Integer>> series = createSeries(studentService, schoolYear);
        Graph graph = Graph.Builder.get().histogram().series(series).width("100%").height("100%").YAxisForceNiceScale(true)
                .title("GPA", GraphAlign.CENTER).dataLabelsEnabled(false).legendShow(false)
                .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF").build();
        add(graph.getGraph());
    }

    // GPAのデータから、GPAのSeriesを作り出す機能
    private GraphSeries<Data<String, Integer>> createSeries(StudentService studentService, String schoolYear) {
        final var GPAData = studentService.getStudentGPA().data();
        Data<String, Integer>[] data = new Data[9];
        for(StudentGPA GPA : GPAData) {
            if(!GPA.学年().equals(schoolYear)) continue;
            data[(int) (GPA.gpa() * 2.0)] = new Data<>(String.valueOf(GPA.gpa()), GPA.度数());
        }
        for(int i = 0; i < 9; i++) if(data[i] == null) data[i] = new Data<>(String.valueOf((float) (i / 2)), 0);
        return new GraphSeries<>(data);
    }
}
