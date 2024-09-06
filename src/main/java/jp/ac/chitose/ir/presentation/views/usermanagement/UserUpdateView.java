package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.application.service.management.UsersData;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.util.Set;

@PageTitle("UserUpdate")
@Route(value = "/user_management/update", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserUpdateView extends VerticalLayout {
    private TextField loginIDTextField;
    private TextField userNameTextField;
    private TextField userPasswordTextField;
    private CheckboxGroup<String> rolesCheckboxGroup;
    private Button updateAccount;
    private Button cancelButton;

    public UserUpdateView() {
        initializeTextField();
        initializeButton();
        initializeCheckBox();
        addComponents();

        // 選択したユーザーの情報を取得し、テキストフィールドに格納
        UsersData usersData = (UsersData) UI.getCurrent().getSession().getAttribute(UsersData.class);
        if (usersData != null) {
            loginIDTextField.setValue(usersData.login_id());
            userNameTextField.setValue(usersData.user_name());
            userPasswordTextField.setValue(usersData.password());
        }
    }

    // テキストフィールドの初期化
    private void initializeTextField() {
        loginIDTextField = new TextField("ログインID");
        userNameTextField = new TextField("ユーザーネーム");
        userPasswordTextField = new TextField("パスワード");
    }

    private void initializeCheckBox() {
        rolesCheckboxGroup = new CheckboxGroup<>();
        rolesCheckboxGroup.setLabel("権限");
        rolesCheckboxGroup.setItems("システム管理者", "IR委員会メンバー", "教員", "学生");
        add(rolesCheckboxGroup);
    }

    // ボタンの初期設定
    private void initializeButton() {
        updateAccount = new Button("変更", new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
            // ここでDBとやりとりするための情報を取得している(サービスが出来たらデータを引き渡す)
            String loginID = loginIDTextField.getValue();
            String userName = userNameTextField.getValue();
            String password = userPasswordTextField.getValue();
            Set<String> selectedRoles = rolesCheckboxGroup.getValue();

        });
        updateAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(cancelButton);
        add(new H1("ユーザーの情報変更"), new Paragraph("ユーザーの情報を変更することができます。変更したい情報を入力してください。"));
        FormLayout formLayout = new FormLayout(loginIDTextField, userNameTextField, userPasswordTextField, rolesCheckboxGroup);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        add(updateAccount);
    }

}
