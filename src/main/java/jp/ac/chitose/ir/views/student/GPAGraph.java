package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.service.student.StudentGPA;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.component.*;

public class GPAGraph extends VerticalLayout {
    private Graph graph;

    public void create(StudentService studentService, String schoolYear) {
        final GraphSeries<Data<String, Integer>> series = createSeries(studentService, schoolYear);
        graph = Graph.Builder.get().histogram().series(series).width("100%").height("250px").YAxisForceNiceScale(true)
                .title("GPA", GraphAlign.CENTER).dataLabelsEnabled(false).legendShow(false)
                .colors("#0000FF", "#0000FF", "#0000FF", "#0000FF", "#0000FF").build();
        add(graph.getGraph());
    }

    private GraphSeries<Data<String, Integer>> createSeries(StudentService studentService, String schoolYear) {
        final var GPAData = studentService.getStudentGPA().data();
        Data<String, Integer>[] datas = new Data[5];
        int i = 0;
        for(StudentGPA GPA : GPAData) {
            if(GPA.学年().equals(schoolYear)) {
                datas[i] = new Data<>(gradeChangeString(i), GPA.度数());
                i++;
            }
        }
        for(;i < 5; i++) datas[i] = new Data<>(gradeChangeString(i), 0);
        return new GraphSeries<>(datas);
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
