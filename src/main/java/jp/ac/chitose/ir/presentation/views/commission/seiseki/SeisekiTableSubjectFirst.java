package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import jp.ac.chitose.ir.application.service.TableData;
import jp.ac.chitose.ir.application.service.commission.*;

import java.util.ArrayList;
import java.util.List;

public class SeisekiTableSubjectFirst {
    private TableData<CommissionGpa2> tableAll;
    private TableData<CommissionGpa2First> tableFirst;
    private TableData<CommissionGpa2Second> tableSecond;
    private TableData<CommissionGpa2Third> tableThird;
    private TableData<CommissionGpa2Fourth> tableFourth;

    public SeisekiTableSubjectFirst(TableData<CommissionGpa2> tableAll,
                                    TableData<CommissionGpa2First> tableFirst,
                                    TableData<CommissionGpa2Second> tableSecond,
                                    TableData<CommissionGpa2Third> tableThird,
                                    TableData<CommissionGpa2Fourth> tableFourth
                                    ){
        this.tableAll = tableAll;
        this.tableFirst = tableFirst;
        this.tableSecond = tableSecond;
        this.tableThird = tableThird;
        this.tableFourth = tableFourth;
    }

    public List<GetTableData> getTable(int i) {

        List<GetTableData> list = new ArrayList<>();
        CommissionGpa2 all = tableAll.data().get(i);
        CommissionGpa2First first = tableFirst.data().get(i);
        CommissionGpa2Second second = tableSecond.data().get(i);
        CommissionGpa2Third third = tableThird.data().get(i);
        CommissionGpa2Fourth fourth = tableFourth.data().get(i);
        list.add(new GetTableData("全体", all.human(), all.average(), all.mid(), all.min(), all.max(), all.std()));
        list.add(new GetTableData("1年生", first.human(), first.average(), first.mid(), first.min(), first.max(), first.std()));
        list.add(new GetTableData("２年生", second.human(), second.average(), second.mid(), second.min(), second.max(), second.std()));
        list.add(new GetTableData("３年生", third.human(), third.average(), third.mid(), third.min(), third.max(), third.std()));
        list.add(new GetTableData("４年生", fourth.human(), fourth.average(), fourth.mid(), fourth.min(), fourth.max(), fourth.std()));
        if(i != 0){
            list.remove(1);
        }
        return list;

        }



}
