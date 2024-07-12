package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserManagementButtons extends VerticalLayout {
    public UserManagementButtons() {
    }

    private void InitializeButtons() {
        // ユーザーの一括追加ボタン
        Button userBulkAddButton = new Button("一括追加");
        userBulkAddButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        userBulkAddButton.setAutofocus(true);

        // ユーザーの追加ボタン
        Button userAddButton = new Button("Secondary");
        userAddButton.setAutofocus(true);

        // ユーザーの削除ボタン
        Button userDeleteButton = new Button("Secondary");
        userDeleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }
}
