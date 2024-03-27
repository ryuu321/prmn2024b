package jp.ac.chitose.ir.views.class_select;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.Graph;
import jp.ac.chitose.ir.views.component.GraphSeries;

@PageTitle("class_PfYo")
@Route(value = "class_select/PfYo", layout = MainLayout.class)

public class PfYoView  extends VerticalLayout {
    private ClassSelect classSelect;

    public  PfYoView(ClassSelect classSelect){
        this.classSelect = classSelect;

        init1();//統一UI画面の上部分

        /*subject_name();//科目名の表示
        teacher_name();//担当者の名前*/

        index();//目次
        add(band(4));//Q4~Q19までのグラフを表示

        init2();//目的と違うので仮

    }
    private void init1() {
        add(new H1("Teacher"));
        add(new Paragraph("説明文:画面内に表示される内容の説明"));
        add(new H3("種別"));
        RadioButtonGroup<String> categoryRadioButton = new RadioButtonGroup<>("", "IRアンケート", "授業評価アンケート");
        categoryRadioButton.addValueChangeListener(event -> {
            if (event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
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

        private void init2(){
        add(new H3("学科"));
        RadioButtonGroup<String> departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.addValueChangeListener(event -> {
            if(event.getValue().equals(event.getOldValue())) return;
            else {
                String value = event.getValue();
            }
        });
        add(departmentsRadioButton);

        Select<String> select = new Select<>();
        var ClassTest = classSelect.getClassqPfYo().data();
        var subject_name =  ClassTest.get(0);
        select.setLabel("担当科目を検索");

        select.setItems(String.valueOf(subject_name)); //データの形式が不正で動かない可能性

        add(select);
    }

    private void index(){
        add(new H3("質問項目一覧(未実装)"));
        //質問項目一覧の表示
        //クリックすると該当科目まで遷移
    }

    private void subject_name(){
        var ClassTest = classSelect.getClassqPfYo().data();
        var subject_name =  ClassTest.get(0);
        add(new H3("表示中の科目:"));
                add(new H3(String.valueOf(subject_name))); //データの形式が不正で動かない可能性

    }

    private void teacher_name(){
        var ClassTest = classSelect.getClassqPfYo().data();
        var teacher_name =  ClassTest.get(0);
        add(new H3("担当者:"));
        add(new H3(String.valueOf(teacher_name))); //データの形式が不正で動かない可能性

    }

    private ApexCharts band(int Question_num) {
        var ClassTest = classSelect.getClassqPfYo().data();
        //試験的にQ4だけだが'Q+"Question_num"'の形で一般化させる予定


        return Graph.Builder.get().band()
                .height("400px").width("400px").series(ClassTest.stream().map(e1 ->
                        new GraphSeries(e1.q4()/*元 e1.項目() */,new Coordinate<>("Q4",e1.q4()/*元 e3.割合()*/))).toArray(GraphSeries[]::new))
                .animationsEnabled(false).dataLabelsEnabled(false).build().getGraph(); //データの形式が不正で動かない可能性大

    }
}
