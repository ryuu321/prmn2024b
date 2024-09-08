package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import jp.ac.chitose.ir.application.service.management.RoleService;

import java.util.Set;

public class UserManagementTextFields extends VerticalLayout {
    private CheckboxGroup<String> rolesCheckboxGroup;
    private TextField loginIDTextField;
    private TextField userNameTextField;
    private TextField userPasswordTextField;
    private final RoleService roleService;

    public UserManagementTextFields(RoleService roleService) {
        this.roleService = roleService;
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

        rolesCheckboxGroup.setItems(roleService.getRoles());
        add(rolesCheckboxGroup);
    }

    // 各種コンポーネントの追加
    private void addComponentsToLayout() {
        FormLayout formLayout = new FormLayout(loginIDTextField, userNameTextField, userPasswordTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        add(rolesCheckboxGroup);
    }

    // 各コンポーネントから値を取得するメソッド
    public String getLoginID() {
        return loginIDTextField.getValue();
    }

    public String getUserName() {
        return userNameTextField.getValue();
    }

    public String getUserPassword() {
        return userPasswordTextField.getValue();
    }

    public Set<String> getRoles() {
        return rolesCheckboxGroup.getSelectedItems();
    }
}
