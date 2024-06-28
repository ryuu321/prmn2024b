package jp.ac.chitose.ir.presentation.views.class_select;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.ClassSelect;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.graph.Graph;
import jp.ac.chitose.ir.presentation.component.graph.GraphSeries;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/QPOJFICHKVJB", layout = MainLayout.class)
@PermitAll
public class QPOJFICHKVJBView extends VerticalLayout {
    private ClassSelect classSelect;

    public QPOJFICHKVJBView(ClassSelect classSelect) {
        this.classSelect = classSelect;

        init1();//統一UI画面の上部分


        index();//目次

        add(new H3("Q4:実験・実習テーマの全般的な難易度について、どのように感じましたか。"));
        add(band1(4));
        add(new H3("Q5:実験・実習で実施する作業量について、どのように感じましたか。"));
        add(band2(5));//グラフ表示
        add(new H3("Q6:実験・実習の進行速度について、どのように感じましたか。"));
        add(band3(6));

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


    private ApexCharts band(int Question_num) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB().data();


        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                        new GraphSeries(e3.q4_項目(), new Coordinate<>("Q4", e3.q4_割合()))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();


        /*return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e1 ->
                {
                    try {
                        return new GraphSeries(e1.getClass().getMethod("q"+Question_num+"_項目").invoke(e1),new Coordinate<>("Q"+Question_num+"_割合",e1.getClass().getMethod("q"+Question_num).invoke(e1)));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph(); //データの形式が不正で動かない可能性大*/

    }

    /*private void insertResult(){//質問文をdatabaseから持ってくる必要あり
        add(new H3("Q4:実験・実習テーマの全般的な難易度について、どのように感じましたか。"));
        add(band(4));
        add(new H3("Q5:実験・実習で実施する作業量について、どのように感じましたか。"));
        add(band(5));
        add(new H3("Q6:実験・実習の進行速度について、どのように感じましたか。"));
        add(band(6));
        add(new H3("Q7:あなたはどのテーマ（実習においてはどの部分）に興味・関心が持てましたか。"));
        add(band(7));
        add(new H3("Q8:実験・実習するにあたって、事前学習をしてきましたか。"));
        add(band(8));
        add(new H3("Q9:あなたは実験・実習に積極的に取り組みましたか。"));
        add(band(9));
        add(new H3("Q10:あなたはシラバスに記載されている到達目標のうち、どの程度達成できましたか。"));
        add(band(10));
        add(new H3("Q11:教員、TA・SAの実験・実習内容の説明は理解し易かったですか。"));
        add(band(11));
        add(new H3("Q12:教材（テキスト・配布資料・スライド・ビデオ・E-learning・板書・オンライン授業においてはコンテンツ等）は理解しやすかったですか。"));
        add(band(12));
        add(new H3("Q13:実験・実習を実施するにあたっての器材・教材・設備・環境などは整っていましたか。"));
        add(band(13));
        add(new H3("Q14:実験・実習に際しての教員、TA・SAからのアドバイスは適切でしたか。"));
        add(band(14));
        add(new H3("Q15:レポートや課題提出に関しての教員、TA・SAからのアドバイスは適切でしたか。"));
        add(band(15));
        add(new H3("Q16:総合的に判断してこの授業は満足でしたか。"));
        add(band(16));

        Button graphButton = new Button("回答を表示する");

        add(new H3("Q17:この授業で良かった点があれば記述してください。"));
        add(graphButton);
        add(new H3("Q18:この授業で改善点があれば記述してください。"));
        add(graphButton);
        add(new H3("Q19:その他、気づいた点があれば記述してください。"));
        add(graphButton);
    }*/

    private ApexCharts band1(int Question_num) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB().data();


        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                        new GraphSeries(e3.q4_項目(), new Coordinate<>("Q4", e3.q4_割合()))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).colors().build().getGraph();
    }

    private ApexCharts band2(int Question_num) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB().data();


        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                        new GraphSeries(e3.q5_項目(), new Coordinate<>("Q5", e3.q5_割合()))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }

    private ApexCharts band3(int Question_num) {
        var ClassTest = classSelect.getClassQPOJFICHKVJB().data();


        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                        new GraphSeries(e3.q6_項目(), new Coordinate<>("Q6", e3.q6_割合()))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }
}