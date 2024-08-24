package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.application.service.usermanagement.UserManagementService;
import jp.ac.chitose.ir.application.service.usermanagement.UsersData;

public class UsersDataGrid extends VerticalLayout {
    private RadioButtonGroup<String> rolesRadioButton;
    private GridListDataView<UsersData> gridListDataView;
    private Grid<UsersData> grid;

    // グリッドにマルチセレクトモードを付けるかどうか判定する
    public enum SelectionMode {
        SINGLE, MULTI
    }

    // コンストラクタ
    public UsersDataGrid(UserManagementService userManagementService, SelectionMode selectionMode) {
        initializeRadioButtons();
        grid = initializeGrid(userManagementService, selectionMode);
        addComponentsToLayout(grid);
    }

    // ラジオボタンをまとめて初期化
    private void initializeRadioButtons() {
        initializeRoleRadioButton();
    }

    // ロールの絞り込みをするラジオボタンの初期化
    private void initializeRoleRadioButton() {
        rolesRadioButton = new RadioButtonGroup<>();
        rolesRadioButton.setItems("全て", "システム管理者", "IR委員会メンバー", "教員", "学生");
        rolesRadioButton.setValue("全て");
        rolesRadioButton.addValueChangeListener(event -> applyFilters());
    }

    // グリッドの初期化
    private Grid<UsersData> initializeGrid(UserManagementService userManagementService, SelectionMode selectionMode) {
        Grid<UsersData> grid = new Grid<>(UsersData.class, false);
        addColumnsToGrid(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);

        // コンストラクタで渡された選択モードに基づいてグリッドを設定
        if (selectionMode == SelectionMode.MULTI) {
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
        } else {
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        }

        gridListDataView = grid.setItems(userManagementService.getUsersData().data());
        return grid;
    }

    // グリッドの情報を得るメソッド
    public Grid<UsersData> getGrid() {
        return grid;
    }

    // 状態の表示を有効か無効に変更するメソッド
    private String changeIsAvailableValue(boolean isAvailable) {
        if (isAvailable) return "有効";
        return "無効";
    }

    // グリッドにカラムを追加
    private void addColumnsToGrid(Grid<UsersData> grid) {
        grid.addColumn(data -> changeIsAvailableValue(data.is_available())).setHeader("状態").setSortable(true);
        grid.addColumn(UsersData::id).setHeader("アカウントID").setSortable(true);
        grid.addColumn(UsersData::user_name).setHeader("ユーザーネーム");
        grid.addColumn(UsersData::display_name).setHeader("ロール");
    }

    // 各種コンポーネントを画面に追加
    private void addComponentsToLayout(Grid<UsersData> grid) {
        add(new H3("ロール"), rolesRadioButton);
        add(grid);
    }

    // フィルタを適用
    private void applyFilters() {
        gridListDataView.setFilter(new Filter());
    }

    // フィルタクラス
    private class Filter implements SerializablePredicate<UsersData> {
        @Override
        public boolean test(UsersData usersData) {
            boolean roleMatches = "全体".equals(rolesRadioButton.getValue()) || usersData.display_name().equals(rolesRadioButton.getValue());
            return roleMatches;
        }
    }
}
