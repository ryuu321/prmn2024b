package jp.ac.chitose.ir.application.service.class_select;

import com.github.appreciated.apexcharts.helper.Coordinate;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

import java.util.List;
import java.util.stream.IntStream;


public class QuestionnaireGraph {
    private ClassSelect classSelect;
    public QuestionnaireGraph(ClassSelect classSelect){

        this.classSelect = classSelect;
    }

    private Graph band(int questionNum,String subject_id) {
        var classTests = classSelect.getClassQPOJFICHKVJB(subject_id).data();

        List<GraphSeries> seriesList;


        switch (questionNum) {
            case 4 -> seriesList = createGraphSeries(classTests.get(0).q4_項目(), classTests.get(0).q4_割合(), "Q4");
            case 5 -> seriesList = createGraphSeries(classTests.get(0).q5_項目(), classTests.get(0).q5_割合(), "Q5");
            case 6 -> seriesList = createGraphSeries(classTests.get(0).q6_項目(), classTests.get(0).q6_割合(), "Q6");
            case 8 -> seriesList = createGraphSeries(classTests.get(0).q8_項目(), classTests.get(0).q8_割合(), "Q8");
            case 9 -> seriesList = createGraphSeries(classTests.get(0).q9_項目(), classTests.get(0).q9_割合(), "Q9");
            case 10 -> seriesList = createGraphSeries(classTests.get(0).q10_項目(), classTests.get(0).q10_割合(), "Q10");
            case 11 -> seriesList = createGraphSeries(classTests.get(0).q11_項目(), classTests.get(0).q11_割合(), "Q11");
            case 12 -> seriesList = createGraphSeries(classTests.get(0).q12_項目(), classTests.get(0).q12_割合(), "Q12");
            case 13 -> seriesList = createGraphSeries(classTests.get(0).q13_項目(), classTests.get(0).q13_割合(), "Q13");
            case 14 -> seriesList = createGraphSeries(classTests.get(0).q14_項目(), classTests.get(0).q14_割合(), "Q14");
            case 15 -> seriesList = createGraphSeries(classTests.get(0).q15_項目(), classTests.get(0).q15_割合(), "Q15");
            default -> throw new IllegalArgumentException("Invalid Question Number");
        }

        return Graph.Builder.get().band()
                .height("400px")
                .width("400px")
                .series(seriesList.toArray(new GraphSeries[0]))
                .animationsEnabled(false)
                .dataLabelsEnabled(false)
                .colors()
                .build();
    }

    private List<GraphSeries> createGraphSeries(List<String> items, List<Double> ratios, String label) {
        return IntStream.range(0, items.size())
                .mapToObj(i -> new GraphSeries(items.get(i), new Coordinate<>(label, ratios.get(i))))
                .toList();
    }

    public Graph generateQuestionnaireGraph(int questionNum, String subjectId) {
        return band(questionNum, subjectId);
    }
}

/*
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
}*/
