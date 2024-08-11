package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        this.userBulkAddButton = new Button("一括追加", new Icon(VaadinIcon.PLUS));
        userBulkAddButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        userBulkAddButton.setAutofocus(true);

        // ユーザーの追加ボタン
        this.userAddButton = new Button("追加", new Icon(VaadinIcon.PLUS));
        userAddButton.setAutofocus(true);

        // ユーザーの削除ボタン
        this.userDeleteButton = new Button("削除", new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management/delete");
        });
        userDeleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);


    }

    // 各種コンポーネントを画面に追加
    private void addComponentsToLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout(userBulkAddButton,
                userAddButton, userDeleteButton);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        add(buttonLayout);
    }
}
