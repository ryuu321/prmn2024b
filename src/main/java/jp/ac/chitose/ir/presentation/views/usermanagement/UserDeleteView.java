package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.application.service.usermanagement.UserManagementService;
import jp.ac.chitose.ir.application.service.usermanagement.UsersData;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.util.Set;

@PageTitle("UserDelete")
@Route(value = "/user_management/delete", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserDeleteView extends VerticalLayout {
    private final UserManagementService userManagementService;
    private Button deleteAccount;
    private Button cancelButton;
    private final UsersDataGrid usersDataGrid;

    // コンストラクタ
    public UserDeleteView(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
        initializeButton();
        usersDataGrid = new UsersDataGrid(this.userManagementService, UsersDataGrid.SelectionMode.MULTI);
        addComponents();
    }


    // ボタンの初期設定
    private void initializeButton() {
        deleteAccount = new Button("削除", new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
            // 選択されているユーザーの情報を取得
            Set<UsersData> selectedUsers = usersDataGrid.getGrid().getSelectedItems();

            // ここでDBとやりとりするための情報を取得している(サービスが出来たらデータを引き渡す)


        });
        deleteAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(cancelButton);
        add(new H1("ユーザーの削除"), new Paragraph("ユーザーを削除することができます。削除したいユーザーのユーザIDとユーザーネームを入力してください。"));
        add(usersDataGrid);
        add(deleteAccount);
    }
}
