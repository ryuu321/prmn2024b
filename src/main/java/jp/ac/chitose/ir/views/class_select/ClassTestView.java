package jp.ac.chitose.ir.views.class_select;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.Graph;

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

        Series Classtest = new Series<>();
        Coordinate<String, Float>[] data = new Coordinate[5];
        ClassTest.forEach(e2 -> {
            if(e2.項目().equals("やや難しかった")) data[4] = new Coordinate<>(e2.項目(), e2.割合());
            else if(e2.項目().equals("丁度良かった")) data[3] = new Coordinate<>(e2.項目(), e2.割合());
            else if(e2.項目().equals("少し易しかった")) data[2] = new Coordinate<>(e2.項目(), e2.割合());
            else if(e2.項目().equals("易しかった")) data[1] = new Coordinate<>(e2.項目(), e2.割合());
            else data[0] = new Coordinate<>(e2.項目(), e2.割合());
        });

        Classtest.setData(data);


        return new Graph.Builder().band(true)
                .height("400px").width("400px").series(Classtest).animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }
}
