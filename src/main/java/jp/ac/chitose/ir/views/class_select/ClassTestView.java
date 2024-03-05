package jp.ac.chitose.ir.views.class_select;

import com.github.appreciated.apexcharts.ApexCharts;
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
    public ClassTestView() {
        this.classSelect = classSelect;

        H1 title = new H1("Teacherの予定画面");
        add(title);

        H2 tex = new H2("回答率n%");
        add(tex);


        add(new H1("適当なグラフ"), ヒストグラム());

        H2 rank = new H2("満足度順位 n位　　全科目順位 n位　　同カテゴリ順位 n位");
        add(rank);

        H2 text = new H2("自由記述解答");
        add(text);

        Button graphButton = new Button("回答を表示する");
        add(graphButton);
    }

    private ApexCharts ヒストグラム() {
        var ClassTest = (classSelect.getClassTest().割合()).data();


        // ヒストグラムで表示するデータを用意する
        // データはSeriesクラスを使う
        // Seriesクラスのコンストラクタには、名前、データの中身、を設定する。
        // データの中身は、Coordinateクラスを設定する。Coordinateクラスが一つで柱（棒）；階級が一つできる
        // 柱（棒）；階級には、データ数、があるので、Coordinateクラスのコンストラクタの第２引数にデータ数を設定する
        // 階級の幅の数に応じてCoordinateクラスをnewして設定する（階級数が7なら7個Cooridnateクラスを設定する）


        return new Graph.Builder().histogram(true)
                .height("400px").width("400px").series((Series) ClassTest).animationsEnabled(false).dataLabelsEnabled(false).build().getGraph();
    }
}
