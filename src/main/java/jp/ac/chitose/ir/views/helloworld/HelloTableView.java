package jp.ac.chitose.ir.views.helloworld;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jp.ac.chitose.ir.service.sample.SampleService;
import jp.ac.chitose.ir.service.sample.SampleTwo;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("Hello Table")
@Route(value = "table", layout = MainLayout.class)
public class HelloTableView extends VerticalLayout {

    private SampleService sampleService;

    public HelloTableView(SampleService sampleService) {
        this.sampleService = sampleService;

        viewSampleTable1();


    }

    private void viewSampleTable1() {
        Grid<SampleTwo.Row> grid = new Grid(SampleTwo.Row.class, false);
        grid.addColumn(SampleTwo.Row::年度).setHeader("年度");
        grid.addColumn(SampleTwo.Row::秀).setHeader("秀の割合");
        grid.addColumn(SampleTwo.Row::優).setHeader("優の割合");
        grid.addColumn(SampleTwo.Row::良).setHeader("良の割合");
        grid.addColumn(SampleTwo.Row::可).setHeader("可の割合");
        grid.addColumn(SampleTwo.Row::不可).setHeader("不可の割合");
        grid.addColumn(SampleTwo.Row::欠席).setHeader("欠席の割合");

        SampleTwo sampleTwo = sampleService.getSampleTwo();
        // 表示のためにデータ追加；（普通はしませんが、サンプル表示のためにやりました。）
        sampleTwo.data().add(new SampleTwo.Row("2023", 15.0D,20.1,10.0,49.9, 10.0,0));
        grid.setItems(sampleTwo.data());
        add(grid);
    }
}
