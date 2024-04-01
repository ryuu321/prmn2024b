package jp.ac.chitose.ir.views.class_select;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphSeries;

@PageTitle("class_test")
@Route(value = "class_select/test", layout = MainLayout.class)
public class ClassTestView extends VerticalLayout {

    private ClassSelect classSelect;

    public ClassTestView(ClassSelect classSelect) {
        this.classSelect = classSelect;

        H1 title = new H1("Teacherの予定画面");
        add(title);

        H2 tex = new H2("回答率n%");
        add(tex);

        H3 t = new H3("担当科目");
        add(t);


        Select<String> select = new Select<>();
        select.setLabel("担当科目を検索");
        select.setItems("科目A", "科目B");
        add(select);

        VerticalLayout layout = new VerticalLayout();



        add(new H1("適当なグラフ"), band());

        H2 rank = new H2("満足度順位 n位　　全科目順位 n位　　同カテゴリ順位 n位");
        add(rank);

        H2 text = new H2("自由記述解答");
        add(text);

        Button graphButton = new Button("回答を表示する");
        add(graphButton);
    }

    private ApexCharts band() {
        var ClassTest = classSelect.getClassTest().data();

        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e3 ->
                        new GraphSeries(e3.項目(),new Coordinate<>("Q1",e3.割合()))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }
}