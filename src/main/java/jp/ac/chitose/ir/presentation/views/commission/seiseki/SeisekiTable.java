package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.grid.Grid;
import jp.ac.chitose.ir.application.service.TableData;
import jp.ac.chitose.ir.application.service.commission.*;

public class SeisekiTable {
    private TableData<GradeGpaStat> tableAll;
    private TableData<GradeGpaStat> tableFirst;
    private TableData<GradeGpaStat> tableSecond;
    private TableData<GradeGpaStat> tableThird;
    private TableData<GradeGpaStat> tableFourth;
    private GradeService gradeService;
    private SeisekiTableSubjectFirst getTable;


    public SeisekiTable(GradeService gradeService){
        this.gradeService=gradeService;
        tableAll = this.gradeService.getGradeGpaStat("B");
        tableFirst = this.gradeService.getGradeGpaStat("B1");
        tableSecond = this.gradeService.getGradeGpaStat("B2");
        tableThird = this.gradeService.getGradeGpaStat("B3");
        tableFourth = this.gradeService.getGradeGpaStat("B4");
        getTable = new SeisekiTableSubjectFirst(tableAll,tableFirst,tableSecond,tableThird,tableFourth);
    }
    public Grid<GradeGpaStat> getTableYearFirstAll(){
        Grid<GradeGpaStat> grid = new Grid<>();
        grid.addColumn(GradeGpaStat::subject).setHeader("学科").setSortable(true);
        grid.addColumn(GradeGpaStat::human).setHeader("人数").setSortable(true);
        grid.addColumn(GradeGpaStat::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GradeGpaStat::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GradeGpaStat::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GradeGpaStat::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GradeGpaStat::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableAll.data());
        return grid;
    }

    public Grid<GradeGpaStat> getTableFirst(){
        Grid<GradeGpaStat> grid = new Grid<>();
        grid.addColumn(GradeGpaStat::subject).setHeader("学科").setSortable(true);
        grid.addColumn(GradeGpaStat::human).setHeader("人数").setSortable(true);
        grid.addColumn(GradeGpaStat::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GradeGpaStat::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GradeGpaStat::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GradeGpaStat::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GradeGpaStat::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableFirst.data());
        return grid;
    }

    public Grid<GradeGpaStat> getTableSecond(){
        Grid<GradeGpaStat> grid = new Grid<>();
        grid.addColumn(GradeGpaStat::subject).setHeader("学科").setSortable(true);
        grid.addColumn(GradeGpaStat::human).setHeader("人数").setSortable(true);
        grid.addColumn(GradeGpaStat::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GradeGpaStat::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GradeGpaStat::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GradeGpaStat::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GradeGpaStat::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableSecond.data());
        return grid;
    }

    public Grid<GradeGpaStat> getTableThird(){
        Grid<GradeGpaStat> grid = new Grid<>();
        grid.addColumn(GradeGpaStat::subject).setHeader("学科").setSortable(true);
        grid.addColumn(GradeGpaStat::human).setHeader("人数").setSortable(true);
        grid.addColumn(GradeGpaStat::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GradeGpaStat::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GradeGpaStat::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GradeGpaStat::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GradeGpaStat::std).setHeader("標準偏差").setSortable(true);
        grid.setItems(tableThird.data());
        return grid;
    }

    public Grid<GradeGpaStat> getTableFourth(){
        Grid<GradeGpaStat> grid = new Grid<>();
        grid.addColumn(GradeGpaStat::subject).setHeader("学科").setSortable(true);
        grid.addColumn(GradeGpaStat::human).setHeader("人数").setSortable(true);
        grid.addColumn(GradeGpaStat::average).setHeader("平均値").setSortable(true);
        grid.addColumn(GradeGpaStat::mid).setHeader("中央値").setSortable(true);
        grid.addColumn(GradeGpaStat::min).setHeader("最小値").setSortable(true);
        grid.addColumn(GradeGpaStat::max).setHeader("最大値").setSortable(true);
        grid.addColumn(GradeGpaStat::std).setHeader("標準偏差").setSortable(true);
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
