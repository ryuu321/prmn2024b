package jp.ac.chitose.ir.views.commission.seiseki;

import com.vaadin.flow.component.grid.Grid;
import jp.ac.chitose.ir.service.TableData;
import jp.ac.chitose.ir.service.commission.*;

public class SeisekiTable {
    private TableData<CommissionGpa2> tableAll;
    private TableData<CommissionGpa2First> tableFirst;
    private TableData<CommissionGpa2Second> tableSecond;
    private TableData<CommissionGpa2Third> tableThird;
    private TableData<CommissionGpa2Fourth> tableFourth;
    private CommissionService commissionService;
    private SeisekiTableSubjectFirst getTable;
    public SeisekiTable(CommissionService com){
        this.commissionService=com;
        tableAll = commissionService.getCommissionGpa2();
        tableFirst = commissionService.getCommissionGpa2First();
        tableSecond = commissionService.getCommissionGpa2Second();
        tableThird = commissionService.getCommissionGpa2Third();
        tableFourth = commissionService.getCommissionGpa2Fourth();
        getTable = new SeisekiTableSubjectFirst(tableAll,tableFirst,tableSecond,tableThird,tableFourth);
    }
    public Grid<CommissionGpa2> getTableYearFirstAll(){
        Grid<CommissionGpa2> grid = new Grid<>();
        grid.addColumn(CommissionGpa2::subject).setHeader("学科").setSortable(true);
        grid.addColumn(CommissionGpa2::human).setHeader("人数").setSortable(true);
        grid.addColumn(CommissionGpa2::average).setHeader("平均値").setSortable(true);
        grid.addColumn(CommissionGpa2::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(CommissionGpa2::min).setHeader("最小値").setSortable(true);
        grid.addColumn(CommissionGpa2::max).setHeader("最大値").setSortable(true);
        grid.addColumn(CommissionGpa2::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableAll.data());
        return grid;
    }

    public Grid<CommissionGpa2First> getTableFirst(){
        Grid<CommissionGpa2First> grid = new Grid<>();
        grid.addColumn(CommissionGpa2First::subject).setHeader("学科").setSortable(true);
        grid.addColumn(CommissionGpa2First::human).setHeader("人数").setSortable(true);
        grid.addColumn(CommissionGpa2First::average).setHeader("平均値").setSortable(true);
        grid.addColumn(CommissionGpa2First::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(CommissionGpa2First::min).setHeader("最小値").setSortable(true);
        grid.addColumn(CommissionGpa2First::max).setHeader("最大値").setSortable(true);
        grid.addColumn(CommissionGpa2First::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableFirst.data());
        return grid;
    }

    public Grid<CommissionGpa2Second> getTableSecond(){
        Grid<CommissionGpa2Second> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Second::subject).setHeader("学科").setSortable(true);
        grid.addColumn(CommissionGpa2Second::human).setHeader("人数").setSortable(true);
        grid.addColumn(CommissionGpa2Second::average).setHeader("平均値").setSortable(true);
        grid.addColumn(CommissionGpa2Second::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(CommissionGpa2Second::min).setHeader("最小値").setSortable(true);
        grid.addColumn(CommissionGpa2Second::max).setHeader("最大値").setSortable(true);
        grid.addColumn(CommissionGpa2Second::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableSecond.data());
        return grid;
    }

    public Grid<CommissionGpa2Third> getTableThird(){
        Grid<CommissionGpa2Third> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Third::subject).setHeader("学科").setSortable(true);
        grid.addColumn(CommissionGpa2Third::human).setHeader("人数").setSortable(true);
        grid.addColumn(CommissionGpa2Third::average).setHeader("平均値").setSortable(true);
        grid.addColumn(CommissionGpa2Third::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(CommissionGpa2Third::min).setHeader("最小値").setSortable(true);
        grid.addColumn(CommissionGpa2Third::max).setHeader("最大値").setSortable(true);
        grid.addColumn(CommissionGpa2Third::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableThird.data());
        return grid;
    }

    public Grid<CommissionGpa2Fourth> getTableFourth(){
        Grid<CommissionGpa2Fourth> grid = new Grid<>();
        grid.addColumn(CommissionGpa2Fourth::subject).setHeader("学科").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::human).setHeader("人数").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::average).setHeader("平均値").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::min).setHeader("最小値").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::max).setHeader("最大値").setSortable(true);
        grid.addColumn(CommissionGpa2Fourth::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableFourth.data());
        return grid;
    }

    public Grid<GetTableData> getTableSubjectFirst(){
        Grid<GetTableData> grid = new Grid<>();
        grid.addColumn(GetTableData::subject).setHeader("学年").setSortable(true);
        grid.addColumn(GetTableData::human).setHeader("人数").setSortable(true);
        grid.addColumn(GetTableData::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GetTableData::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GetTableData::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GetTableData::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GetTableData::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(getTable.getTable(0));
        return grid;
    }

    public Grid<GetTableData> getTableSubjectFirstScience(){
        Grid<GetTableData> grid = new Grid<>();
        grid.addColumn(GetTableData::subject).setHeader("学年").setSortable(true);
        grid.addColumn(GetTableData::human).setHeader("人数").setSortable(true);
        grid.addColumn(GetTableData::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GetTableData::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GetTableData::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GetTableData::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GetTableData::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(getTable.getTable(1));
        return grid;
    }

    public Grid<GetTableData> getTableSubjectFirstElectronic(){
        Grid<GetTableData> grid = new Grid<>();
        grid.addColumn(GetTableData::subject).setHeader("学年").setSortable(true);
        grid.addColumn(GetTableData::human).setHeader("人数").setSortable(true);
        grid.addColumn(GetTableData::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GetTableData::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GetTableData::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GetTableData::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GetTableData::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(getTable.getTable(2));
        return grid;
    }

    public Grid<GetTableData> getTableSubjectFirstInformation(){
        Grid<GetTableData> grid = new Grid<>();
        grid.addColumn(GetTableData::subject).setHeader("学年").setSortable(true);
        grid.addColumn(GetTableData::human).setHeader("人数").setSortable(true);
        grid.addColumn(GetTableData::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GetTableData::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GetTableData::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GetTableData::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GetTableData::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(getTable.getTable(3));
        return grid;
    }


}
