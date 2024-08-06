package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserManagementButtons extends VerticalLayout {
    private Button userBulkAddButton;
    private Button userAddButton;
    private Button userDeleteButton;

    public UserManagementButtons() {
        initializeButtons();
        addComponentsToLayout();
    }

    private void initializeButtons() {
        // ユーザーの一括追加ボタン
        this.userBulkAddButton = new Button("一括追加");
        userBulkAddButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        userBulkAddButton.setAutofocus(true);

        // ユーザーの追加ボタン
        this.userAddButton = new Button("追加");
        userAddButton.setAutofocus(true);

        // ユーザーの削除ボタン
        this.userDeleteButton = new Button("削除");
        userDeleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    // 各種コンポーネントを画面に追加
    private void addComponentsToLayout() {
        add(userBulkAddButton, userAddButton, userDeleteButton);
    }
}
