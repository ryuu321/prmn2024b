package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.usermanagement.UserManagementService;
import jp.ac.chitose.ir.application.service.usermanagement.UsersData;

public class UsersDataGrid extends VerticalLayout {
    private GridListDataView<UsersData> gridListDataView;

    // コンストラクタ
    public UsersDataGrid(UserManagementService userManagementService) {
        Grid<UsersData> grid = initializeGrid(userManagementService);
        addComponentsToLayout(grid);
    }

    // 各種湖コンポーネントを画面に追加
    private void addComponentsToLayout(Grid<UsersData> grid) {
        add(grid);
    }

    // グリッドの初期化
    private Grid<UsersData> initializeGrid(UserManagementService userManagementService) {
        Grid<UsersData> grid = new Grid<>(UsersData.class, false);
        addColumnsToGrid(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        gridListDataView = grid.setItems(userManagementService.getUsersData().data());
        return grid;
    }

    // グリッドにカラムを追加
    private void addColumnsToGrid(Grid<UsersData> grid) {
        grid.addColumn(UsersData::id).setHeader("アカウントID").setSortable(true);
        grid.addColumn(UsersData::user_name).setHeader("ユーザーネーム");
        grid.addColumn(UsersData::display_name).setHeader("ロール");
        grid.addColumn(UsersData::is_available).setHeader("状態").setSortable(true);

    }
}
