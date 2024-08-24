package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.application.service.usermanagement.UserManagementService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

// ユーザー管理画面のTop画面
@PageTitle("UserManagementTop")
@Route(value = "/user_management", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserManagementTopView extends VerticalLayout {
    private final UserManagementService userManagementService;
    private final UserManagementButtons userManagementButtons;
    private final UsersDataGrid usersDataGrid;


    public UserManagementTopView(UserManagementService userManagementService) {
        add(new H1("ユーザー管理画面"));
        this.userManagementService = userManagementService;
        userManagementButtons = new UserManagementButtons();
        usersDataGrid = new UsersDataGrid(this.userManagementService, UsersDataGrid.SelectionMode.SINGLE);
        add(userManagementButtons, usersDataGrid);
    }
}
