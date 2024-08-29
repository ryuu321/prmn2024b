package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PageTitle("UserUpdate")
@Route(value = "/user_management/update", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserUpdateView extends VerticalLayout {
    public UserUpdateView() {}
}
