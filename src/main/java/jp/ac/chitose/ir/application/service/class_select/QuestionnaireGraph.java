package jp.ac.chitose.ir.application.service.class_select;

import com.github.appreciated.apexcharts.helper.Coordinate;

import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;


public class QuestionnaireGraph {
    private ClassSelect classSelect;
    public QuestionnaireGraph(ClassSelect classSelect){
        this.classSelect = classSelect;
    }
    private Graph band(int Question_num,String subject_id) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB(subject_id).data();//q4_項目を項目()に変えて

        if (Question_num == 4) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q4_項目(), new Coordinate<>("Q4", e3.q4_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        } else if (Question_num == 5) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q5_項目(), new Coordinate<>("Q5", e3.q5_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        } else if (Question_num == 6) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q6_項目(), new Coordinate<>("Q6", e3.q6_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();

        } else if (Question_num == 8) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q8_項目(), new Coordinate<>("Q8", e3.q8_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        } else if (Question_num == 9) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q9_項目(), new Coordinate<>("Q9", e3.q9_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();

        } else if (Question_num == 11) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q11_項目(), new Coordinate<>("Q11", e3.q11_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        } else if (Question_num == 12) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q12_項目(), new Coordinate<>("Q12", e3.q12_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        } else if (Question_num == 13) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q13_項目(), new Coordinate<>("Q13", e3.q13_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();

        } else if (Question_num == 14) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q14_項目(), new Coordinate<>("Q14", e3.q14_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();

        } else if (Question_num == 15) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q15_項目(), new Coordinate<>("Q15", e3.q15_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).colors().build();
        }


        return null;
    }

    public Graph generateQuestionnaireGraph(int i,String subject_id){
        return band(i,subject_id);
    }
}
