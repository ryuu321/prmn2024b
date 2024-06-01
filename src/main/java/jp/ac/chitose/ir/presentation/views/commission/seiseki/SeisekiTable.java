package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.grid.Grid;
import jp.ac.chitose.ir.application.service.commission.*;
import jp.ac.chitose.ir.application.service.commission.CommissionGpa2;

import java.util.List;

public class SeisekiTable {
    public static Grid<CommissionGpa2> getTable(List<CommissionGpa2> list){
        Grid<CommissionGpa2> grid = new Grid<>();
        grid.addColumn(CommissionGpa2::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2::human).setHeader("人数");
        grid.addColumn(CommissionGpa2::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2::std).setHeader("標準偏差");
        grid.setItems(list);
        return grid;
    }

    public static Grid<CommissionGpa2First> getTableFirst(List<CommissionGpa2First> list){
        Grid<CommissionGpa2First> grid = new Grid<>();
        grid.addColumn(CommissionGpa2First::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2First::human).setHeader("人数");
        grid.addColumn(CommissionGpa2First::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2First::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2First::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2First::std).setHeader("標準偏差");
        grid.setItems(list);
        return grid;
    }

    public static Grid<CommissionGpa2Second> getTableSecond(List<CommissionGpa2Second> list){
        Grid<CommissionGpa2Second> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Second::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2Second::human).setHeader("人数");
        grid.addColumn(CommissionGpa2Second::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2Second::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2Second::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2Second::std).setHeader("標準偏差");
        grid.setItems(list);
        return grid;
    }

    public static Grid<CommissionGpa2Third> getTableThird(List<CommissionGpa2Third> list){
        Grid<CommissionGpa2Third> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Third::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2Third::human).setHeader("人数");
        grid.addColumn(CommissionGpa2Third::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2Third::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2Third::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2Third::std).setHeader("標準偏差");
        grid.setItems(list);
        return grid;
    }

    public static Grid<CommissionGpa2Fourth> getTableFourth(List<CommissionGpa2Fourth> list){
        Grid<CommissionGpa2Fourth> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Fourth::subject).setHeader("学科");
        grid.addColumn(CommissionGpa2Fourth::human).setHeader("人数");
        grid.addColumn(CommissionGpa2Fourth::average).setHeader("平均値");
        grid.addColumn(CommissionGpa2Fourth::min).setHeader("最小値");
        grid.addColumn(CommissionGpa2Fourth::max).setHeader("最大値");
        grid.addColumn(CommissionGpa2Fourth::std).setHeader("標準偏差");
        grid.setItems(list);
        return grid;
    }
}
