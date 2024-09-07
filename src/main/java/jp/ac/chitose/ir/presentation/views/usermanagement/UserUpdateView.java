package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.application.exception.UserManagementException;
import jp.ac.chitose.ir.application.service.management.User;
import jp.ac.chitose.ir.application.service.management.UsersData;
import jp.ac.chitose.ir.application.service.management.UsersService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.notification.ErrorNotification;
import jp.ac.chitose.ir.presentation.component.notification.SuccessNotification;

import java.util.Set;

@PageTitle("UserUpdate")
@Route(value = "/user_management/update", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserUpdateView extends VerticalLayout {
    private final UsersService usersService;
    private TextField loginIDTextField;
    private TextField userNameTextField;
    private TextField userPasswordTextField;
    private CheckboxGroup<String> rolesCheckboxGroup;
    private Grid<UsersData> targetUserGrid;
    private Button updateAccount;
    private Button cancelButton;
    private final UsersData targetUser;
    private UserManagementTextFields userManagementTextFields;

    public UserUpdateView(UsersService usersService) {
        this.usersService = usersService;

        // 選択したユーザーの情報を取得
        this.targetUser = (UsersData) UI.getCurrent().getSession().getAttribute(UsersData.class);

        userManagementTextFields = new UserManagementTextFields();
        initializeGrid();
        initializeButton();
        addComponents();

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
        // ロールIDも保持できるか
        rolesCheckboxGroup.setItems("システム管理者", "IR委員会メンバー", "教員", "学生");
        add(rolesCheckboxGroup);
    }

    // ボタンの初期設定
    private void initializeButton() {
        updateAccount = new Button("変更", buttonClickEvent -> {

            String newLoginID = loginIDTextField.getValue();
            String newUserName = userNameTextField.getValue();
            String newPassword = userPasswordTextField.getValue();
            Set<String> newRoles = rolesCheckboxGroup.getValue();

            // レコードが混在している（UsersDataとUser）のでキャストしている。統一したい。
            User castedtargetUser = new User(targetUser.id(), targetUser.login_id(), targetUser.user_name(), targetUser.is_available());
            try {
                usersService.updateUser(castedtargetUser, newLoginID, newUserName, newPassword, newRoles);
                new SuccessNotification(targetUser.user_name() + "さんの情報を変更しました");
                //todo 変更した情報が確認できるようにする（gridの情報を更新）
                UI.getCurrent().navigate("/user_management");
            } catch (UserManagementException e) {
                if (e.getMessage().isEmpty()) new ErrorNotification("エラーが発生しました");
                else new ErrorNotification(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
                new ErrorNotification("エラーが発生しました");
            }
        });
        updateAccount.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // グリッドの初期化
    private void initializeGrid() {
        targetUserGrid = new Grid<>(UsersData.class, false);
        targetUserGrid.addColumn(UsersData::login_id).setHeader("ログインID");
        targetUserGrid.addColumn(UsersData::user_name).setHeader("ユーザーネーム");
        targetUserGrid.addColumn(UsersData::display_name).setHeader("ロール");
        targetUserGrid.setHeight("100px");
        targetUserGrid.setWidthFull();
        targetUserGrid.setItems(targetUser);

    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(new H1("ユーザーの情報変更"), new Paragraph("ユーザーの情報を変更することができます。変更したい情報のみ入力してください。\n入力があった情報のみ変更されます。また、パスワードは12文字以上で設定してください。"));
        add(cancelButton);
        add(targetUserGrid);
        add(userManagementTextFields);
        add(updateAccount);
    }

}
