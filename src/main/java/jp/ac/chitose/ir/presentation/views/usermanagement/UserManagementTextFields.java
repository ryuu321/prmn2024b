package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UserManagementTextFields extends VerticalLayout {
    private CheckboxGroup<String> rolesCheckboxGroup;
    private TextField loginIDTextField;
    private TextField userNameTextField;
    private TextField userPasswordTextField;

    public UserManagementTextFields() {
        initializeTextField();
        initializeCheckBox();
        addComponentsToLayout();
    }


    // テキストフィールドの初期化
    private void initializeTextField() {
        loginIDTextField = new TextField("ログインID");
        userNameTextField = new TextField("ユーザーネーム");
        userPasswordTextField = new TextField("パスワード");
    }

    // チェックボックスの初期化
    private void initializeCheckBox() {
        rolesCheckboxGroup = new CheckboxGroup<>();
        rolesCheckboxGroup.setLabel("権限");
        // ロールIDも保持できるか
        rolesCheckboxGroup.setItems("システム管理者", "IR委員会メンバー", "教員", "学生");
        add(rolesCheckboxGroup);
    }

    // 各種コンポーネントの追加
    private void addComponentsToLayout() {
        FormLayout formLayout = new FormLayout(loginIDTextField, userNameTextField, userPasswordTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        add(rolesCheckboxGroup);
    }
}
