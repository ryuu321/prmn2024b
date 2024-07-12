package jp.ac.chitose.ir.presentation.views.usermanagement;

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
    private final UsersDataGrid usersDataGrid;

    public UserManagementTopView(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
        usersDataGrid = new UsersDataGrid(userManagementService);
        add(usersDataGrid);
    }
}
