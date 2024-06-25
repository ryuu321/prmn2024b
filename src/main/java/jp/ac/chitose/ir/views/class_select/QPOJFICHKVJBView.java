package jp.ac.chitose.ir.views.class_select;

import com.github.appreciated.apexcharts.helper.Coordinate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.service.class_select.ReviewQPOJFICHKVJBDescription;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphSeries;

import java.util.List;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/QPOJFICHKVJB", layout = MainLayout.class)
@PermitAll
public class QPOJFICHKVJBView extends VerticalLayout {
    private ClassSelect classSelect;

    public QPOJFICHKVJBView(ClassSelect classSelect) {
        this.classSelect = classSelect;

        init1();//統一UI画面の上部分


        index();//目次

        for (int i = 0; i < 11; i++) {
            if(i == 3 || i == 6){
                title(3);
                test();//自由記述
                continue;}

            title(i);//example
            band(i+4).getGraph();
        }


    }

    private void init1() {
        add(new H1("科目名:ソフトウェア工学とアジャイル開発"));
        add(new Paragraph("説明文:画面内に表示される内容の説明"));
        add(new H3("種別"));
        RadioButtonGroup<String> categoryRadioButton = new RadioButtonGroup<>("", "IRアンケート", "授業評価アンケート");
        categoryRadioButton.addValueChangeListener(event -> {
            if (event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
        add(categoryRadioButton);
        add(new H3("学年"));
        RadioButtonGroup<String> gradesRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        gradesRadioButton.addValueChangeListener(event -> {
            if (event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
        add(gradesRadioButton);
    }


    private void index() {

        add(new H3("質問項目一覧(未実装)"));
        //質問項目一覧の表示
        //クリックすると該当科目まで遷移
    }

    /**
     * 自由記述解答の表示example
     */
    private void test() {


        Grid<ReviewQPOJFICHKVJBDescription> grid = new Grid<>(ReviewQPOJFICHKVJBDescription.class, false);
        grid.addColumn(ReviewQPOJFICHKVJBDescription::q19).setHeader("Q7");
        List<ReviewQPOJFICHKVJBDescription> people = classSelect.getReviewQPOJFICHKVJBDescription().data();
        grid.setItems(people);

        add(grid);

        //質問項目一覧の表示
        //クリックすると該当科目まで遷移
    }

    private void title(int i) {
        var Classtitle = classSelect.getReviewTitle().data();
        String Title = String.valueOf(Classtitle.get(i));

        if (Title != null && Title.length() > 0) {
            String newTitle = Title.substring(0, Title.length() - 1);
            String[] parts = newTitle.split("=");
            StringBuilder num = new StringBuilder();
            num.append("Q").append(i+4).append(":");
            add(new H3(num + parts[1]));
        }
    }



    private Graph band(int Question_num) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB().data();

        if (Question_num == 4) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q4_項目(), new Coordinate<>("Q4", e3.q4_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        } else if (Question_num == 5) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q5_項目(), new Coordinate<>("Q5", e3.q5_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        } else if (Question_num == 6) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q6_項目(), new Coordinate<>("Q6", e3.q6_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();

        } else if (Question_num == 8) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q8_項目(), new Coordinate<>("Q8", e3.q8_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        } else if (Question_num == 9) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q9_項目(), new Coordinate<>("Q9", e3.q9_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();

        } else if (Question_num == 11) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q11_項目(), new Coordinate<>("Q11", e3.q11_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        } else if (Question_num == 12) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q12_項目(), new Coordinate<>("Q12", e3.q12_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        } else if (Question_num == 13) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q13_項目(), new Coordinate<>("Q13", e3.q13_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();

        } else if (Question_num == 14) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q14_項目(), new Coordinate<>("Q14", e3.q14_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();

        } else if (Question_num == 15) {
            return Graph.Builder.get().band()
                    .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                            new GraphSeries(e3.q15_項目(), new Coordinate<>("Q15", e3.q15_割合()))).toArray(GraphSeries[]::new))
                    .animationsEnabled(false).dataLabelsEnabled(false).build();
        }


        return null;
    }







}